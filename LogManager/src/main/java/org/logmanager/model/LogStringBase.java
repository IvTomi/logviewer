package org.logmanager.model;

import javafx.beans.property.SimpleStringProperty;
import org.logmanager.utils.Enums;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class LogStringBase {

    protected final SimpleStringProperty logdate;

    public String getLogdate() {
        return logdate.get();
    }
    protected void setLogdate(String date) {
        logdate.set(date);
    }

    public String getDay() {
        return new SimpleDateFormat("yyyy/MM/dd").format(getDate()).toString();
    }

    public String getTime() {
        return new SimpleDateFormat("hh:mm:ss a").format(getDate()).toString();
    }

    public Date getDate() {
        return date;
    }

    protected void setDate(Date date) {
        this.date = date;
    }

    protected Date date;

    public String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    protected String message;

    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    protected int level;

    public Enums.LogTypes getType() {
        return type;
    }

    protected void setType(Enums.LogTypes type) {
        this.type = type;
    }

    protected Enums.LogTypes type;

    public String getTextColor() {
        return textColor;
    }

    protected void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    protected String textColor;

    public LogStringBase(String logString) throws Exception {
        this.logdate = new SimpleStringProperty("");
        init(logString);
        stripDate(logString);
        stripMessage(logString);
    }

    public static LogStringBase getLogStringObject(String logString) throws Exception {
        switch (getLogTypeFromString(logString)){
            case Error:
                return new ErrorLog(logString);
            case Info:
                return new InfoLog(logString);
            case Debug:
                return new DebugLog(logString);
            default:
                throw new IllegalStateException("Unexpected value: " + getLogTypeFromString(logString));
        }
    }

    protected static Enums.LogTypes getLogTypeFromString(String logString) throws Exception {
        var strippedString = logString.substring(24,30);
        if(strippedString.indexOf("Info") != -1? true:false){
            return Enums.LogTypes.Info;
        }
        if(strippedString.indexOf("Error") != -1? true:false){
            return Enums.LogTypes.Error;
        }
        if(strippedString.indexOf("Debug") != -1? true:false){
            return Enums.LogTypes.Debug;
        }

        throw new Exception("Could not determine log type");
    }

    protected abstract boolean init(String logString) throws Exception;

    protected void stripDate(String logString) throws ParseException {
        Date dt = new SimpleDateFormat("yyyy/MM/dd, hh:mm:ss a").parse(logString.substring(0,23));
        setDate(dt);
        setLogdate(getDay() + "\t" + getTime());
    }

    protected void stripMessage(String logString) throws ParseException {
        var msg = logString.substring(30,logString.length());
        if(msg.charAt(0) == ' '){
            msg = msg.substring(1);
        }
        setMessage(msg);
    }
}
