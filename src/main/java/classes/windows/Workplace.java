package classes.windows;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class Workplace {
    @FXML
    private ImageView logo;


    @FXML
    private Button profile_button;

    @FXML
    private ImageView profile_icon;


    @FXML
    private Menu file_menu;

    @FXML
    private Menu view_menu;

    @FXML
    private Menu help_menu;


    @FXML
    private TreeView file_tree;

    @FXML
    private TextFlow text_flow;



    @FXML
    public void initialize() {
        logo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });




    }
}
