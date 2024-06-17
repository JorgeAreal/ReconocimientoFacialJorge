/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static reconocimientofacialjorge.InterfazGrafica.panelGeneral;

/**
 *
 * @author Jorge Areal Alberich
 */
public class MenuPrincipal {

    // Inicializo los paneles que se van a utilizar dentro del menu principal
    private JPanel panelArribaIzquierda;
    private JPanel panelListaConocidosContenido; // Panel que va a contener los nombres de los conocidos y que va a ser añadido al scrollPane "panelListaConocidos"
    private JScrollPane panelListaConocidos;
    private JPanel panelBotonAgregar;
    private JPanel panelDerecho;

    // Inicializo todos los elementos de la interfaz
    private JLabel lineaDivisoriaVertical;
    private JLabel lineaDivisoriaHorizontalArriba;
    private JLabel lineaDivisoriaHorizontalAbajo;
    private JLabel fondoFogata;
    private JLabel fondoDegradado;
    private JLabel botonSalir;
    private JLabel botonEliminarTodosConocidos;
    private JLabel botonBuscarPorDirectorio;
    private JLabel botonBuscarPorImagen;
    private JLabel botonAgregarConocido;

    private JLabel botonBuscarPorImagenPersonaIndividual;
    private JLabel botonBuscarPorDirectorioPersonaIndividual;
    private JLabel botonEliminarConocido;

    // Matriz donde se van a guardar los nombres de las personas conocidas
    private String[] nombres;
    private int indiceNombreActual; // Este valor va a ser el indice de la lista del nombre que está pulsado en este momento

    // Declarar el frame "MenuAgregarConocido" como atributo de clase para que sea accesible dentro de toda la clase
    // Eso me servirá para que se cierre la ventana de agregar conocido si el usuario le da al boton de salir con el agregar conocido abierto
    private MenuAgregarConocido agregarConocido;
    // Lo mismo para el resto de frames desplegados en el menu
    private MenuBuscarTodoPorDirectorio buscarTodoPorDirectorio;
    private MenuBuscaTodoPorImagen buscarTodoPorImagen;
    private MenuBuscaPersonaPorImagen buscarPersonaDentroDeImagen;
    private MenuBuscaPersonaPorDirectorio buscaPersonaDentroDeDireccion;

    // Constructor: llama a todos los métodos encargados de construir la interfaz grafica
    public MenuPrincipal() {
        configurarPaneles();
        colocarElementos();
        colocarElemntosPanelDerecho();

        panelGeneral.revalidate();
        panelGeneral.repaint();
    }

    // Configura los paneles del menu principal, le da tamaño forma y posicion a cada uno de los paneles
    private void configurarPaneles() {

        // Configurar el panel de arriba a la izquierda, la cual tiene el boton de volver a atras, entre otros
        panelArribaIzquierda = new JPanel(null);
        panelArribaIzquierda.setBounds(0, 0, 320, 80);
        panelArribaIzquierda.setOpaque(false);
        //panelArribaIzquierda.setBackground(Color.RED);

        // Configurar el panel de la izquierda, el cual va a mostrar la lista de personas conocidas
        panelListaConocidosContenido = new JPanel(null);
        panelListaConocidos = new JScrollPane(panelListaConocidosContenido);
        panelListaConocidos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelListaConocidos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelListaConocidos.setBounds(0, 78, 320, 504); // El width deberia ser 320 y el height 504, pero debido a la naturaleza del diseño del ScrollPane obliga a hacerlo un poquito mas grande para que coincida bien con el resto de JPanels
        panelListaConocidosContenido.setOpaque(false);
        panelListaConocidos.setOpaque(false);
        //panelListaConocidos.setBackground(Color.CYAN);

        // Configurar el panel de abajo a la izquierda, la cual va a tener los botones buscarPersonaDentroDeImagen todos, y agregar conocido
        panelBotonAgregar = new JPanel(null);
        panelBotonAgregar.setBounds(0, 580, 320, 102);
        panelBotonAgregar.setOpaque(false);
        //panelBotonAgregar.setBackground(Color.GREEN);

        // Configurar el panel de la derecha, el cual va a mostrar botones de configuracion del conocido
        panelDerecho = new JPanel(null);
        panelDerecho.setOpaque(false);
        panelDerecho.setBounds(320, 0, 954, panelGeneral.getHeight());
        //panelDerecho.setBackground(Color.PINK);

        // Agregar los nuevos paneles al panel general
        panelGeneral.add(panelDerecho);
        panelGeneral.add(panelArribaIzquierda);
        panelGeneral.add(panelBotonAgregar);
        panelGeneral.add(panelListaConocidos);
    }

