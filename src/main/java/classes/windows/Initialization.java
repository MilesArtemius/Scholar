package classes.windows;

import classes.dataholders.Prefs;
import classes.Scholar;
import classes.dataholders.UserInfo;
import classes.utils.Bundle;
import classes.utils.HashFunction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Initialization {
    private Node mana;
    private Node never;

    public static final String ult_const = "Latin";


    @FXML
    private VBox parent;


    @FXML
    private Text welcome_text;

    @FXML
    private Text info_text;


    @FXML
    private Text name_text;

    @FXML
    private TextField name_field;

    @FXML
    private Text email_text;

    @FXML
    private TextField email_field;


    @FXML
    private ChoiceBox teacher_choice;


    @FXML
    private CheckBox secure_box;


    @FXML
    private Text login_text;

    @FXML
    private TextField login_field;


    @FXML
    private Text password_text;

    @FXML
    private TextField password_field;


    @FXML
    private Text repet_text;

    @FXML
    private TextField repet_field;


    @FXML
    private Button submit_button;



    @FXML
    public void initialize() {
        welcome_text.setText("Welcome to the Scholar educational app!");
        info_text.setText("Before we started, we would like to know something about you:");

        name_text.setText("Your name:");
        email_text.setText("Your email:");

        teacher_choice.setValue(ult_const);
        teacher_choice.getItems().add(ult_const);

        secure_box.setSelected(true);
        secure_box.setText("Do you want to protect your profile with password?");
        secure_box.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                int size = parent.getChildren().size();
                int height = (int) Scholar.getCurrentStage().getHeight();

                if (!new_val) {
                    mana = parent.getChildren().get(size - 2);
                    parent.getChildren().remove(mana);

                    Scholar.getCurrentStage().setHeight(height - mana.prefWidth(99));
                } else {
                    never = parent.getChildren().get(size - 1);
                    parent.getChildren().set(size - 1, mana);
                    parent.getChildren().add(never);
                    Scholar.getCurrentStage().setHeight(height + mana.prefWidth(99));
                }
            }
        });

        login_text.setText("Login");
        password_text.setText("Password");
        repet_text.setText("Repeat password");

        submit_button.setText("Submit");
        submit_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                name_field.setEditable(false);
                email_field.setEditable(false);
                login_field.setEditable(false);
                password_field.setEditable(false);
                repet_field.setEditable(false);

                Bundle<String, Object> bundle = new Bundle<String, Object>();
                bundle.put(Prefs.name_key, name_field.getCharacters().toString());
                bundle.put(Prefs.email_key, email_field.getCharacters().toString());
                bundle.put(Prefs.subject_key, ult_const);
                bundle.put(Prefs.secure_key, secure_box.isSelected());
                bundle.put(Prefs.login_key, login_field.getCharacters().toString());
                bundle.put(Prefs.passwordHash_key, HashFunction.f(name_field.getCharacters().toString()));

                UserInfo.get().updatePerson(bundle);

                Scholar.change("/layouts/treebuilder.fxml", 600, 400);
            }
        });
    }
}
