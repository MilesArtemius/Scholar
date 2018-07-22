package classes.dataholders;

import classes.utils.ProThread;
import javafx.scene.image.Image;

public class MediaBase {
    private static MediaBase ourInstance = new MediaBase();

    //ICONS:
    public Image IMGlevel_bundle;
    public Image IMGfolder;
    public Image IMGfile;
    public Image IMGlevel;
    public Image IMGlevel_folder;
    public Image IMGresources;
    public Image IMGtexture;
    public Image IMGno_file;

    public static MediaBase get() {
        if (ourInstance == null) {
            ourInstance = new MediaBase();
        }
        return ourInstance;
    }

    public static void load() {
        if (ourInstance == null) {
            ourInstance = new MediaBase();
        }
    }

    private MediaBase() {
        ProThread pt = new ProThread() {
            @Override
            public void setAction() {
                ourInstance.IMGlevel_bundle = new Image(getClass().getResourceAsStream("/pictures/level_bundle.png"));
                ourInstance.IMGfolder = new Image(getClass().getResourceAsStream("/pictures/folder.png"));
                ourInstance.IMGfile = new Image(getClass().getResourceAsStream("/pictures/file.png"));
                ourInstance.IMGlevel = new Image(getClass().getResourceAsStream("/pictures/level.png"));
                ourInstance.IMGlevel_folder = new Image(getClass().getResourceAsStream("/pictures/level_folder.png"));
                ourInstance.IMGresources = new Image(getClass().getResourceAsStream("/pictures/resources.png"));
                ourInstance.IMGtexture = new Image(getClass().getResourceAsStream("/pictures/texture.png"));
                ourInstance.IMGno_file = new Image(getClass().getResourceAsStream("/pictures/no_file.png"));
            }
        };
        pt.doRun();
    }
}