    // Colocar cada elemento de la interfaz, con su tamaño, posicion, y enlace directorio de la textura
    private void colocarElementos() {
        lineaDivisoriaVertical = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/marcos/linea_divisoria_vertical.png", lineaDivisoriaVertical, 0, 0, 2, panelDerecho.getHeight());
        lineaDivisoriaHorizontalArriba = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/marcos/linea_divisoria_horizontal.png", lineaDivisoriaHorizontalArriba, 0, 78, panelArribaIzquierda.getWidth(), 2);
        lineaDivisoriaHorizontalAbajo = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/marcos/linea_divisoria_horizontal.png", lineaDivisoriaHorizontalAbajo, 0, 0, panelBotonAgregar.getWidth(), 2);
        fondoFogata = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondo5.gif", fondoFogata, 320, 0, panelDerecho.getWidth(), panelDerecho.getHeight());
        fondoDegradado = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondo_panel_izquierda_blanco3.jpg", fondoDegradado, 0, 0, panelArribaIzquierda.getWidth(), panelGeneral.getHeight());
        botonSalir = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_salir.jpg", botonSalir, 5, 11, 60, 60);
        botonBuscarPorDirectorio = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_buscarXurl.jpg", botonBuscarPorDirectorio, 88, 11, 60, 60);
        botonBuscarPorImagen = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_buscarXimagen.jpg", botonBuscarPorImagen, 171, 11, 60, 60);
        botonEliminarTodosConocidos = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_eliminar_conocidos.jpg", botonEliminarTodosConocidos, 254, 11, 60, 60);
        botonAgregarConocido = new JLabel();
        // Fórmulas para centrar horizontal y verticalmente el boton de agregar conocido
        int xBotonAgregar = (panelBotonAgregar.getWidth() - 280) / 2;
        int yBotonAgregar = (panelBotonAgregar.getHeight() - 77) / 2;
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_agregar.jpg", botonAgregarConocido, xBotonAgregar, yBotonAgregar, 280, 77);

        // Colocar los labels dentro de sus paneles
        panelDerecho.add(lineaDivisoriaVertical);
        panelArribaIzquierda.add(lineaDivisoriaHorizontalArriba);
        panelArribaIzquierda.add(botonSalir);
        panelArribaIzquierda.add(botonEliminarTodosConocidos);
        panelArribaIzquierda.add(botonBuscarPorDirectorio);
        panelArribaIzquierda.add(botonBuscarPorImagen);
        panelBotonAgregar.add(lineaDivisoriaHorizontalAbajo);
        panelBotonAgregar.add(botonAgregarConocido);

        panelGeneral.add(fondoDegradado);
        panelGeneral.add(fondoFogata);
        // Rellenar la lista de conocidos
        agregarNombresDeConocidos();
        // Llamar al método que se encarga de dar comportamientos a los botones
        gestionarFuncionalidadesBotones();
    }

