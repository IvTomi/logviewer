package org.logmanager.model;

import org.logmanager.utils.Enums;

import java.text.ParseException;

public class InfoLog extends LogStringBase {

    public InfoLog(String logString) throws Exception {
        super(logString);
    }

    @Override
    protected boolean init(String logString) throws ParseException {
        setType(Enums.LogTypes.Info);
        setTextColor("black");
        setLevel(2);
        return true;
    }
}
