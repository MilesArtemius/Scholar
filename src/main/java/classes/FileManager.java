package classes;

import classes.dataholders.InternetPassage;
import classes.dataholders.MediaBase;
import classes.dataholders.UserInfo;
import classes.utils.Bundle;
import classes.utils.Normalizator;
import classes.utils.ScLog;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import javax.annotation.Nullable;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Consumer;

public class FileManager {
    public static String init(String prePath) {
        Path dest = null;
        try {
            dest = Files.createDirectories(Paths.get(prePath + File.separator + Normalizator.normalize(UserInfo.get().email)));
        } catch (IOException e) {
            ScLog.out("Root ca not be created");
            e.printStackTrace();
        }
        return dest.toAbsolutePath().toString();
    }

    public static void createFiles(@Nullable Page<Blob> data) {
        if (data != null) {
            for (Blob dataPiece: data.iterateAll()) {
                try {
                    Files.createDirectories(Paths.get(UserInfo.get().path + File.separator + dataPiece.getName()
                                            .replace(InternetPassage.storageSeparator, File.separator)
                                            .substring(dataPiece.getName().indexOf(InternetPassage.storageSeparator) + 1))
                            .getParent());
                    Path dest = Files.createFile(Paths.get(UserInfo.get().path + File.separator + dataPiece.getName()
                            .replace(InternetPassage.storageSeparator, File.separator)
                            .substring(dataPiece.getName().indexOf(InternetPassage.storageSeparator) + 1)));
                    FileOutputStream fos = new FileOutputStream(dest.toFile());
                    fos.write(dataPiece.getContent());
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    ScLog.out("Some files can not be writen");
                    e.printStackTrace();
                }
            }
        }
    }



    public static File getSystemRoot() {
        File fakeRoot = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
        File root = fakeRoot;
        while (fakeRoot != null) {
            root = fakeRoot;
            fakeRoot = fakeRoot.getParentFile();
        }
        return root;
    }

    public static Bundle<String, Object> signFiles(String path, int level) {
        Bundle<String, Object> bundle = new Bundle<>();
        if ((Files.exists(Paths.get(path))) && (Files.isDirectory(Paths.get(path)))) {
            try {
                bundle.put(Paths.get(path).getFileName().toString(), new HashMap<String, Object>());
                Files.list(Paths.get(path)).forEach(new Consumer<Path>() {
                    @Override
                    public void accept(Path path) {
                        signFiles(path.toString(), level + 1);
                    }
                });
            } catch (IOException e) {
                ScLog.out("Project files can not be pushed");
            }
        } else {
            int nc = Paths.get(path).getNameCount();
            String name = Normalizator.normalize(UserInfo.get().email) + File.separatorChar + Paths.get(path).subpath(nc - level, nc).toString();
            bundle.put(Paths.get(path).getFileName().toString(), InternetPassage.open().send(Paths.get(path), name));
        }
        return bundle;
    }



    public static TreeItem<TreeFile> FSMarkDown(TreeFile file, boolean foldersOnly) {
        TreeItem<TreeFile> treeItem = new TreeItem<>(file);
        if (file.isNormFolder()) {
            ImageView ivfo = new ImageView(MediaBase.get().IMGfolder);
            treeItem.setGraphic(ivfo);
            treeItem.setExpanded(false);
            if (treeItem.getValue().listFiles() != null) treeItem.getChildren().add(new TreeItem<>());
            treeItem.expandedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    treeItem.getChildren().clear();
                    for (File childFile : treeItem.getValue().listFiles()) {
                        TreeFile childTreeFile = new TreeFile(childFile.getAbsolutePath(), childFile.getName());
                        if ((childTreeFile.isDirectory()) || (!foldersOnly)){
                            treeItem.getChildren().add(FSMarkDown(childTreeFile, foldersOnly));
                            }
                    }
                }
            });
        } else  if (file.isNormFile()) {
            ImageView ivfi = new ImageView(MediaBase.get().IMGfile);
            treeItem.setGraphic(ivfi);
            return treeItem;
        } else {
            ImageView ivnf = new ImageView(MediaBase.get().IMGno_file);
            treeItem.setGraphic(ivnf);
            System.out.println("Crashed: " + treeItem.getValue().toString());
            return treeItem;
        }
        return treeItem;
    }
}
