package com.self.transac.distribut_client.decorator;


import com.self.transac.distribut_client.common.DataConnetion;

@FunctionalInterface
public interface DataConnectionApi  {

    DataConnetion getConnection();

    default void findConnectionInfo(){

    }

}
