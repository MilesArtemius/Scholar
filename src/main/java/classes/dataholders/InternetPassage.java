package classes.dataholders;

import classes.utils.Bundle;
import classes.utils.Normalizator;
import classes.utils.ScLog;
import com.google.api.core.ApiFuture;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class InternetPassage {
    private static InternetPassage ourInstance = new InternetPassage();

    public static String storageSeparator = "/";

    private static final String data_doc       = "data";
    private static final String projects_doc   = "projects";

    private boolean connected = false;

    private Firestore db;



    public static InternetPassage open() {
        if (ourInstance == null) {
            ourInstance = new InternetPassage();
        }
        if (!ourInstance.connected) {
            try {
                Path temp = Files.createTempFile("resource", ".json");
                Files.copy(ourInstance.getClass().getResourceAsStream("/key.json"), temp, StandardCopyOption.REPLACE_EXISTING);
                FileInputStream serviceAccount = new FileInputStream(temp.toFile());
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://ultimateplatformer.firebaseio.com/")
                        .setStorageBucket("ek-scholar.appspot.com")
                        .build();
                FirebaseApp.initializeApp(options);
                ourInstance.db = FirestoreClient.getFirestore();

                ourInstance.connected = true;
            } catch (Exception e) {
                ourInstance.connected = false;
                e.printStackTrace();
                ScLog.out("Can not be connected, something went wrong");
            }
        }
        return ourInstance;
    }

    private InternetPassage() {}



    public void updateInfo(Bundle<String, String> bundle) {
        try {
            ApiFuture<WriteResult> result = db.collection(UserInfo.get().email).document(data_doc).set(bundle, SetOptions.merge());
            ScLog.out("Successfully updated in " + result.get().getUpdateTime());
        } catch (Exception e) {
            ScLog.out("Can not be updated. For some reason.");
            ScLog.out("You must connect to the destination first!");
        }
    }

    public Page<Blob> pullData() {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();

            Page<Blob> blobs = bucket.list(Storage.BlobListOption.prefix(Normalizator.normalize(UserInfo.get().email)));

            return blobs;
        } catch (Exception e) {
            e.printStackTrace();
            ScLog.out("Data can not be received. For some reason.");
            ScLog.out("You must connect to the destination first!");
            return null;
        }
    }
    
    public Path send(Path path, String name) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();

            String blobName = name.replace(File.separator, storageSeparator);
            InputStream content = new FileInputStream(path.toString());
            Blob blob = bucket.create(blobName, content, "text/plain");

            Map<String, String> newMetadata = new HashMap<>();
            newMetadata.put("key", "value");
            blob.toBuilder().setMetadata(newMetadata).build().update(Storage.BlobTargetOption.metagenerationMatch());
        } catch (Exception e) {
            e.printStackTrace();
            ScLog.out("Data can not be sent. For some reason.");
            ScLog.out("You must connect to the destination first!");
        }
        return path;
    }
}
