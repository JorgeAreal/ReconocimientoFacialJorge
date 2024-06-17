/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import static reconocimientofacialjorge.InterfazGrafica.panelGeneral;

/**
 *
 * @author Jorge Areal Alberich
 */
public class MenuAgregarConocido extends JFrame {

    private JPanel panelAgregarConocido;

    private JLabel fotoReconocimiento;
    private JTextField nombreTextField;
    private JButton selectorArchivosButton;
    private JLabel botonEnviar;
    private String direccionImagen;
    private JLabel tickVerde;

    // Constructor: llama a los métodos encargados de construir la interfaz gráfica
    public MenuAgregarConocido() {
        configurarFrame();
        configurarPanel();
        colocarElementos();

        direccionImagen = ""; // Poner las comillas de esa manera hacer que la cadena no sea nula, y por tanto puede solucionar bugs
        setVisible(true);
    }

    // Atribuir propiedades al pequeño frame creado
    private void configurarFrame() {
        setTitle("Agregar Conocido");
        setSize(600, 450); // Tamaño del JFrame
        setLayout(null);
        setLocationRelativeTo(null); // Centro la ventana

        // Inicializo la variable que contiene la imagen del icono de la base de datos
        ImageIcon iconoPrincipal = new ImageIcon("recursos\\logo2.png");
        // Aplico la variable que contiene la imagen del icono y la pongo como icono del frame
        setIconImage(iconoPrincipal.getImage()); // Colocar logo utilizando la variable icono creada anteriormente
        setResizable(false);
    }

    // Configura el panel que se va a colocar en este frame
    private void configurarPanel() {
        panelAgregarConocido = new JPanel(null);
        panelAgregarConocido.setBounds(0, 0, this.getWidth(), this.getHeight());
        panelAgregarConocido.setBackground(Color.WHITE);
        this.add(panelAgregarConocido);
    }

    // Inicializar cada label y boton que se va a colocar en el panel
    private void colocarElementos() {
        // Foto que se pone a la izquierda
        fotoReconocimiento = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/logo2.png", fotoReconocimiento, 20, 20, 230, 230);

        // Etiqueta de texto que dice "nombre"
        JLabel textoNombre = new JLabel("Nombre del nuevo conocido");
        textoNombre.setBounds(260, 35, 200, 30);

        // Campo de texto para escribir el nombre de la persona a agregar
        nombreTextField = new JTextField();
        nombreTextField.setBounds(260, 65, 300, 30);
        nombreTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        nombreTextField.setForeground(Color.BLACK);

        // Boton de seleccionar archivo
        selectorArchivosButton = new JButton("Seleccionar Imagen");
        selectorArchivosButton.setBounds(260, 115, 150, 30);
        selectorArchivosButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // Boton de enviar
        botonEnviar = new JLabel();
        int xBotonEnviar = (panelAgregarConocido.getWidth() - 220) / 2; // Fórmula para centrar el botón enviar
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_enviar.jpg", botonEnviar, xBotonEnviar, 300, 220, 60);

        // Tick verde para cuando se seleccione una imagen
        tickVerde = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/tick.png", tickVerde, 420, 112, 40, 40);
        tickVerde.setVisible(false);

        // Agregar elementos al panel
        panelAgregarConocido.add(fotoReconocimiento);
        panelAgregarConocido.add(textoNombre);
        panelAgregarConocido.add(nombreTextField);
        panelAgregarConocido.add(selectorArchivosButton);
        panelAgregarConocido.add(botonEnviar);
        panelAgregarConocido.add(tickVerde);

        // Llamar a la funcion que se encarga de ponerle funciones a los botones
        gestionarFuncionalidadesBotones();
    }

    // Gestiona la utilidad de los botones de este frame
    private void gestionarFuncionalidadesBotones() {
        // BOTON SELECCIONAR DIRECTORIO
        selectorArchivosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    direccionImagen = fileChooser.getSelectedFile().getPath();
                    JOptionPane.showMessageDialog(null, "Imagen seleccionada: " + direccionImagen);
                    tickVerde.setVisible(true);
                }
            }
        });
        // BOTON ENVIAR
        botonEnviar.addMouseListener(new MouseAdapter() {
            int xBotonEnviar = (panelAgregarConocido.getWidth() - 220) / 2; // Fórmula para centrar el botón enviar

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_enviar_hover.jpg", botonEnviar, xBotonEnviar, 300, 220, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_enviar.jpg", botonEnviar, xBotonEnviar, 300, 220, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Si hay conexion a la base de datos y los campos no estan vacios, proceder a llamar al programa de python
                if (!nombreTextField.getText().isEmpty() && !direccionImagen.isEmpty() && ConexionBBDD.comprobarConexionTabla("conocidos")) {
                    try {
                        // Hacer la consulta al programa de python
                        IntegracionPython.agregarConocido(nombreTextField.getText(), direccionImagen);
                        dispose(); // Si llegamos a este punto la persona se ha registrado con exito
                        // Informar al usuario de que se ha registrado la persona
                        JOptionPane.showMessageDialog(null, nombreTextField.getText() + " ha sido registrado con éxito en nuestros servidores");
                        panelGeneral.removeAll();
                        MenuPrincipal menuPrincipal = new MenuPrincipal();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Ha habido un error registrando la persona. Inténtalo de nuevo mas tarde :D", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    // Si no hay conexion a la base de datos no hacer la llamada
                } else if (!ConexionBBDD.comprobarConexionTabla("conocidos")) {
                    JOptionPane.showMessageDialog(null, "No se ha podido establecer conexion con la Base de Datos. Comprueba tu conexion a internet", "Error", JOptionPane.ERROR_MESSAGE);
                    // Avisar de que los campos no estan llenos
                } else {
                    JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
