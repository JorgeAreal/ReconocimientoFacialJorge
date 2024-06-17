/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reconocimientofacialjorge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jorge Areal Alberich
 */
public class ConexionBBDD {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "";

    // Método para obtener todos los datos de una columna de una tabla
    public static String[] obtenerValoresColumna(String nombreColumna) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> columnData = new ArrayList<>();

        // Establecer la conexión con la base de datos
        connection = DriverManager.getConnection(URL + "reconocimiento", USUARIO, CONTRASENIA);

        // Crear una declaración SQL
        statement = connection.createStatement();

        // Ejecutar la consulta SQL para obtener los datos de la columna especificada
        String query = "SELECT " + nombreColumna + " FROM conocidos";
        resultSet = statement.executeQuery(query);

        // Recorrer el resultado de la consulta y agregar cada valor al ArrayList
        while (resultSet.next()) {
            columnData.add(resultSet.getString(nombreColumna));
        }
        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();
        // Convertir el ArrayList a un array y devolverlo
        return columnData.toArray(new String[0]);
    }
    
    // Devuelve un true si la conexion ha sido exitosa, y un false si no ha sido exitosa
    public static boolean comprobarConexionTabla(String nombreTabla) {
        try {
            // Establecer conexión a la base de datos
            Connection conexion = DriverManager.getConnection(URL + "reconocimiento", USUARIO, CONTRASENIA);
            // Crear una sentencia SQL
            Statement statement = conexion.createStatement();
            // Consulta para verificar la existencia de la tabla
            String consulta = "SELECT 1 FROM " + nombreTabla + " LIMIT 1";
            // Ejecutar la consulta
            statement.executeQuery(consulta);
            // Cerrar recursos
            statement.close();
            conexion.close();
            // Si no se produce ninguna excepción, significa que hay conexión a la tabla
            return true;
        } catch (SQLException e) {
            // Si se produce una excepción, no hay conexión a la tabla
            return false;
        }
    }
}
