package org.nouk.dataview.core.display.ui;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SelectLong extends SqlSingleList<Long> {

    public SelectLong() {

    }

    public SelectLong(String name, Long defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }

}
