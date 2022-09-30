package org.nouk.dataview.core;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.nouk.dataview.core.sql.DataViewSql;

import java.io.Serializable;
import java.util.*;

public class DataViewContext implements Serializable {
    @Getter
    private Map<String, DataViewSql> dataViewSqlMap = Maps.newHashMap();

    //构造器私有化
    private DataViewContext(){
    }
    private static DataViewContext dataViewDataSourceSingle;

    public static DataViewContext getInstance(){
        return DataViewContext.SingletonHander.instance.sourceSingle;
    }
    private enum SingletonHander{
        instance;//枚举项，每个枚举项只会创建一个
        private final DataViewContext sourceSingle;
        SingletonHander() {
            sourceSingle = new DataViewContext();
        }
    }

    public void addDataView(String dataViewKey, String script){
        final DataViewSql dataViewSql = DataViewSql.parsingSql(new StringBuilder(script));
        dataViewSqlMap.put(dataViewKey,dataViewSql);
    }

    public String getInputsView(String dataViewKey){
        if (DataViewContext.getInstance().dataViewSqlMap.containsKey(dataViewKey)) {
            final DataViewSql dataViewSql = DataViewContext.getInstance().dataViewSqlMap.get(dataViewKey);
            return dataViewSql.toJson();
        }
        return null;
    }

//    public Map<String, List<String>> executSql(String dataViewKey, Map<String,Input> params) throws SQLException {
//        if (DataViewContext.getInstance().dataViewSqlMap.containsKey(dataViewKey)) {
//            final DataViewSql dataViewSql = DataViewContext.getInstance().dataViewSqlMap.get(dataViewKey);
//            String sql = dataViewSql.buildSql(params);
//            ResultSet resultSet = null;
//            try (final Connection connection = DataViewDataSourceSingle.getInstance().getDataSource().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
//                for (String key : params.keySet()) {
//                    if (Objects.nonNull(params.get(key).getValue())) {
//                        final Input input = dataViewSql.getInputsView().get(key);
//                        input.setPreparedStatementParams(0,ps);
//                    }
//                }
//                resultSet = ps.executeQuery();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//            Map<String, List<String>> map = new HashMap<String, List<String>>();
//            ResultSetMetaData rsmd = resultSet.getMetaData();
//            int count = rsmd.getColumnCount();
//            //先生成几个list对象
//            @SuppressWarnings("unchecked")
//            List<String>[] lists = new List[count];
//            for (int i = 0; i < lists.length; i++) {
//                lists[i] = new ArrayList<String>();
//                map.put(rsmd.getColumnName(i + 1), lists[i]);
//            }
//            /**
//             * 这里是获取的一条一条
//             */
//            while (resultSet.next()) {
//                for (int i = 0; i < lists.length; i++) {
//                    lists[i].add(resultSet.getString(i + 1));
//                }
//            }
//            return map;
//        }
//        return null;
//    }

}
