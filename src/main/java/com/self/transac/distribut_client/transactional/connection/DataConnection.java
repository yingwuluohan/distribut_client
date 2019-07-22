package com.self.transac.distribut_client.transactional.connection;

import java.sql.SQLException;

/**
 * @Created by yingwuluohan on 2019/7/19.
 * data connection owner , the kind of mysql and mongo and redis
 * which can commit ,roback
 *
 */
public class DataConnection extends BaceConnection {




    @Override
    public void commit(Object o) throws SQLException {

    }

    @Override
    public void rollback(Object o) throws SQLException {

    }
}
