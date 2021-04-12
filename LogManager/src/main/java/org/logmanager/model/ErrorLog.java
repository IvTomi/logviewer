package org.logmanager.model;

import org.logmanager.utils.Enums;

import java.text.ParseException;

public class ErrorLog extends LogStringBase {

    public ErrorLog(String logString) throws Exception {
        super(logString);
    }

    @Override
    protected boolean init(String logString) throws ParseException {
        setType(Enums.LogTypes.Error);
        setTextColor("red");
        setLevel(3);
        return true;
    }
}
