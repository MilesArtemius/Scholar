package classes.dataholders;

import classes.FileManager;
import classes.utils.Bundle;
import classes.utils.ProThread;
import classes.utils.ScLog;
import classes.windows.Initialization;

public class UserInfo {
    private static UserInfo ourInstance = new UserInfo();

    public String name;
    public String email;
    public String subject;
    public boolean secure;
    public String login;
    public String passwordHash;
    public String path;



    public static UserInfo get() {
        if (ourInstance == null) {
            ourInstance = new UserInfo();
        }
        return ourInstance;
    }

    private UserInfo() {
        this.name = Prefs.pull().getString(Prefs.name_key, "Nulla Pariatur");
        this.email = Prefs.pull().getString(Prefs.email_key, "spqr@roma.la");
        this.subject = Prefs.pull().getString(Prefs.subject_key, Initialization.ult_const);
        this.secure = Prefs.pull().getBoolean(Prefs.secure_key, false);
        this.login = Prefs.pull().getString(Prefs.login_key, "khazad-dum");
        this.passwordHash = Prefs.pull().getString(Prefs.passwordHash_key, "melon");
        this.path = Prefs.pull().getString(Prefs.path_key, FileManager.getSystemRoot().toString());
    }

    public void updatePerson(Bundle<String, Object> bundle) {
        ourInstance.name = bundle.getString(Prefs.name_key, "Nulla Pariatur");
        ourInstance.email = bundle.getString(Prefs.email_key, "spqr@roma.la");
        ourInstance.subject = bundle.getString(Prefs.subject_key, Initialization.ult_const);
        ourInstance.secure = bundle.getBoolean(Prefs.secure_key, false);
        ourInstance.login = bundle.getString(Prefs.login_key, "khazad-dum");
        ourInstance.passwordHash = bundle.getString(Prefs.passwordHash_key, "melon");

        ProThread pt = new ProThread() {
            @Override
            public void setAction() {
                InternetPassage.open().updateInfo(Bundle.collectStringBundle(new String[]{Prefs.name_key, Prefs.subject_key}, new String[]{ourInstance.name, ourInstance.subject}));
            }
        };
        pt.doRun();

        Prefs.pull().setBundle(bundle);
    }

    public void updatePath(String path) {
        ourInstance.path = FileManager.init(path);

        ProThread pt;
        pt = new ProThread() {
            @Override
            public void setAction() {
                FileManager.createFiles(InternetPassage.open().pullData());
            }
        };
        pt.doRun();
        ScLog.out("All files up-to-date");

        pt = new ProThread() {
            @Override
            public void setAction() {
                FileManager.signFiles(ourInstance.path, 0);
            }
        };
        pt.doRun();
        ScLog.out("All files loaded!");

        Prefs.pull().setString(Prefs.path_key, ourInstance.path);
        Prefs.pull().setBoolean(Prefs.init_key, true);
    }

}
