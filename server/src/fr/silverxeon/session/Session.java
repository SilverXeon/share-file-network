package fr.silverxeon.session;

import fr.silverxeon.server.Server;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, String> zip_properties = new HashMap<>();
    private URI zip_disk;
    private FileSystem fs;

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
        saveFile();
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
        loadFile();
        saveFile();
    }

    private void saveFile(){
        try {
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get(Server.PATH+pathToFile));
        }catch(IOException io){
            System.err.println(io.getMessage());
        }

        zip_properties.put("create", "true");
        zip_properties.put("encoding", "UTF-8");
        this.zip_disk =  URI.create("jar:file:"+pathToZip+"/archive.zip");
        loadZip();
        zip_properties.replace("create", "false");
    }

    private void loadFile(){
        FileDataSource f = new FileDataSource(Server.PATH+pathToFile);
        file = new DataHandler(f);
    }

    private void unloadFile(){
        file = null;
    }

    private void loadZip(){
        try {
            fs = FileSystems.newFileSystem(zip_disk, zip_properties);
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void unloadZip(){
        try{
            fs.close();
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        fs = null;
        zip = null;
    }

    public DataHandler getZip(){
        if(!activeZip)
            loadZip();
        lastUsedZip = System.currentTimeMillis();
        return new DataHandler(new FileDataSource(Server.PATH+pathToZip));
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
        lastUsedZip = System.currentTimeMillis();
        String extension[] = file.getName().split(".");
        String ext = extension[extension.length];
        Path ZipFilePath = fs.getPath(name+"_"+surname+"."+ext);
        Path addNewFile = Paths.get(pathToFile);
        try{
            Files.copy(addNewFile,ZipFilePath);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
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