    // Extrae de la base de datos los nombres de las personas conocidas
    private void agregarNombresDeConocidos() {
        try {
            // Obtener los nombres de conocidos de la base de datos utilizando el método de la clase ConexionBBDD, y guardarlos en una matriz
            nombres = ConexionBBDD.obtenerValoresColumna("nombre");

            // Fuente con la que se va a mostrar cada nombre dentro de la lista
            Font fuenteListaNombres = new Font("ARIAL", Font.BOLD, 20);

            for (int i = 0; i < nombres.length; i++) {
                JLabel nombreLabel = new JLabel(nombres[i]);
                nombreLabel.setBounds(10, i * 30, 250, 30); // Colocar cada nombre con un espaciado de 30 píxeles

                // Cambiar la fuente, el tamaño y el color del texto
                nombreLabel.setFont(fuenteListaNombres);
                nombreLabel.setForeground(Color.BLACK);

                int indice = i; // Si utilizo la i para ponerle el valor al indiceNombreActual no me deja por alguna razon, por eso lo meto dentro de "indice"
                // MOUSE LISTENER DEL NOMBRE: se cambiará el valor de "indiceNombreActual" cada vez que se pulse encima de un nombre
                nombreLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Refrescar el numero del indice del nombre selecionado
                        indiceNombreActual = indice;
                        panelDerecho.removeAll(); // Eliminar primero todo el contenido del panel para que no se superponga todo
                        colocarElemntosPanelDerecho(); // Colocar botones y titulo del nombre seleccionado en el panel derecho
                    }
                });
                // Agregar el nombre a la lista
                panelListaConocidosContenido.add(nombreLabel);
            }

            // Ajustar el tamaño del panel de contenido del scroll para asegurar que sea lo suficientemente grande
            panelListaConocidosContenido.setPreferredSize(new Dimension(320, nombres.length * 30));

        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con el servidor");
        }
    }

    // Habilita funciones para cada boton para cuando se pone el cursor encima, para cuando se quita el cursor, y para cuando se le da click
    // Este método es llamado desde el método "colocarElementos()"
    private void gestionarFuncionalidadesBotones() {
        // BOTON CERRAR
        botonSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_salir_hover.jpg", botonSalir, 5, 11, 60, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_salir.jpg", botonSalir, 5, 11, 60, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Volver al menu de inicio
                panelGeneral.removeAll(); // Limpiar pantalla actual
                // Llamar al método que se encarga de cerrar todas aquellas ventanas (como la de agregar conocido, etc) para realizar una salida limpia
                limpiarVentanasSecundarias();
                MenuInicio menuInicio = new MenuInicio(false); // Volver a cargar el menú de inicio
            }
        });
        // BOTON BUSCAR POR URL
        botonBuscarPorDirectorio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_buscarXurl_hover.jpg", botonBuscarPorDirectorio, 88, 11, 60, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_buscarXurl.jpg", botonBuscarPorDirectorio, 88, 11, 60, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!InterfazGrafica.isFrameAbierto("Buscar Todo por Directorio")) {
                    buscarTodoPorDirectorio = new MenuBuscarTodoPorDirectorio();
                } else {
                    // Colocar el frame delante
                    buscarTodoPorDirectorio.toFront();
                    buscarTodoPorDirectorio.requestFocus();
                }
            }
        });
        // BOTON BUSCAR POR IMAGEN
        botonBuscarPorImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_buscarXimagen_hover.jpg", botonBuscarPorImagen, 171, 11, 60, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_buscarXimagen.jpg", botonBuscarPorImagen, 171, 11, 60, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!InterfazGrafica.isFrameAbierto("Buscar Todo dentro de una Imagen")) {
                    buscarTodoPorImagen = new MenuBuscaTodoPorImagen();
                } else {
                    // Colocar el frame delante
                    buscarTodoPorImagen.toFront();
                    buscarTodoPorImagen.requestFocus();
                }
            }
        });
        // BOTON ELIMINAR TODOS LOS CONOCIDOS
        botonEliminarTodosConocidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_eliminar_conocidos_hover.jpg", botonEliminarTodosConocidos, 254, 11, 60, 60);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_eliminar_conocidos.jpg", botonEliminarTodosConocidos, 254, 11, 60, 60);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        // BOTON AGREGAR CONOCIDO
        botonAgregarConocido.addMouseListener(new MouseAdapter() {
            // Fórmulas para centrar horizontal y verticalmente el boton de agregar conocido
            int xBotonAgregar = (panelBotonAgregar.getWidth() - 280) / 2;
            int yBotonAgregar = (panelBotonAgregar.getHeight() - 77) / 2;

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_agregar_hover.jpg", botonAgregarConocido, xBotonAgregar, yBotonAgregar, 280, 77);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_agregar.jpg", botonAgregarConocido, xBotonAgregar, yBotonAgregar, 280, 77);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Abrir la ventana solamente si no está abierta. Si está abierta, hacerle focus para superponerlo
                if (!InterfazGrafica.isFrameAbierto("Agregar Conocido")) {
                    // Abrir el frame que corresponde con la agregacion de la persona
                    agregarConocido = new MenuAgregarConocido();
                } else {
                    // Colocar el frame delante
                    agregarConocido.toFront();
                    agregarConocido.requestFocus();
                }
            }
        });
    }

    // Método especializado en colocar los elementos del panel derecho
    private void colocarElemntosPanelDerecho() {
        // Fuente con la que se va a mostrar en grande el nombre del conocido seleccionado
        Font fuenteTituloNombreSeleccionado = new Font("IMPACT", Font.ROMAN_BASELINE, 70);
        // Inicializar y configurar el letrero
        JLabel nombreSeleccionadoTitulo = new JLabel(nombres[indiceNombreActual]);
        nombreSeleccionadoTitulo.setBounds(100, 45, 500, 70);
        nombreSeleccionadoTitulo.setFont(fuenteTituloNombreSeleccionado);
        nombreSeleccionadoTitulo.setForeground(Color.WHITE);
        panelDerecho.add(nombreSeleccionadoTitulo);

        botonBuscarPorImagenPersonaIndividual = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_personaindivisual_button.jpg", botonBuscarPorImagenPersonaIndividual, 100, 150, 300, 80);

        botonBuscarPorDirectorioPersonaIndividual = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_personaindividual_directorio.jpg", botonBuscarPorDirectorioPersonaIndividual, 100, 280, 300, 80);

        botonEliminarConocido = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_eliminar_conocido.jpg", botonEliminarConocido, 100, 410, 300, 80);

        // Agregar elementos al panel
        panelDerecho.add(botonBuscarPorImagenPersonaIndividual);
        panelDerecho.add(botonBuscarPorDirectorioPersonaIndividual);
        panelDerecho.add(botonEliminarConocido);
        // LLamar a la funcion para que le de funciones a los botones
        atribuirFuncionesBotonesPanelDerecho();
    }

    private void atribuirFuncionesBotonesPanelDerecho() {
        // BOTON BUSCAR PERSONA INDIVIDUAL POR IMAGEN
        botonBuscarPorImagenPersonaIndividual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_personaindivisual_button_hover.jpg", botonBuscarPorImagenPersonaIndividual, 100, 150, 300, 80);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_personaindivisual_button.jpg", botonBuscarPorImagenPersonaIndividual, 100, 150, 300, 80);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!InterfazGrafica.isFrameAbierto("Buscar Persona dentro de una Imagen")) {
                    // Llamo a la clase que se encarga del frame de buscar a una persona concreta dentro de una imagen, y le mando el parametro al constructor del nombre que se ha seleccionado
                    buscarPersonaDentroDeImagen = new MenuBuscaPersonaPorImagen(nombres[indiceNombreActual]);
                } else {
                    // Colocar el frame delante
                    buscarPersonaDentroDeImagen.toFront();
                    buscarPersonaDentroDeImagen.requestFocus();
                }
            }
        });
        // BOTON BUSCAR PERSONA INDIVIDUAL POR ENLACE
        botonBuscarPorDirectorioPersonaIndividual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_personaindividual_directorio_hover.jpg", botonBuscarPorDirectorioPersonaIndividual, 100, 280, 300, 80);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/buscar_personaindividual_directorio.jpg", botonBuscarPorDirectorioPersonaIndividual, 100, 280, 300, 80);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!InterfazGrafica.isFrameAbierto("Buscar Persona dentro de un Directorio")) {
                    // Llamo a la clase que se encarga del frame de buscar a una persona concreta dentro de una direccion, y le mando el parametro al constructor del nombre que se ha seleccionado
                    buscaPersonaDentroDeDireccion = new MenuBuscaPersonaPorDirectorio(nombres[indiceNombreActual]);
                } else {
                    // Colocar el frame delante
                    buscaPersonaDentroDeDireccion.toFront();
                    buscaPersonaDentroDeDireccion.requestFocus();
                }
            }
        });
        // BOTON ELIMINAR CONOCIDO
        botonEliminarConocido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_eliminar_conocido_hover.jpg", botonEliminarConocido, 100, 410, 300, 80);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_eliminar_conocido.jpg", botonEliminarConocido, 100, 410, 300, 80);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }

    // Este método sirve para cerrar todas aquellas ventanas secundarias (como agregar persona, buscar por imagen, etc)
    // Se utiliza cuando el usuario va a retroceder al menu de inicio, para que cuando salga no se queden dichas ventanas abiertas
    private void limpiarVentanasSecundarias() {
        // Si el objeto de las clases de los frames no son null, significa que pueden estar abiertos, asi que se cierran
        if (agregarConocido != null) {
            agregarConocido.dispose();
        }
        if(buscarTodoPorDirectorio != null) {
            buscarTodoPorDirectorio.dispose();
        }
        if(buscarTodoPorImagen != null) {
            buscarTodoPorImagen.dispose();
        }
        if(buscarPersonaDentroDeImagen != null) {
            buscarPersonaDentroDeImagen.dispose();
        }
        if(buscaPersonaDentroDeDireccion != null) {
            buscaPersonaDentroDeDireccion.dispose();
        }
    }
}