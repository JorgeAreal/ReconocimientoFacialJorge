/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jorge Areal Alberich
 */
public class MenuBuscarTodoPorDirectorio extends JFrame {

    private JPanel panel;

    private JLabel fondoMenuBuscarTodosPorDireccion;
    private JLabel botonSeleccionarDirectorio;
    private JLabel botonEjecutarPython;
    private String directorioSeleccionado;

    // Constructor: llama a todos los métodos encargados de construir la interfaz grafica
    public MenuBuscarTodoPorDirectorio() {
        configurarFrame();
        configurarPanel();
        colocarElementos();

        setVisible(true);
    }

    private void configurarFrame() {
        setTitle("Buscar Todo por Directorio");
        setSize(600, 400); // Tamaño del JFrame
        setLayout(null);
        setLocationRelativeTo(null); // Centro la ventana

        // Inicializo la variable que contiene la imagen del icono de la base de datos
        ImageIcon iconoPrincipal = new ImageIcon("recursos\\logo2.png");
        // Aplico la variable que contiene la imagen del icono y la pongo como icono del frame
        setIconImage(iconoPrincipal.getImage()); // Colocar logo utilizando la variable icono creada anteriormente
        setResizable(false);
    }

    private void configurarPanel() {
        panel = new JPanel(null);
        panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        panel.setBackground(Color.WHITE);
        this.add(panel);
    }

    private void colocarElementos() {
        fondoMenuBuscarTodosPorDireccion = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/buscarDirectorioPorTodo.png", fondoMenuBuscarTodosPorDireccion, 0, 0, panel.getWidth(), panel.getHeight());

        // Botón para seleccionar directorio
        botonSeleccionarDirectorio = new JLabel();
        int xBotones = (panel.getWidth() - 200)/2;
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_directorio_button.jpg", botonSeleccionarDirectorio, xBotones, 205, 200, 60);

        // Botón para ejecutar el programa Python
        botonEjecutarPython = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/ejecutar_button.jpg", botonEjecutarPython, xBotones, 280, 200, 60);

        // Agregar los elementos al panel de este frame
        panel.add(botonSeleccionarDirectorio);
        panel.add(botonEjecutarPython);
        panel.add(fondoMenuBuscarTodosPorDireccion);

        gestionarFuncionalidadesBotones();
    }

    private void gestionarFuncionalidadesBotones() {
        int xBotones = (panel.getWidth() - 200)/2;
        // BOTON SELECCIONAR DIRECTORIO
        botonSeleccionarDirectorio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_directorio_button_hover.jpg", botonSeleccionarDirectorio, xBotones, 205, 200, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_directorio_button.jpg", botonSeleccionarDirectorio, xBotones, 205, 200, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarDirectorio();
            }
        });
        // BOTON EJECUTAR
        botonEjecutarPython.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/ejecutar_button_hover.jpg", botonEjecutarPython, xBotones, 280, 200, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/ejecutar_button.jpg", botonEjecutarPython, xBotones, 280, 200, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(ConexionBBDD.comprobarConexionTabla("conocidos")){
                    IntegracionPython.ejecutarBuscarTodo(directorioSeleccionado, "direccion");
                } else {
                    JOptionPane.showMessageDialog(panel, "No se ha podido establecer conexion con la Base de Datos. Comprueba tu conexion a internet", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Método que abre el explorador de archivos y almacena en la variable "directorioSeleccionado" el directorio seleccionado por el usuario dentro del explorador
    private void seleccionarDirectorio() {
        JFileChooser selectorDirectorio = new JFileChooser();
        selectorDirectorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = selectorDirectorio.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            directorioSeleccionado = selectorDirectorio.getSelectedFile().getPath();
            JOptionPane.showMessageDialog(null, "Directorio seleccionado: " + directorioSeleccionado);
        }
    }
}
