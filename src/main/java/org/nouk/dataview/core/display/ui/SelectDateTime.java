package org.nouk.dataview.core.display.ui;

import org.nouk.dataview.core.display.Input;

import java.util.Date;

public class SelectDateTime extends Input<Date> {


    public SelectDateTime() {

    }

    public SelectDateTime(String name, Date defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }
}
