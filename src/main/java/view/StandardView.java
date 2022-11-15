package view;
import connector.Connector;
import operations.IOperations;
import operations.Operations;

import java.util.Scanner;

public class StandardView implements IOperationsView {

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
        System.out.println("1 - Show the list of your notes");
        System.out.println("2 - Show the content of chosen note");
        System.out.println("3 - Modify note");
        System.out.println("4 - Add new note");
        System.out.println("5 - Delete note");
        System.out.println("6 - Log out");



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
                        System.out.println("\nSigned in successfully\n");
                        do {
                            printMenu2();
                            do {
                                choice = getChoice("\nChoose action: ");
                            } while (choice < 1 || choice > 6);

                            switch (choice) {
                                case 1:
                                    System.out.println("\nYour notes list:");
                                    operations.showAllNotes();
                                    System.out.println("\n");
                                    break;
                                case 2:

                                    String tytle = getText("Enter the tytle of note that you would like to read: ");
                                    if (operations.findNote(tytle)) {
                                        System.out.println("\n\'" + tytle +"\':");
                                        operations.showContent(tytle);
                                        System.out.println("\n");
                                    } else {
                                        System.out.println("\nNote with given title doesn't exist.\n");
                                    }
                                    break;

                                case 3:

                                    tytle = getText("\nEnter the tytle of note that you would like to modify: ");
                                    if (operations.findNote(tytle)) {
                                        String content = getText("Enter the new content of note \'" + tytle + "\': \n");
                                        if(operations.editNote(tytle, content))
                                            System.out.println("\nYour note has been successfully modified.\n");
                                    } else {
                                        System.out.println("\nNote with given title doesn't exist.\n");
                                    }

                                    break;

                                case 4:
                                    tytle = getText("\nEnter the tytle of your new note: ");
                                    String content = getText("\nEnter content of your new note: ");
                                    if (operations.addNewNote(tytle, content))
                                        System.out.println("\nNote \'" + tytle + "' has been successfully added to your notes.\n");

                                    break;

                                case 5:
                                    tytle = getText("\nEnter the tytle of note that you would like to delete: ");
                                    if (operations.findNote(tytle)) {
                                        if(operations.deleteNote(tytle))
                                            System.out.println("\nYour note has been successfully deleted.\n");
                                    } else {
                                        System.out.println("\nNote with given title doesn't exist.\n");
                                    }

                                    break;

                                case 6:
                                    System.out.println("\nLogging out\n");
                                    operations.setIdUser(0);
                                    break;

                                default:
                                    System.out.println("\nWrong choice.\n");
                                    break;
                            }

                        } while (choice != 6);

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
