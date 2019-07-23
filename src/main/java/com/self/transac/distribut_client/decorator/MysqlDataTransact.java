package com.self.transac.distribut_client.decorator;

import com.self.transac.distribut_client.transactional.connection.BaceConnection;

import java.sql.SQLException;

/**
 * @Created by yingwuluohan on 2019/7/19.
 * @Company 北京云知声技术有限公司
 */
public class MysqlDataTransact extends DataTransact {

    private BaceConnection baceConnection;


    public MysqlDataTransact( Transact transact ) throws SQLException {

        super.manageTransaction( baceConnection );
        this.manageTransaction( baceConnection );
    }

    @Override
    public void manageTransaction(BaceConnection baceConnection) throws SQLException {
        baceConnection.commit( baceConnection );
    }













}
