package classes;

import classes.dataholders.MediaBase;
import classes.dataholders.Prefs;
import classes.utils.ScLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Scholar extends Application {
    private static Stage current;

    @Override
    public void start(Stage primaryStage) throws Exception {
        current = primaryStage;

        String version = getClass().getPackage().getImplementationVersion() == null ? "INDEV" : getClass().getPackage().getImplementationVersion();
        primaryStage.setTitle("Scholar " + version);

        Prefs.pull().setBoolean(Prefs.init_key, false);
        MediaBase.load();

        boolean initialized = Prefs.pull().getBoolean(Prefs.init_key, false);
        boolean secured = Prefs.pull().getBoolean(Prefs.secure_key, false);

        if (!initialized) {
            change("/layouts/initialization.fxml", 600, 400);
        } else if (secured) {
            change("/layouts/signin.fxml", 500, 200);
        } else {
            change("/layouts/workplace.fxml");
        }
        primaryStage.show();
    }

    public static Stage getCurrentStage() {
        return current;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }



    public static void change(String resourceName) {
        change(resourceName, 100, 100, true);
    }

    public static void change(String resourceName, int width, int height) {
        change(resourceName, width, height, false);
    }

    private static void change(String resourceName, int width, int height, boolean isMaximized) {
        try {
            Parent root = FXMLLoader.load(Prefs.pull().getClass().getResource(resourceName));
            Scene scene = new Scene(root, width, height);
            current.setScene(scene);
            current.sizeToScene();
            current.setMaximized(isMaximized);
            current.setResizable(isMaximized);
            current.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);
            current.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
        } catch (Exception e) {
            e.printStackTrace();
            ScLog.out("Scene can not be changed");
        }
    }
}
