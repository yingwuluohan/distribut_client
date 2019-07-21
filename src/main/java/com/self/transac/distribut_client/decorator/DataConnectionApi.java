package com.self.transac.distribut_client.decorator;


import com.self.transac.distribut_client.common.DataConnetion;

@FunctionalInterface
public interface DataConnectionApi  {

    /** 获取数据库连接 */
    DataConnetion getConnection(Long connectionId );

    default void findConnectionInfo(){

    }

}
