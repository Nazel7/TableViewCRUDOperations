package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DialogController {

    @FXML
    private TextField firstNameDialog;
    @FXML
    private TextField lastNameDialog;
    @FXML
    private TextField phoneNumberDialog;
    @FXML
    private TextField shortNoteDialog;

    @FXML
    public Person newPersonOperation() {
        String firstName = firstNameDialog.getText().trim();
        String lastName = lastNameDialog.getText().trim();
        String phoneNumber = phoneNumberDialog.getText().trim();
        String shortNote = shortNoteDialog.getText().trim();
        Person person = new Person(firstName, lastName, phoneNumber, shortNote);
        PersonSingleton.getPersonInstance().addNewPerson(person);
        return person;
    }
    @FXML
    public Person editNewPerson(){
        String firstName = firstNameDialog.getText().trim();
        String lastName = lastNameDialog.getText().trim();
        String phoneNumber = phoneNumberDialog.getText().trim();
        String shortNote = shortNoteDialog.getText().trim();
        Person person= new Person(firstName, lastName, phoneNumber, shortNote);
       return person;
    }

}
