package com.self.transac.distribut_client.decorator;

import com.self.transac.distribut_client.transactional.DistributTransaction;
import com.self.transac.distribut_client.transactional.connection.BaceConnection;
import com.self.transac.distribut_client.transactional.connection.DistributConnection;

import java.sql.SQLException;

/**
 * @Created by yingwuluohan on 2019/7/19.
 * @Company 北京云知声技术有限公司
 */
@FunctionalInterface
public interface Transact {

    void manageTransaction(BaceConnection baceConnection  ) throws SQLException;


    default DistributTransaction getTransaction(String transactionId, String groupId ){
        return new DistributTransaction( groupId , transactionId );
    }


}
