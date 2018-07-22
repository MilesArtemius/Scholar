package classes;

import com.google.common.io.Files;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HP on 04.07.2017.
 */
public class TreeFile extends File {
    String viewName;

    public TreeFile(String pathname) {
        super(pathname);
        //this.viewName = "NoName";
    }

    @Override
    public String toString() {
        return this.viewName;
    }

    public TreeFile(String pathname, String viewName) {
        super(pathname);
        this.viewName = viewName;
    }

    public boolean isNormFolder() {
        if ((this.canRead()) && (this.isDirectory()) && (this.canWrite())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNormFile() {
        if ((this.canRead()) && (!this.isDirectory()) && (this.canWrite())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isImage() {
        if ((this.isFile()) && (Files.getFileExtension(this.getName()).equals("png"))) {
            return true;
        } else {
            return false;
        }
    }

    /*public void setViewName(String name) {
        this.viewName = name;
    }*/
}
