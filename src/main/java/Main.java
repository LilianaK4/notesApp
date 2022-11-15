import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import connector.Connector;
import operations.Operations;
import view.StandardView;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        Connector connector = new Connector();
        StandardView standardView = new StandardView();
        standardView.Menu();








        connector.closeConnection();

    }

}
