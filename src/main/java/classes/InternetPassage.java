package classes;

import classes.utils.Bundle;
import classes.utils.ScLog;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class InternetPassage {
    private static InternetPassage ourInstance = new InternetPassage();

    private static final String data_doc = "data";

    private String email;
    private boolean connected = false;

    private Firestore db;



    public static InternetPassage open() {
        if (ourInstance == null) {
            ourInstance = new InternetPassage();
        }
        return ourInstance;
    }

    private InternetPassage() {}

    public void connect(String email) {
        ourInstance.email = email;

        if (!connected) {
            try {
                Path temp = Files.createTempFile("resource", ".json");
                Files.copy(this.getClass().getResourceAsStream("/key.json"), temp, StandardCopyOption.REPLACE_EXISTING);
                FileInputStream serviceAccount = new FileInputStream(temp.toFile());
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://ultimateplatformer.firebaseio.com/")
                        .build();
                FirebaseApp.initializeApp(options);
                ourInstance.db = FirestoreClient.getFirestore();

                ourInstance.connected = true;
            } catch (Exception e) {
                ourInstance.connected = false;
                ScLog.out("Can not be connected, something went wrong");
            }
        }
    }



    public void updateInfo(Bundle bundle) {
        try {
            ApiFuture<WriteResult> result = db.collection(email).document(data_doc).set(bundle, SetOptions.merge());
            ScLog.out("Successfully updated in " + result.get().getUpdateTime());
        } catch (Exception e) {
            ScLog.out("Can not be updated. For some reason.");
        }
    }
    
    public static void send(String email) {
        if (ourInstance.connected) {

        } else {
            ScLog.out("You must connect to the destination first!");
        }
    }
}
