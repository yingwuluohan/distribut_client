package com.self.transac.distribut_client.decorator;

import com.self.transac.distribut_client.transactional.connection.BaceConnection;
import com.self.transac.distribut_client.transactional.connection.DistributConnection;

import java.sql.SQLException;

/**
 * @Created by yingwuluohan on 2019/7/19.
 */
public class DataTransact implements Transact {

    private Transact transact;

    @Override
    public void manageTransaction(BaceConnection baceConnection) throws SQLException {
        baceConnection.commit( transact );
    }


















}
