package com.self.transac.distribut_client.service;


import com.self.transac.distribut_client.dao.TransacSecendDao;
import com.self.transac.distribut_client.transactional.annotation.DistributTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacService {

    @Autowired
    private TransacSecendDao transacSecendDao;

    @DistributTransactional( )
    public Boolean updateItem(){
        transacSecendDao.updateItem();
        int i = 100;
        int a = i/0;
        return true;
    }







}