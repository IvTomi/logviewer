package org.logmanager;

import java.io.IOException;
import javafx.fxml.FXML;
import org.logmanager.utils.FileManager;

public class PrimaryController {

    @FXML
    private void openLogFile() throws IOException {
        if(FileManager.readLogs()){
            App.setRoot("secondary");
        }
    }
}
