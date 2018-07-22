package classes.utils;

import javafx.application.Platform;

import java.sql.SQLOutput;

public abstract class ProThread {

    public void doRun() {
        ScLog.out("Running in background...");
        new Thread() {
            @Override
            public void run() {
                ProThread.this.onBegin();
                ProThread.this.setAction();
                ProThread.this.onEnd();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ScLog.out("Done in background!");
                    }
                });
            }
        }.start();
    }

    public abstract void setAction();

    public void onBegin() {}

    public void onEnd() {}
}
