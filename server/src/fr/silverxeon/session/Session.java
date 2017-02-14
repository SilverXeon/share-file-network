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
    private String extension;

    public Session(String id, DataHandler file, String ext) {
        this.id = id;
        pathToFile = id+"/file";
        pathToZip = id+"/archive.zip";
        activeFile = true;
        activeZip = true;
        lastUsedFile = System.currentTimeMillis();
        lastUsedZip = System.currentTimeMillis();
        this.file = file;
        this.zip = null;
        extension = ext;
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

    public String getExtension() {
        return extension;
    }

    private void saveFile(){
        new File(Server.PATH+id).mkdirs();
        try {
            InputStream is = file.getInputStream();
            Files.copy(is, Paths.get(Server.PATH+pathToFile));

        }catch(IOException io){
            io.printStackTrace();
        }

        zip_properties.put("create", "true");
        zip_properties.put("encoding", "UTF-8");
        this.zip_disk = URI.create("jar:file:/"+Server.PATH+pathToZip);

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
            e.printStackTrace();
        }
    }

    private void unloadZip(){
        try{
            fs.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        fs = null;
        zip = null;
    }

    public DataHandler getZip(){
        if(activeZip) {
            unloadZip();
            activeZip = false;
        }
        lastUsedZip = System.currentTimeMillis();
        return new DataHandler(new FileDataSource(Server.PATH+pathToZip));
    }

    public DataHandler getFile(){
        if(!activeFile)
            loadFile();
        lastUsedFile = System.currentTimeMillis();

        return file;
    }

    public void addFileToZip(DataHandler file, String name, String surname, String ext){

        if(!activeZip)
            loadZip();
        lastUsedZip = System.currentTimeMillis();
        File f = null;
        String fileName = "G:\\TMP\\share"+System.currentTimeMillis();
        try {
            InputStream is = file.getInputStream();
            Files.copy(is, Paths.get(fileName));

            Thread.sleep(10000);
        }catch(InterruptedException|IOException io){
            io.printStackTrace();
        }

        Path ZipFilePath = fs.getPath(name+"_"+surname+"."+ext);
        Path addNewFile = Paths.get(fileName);
        try{
            Files.copy(addNewFile,ZipFilePath);
        }
        catch(IOException e){
            e.printStackTrace();
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
