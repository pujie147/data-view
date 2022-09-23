package org.nouk.dataview.core.display.ui;

import org.nouk.dataview.core.display.Input;

import java.util.List;

public class CheckBoxLong extends SqlSingleList<List<Long>> {

    public CheckBoxLong() {

    }

    public CheckBoxLong(String name, List<Long> defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }
}
