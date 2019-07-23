package com.self.transac.distribut_client.service;


import com.self.transac.distribut_client.common.modle.TransactionInfo;
import com.self.transac.distribut_client.dao.TransactionDao;
import com.self.transac.distribut_client.transactional.annotation.DistributTransactional;
import com.self.transac.distribut_client.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
public class DisTestService {

    private ThreadLocal<TransactionInfo > threadLocal = new ThreadLocal();
    private HttpRequestUtil request = new HttpRequestUtil();

//    @Autowired
    private TransactionDao transactionDao;

//    @Autowired
    private TransacService transacService;


//    @DistributTransactional( isStart = true )
    public void updateDisInfo(){

//        request.sendGet( "http://10.200.8.173:8090/dis/initclient" , 5000 );
        transactionDao.updateInfo( 12 );
        System.out.println( "--------------" );
        transacService.updateItem();
        int num = 0 ;
//        int result = 100/num;

    }

    @Transactional
    public void addInfo(){
        TransactionInfo info = new TransactionInfo();
//        info.setConnection( );
//        threadLocal.set( );

    }

}
