package com.liu.utils;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;

/**
 * BaseDao的目地就是去优化dao实现类
 */
public abstract class BaseDao {
    public QueryRunner queryRunner;
    public int pageSize = 4;

    public BaseDao() {
        queryRunner = new QueryRunner(MyDataSource.getDataSource());
    }
    public RowProcessor getProcessor(){
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        return processor;
    }
}
