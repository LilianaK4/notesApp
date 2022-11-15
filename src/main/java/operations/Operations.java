package operations;

import java.sql.*;
import connector.Connector;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Operations implements IOperations {


    Statement statement = null;

    PreparedStatement st = null;
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
            st = connection.prepareStatement("insert into Users values (?,?,?,?,?)");
            st.setString(1, null);
            st.setString(2, login);
            st.setString(3, name);
            st.setString(4, surname);
            st.setString(5, BCrypt.hashpw(password, BCrypt.gensalt(10)));

            if(st.executeUpdate() == 1)
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
            st = connection.prepareStatement("select * from Users where login = ?");
            st.setString(1, login);
             resultSet = st.executeQuery();
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
            st = connection.prepareStatement("select  * from Users where login = ?");
            st.setString(1, login);
            resultSet = st.executeQuery();

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
            st = connection.prepareStatement("select * from Notes where idUser = ?");
            st.setInt(1, this.idUser);
            resultSet = st.executeQuery();

            while(resultSet.next()) {
                System.out.println(resultSet.getString("tytle"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean addNewNote(String tytle, String content) {
        try {
            st = connection.prepareStatement("Insert into Notes values (?, ?, ?, ?)");
            st.setString(1, null);
            st.setString(2, tytle);
            st.setString(3, content);
            st.setInt(4, this.idUser);
            return st.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean findNote(String tytle) {
        try {
            st = connection.prepareStatement("select * from Notes where idUser = ? and tytle = ?");
            st.setInt(1, this.idUser);
            st.setString(2, tytle);
            resultSet = st.executeQuery();

            if(resultSet.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }



    public boolean editNote(String tytle, String content) {
        try {
            PreparedStatement st = connection.prepareStatement("update Notes set content = ? where idUser = ? and tytle = ?");
            st.setString(1, content);
            st.setInt(2, this.idUser);
            st.setString(3, tytle);
            st.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean showContent(String tytle) {
        try {
            st = connection.prepareStatement("select content from Notes where idUser = ? and tytle = ?");
            st.setInt(1, this.idUser);
            st.setString(2, tytle);
            resultSet = st.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getString("content"));
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean deleteNote(String tytle) {
        try {
            st = connection.prepareStatement("delete from Notes where idUser = ? and tytle = ?");
            st.setInt(1, this.idUser);
            st.setString(2, tytle);
            st.executeUpdate();
            return true;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }


    }




}
