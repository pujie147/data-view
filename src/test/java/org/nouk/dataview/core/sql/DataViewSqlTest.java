package org.nouk.dataview.core.sql;

import org.junit.jupiter.api.Test;
import org.nouk.dataview.core.DataViewContext;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DataViewSqlTest {
    @Test
    public void parsingSqlTest(){
        final String s = "select id,name,age from member where 1=1 $SelectLong{'defaultValue':111,'name':'loginName','selectListSql':'select id as userId from member','text':'and id=?','selectList':{'n1':'v1'}}";
        final DataViewSql dataViewSql = DataViewSql.parsingSql(new StringBuilder(s));
        System.out.println(dataViewSql);
        DataViewContext.getInstance().addDataView("sss",s);

        String sss = DataViewContext.getInstance().getInputsView("sss");

        System.out.println(sss);


    }
}
