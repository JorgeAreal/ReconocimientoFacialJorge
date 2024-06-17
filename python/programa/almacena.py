import cv2
import face_recognition
import MySQLdb
import sys
import numpy
import io
import json
import argparse

parser = argparse.ArgumentParser()

parser.add_argument("nombre",help="Nombre de la persona")
parser.add_argument("imagen",help="Ubicación y nombre de la imagen")
args=parser.parse_args()

nombre = args.nombre
imagen = args.imagen

# Cargamos la imagen
imagen_a_procesar = face_recognition.load_image_file(imagen)

# Codificamos la imagen
imagen_encodings = face_recognition.face_encodings(imagen_a_procesar)[0]

memfile = io.BytesIO()
numpy.save(memfile, imagen_encodings)
memfile.seek(0)
serialiazed= json.dumps(memfile.read().decode('latin-1'))

# Conecto con la bbdd
try:
    conn = MySQLdb.connect(
        user="root",
        password="",
        host="127.0.0.1",
        port=3306,
        database = "reconocimiento"
    )
except MySQLdb.Error as e:
    print("Error conectado con la bbdd: {e}")

cur = conn.cursor()
sql = "insert into conocidos values (null,%s,%s)"
datos_guardar = (nombre,serialiazed)

try:
    cur.execute(sql,datos_guardar)
    conn.commit()
except MySQLdb.Error as e:
    try:
        print("MySql error {%d}: %s" % (e.args[0],e.args[1]))
    except IndexError:
        print("MySql error: %s" % str(e))
    except TypeError as e:
        print(e)
    except ValueError as e:
        print(e)

print("Listo....")





