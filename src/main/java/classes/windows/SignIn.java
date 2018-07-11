package classes.windows;

import classes.Prefs;
import classes.Scholar;
import classes.UserInfo;
import classes.utils.HashFunction;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SignIn {
    @FXML
    private Text dialog_text;

    @FXML
    private Text courage_text;


    @FXML
    private Text login_text;

    @FXML
    private TextField login_field;


    @FXML
    private Text password_text;

    @FXML
    private TextField password_field;


    @FXML
    private Button submit_button;



    @FXML
    public void initialize() {
        dialog_text.setText("Sign into your account!");
        courage_text.setText("You have to put your login and password here;");

        login_text.setText("Login");
        password_text.setText("Password");

        submit_button.setText("Submit");
        submit_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login_field.setEditable(false);
                password_field.setEditable(false);

                String enteredHash = HashFunction.f(password_field.getCharacters().toString());
                String passwordHash = Prefs.pull().getString(UserInfo.passwordHash_key, "");

                if ((enteredHash.equals(passwordHash)) || (passwordHash.equals(""))) {
                    Scholar.change("/layouts/workplace.fxml", true);

                    if (passwordHash.equals("")) {

                    }
                }
            }
        });
    }
}
