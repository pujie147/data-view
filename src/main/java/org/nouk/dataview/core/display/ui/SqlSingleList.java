package org.nouk.dataview.core.display.ui;

import com.google.common.collect.Maps;
import lombok.Data;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.apache.commons.lang3.StringUtils;
import org.nouk.dataview.core.datasource.DataViewDataSourceSingle;

import java.io.StringReader;
import java.sql.*;
import java.util.List;
import java.util.Map;

@Data
public abstract class SqlSingleList<T> extends SelectList<T> {
    protected String selectListSql;

    @Override
    public void inputGuiPreTask() {
        if (StringUtils.isEmpty(selectListSql)) {
            return;
        }
        List<SelectItem> selectItems = null;
        try {
            final CCJSqlParserManager parserManager = new CCJSqlParserManager();
            Select select = (Select) parserManager.parse(new StringReader(selectListSql));
            selectItems = ((PlainSelect) select.getSelectBody()).getSelectItems();
            if (0 > selectItems.size() || selectItems.size() > 2) {
                throw new Exception("select list params limit 1-2");
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try (final Connection connection = DataViewDataSourceSingle.getInstance().getDataSource().getConnection(); PreparedStatement ps = connection.prepareStatement(selectListSql)){
            Map<String,String> selectListData = Maps.newHashMap();
            String keyAlias;
            String valueAlias;
            if (selectItems.size()==1) {
                final String lastToken = selectItems.get(0).getASTNode().jjtGetLastToken().toString();
                keyAlias = lastToken;
                valueAlias = lastToken;
            }else{
                keyAlias = selectItems.get(0).getASTNode().jjtGetLastToken().toString();
                valueAlias = selectItems.get(1).getASTNode().jjtGetLastToken().toString();
            }
            try (ResultSet rs = ps.executeQuery();) {
                selectListData.put(rs.getString(keyAlias),rs.getString(valueAlias));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
