/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Jorge Areal Alberich
 */
public class MenuBuscaPersonaPorImagen extends JFrame {
    
    private JPanel panel;
    
    private JLabel fotoAdorno;
    private JLabel textoInstrucciones;
    private JLabel textoNombre;
    private JLabel selectorFotoButton;
    private JLabel botonEjecutar;
    private JLabel tickVerde;
    
    private String imagenSeleccionada;
    private String nombre; // Nombre del conocido seleccionado

    public MenuBuscaPersonaPorImagen(String nombre) {
        this.nombre = nombre;
        imagenSeleccionada = ""; // Inicio la variable con "" para que no sea nula y pueda gestionar mas facilmente los errores
        configurarFrame();
        configurarPanel();
        colocarElementos();
        
        setVisible(true);
    }
    
    private void configurarFrame(){
        setTitle("Buscar Persona dentro de una Imagen");
        setSize(600, 500); // Tamaño del JFrame
        setLayout(null);
        setLocationRelativeTo(null); // Centro la ventana

        // Inicializo la variable que contiene la imagen del icono de la base de datos
        ImageIcon iconoPrincipal = new ImageIcon("recursos\\logo2.png");
        // Aplico la variable que contiene la imagen del icono y la pongo como icono del frame
        setIconImage(iconoPrincipal.getImage()); // Colocar logo utilizando la variable icono creada anteriormente
        setResizable(false);
    }
    
    private void configurarPanel(){
        panel = new JPanel(null);
        panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(panel);
    }
    
    // Inicializar cada label y boton que se va a colocar en el panel
    private void colocarElementos() {
        // Foto que se pone a la izquierda
        fotoAdorno = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/barcenas.jpg", fotoAdorno, 20, 20, 230, 230);
        
        textoInstrucciones = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/textos/texto_imagen_individual.png", textoInstrucciones, 300, -10, 230, 200);
        
        textoNombre = new JLabel(nombre);
        //Font fuenteListaNombres = new Font("IMPACT", Font.ROMAN_BASELINE, 20);
        textoNombre.setFont(new Font("IMPACT", Font.ROMAN_BASELINE, 32));
        textoNombre.setForeground(Color.BLACK);
        textoNombre.setBounds(380,160, 200, 40);

        // Boton de seleccionar archivo
        selectorFotoButton = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_imagen_button.jpg", selectorFotoButton, 320, 210, 200, 50);

        // Boton de enviar
        botonEjecutar = new JLabel();
        int xBotonEnviar = (panel.getWidth() - 220) / 2; // Fórmula para centrar el botón enviar
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/ejecutar_button.jpg", botonEjecutar, xBotonEnviar, 360, 220, 60);

        // Tick verde para cuando se seleccione una imagen
        tickVerde = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/tick.png", tickVerde, 530, 165, 40, 40);
        tickVerde.setVisible(false);

        // Agregar elementos al panel
        panel.add(fotoAdorno);
        panel.add(textoInstrucciones);
        panel.add(textoNombre);
        panel.add(selectorFotoButton);
        panel.add(botonEjecutar);
        panel.add(tickVerde);

        // Llamar a la funcion que se encarga de ponerle funciones a los botones
        gestionarFuncionalidadesBotones();
    }
    
    
    // Gestiona la utilidad de los botones de este frame
    private void gestionarFuncionalidadesBotones() {
        // BOTON SELECCIONAR DIRECTORIO
        selectorFotoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_imagen_button_hover.jpg", selectorFotoButton, 320, 210, 200, 50);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_imagen_button.jpg", selectorFotoButton, 320, 210, 200, 50);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFoto();
            }
        });

        // BOTON ENVIAR
        botonEjecutar.addMouseListener(new MouseAdapter() {
            int xBotonEnviar = (panel.getWidth() - 220) / 2; // Fórmula para centrar el botón enviar

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/ejecutar_button_hover.jpg", botonEjecutar, xBotonEnviar, 360, 220, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/ejecutar_button.jpg", botonEjecutar, xBotonEnviar, 360, 220, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Si hay conexion a la base de datos y los campos no estan vacios, proceder a llamar al programa de python
                if (!imagenSeleccionada.isEmpty() && ConexionBBDD.comprobarConexionTabla("conocidos")) {
                    // Hacer la consulta al programa de python
                    IntegracionPython.ejecutarBuscarIndividual(imagenSeleccionada, nombre, "imagen");
                } else if (!ConexionBBDD.comprobarConexionTabla("conocidos")) {
                    JOptionPane.showMessageDialog(null, "No se ha podido establecer conexion con la Base de Datos. Comprueba tu conexion a internet", "Error", JOptionPane.ERROR_MESSAGE);
                    // Avisar de que los campos no estan llenos
                } else {
                    JOptionPane.showMessageDialog(null, "¡Debes seleccionar la imagen!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    // Método que abre el file chooser y filtra los archivos para que solamente se puedan escoger imagenes
    private void seleccionarFoto() {
        JFileChooser fileChooser = new JFileChooser();

        // Filtro para archivos PNG y JPG
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String fileName = file.getName().toLowerCase();
                return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
            }

            @Override
            public String getDescription() {
                return "Archivos de imagen (*.png, *.jpg, *.jpeg)";
            }
        });

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = fileChooser.getSelectedFile().getPath();
            JOptionPane.showMessageDialog(null, "Imagen seleccionada: " + imagenSeleccionada);
            tickVerde.setVisible(true);
        }
    }
}
