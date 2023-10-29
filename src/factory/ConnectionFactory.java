package factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static final String username = "root";
    private static final String password = "root";
    private static final String database_url = "jdbc:mysql://localhost:3306/crd";

    Connection connection;

    public Connection createConnection() {
        try {
            connection = DriverManager.getConnection(database_url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                connection.close();
                System.out.println("Conexão fechada!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection conn = connectionFactory.createConnection();

        if (conn != null) {
            System.out.println("Conexão obtida com sucesso!");
            conn.close();
        }
    }
}
