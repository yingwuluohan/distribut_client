package com.self.transac.distribut_client.transactional.connection;

import com.alibaba.druid.pool.DruidConnectionHolder;
import com.alibaba.druid.pool.DruidPooledConnection;

public class DruidPooledConnectionAliChild extends DruidPooledConnection {
    public DruidPooledConnectionAliChild(DruidConnectionHolder holder) {
        super(holder);
    }
}
