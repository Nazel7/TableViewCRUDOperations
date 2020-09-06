package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class  Controller {
    @FXML
    private TableView<Person> tableView;
    @FXML
    private BorderPane mainTableDialogWindow;

    public void initialize() {
        tableView.setEditable(true);
        TableColumn firsNameCol = new TableColumn("First Name");
        firsNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        firsNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firsNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((Person) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setFirstName((String) event.getNewValue());
            }
        });

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((Person) event.getTableView().getItems().get(event.getTablePosition().getRow()))
                        .setLastName((String) event.getNewValue());
            }
        });

        TableColumn phoneNumberCol = new TableColumn("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Person, String>("phoneNumber"));
        phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((Person) event.getTableView().getItems().get(event.getTablePosition().getRow()))
                        .setPhoneNumber((String) event.getNewValue());
            }
        });

        TableColumn noteCol = new TableColumn("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<Person, String>("note"));
        noteCol.setCellFactory(TextFieldTableCell.forTableColumn());
        noteCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((Person) event.getTableView().getItems().get(event.getTablePosition().getRow()))
                        .setNote((String) event.getNewValue());
            }
        });
        tableView.getColumns().addAll(firsNameCol, lastNameCol, phoneNumberCol, noteCol);
        Comparator<Person> personCompared= Comparator.comparing(Person::getFirstName);
        SortedList<Person> sortedList = new SortedList<>(PersonSingleton.getPersonInstance().getPersonList(),
                personCompared);
        tableView.setItems(sortedList);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                if (newValue != null) {

                    tableView.getSelectionModel().getSelectedItem();

                }
            }
        });
        tableView.getSelectionModel().selectFirst();
    }

    @FXML
    public void addNewPersonControl() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainTableDialogWindow.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("IOException thrown" + e.getMessage());
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> button = dialog.showAndWait();
        if (button.isPresent() && button.get() == ButtonType.OK) {
            DialogController dialogController = fxmlLoader.getController();
            Person newPerson = dialogController.newPersonOperation();
            tableView.getSelectionModel().select(newPerson);
        }
    }

    @FXML
    public void editPersonControl() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainTableDialogWindow.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("IOException thrown" + e.getMessage());
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> button = dialog.showAndWait();
        if (button.isPresent() && button.get() == ButtonType.OK) {
            DialogController dialogController = fxmlLoader.getController();
            Person newPerson = dialogController.editNewPerson();
            Person person = PersonSingleton.getPersonInstance().searchPerson(newPerson);
            tableView.getSelectionModel().select(person);
        }
    }

    @FXML
    public void deletePersonControl() {
        Person person = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Action");
        alert.setHeaderText("Delete Person: " + person.getFirstName() + " " + person.getLastName());
        alert.setContentText(" Are you sure? if not press cancel button");
        Optional<ButtonType> button = alert.showAndWait();
        if (button.isPresent() && button.get() == ButtonType.OK)
            PersonSingleton.getPersonInstance().deletePerson(person);
    }

    @FXML
    public void deletePersonControl2(KeyEvent keyEvent){
        Person person = tableView.getSelectionModel().getSelectedItem();
        if(keyEvent.getCode().equals(KeyCode.getKeyCode("Delete"))){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Action");
            alert.setHeaderText("Delete Person: " + person.getFirstName() + " " + person.getLastName());
            alert.setContentText(" Are you sure? if not press cancel button");
            Optional<ButtonType> button = alert.showAndWait();
            if (button.isPresent() && button.get() == ButtonType.OK)
                PersonSingleton.getPersonInstance().deletePerson(person);

        }
    }
}