package org.nouk.dataview.core.datasource;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.io.Serializable;


public class DataViewDataSourceSingle implements Serializable {
    @Getter
    @Setter
    private DataSource dataSource;

    //构造器私有化
    private DataViewDataSourceSingle(){
    }

    private static  DataViewDataSourceSingle dataViewDataSourceSingle;

    //调用该方法就会执行SingletonHander.instance.singleton4;执行这个方法就会
    //加载内部枚举，加载内部枚举类然后会创建一个枚举项，一个枚举项就是一个枚举的实例
    //所以在创建一个枚举项时会调用内部枚举的构造方法，从而创建一个单例
    public static DataViewDataSourceSingle getInstance(){
        return SingletonHander.instance.sourceSingle;
    }

    private enum SingletonHander{
        instance;//枚举项，每个枚举项只会创建一个
        private final DataViewDataSourceSingle sourceSingle;

        SingletonHander() {
            sourceSingle = new DataViewDataSourceSingle();
        }
    }

}
