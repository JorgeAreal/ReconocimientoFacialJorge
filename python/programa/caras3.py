import cv2
import face_recognition

imagen_jorge = face_recognition.load_image_file("jorge.jpg")
imagen_adrian = face_recognition.load_image_file("adrian.jpg")

jorge_encodings = face_recognition.face_encodings(imagen_jorge)[0]
adrian_encodings = face_recognition.face_encodings(imagen_adrian)[0]

print(jorge_encodings)

encodings_conocidos = [
    jorge_encodings,
    adrian_encodings
]

nombres_conocidos = [
    "Jorge",
    "Adrián"
]

font = cv2.FONT_HERSHEY_SIMPLEX

img = face_recognition.load_image_file("awy2.png")

loc_rostros = []
encodings_rostros = []
nombres_rostros = []

loc_rostros = face_recognition.face_locations(img)
encodings_rostros = face_recognition.face_encodings(img, loc_rostros)

for encoding in encodings_rostros:
    coincidencias = face_recognition.compare_faces(encodings_conocidos,encoding)
    print(coincidencias)
    if True in coincidencias:
        nombre = nombres_conocidos[coincidencias.index(True)]
    else:
        nombre = "???"
    nombres_rostros.append(nombre)

for (top, right, bottom, left), nombre  in zip(loc_rostros,nombres_rostros):
    
    if nombre != "???":
        color = (0,255,0)
    else:
        color = (0,0,255)

    cv2.rectangle(img, (left,top),(right,bottom),color,2)
    cv2.rectangle(img,(left, bottom - 20),(right, bottom),color,1)

    cv2.putText(img,nombre,(left, bottom - 6), font , 0.6, color , 1)

cv2.imshow("Ventana",img)

cv2.waitKey(0)
cv2.destroyAllWindows()