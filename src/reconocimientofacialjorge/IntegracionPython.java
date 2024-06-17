/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge Areal Alberich
 */
public class IntegracionPython {
    
    private static final String DIRECTORIO_BUSCA_PY = "python/programa/busca.py";

    public static void agregarConocido(String nombreNuevoConocido, String fotoNuevoConocido) throws IOException {
        // Crear nuevo personaje conocido
        String pythonDirectorio = "python/programa/almacena.py";
        String[] cmd = new String[4];
        cmd[0] = "python";
        cmd[1] = pythonDirectorio;
        cmd[2] = nombreNuevoConocido;
        cmd[3] = fotoNuevoConocido;

        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process p = pb.start();

        // Capturar la salida del script de Python
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
    
    public static void ejecutarBuscarTodo(String directorioSeleccionado, String concepto) {
        if (directorioSeleccionado != null && !directorioSeleccionado.isEmpty()) {
            String comando;
            // Si en "concepto" pongo imagen, se buscaran todas las personas conocidas dentro de una imagen. Si no, se buscarán todas las fotos con caras conocidas dentro de un directorio entero
            // Esta forma de trabajar reduce la duplicacion de codigo
            if ("imagen".equals(concepto)){
                comando = "python " + DIRECTORIO_BUSCA_PY + " -i \"" + directorioSeleccionado + "\"";
                System.out.println("HOLAAAAA");
            } else {
                comando = "python " + DIRECTORIO_BUSCA_PY + " -d \"" + directorioSeleccionado + "\"";
            }
            
            try {
                Process proceso = Runtime.getRuntime().exec(comando);
                //proceso.waitFor();
                JOptionPane.showMessageDialog(null, "¡Búsqueda iniciada!.");
                System.out.println(comando);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar el programa Python: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "¡Selecciona algo en la carpeta primero!!");
        }
    }
    
    public static void ejecutarBuscarIndividual(String directorio, String nombreActual, String concepto) {
        if (directorio != null && !directorio.isEmpty()) {
            String comando;
            // Si en "concepto" pongo imagen, se buscaran todas las personas conocidas dentro de una imagen. Si no, se buscarán todas las fotos con caras conocidas dentro de un directorio entero
            // Esta forma de trabajar reduce la duplicacion de codigo
            if ("imagen".equals(concepto)){
                comando = "python " + DIRECTORIO_BUSCA_PY + " -i \"" + directorio + "\" -p \"" + nombreActual + "\"";
            } else {
                comando = "python " + DIRECTORIO_BUSCA_PY + " -d \"" + directorio + "\" -p \"" + nombreActual + "\"";
            }
            
            try {
                Process proceso = Runtime.getRuntime().exec(comando);
                //proceso.waitFor();
                JOptionPane.showMessageDialog(null, "Buscando a " + nombreActual + "...");
                System.out.println(comando);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar el programa Python: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "¡Selecciona algo en la carpeta primero!!");
        }
    }
}
