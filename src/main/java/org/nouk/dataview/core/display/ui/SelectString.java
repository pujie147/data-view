package org.nouk.dataview.core.display.ui;

import org.nouk.dataview.core.display.Input;

public class SelectString extends SqlSingleList<String> {

    public SelectString() {

    }

    public SelectString(String name, String defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }
}
