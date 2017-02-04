package fr.silverxeon.session;

import fr.silverxeon.server.Server;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Pierre on 04/02/2017.
 */
public class Session {
    private String id;
    private String pathToFile;
    private String pathToZip;
    private boolean activeFile;
    private boolean activeZip;
    private long lastUsedFile;
    private long lastUsedZip;
    private DataHandler file;
    private DataHandler zip;

    public Session(String id, DataHandler file) {
        this.id = id;
        pathToFile = id+"/file";
        pathToZip = id+"/archive.zip";
        activeFile = true;
        activeZip = false;
        lastUsedFile = System.currentTimeMillis();
        lastUsedZip = System.currentTimeMillis();
        this.file = file;
        this.zip = null;
    }

    public Session(String id){
        this.id = id;
        pathToFile = id+"/file";
        pathToZip = id+"/archive.zip";
        activeZip = false;
        activeFile = false;
        lastUsedZip = 0;
        lastUsedFile = 0;
        this.file = null;
        this.zip = null;
    }

    private void saveFile(){
        try {
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get(Server.PATH+pathToFile));
        }catch(IOException io){
            System.err.println(io.getMessage());
        }

        //TODO Creer le zip
    }

    private void loadFile(){
        FileDataSource f = new FileDataSource(Server.PATH+pathToFile);
        file = new DataHandler(f);
    }

    private void unloadFile(){
        file = null;
    }

    private void loadZip(){
        FileDataSource f = new FileDataSource(Server.PATH+pathToZip);
        zip = new DataHandler(f);
    }

    private void unloadZip(){
        zip = null;
    }

    public DataHandler getZip(){
        if(!activeZip)
            loadZip();
        lastUsedZip = System.currentTimeMillis();

        return zip;
    }

    public DataHandler getFile(){
        if(!activeFile)
            loadFile();
        lastUsedFile = System.currentTimeMillis();

        return file;
    }

    public void addFileToZip(DataHandler file, String name, String surname){
        if(!activeZip)
            loadZip();

        //TODO ajout de file au zip
    }

    public void updateLoad(long act){
        if(activeFile)
            if(act-lastUsedFile>=Server.INACTIVE_DELAY)
                unloadFile();
        if(activeZip)
            if(act-lastUsedZip>=Server.INACTIVE_DELAY)
                unloadZip();
    }

    public void closeSession(){
        deleteFile(Server.PATH+id);
    }

    private void deleteFile(String path){
        File index = new File(path);
        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
    }
}
