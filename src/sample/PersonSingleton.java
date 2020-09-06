package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonSingleton {
    private static PersonSingleton personInstance = new PersonSingleton();
    private ObservableList<Person> personList;
    private static final String FILE_NAME = "fileDate.txt";

    public PersonSingleton() {
    }

    public static PersonSingleton getPersonInstance() {
        return personInstance;
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(ObservableList<Person> personList) {
        this.personList = personList;
    }


    public void storeFile() throws IOException {
        Path path = Paths.get(FILE_NAME);

        try (BufferedWriter wr = Files.newBufferedWriter(path)) {
            for (Person person : personList) {
                wr.write(person.getFirstName() + "\t" + person.getLastName() + "\t" +
                        person.getPhoneNumber() + "\t" + person.getNote() + "\n");
            }

        }
    }

    public void loadFile() throws IOException {
        personList = FXCollections.observableArrayList();
        Path path = Paths.get(FILE_NAME);
        String line;
        try (BufferedReader rd = Files.newBufferedReader(path)) {
            while ((line = rd.readLine()) != null) {
                String[] stringPerson = line.split("\t");
                String firstName = stringPerson[0];
                String lastName = stringPerson[1];
                String phoneNumber = stringPerson[2];
                String note = stringPerson[3];
                Person personScheme = new Person(firstName, lastName, phoneNumber, note);
                personList.add(personScheme);

            }
        }

    }


    public void addNewPerson(Person person) {
        personList.add(person);
    }

    public void deletePerson(Person person) {
        personList.remove(person);
    }

    public Person searchPerson(Person person) {

        for (Person person1 : personList) {
            if (person1.getFirstName().equalsIgnoreCase(person.getFirstName()) ||
                    person1.getLastName().equalsIgnoreCase(person.getLastName())) {
                return person1;
            }
        }
        return null;
    }
}
