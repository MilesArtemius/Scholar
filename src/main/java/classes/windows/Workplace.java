package classes.windows;

import classes.FileManager;
import classes.Scholar;
import classes.TreeFile;
import classes.dataholders.MediaBase;
import classes.dataholders.UserInfo;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Workplace {
    @FXML
    private StackPane toolbar;
    @FXML
    private HBox frame;
    @FXML
    private HBox toolbar_left;
    @FXML
    private HBox toolbar_right;
    @FXML
    private MenuBar menu;
    @FXML
    private SplitPane split_pane;


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
    private MenuItem prop_fullscreen;

    @FXML
    private Menu help_menu;


    @FXML
    private TreeView file_tree;

    @FXML
    private TextFlow text_flow;

    @FXML
    private ScrollPane scroll;



    @FXML
    public void initialize() {
        toolbar.setStyle("-fx-background-color: linear-gradient(to top, transparent, white)");

        logo.setStyle("-fx-cursor: hand;");
        logo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        File root = Paths.get(UserInfo.get().path).toFile();
        TreeItem rootItem = FileManager.FSMarkDown(new TreeFile(root.getAbsolutePath(), root.toString()), false);
        ImageView ivfo = new ImageView(MediaBase.get().IMGlevel_folder);
        rootItem.setGraphic(ivfo);
        file_tree.setRoot(rootItem);
        file_tree.setOnMouseClicked(event -> {
            TreeItem item = (TreeItem) file_tree.getSelectionModel().getSelectedItem();
            if (item != null) {
                TreeFile treeFile = (TreeFile) item.getValue();
            }
        });

        prop_fullscreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                TextFlow tfl = new TextFlow();
                tfl.getChildren().addAll(text_flow.getChildren());
                ScrollPane scr = new ScrollPane(tfl);
                scr.setPadding(new Insets(25,25,25,25));
                Scene scene = new Scene(scr);
                stage.setFullScreen(true);
                stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent type) -> {
                    if (KeyCode.ESCAPE == type.getCode()) {
                        text_flow.getChildren().addAll(tfl.getChildren());
                        stage.close();
                    }
                });
                stage.setScene(scene);
                stage.show();
                Platform.runLater(() -> {
                    tfl.setPrefWidth(scr.getViewportBounds().getWidth() - 1);
                    scr.requestLayout();
                });
            }
        });

        try {
            FileInputStream fis = new FileInputStream("C:\\ekScholar\\user\\rjhbya.docx");
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            text_flow.getChildren().add(new Text(extractor.getText()));
        } catch(Exception ex) {
            ex.printStackTrace();
        }




        Scholar.getCurrentStage().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        split_pane.setPrefHeight((Scholar.getCurrentStage().getScene().getHeight() - toolbar.getHeight() - menu.getHeight()));
                        split_pane.setDividerPosition(0, 0.2);
                        split_pane.getParent().requestLayout();
                    }
                });
            }
        });

        Scholar.getCurrentStage().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        toolbar_left.setPrefWidth((Scholar.getCurrentStage().getScene().getWidth() - toolbar.getInsets().getLeft() - toolbar.getInsets().getRight()) / 2);
                        toolbar_right.setPrefWidth((Scholar.getCurrentStage().getScene().getWidth() - toolbar.getInsets().getLeft() - toolbar.getInsets().getRight()) / 2);
                        frame.setPrefWidth(Scholar.getCurrentStage().getScene().getWidth() - toolbar.getInsets().getLeft() - toolbar.getInsets().getRight());
                        toolbar.setPrefWidth(Scholar.getCurrentStage().getScene().getWidth());
                        menu.setPrefWidth(Scholar.getCurrentStage().getScene().getWidth());
                        split_pane.setPrefWidth(Scholar.getCurrentStage().getScene().getWidth());
                        toolbar.getParent().requestLayout();
                    }
                });
            }
        });

        scroll.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                text_flow.setPrefWidth(newValue.doubleValue() - (oldValue.doubleValue() - scroll.getViewportBounds().getWidth()) - 1);
                scroll.requestLayout();
            }
        });
    }
}
