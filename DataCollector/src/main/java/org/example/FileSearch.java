package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FileSearch {

    List<File> ListJSON;
    List<File> listCSV;

    public FileSearch (String path){
        listCSV = new ArrayList<>();
        ListJSON = new ArrayList<>();
        checkFolders(new File(path));
    }
    public void checkFolders (File file) {
        File[] array = file.listFiles();
        for (File file1 : array) {
            if (file1.isFile()) {
                if (isCSV(file1.getAbsolutePath())) {
                    listCSV.add(file1);
                }
                if (isJSON(file1.getAbsolutePath())) {
                    ListJSON.add(file1);
                }
            }
            if (file1.isDirectory()){
                checkFolders(file1);
            }
        }
    }

    public boolean isJSON (String path){
        String regex = ".json";
        int number = path.indexOf(".");
        return regex.equals(path.substring(number));
    }
    public boolean isCSV (String path){
        String regex = ".csv";
        int number = path.indexOf(".");
        return regex.equals(path.substring(number));
    }
}
