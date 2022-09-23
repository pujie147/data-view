package org.nouk.dataview.core.display.ui;

import org.nouk.dataview.core.display.Input;

import java.util.List;

public class CheckBoxString extends SqlSingleList<List<String>> {

    public CheckBoxString() {

    }

    public CheckBoxString(String name, List<String> defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }
}
