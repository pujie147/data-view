package org.nouk.dataview.core.display.ui;


public class SelectLong extends SqlSingleList<Long> {

    public SelectLong() {

    }

    public SelectLong(String name, Long defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }
}
