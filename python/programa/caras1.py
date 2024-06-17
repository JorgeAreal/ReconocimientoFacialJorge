import cv2

cascada_rostro = cv2.CascadeClassifier('haarcascade_frontalface_alt.xml')

img = cv2.imread("awy2.png")
img_gris = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)

coordenas_rostros = cascada_rostro.detectMultiScale(img_gris,1.3,5)

# Mostrar las coordenadas encontradas
print(coordenas_rostros)

for (x,y,ancho,alto) in coordenas_rostros:
    cv2.rectangle(img,(x,y), (x+ancho,y+alto), (255,0,0),2)

cv2.imshow('Salida',img)

cv2.waitKey(0)
cv2.destroyAllWindows()