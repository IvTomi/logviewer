package org.logmanager.model;

import org.logmanager.utils.Enums;
import org.logmanager.utils.Enums;

import java.text.ParseException;

public class DebugLog extends LogStringBase {

    public DebugLog(String logString) throws Exception {
        super(logString);
    }

    @Override
    protected boolean init(String logString) throws ParseException {
        setType(Enums.LogTypes.Debug);
        setTextColor("gray");
        setLevel(1);
        return true;
    }
}
