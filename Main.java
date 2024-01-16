import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://direccion_ip_o_nombre_host:puerto/tu_base_de_datos";
        String dbUsername = "root";
        String dbPassword = "";
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // Crear
            insertarRegistro(conn, 2, "Carlos", 1788569678, 10, 9);

            // Mostrar
            mostrarRegistros(conn);

            // Actualizar
            actualizarNota(conn, 2, 8);

            // Mostrar
            mostrarRegistros(conn);

            // Eliminar
            eliminarRegistro(conn, 2);

            // Mostrar
            mostrarRegistros(conn);

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void insertarRegistro(Connection conn, int id, String nombre, int cedula, float nota1, float nota2)
            throws SQLException {
        String sql = "INSERT INTO calificaciones VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, nombre);
            statement.setInt(3, cedula);
            statement.setFloat(4, nota1);
            statement.setFloat(5, nota2);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Se insertó un nuevo registro");
            }
        }
    }

    private static void mostrarRegistros(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM calificaciones");

        int id;
        String nombre;

        System.out.println("Registros actuales:");
        while (rs.next()) {
            id = rs.getInt("id");
            nombre = rs.getString("nombre");
            System.out.println(id + " " + nombre);
        }
        System.out.println();
    }

    private static void actualizarNota(Connection conn, int id, float nuevaNota) throws SQLException {
        String sql = "UPDATE calificaciones SET nota1 = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setFloat(1, nuevaNota);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Se actualizó la nota del registro con ID " + id);
            } else {
                System.out.println("No se encontró el registro con ID " + id);
            }
        }
    }

    private static void eliminarRegistro(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM calificaciones WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Se eliminó el registro con ID " + id);
            } else {
                System.out.println("No se encontró el registro con ID " + id);
            }
        }
    }
}