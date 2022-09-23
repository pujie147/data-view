package org.nouk.dataview.core.sql;

import lombok.Data;
import org.nouk.dataview.core.display.Input;

import java.util.LinkedHashMap;

@Data
public class DataViewSql {
    private StringBuilder sql;
    private LinkedHashMap<String, Input> inputs;

    public static DataViewSql parsingSql(StringBuilder rawSql){
        final DataViewSql dataViewSql = new DataViewSql();
        final LinkedHashMap<String, Input> stringInputLinkedHashMap = Input.extractSimpleQueryForm(rawSql);
        dataViewSql.setInputs(stringInputLinkedHashMap);
        dataViewSql.setSql(rawSql);
        return dataViewSql;
    }



}
