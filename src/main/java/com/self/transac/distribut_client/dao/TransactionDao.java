package com.self.transac.distribut_client.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionDao {

    void updateInfo( Integer id );


}
