package org.logmanager.utils;

import javafx.scene.control.Alert;

public final class ErrorHandler {

    private  ErrorHandler(){

    }

    public static void HandleException(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An eror occured");
        alert.setContentText(e.getMessage());
    }
}
