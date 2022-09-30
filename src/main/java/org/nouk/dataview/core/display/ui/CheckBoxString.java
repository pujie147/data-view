package org.nouk.dataview.core.display.ui;

import java.util.ArrayList;

public class CheckBoxString extends SqlSingleList<ArrayList<String>> {

    public CheckBoxString() {

    }

    public CheckBoxString(String name, ArrayList<String> defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }

}
