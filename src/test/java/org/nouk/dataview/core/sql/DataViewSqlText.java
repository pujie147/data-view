package org.nouk.dataview.core.sql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataViewSqlText {
    @Test
    public void parsingSqlTest(){
        final String s = "select id,name,age from member where 1=1 $SelectLong{'defaultValue':111,'name':'loginName','selectListSql':'select id as userId from member','text':'and id=? '}";
        final DataViewSql dataViewSql = DataViewSql.parsingSql(new StringBuilder(s));
        System.out.println(dataViewSql);
    }
}
