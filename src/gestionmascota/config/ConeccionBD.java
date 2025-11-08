
package gestionmascota.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConeccionBD {

    // Ajustá estos valores según tu MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_mascota?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private ConeccionBD() {
        // evitar instanciar
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}