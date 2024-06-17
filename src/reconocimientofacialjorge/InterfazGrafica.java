/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.awt.Frame;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jorge Areal Alberich
 */
public class InterfazGrafica {
    // Variables Interfaz Grafica generales y accesibles para todo el programa
    public static JFrame frame;
    public static JPanel panelGeneral;
    
    // Este método es llamado para cambiar el tamaño a cualquier imagen que le llegue por parametros
    // Recibe urlImagen, label que se quiere adaptar, posicion y tamaño del label en cuestion
    public static void calcularNuevoTamanioImagen(String urlImagen, JLabel labelAVonvertir, int x, int y, int width, int height) {
        // Actualizar el tamaño del label proporcionalmente al tamaño de la ventana
        ImageIcon icono = new ImageIcon(urlImagen);
        Image imagen = icono.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        labelAVonvertir.setIcon(new ImageIcon(imagen));
        labelAVonvertir.setBounds(x, y, width, height);
    }
    
    // Método para verificar si un Frame con el título especificado ya está abierto
    public static boolean isFrameAbierto(String title) {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame.getTitle().equals(title) && frame.isShowing()) {
                return true;
            }
        }
        return false;
    }
}
