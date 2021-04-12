package org.logmanager.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class FileManager {

    private static void setLogs(ArrayList<String> logs) {
        Logs = logs;
    }

    public static ArrayList<String> getLogs() {
        if(Logs == null){
            setLogs(new ArrayList<String>());
        }
        return Logs;
    }

    public static ArrayList<String> Logs;

    private FileManager(){

    }

    public static void clear(){
        setLogs(null);
    }

    public static boolean readLogs() throws IOException {
        ArrayList<String> logs = new ArrayList<String>();

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Log files","log"));

        int returnValue = fileChooser.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));){
                while(bufferedReader.ready()){
                    logs.add(bufferedReader.readLine());
                }
                logs.remove(0);
                setLogs(logs);
            }
        }else{
            return false;
        }

        return true;
    }
}
