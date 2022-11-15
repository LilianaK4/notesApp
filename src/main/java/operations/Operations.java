package operations;

import java.sql.*;
import connector.Connector;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Operations {


    Statement statement = null;
    ResultSet resultSet = null;
    Connection connection;
    Connector connector;

    private int idUser;


    public Operations() {
        this.connector = new Connector();
        connection = connector.connection;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    public boolean signUp(String login, String name, String surname, String password) {
        try {
            PreparedStatement add = connection.prepareStatement("insert into Users values (?,?,?,?,?)");
            add.setString(1, null);
            add.setString(2, login);
            add.setString(3, name);
            add.setString(4, surname);
            add.setString(5, BCrypt.hashpw(password, BCrypt.gensalt(10)));

            if(add.executeUpdate() == 1)
                return true;
            else
                return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean signIn(String login, String passw) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Users where login ='" + login +"'");
            if(resultSet.next()) {
                if(BCrypt.checkpw(passw, resultSet.getString("password"))) {
                    setIdUser(resultSet.getInt("idUser"));
                    return true;
                }
                else {
                    System.out.println("Incorrect password");
                    return false;
                }
            }
            else
                System.out.println("There is no user with matching login. Try again or sign up");
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


    public boolean checkLoginUniqueness(String login) {
        int count;
        count = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Users where login ='" + login + "'");

            //Checking uniqueness
            while (resultSet.next())
                count++;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (count != 0)
            return false;
        else
            return true;
    }


    public void showAllNotes() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Notes where idUser = '" + this.idUser + "'");
            while(resultSet.next()) {
                System.out.println(resultSet.getString("tytle"));
                System.out.println("\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }



}
