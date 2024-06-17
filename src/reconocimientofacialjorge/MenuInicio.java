/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static reconocimientofacialjorge.InterfazGrafica.frame;
import static reconocimientofacialjorge.InterfazGrafica.panelGeneral;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jorge Areal Alberich
 */
public class MenuInicio {

    // Elementos del menu de inicio
    private JLabel fondo;
    private JLabel letreroTitulo;
    private JLabel creditos;
    private JLabel botonEmpezar;
    private JLabel botonCerrar;

    // Constructor: llama a los métodos que se encargan de construir la interfaz grafica del menu de inicio
    // Si el parámetro cargarFrame es "true", inicializará el frame y el panel general, y si es false no lo hará.
    // Esto sirve para que cuando se retroceda al menu de inicio desde otros puntos del programa no vuelva a cargar el frame que ya esta cargado de antes
    public MenuInicio(boolean cargarFrame) {
        if (cargarFrame) {
            configurarFrame();
            configurarPaneles();
        }
        colocarElementos();

        frame.setVisible(true);
        panelGeneral.revalidate();
        panelGeneral.repaint();
    }

    // Inicializa el frame con el que se va a trabajar a lo largo del programa y le da propiedades, tamaño, entre otros.
    private void configurarFrame() {
        frame = new JFrame("Reconocimiento Facial Jorge");
        frame.setSize(1280, 720); // Tamaño del JFrame
        frame.setLayout(null);
        frame.setLocationRelativeTo(null); // Centro la ventana

        // Inicializo la variable que contiene la imagen del icono de la base de datos
        ImageIcon iconoPrincipal = new ImageIcon("recursos\\logo2.png");
        // Aplico la variable que contiene la imagen del icono y la pongo como icono del frame
        frame.setIconImage(iconoPrincipal.getImage()); // Colocar logo utilizando la variable icono creada anteriormente
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicializa los paneles que se va a utilizar a lo largo del programa, en este caso, el panel general
    // Se le quita el layout y tiene el mismo tamaño que el frame
    private void configurarPaneles() {
        panelGeneral = new JPanel(null);
        panelGeneral.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(InterfazGrafica.panelGeneral);
    }

    // Inicia los elementos (labels) de toda la interfaz, como el fondo, botones, etc
    // Atribuye parametros de posicion y tamaño para cada elemento y luego los agrega al panel
    private void colocarElementos() {
        fondo = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/portada.gif", fondo, 0, 0, panelGeneral.getWidth(), panelGeneral.getHeight());
        botonCerrar = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/cerrar_button.png", botonCerrar, 5, 3, 80, 80);
        letreroTitulo = new JLabel();
        int xLetrero = (panelGeneral.getWidth() - 600) / 2; // Fórmula para centrar al completo el letrero del titulo 
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/textos/titulo.png", letreroTitulo, xLetrero, 15, 600, 150);
        creditos = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/textos/creditos.png", creditos, 770, 583, 490, 150);
        botonEmpezar = new JLabel();
        int xBotonEmpezar = (panelGeneral.getWidth() - 380) / 2; // Fórmula para centrar al completo el boton comenzar
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/comenzar_button.png", botonEmpezar, xBotonEmpezar, 280, 380, 120);

        // Agregar elementos al panel
        panelGeneral.add(botonCerrar);
        panelGeneral.add(letreroTitulo);
        panelGeneral.add(creditos);
        panelGeneral.add(botonEmpezar);
        panelGeneral.add(fondo);
        // Llamar al método que se encarga de atribuir los comportamientos de cada botón
        gestionarFuncionalidadesBotones();
    }

    // Habilita funciones para cada boton para cuando se pone el cursor encima, para cuando se quita el cursor, y para cuando se le da click
    // Este método es llamado desde el método "colocarElementos()"
    private void gestionarFuncionalidadesBotones() {
        // BOTON CERRAR
        botonCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/cerrar_button_hover.png", botonCerrar, 5, 3, 80, 80);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/cerrar_button.png", botonCerrar, 5, 3, 80, 80);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Cerrar aplicación
                System.exit(0);
            }
        });
        // BOTON COMENZAR
        botonEmpezar.addMouseListener(new MouseAdapter() {
            int xBotonEmpezar = (panelGeneral.getWidth() - 380) / 2; // Fórmula para centrar al completo el boton comenzar

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/comenzar_button_hover.png", botonEmpezar, xBotonEmpezar, 280, 380, 120);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/comenzar_button.png", botonEmpezar, xBotonEmpezar, 280, 380, 120);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Si no hay conexion con la base de datos, no permitirá avanzar al menú princial.
                if (ConexionBBDD.comprobarConexionTabla("conocidos")) {
                    // Entrar al Menu Principal
                    panelGeneral.removeAll(); // Limpiar pantalla actual
                    MenuPrincipal menuPrincipal = new MenuPrincipal();
                } else {
                    JOptionPane.showMessageDialog(panelGeneral, "No se ha podido establecer conexion con la Base de Datos. Comprueba tu conexion a internet", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
