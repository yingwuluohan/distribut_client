package com.self.transac.distribut_client.service;


import com.self.transac.distribut_client.dao.TransactionDao;
import com.self.transac.distribut_client.transactional.annotation.DistributTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisTestService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private TransacService transacService;


    @DistributTransactional( isStart = true )
    public void updateDisInfo(){

        transactionDao.updateInfo( 12 );
        System.out.println( "--------------" );
        transacService.updateItem();
        int num = 0 ;
//        int result = 100/num;

    }


}
