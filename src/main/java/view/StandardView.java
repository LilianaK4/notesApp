package view;
import connector.Connector;
import operations.Operations;

import java.util.Scanner;

public class StandardView {

    private Scanner sc;
    private Operations operations;

    public StandardView() {
        this.sc = new Scanner(System.in);
        this.operations = new Operations();
    }


    private void printMenu() {
        System.out.println("NotesApp");
        System.out.println("1 -> Sign in");
        System.out.println("2 -> Sign up");
        System.out.println("3 - Exit");

    }


    private void printMenu2() {
        System.out.println("1 - Show all notes");
        System.out.println("2 - Modify note");
        System.out.println("3 - Add new note");
        System.out.println("4 - Delete note");
        System.out.println("5 - Log out");



    }

    private int getChoice(String message) {
        System.out.print(message);
        String line;
        int choice;
        try {
            line = sc.nextLine();
            choice = Integer.parseInt(line);
        } catch (Exception e) {
            System.out.println("Wrong choice");
            choice = getChoice(message);
        }
        return choice;
    }

    private String getText(String message) {
        System.out.print(message);
        String line;
        try {
            line = sc.nextLine();
        } catch (Exception e) {
            line = getText(message);
        }
        return line;
    }

    private String getLogin() {
        String login;
        boolean unique;
        do {
            login = getText("Enter login: ");
            unique = operations.checkLoginUniqueness(login);
            if (!unique)
                System.out.println("Given login is not unique");
        } while (!unique);

        return login;
    }


    public void Menu() {
        Connector connector = new Connector();

        int choice = 1;

        while(choice != 3) {
            printMenu();
            choice = getChoice("Choose action: ");
            switch (choice) {
                case 1:
                    String login;
                    String passw;
                    login = getText("Enter login: ");
                    passw = getText("Enter password: ");
                    if (operations.signIn(login, passw)) {
                        System.out.println("Signed in successfully\n");
                        do {
                            printMenu2();
                            do {
                                choice = getChoice("Choose action: ");
                            } while (choice < 1 || choice > 5);

                            switch (choice) {
                                case 1:
                                    break;

                                case 2:

                                    break;


                                case 3:

                                    break;

                                case 4:

                                    break;


                                case 5:
                                    System.out.println("Logging out");
                                    break;
                            }

                        } while (choice != 5);


                    }
                    break;
                case 2:
                    String name, surname;
                    login = getLogin();
                    name = getText("Enter name: ");
                    surname = getText("Enter surname: ");
                    passw = getText("Enter password: ");
                    if (operations.signUp(login, name, surname, passw))
                        System.out.println("Signed up successfully.");
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Application closing");
                    System.exit(0);



            }
        }





        connector.closeConnection();
    }





}
