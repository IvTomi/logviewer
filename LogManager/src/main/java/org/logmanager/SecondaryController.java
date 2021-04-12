package org.logmanager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.logmanager.model.LogStringBase;
import org.logmanager.utils.Enums;
import org.logmanager.utils.ErrorHandler;
import org.logmanager.utils.FileManager;

public class SecondaryController implements Initializable {

    private ObservableList<LogStringBase> getLoadedLogs() throws Exception {
        var lgs = FileManager.getLogs();
        ArrayList<LogStringBase> logobjects = new ArrayList<LogStringBase>();

        for (var lg:lgs) {
            logobjects.add(LogStringBase.getLogStringObject(lg));
        }

        return FXCollections.observableArrayList(logobjects);
    }

    private int debugLevel = 1;
    private String messageFilter = "";
    private LocalDate fromDateFilter = null;
    private LocalDate toDateFilter = null;

    public void setContent(TableView<LogStringBase> content) {
        Content = content;

    }

    public TableView<LogStringBase> getContent() {
        return Content;
    }

    @FXML
    private TableView<LogStringBase> Content;

    public Text getDebugLevelText() {
        return DebugLevelText;
    }

    private void setDebugLevelText(String debugLevelText) {
        DebugLevelText.setText(debugLevelText);
    }

    @FXML
    private Text DebugLevelText;

    public TextField getMessageFilter() {
        return MessageFilter;
    }

    private void setMessageFilter(TextField messageFilter) {
        MessageFilter = messageFilter;
    }

    @FXML
    private TextField MessageFilter;

    public DatePicker getDatePickerFrom() {
        return DatePickerFrom;
    }

    private void setDatePickerFrom(DatePicker datePickerFrom) {
        DatePickerFrom = datePickerFrom;
    }

    @FXML
    private DatePicker DatePickerFrom;

    public DatePicker getDatePickerTo() {
        return DatePickerTo;
    }

    private void setDatePickerTo(DatePicker datePickerTo) {
        DatePickerTo = datePickerTo;
    }

    @FXML
    private DatePicker DatePickerTo;

    @FXML
    private void switchToPrimary() throws IOException {
        FileManager.clear();
        App.setRoot("primary");
    }

    @FXML
    private void raiseDebugLevel() throws IOException {
        if(debugLevel < 3){
            debugLevel ++;
            setDebugLevelText(String.valueOf(debugLevel));
            setTableRecords();
        }
    }

    @FXML
    private void lowerDebugLevel() throws IOException {
        if(debugLevel > 1){
            debugLevel --;
            setDebugLevelText(String.valueOf(debugLevel));
            setTableRecords();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDebugLevelText(String.valueOf(debugLevel));
        initTableView();
        initMessageFilter();
        initDatePicker();
    }

    private void initMessageFilter(){
        getMessageFilter().textProperty().addListener((observable,oldValue,newValue)->{
            setMessageFilter(newValue);
        });

    }

    private void initDatePicker(){
        getDatePickerFrom().valueProperty().addListener((observable,oldValue,newValue)->{
            setFromFilter(newValue);
        });

        getDatePickerTo().valueProperty().addListener((observable,oldValue,newValue)->{
            setToFilter(newValue);
        });

    }

    private void setMessageFilter(String value){
        messageFilter = value;
        setTableRecords();
    }

    private void setFromFilter(LocalDate value){
        fromDateFilter = value;
        setTableRecords();
    }

    private void setToFilter(LocalDate value){
        toDateFilter = value;
        setTableRecords();
    }

    private void initTableView(){
        TableView<String> table = new TableView<String>();

        TableColumn<LogStringBase, Date> dateColumn = new TableColumn<LogStringBase,Date>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("logdate"));

        TableColumn<LogStringBase, Enums.LogTypes> typeColumn = new TableColumn<LogStringBase,Enums.LogTypes>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<LogStringBase,String> messageColumn = new TableColumn<LogStringBase,String>("Message");
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        getContent().setRowFactory(tv -> new TableRow<LogStringBase>() {
            @Override
            protected void updateItem(LogStringBase item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getTextColor() == null)
                    setStyle("");
                else{
                    setStyle(String.format("-fx-text-background-color: %s;",item.getTextColor()));
                }
            }
        });

        getContent().getColumns().addAll(dateColumn,typeColumn,messageColumn);
        getContent().setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);


        setTableRecords();

    }

    private void setTableRecords(){
        try{
            var logs = filterLogs();
            getContent().setItems(logs);
        }catch (Exception e){
            ErrorHandler.HandleException(e);
        }
        getContent().refresh();
    }

    private SortedList<LogStringBase> filterLogs() throws Exception {
        var logs = getLoadedLogs();

        var filteredLogs = getLoadedLogs().filtered(x->x.getLevel() >= debugLevel);

        if(messageFilter != ""){
            filteredLogs = filteredLogs.filtered(d->d.getMessage().indexOf(messageFilter) != -1? true:false);
        }

        if(fromDateFilter != null){
            filteredLogs = filteredLogs.filtered(d->d.getDate().after(Date.from(fromDateFilter.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        }

        if(toDateFilter != null){
            filteredLogs = filteredLogs.filtered(d->d.getDate().before(Date.from(toDateFilter.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        }

        var srtList = new SortedList<LogStringBase>(filteredLogs);
        srtList.comparatorProperty().bind(getContent().comparatorProperty());
        return srtList;
    }
}