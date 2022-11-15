package operations;

public interface IOperations {

    public int getIdUser();

    public void setIdUser(int idUser);

    public boolean signUp(String login, String name, String surname, String password);

    public boolean signIn(String login, String passw);

    public boolean checkLoginUniqueness(String login);

    public void showAllNotes();

    public boolean addNewNote(String tytle, String content);

    public boolean findNote(String tytle);

    public boolean editNote(String tytle, String content);

    public boolean showContent(String tytle);

    public boolean deleteNote(String tytle);

}
