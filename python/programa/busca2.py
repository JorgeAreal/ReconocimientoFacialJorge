import MySQLdb
import cv2
import face_recognition
import sys
import os
import io
import json
import numpy
import argparse


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
        print("La imagen que has puesto no existe o no es del formato correcto")
        sys.exit(-1)
elif args.directorio:
    contenido = os.listdir(args.directorio)
    for fichero in contenido:
        if os.path.isfile(os.path.join(args.directorio,fichero)) and (fichero.endswith(".jpg") or fichero.endswith(".png")):
           imagenes.append(os.path.join(args.directorio,fichero))
    #print(imagenes)
else:
    print("Error")
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



devolver=""

for imagen in imagenes:
   
    encontrado=0
    img = face_recognition.load_image_file(imagen)
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

        for (top,rigth,bottom,left), nombre in zip(loc_rostros,nombres_rostros):
            if nombre!="???":
                color = (0,255,0)
            else:
                color = (0,0,255)
            
            

    if encontrado>0:
        devolver+=imagen+":"+encontrados[0:-1]+";"
        #print(Text("En la imagen")+Text(f"{imagen}",style="bold magenta")+Text(" está alguien conocido"))
        fEscribir.write(f"Encontrado en la {imagen}:  "+encontrados[0:-1]+"\n")
    #else:
        #print("En la imagen {imagen} NO está alguien conocido")


fEscribir.close()
print(devolver)
