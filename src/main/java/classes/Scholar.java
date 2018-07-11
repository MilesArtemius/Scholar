package classes;

import classes.utils.ScLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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
        primaryStage.setResizable(false);

        Prefs.pull().setBoolean(UserInfo.secure_key, false);

        boolean initialized = Prefs.pull().getBoolean(Prefs.keyInit, false);
        boolean secured = Prefs.pull().getBoolean(UserInfo.secure_key, false);

        if (!initialized) {
            change("/layouts/initialization.fxml", 600, 400);
        } else if (secured) {
            change("/layouts/signin.fxml", 500, 200);
        } else {
            change("/layouts/workplace.fxml", true);
        }
        primaryStage.show();
    }

    public static Stage getCurrentStage() {
        return current;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }



    public static void change(String resourceName, boolean isMaximized) {
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
            current.setMaximized(isMaximized);
        } catch (Exception e) {
            e.printStackTrace();
            ScLog.out("Scene can not be changed");
        }
    }
}
