package com.self.transac.distribut_client.transactional.connection;

import com.self.transac.distribut_client.decorator.Transact;

import java.sql.SQLException;

/**
 * @Created by yingwuluohan on 2019/7/19.
 * @Company 北京云知声技术有限公司
 */
public abstract class BaceConnection<T> {


    public abstract void commit( T t ) throws SQLException;

    public abstract void rollback(T t  ) throws SQLException;

}
