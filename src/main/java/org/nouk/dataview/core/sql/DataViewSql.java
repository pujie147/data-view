package org.nouk.dataview.core.sql;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.nouk.dataview.core.display.Input;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class DataViewSql {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(Input.TypeAdapterFactory)
            .excludeFieldsWithoutExposeAnnotation()
            .create();


    private StringBuilder sql;
    @Expose
    private Map<String, Input> forms = new LinkedHashMap<>();
    @Expose
    private Map<String, Object> params = new HashMap<>(); // form parameters from client

    public static DataViewSql parsingSql(StringBuilder rawSql){
        final DataViewSql dataViewSql = new DataViewSql();
        final LinkedHashMap<String, Input> stringInputLinkedHashMap = Input.extractSimpleQueryForm(rawSql);
        dataViewSql.setForms(stringInputLinkedHashMap);
        dataViewSql.setSql(rawSql);
        return dataViewSql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataViewSql dataViewSql = (DataViewSql) o;

        if (params != null ? !params.equals(dataViewSql.params) : dataViewSql.params != null) {
            return false;
        }if (StringUtils.isNotEmpty(sql)? sql.equals(dataViewSql) : false) {
            return false;
        }
        return forms != null ? forms.equals(dataViewSql.forms) : dataViewSql.forms == null;

    }

    public static DataViewSql fromJson(String json) {
        DataViewSql gui = gson.fromJson(json, DataViewSql.class);
        return gui;
    }

    @Override
    public int hashCode() {
        int result = params != null ? params.hashCode() : 0;
        result = 31 * result + (forms != null ? forms.hashCode() : 0);
        return result;
    }

    public String toJson() {
        return gson.toJson(this);
    }

}
