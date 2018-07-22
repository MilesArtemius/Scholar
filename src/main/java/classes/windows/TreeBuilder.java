package classes.windows;

import classes.*;
import classes.dataholders.MediaBase;
import classes.dataholders.UserInfo;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.File;

public class TreeBuilder {
    private static final String folder_name = File.separator + "ekScholar";


    @FXML
    private Text header;


    @FXML
    private TreeView tree;

    @FXML
    private Text desc;


    @FXML
    private TextField path;

    @FXML
    private Button submit_button;

    @FXML
    public void initialize() {
        header.setText("Choose where your data will be stored");

        File root = FileManager.getSystemRoot();
        TreeItem rootItem = FileManager.FSMarkDown(new TreeFile(root.getAbsolutePath(), root.toString()), true);
        tree.setRoot(rootItem);
        tree.setOnMouseClicked(event -> {
            TreeItem item = (TreeItem) tree.getSelectionModel().getSelectedItem();
            if (item != null) {
                TreeFile treeFile = (TreeFile) item.getValue();
                path.setText(treeFile.getAbsolutePath() + folder_name);
            }
        });

        desc.setText("It is recommended to use\nthe disk where your OS\nis installed.\nThe subfolder will be created.");

        path.setText(root.toString() + folder_name);

        submit_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tree.setEditable(false);

                String destination = path.getText();
                destination += (destination.contains(folder_name))?(""):(folder_name);
                UserInfo.get().updatePath(destination);

                Scholar.change("/layouts/workplace.fxml");
            }
        });
    }
}
