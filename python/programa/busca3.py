import MySQLdb
import face_recognition
import sys
import os
import io
import json
import numpy
import argparse
from rich.progress import Progress
from rich import print
from rich.text import Text
from rich.panel import Panel

def buscaDirectorio(directorio):
    contenido = os.listdir(directorio)
    for fichero in contenido:
        if os.path.isfile(os.path.join(directorio,fichero)) and (fichero.endswith(".jpg") or fichero.endswith(".png")):
           imagenes.append(os.path.join(directorio,fichero))
        elif os.path.isdir(os.path.join(directorio,fichero)) and fichero!=".." and fichero!=".":
            buscaDirectorio(os.path.join(directorio,fichero))

parser= argparse.ArgumentParser()

parser.add_argument("-i","--imagen", help="Ubicación y nombre de la imagen")
parser.add_argument("-d","--directorio",help="Ubicación de las fotos")
parser.add_argument("-p","--persona", help="Persona que se quiera buscar")

args=parser.parse_args()

fEscribir = open("salida.txt","w")


imagenes=[]

if args.imagen:
    fichero= args.imagen
    if os.path.isfile(fichero) and (fichero.endswith(".jpg") or fichero.endswith(".png")):
        imagenes.append(fichero)
    else:
        print(Panel("La imagen que has puesto no existe o no es del formato correcto",title="[red]Aviso para inútiles"))
        sys.exit(-1)
elif args.directorio:
    buscaDirectorio(args.directorio)
else:
    print(Panel("Lo que has puesto no es correcto",title="[red]Torpeman"))
    sys.exit(0)


#Conexión a la bbdd
try:
    conn = MySQLdb.connect(
        user="root",
        password="",
        host="127.0.0.1",
        port=3306,
        database="reconocimiento"
    )
except MySQLdb.Error as e:
    print("Error al conectar con el servidor de datos: {e}")

cur = conn.cursor()
if args.persona:
    sql= "select * from conocidos where nombre like '%"+args.persona+"%'"
else:
    sql="select * from conocidos"

cur.execute(sql)

datos=cur.fetchall()

if not(len(datos)>0):
    print("Lo siento no tenemos registros de {args.persona}")
    sys.exit()

encodings_conocidos = []
nombres_conocidos = []

for registro in datos:
    memfile = io.BytesIO()
    memfile.write(json.loads(registro[2]).encode('latin-1'))
    memfile.seek(0)
    a=numpy.load(memfile)
    encodings_conocidos.append(a)
    nombres_conocidos.append(registro[1])

progreso = Progress()
tarea1=progreso.add_task("[red]Analizando imágenes", total = len(imagenes))
progreso.start()

for imagen in imagenes:
    progreso.update(tarea1,advance=1)
    encontrado=0
    try:
        img = face_recognition.load_image_file(imagen)
    except:
        continue
    loc_rostros =[]
    encondings_rostros = []
    nombres_rostros = []
    loc_rostros = face_recognition.face_locations(img)
    encondings_rostros = face_recognition.face_encodings(img,loc_rostros)

    encontrados=""
    for encoding in encondings_rostros:
        coincidencias = face_recognition.compare_faces(encodings_conocidos,encoding)
        if True in coincidencias:
            nombre = nombres_conocidos[coincidencias.index(True)]
            encontrados += nombre + ","
            encontrado += 1
        else:
            nombre = "???"

        nombres_rostros.append(nombre)

    if encontrado>0:
        print(Panel("[green]"+encontrados[0:-1],title="[yellow]"+imagen,subtitle="[cyan]"+str(encontrados.count(","))))
        fEscribir.write(f"Encontrado en la {imagen}:  "+encontrados[0:-1]+"\n")

fEscribir.close()
print("\nPulsa cualquier tecla para terminar")
