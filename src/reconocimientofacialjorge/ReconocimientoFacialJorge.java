/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reconocimientofacialjorge;

/**
 *
 * @author Jorge Areal Alberich
 */
public class ReconocimientoFacialJorge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Llamar al constructor de la clase "MenuInicio" para comenzar a construir la interfaz grafica
        // Le envio por parámetro un "true"
        // true: inicializa el frame y el panel general, ademas del resto de componentes de la pantalla inicial
        // false:  no inicializa el frame y panel general, pero si todo lo demas
        MenuInicio menuInicio = new MenuInicio(true);
    }
}
