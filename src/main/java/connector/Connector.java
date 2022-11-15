package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {

    private String url = null;
    private String user = null;
    private String passw = null;

    public Connection connection = null;

    public Connector() {
        url = "jdbc:mysql://localhost:3306/notes_schema?characterEncoding=latin1";
        this.user = "root";
        this.passw = "liliana_krol";
        connectToDataBase();
    }

    public void connectToDataBase() {

        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.passw);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
