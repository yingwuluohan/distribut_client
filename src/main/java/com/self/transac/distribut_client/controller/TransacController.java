package com.self.transac.distribut_client.controller;


import com.self.transac.distribut_client.client.TransactionClient;
import com.self.transac.distribut_client.service.DisTestService;
import com.self.transac.distribut_client.service.TransacService;
import com.self.transac.distribut_client.transactional.DistributTransaction;
import com.self.transac.distribut_client.transactional.connection.DistributConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequestMapping("/dis")
@Controller
public class TransacController {


    @Autowired
    private DisTestService disTestService;
    @Autowired
    private TransacService transacService;

    @Autowired
    private DataSource dataSource;

    @ResponseBody
    @RequestMapping( value="/transac" ,method= RequestMethod.GET )
    public String goHttpChat( HttpServletRequest request ){

        try {
            disTestService.updateDisInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "chat";
    }

    /**
     * 与服务端建立连接
     * @return
     */
    @ResponseBody
    @RequestMapping( value="/initclient" ,method= RequestMethod.GET )
    public String initNettyClient() throws Exception {
        try {

            System.out.println( " RPC get 请求，线程是:" + Thread.currentThread().getName() );
            transacService.rpcUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception( e );
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping( value="/testLock" ,method= RequestMethod.GET )
    public void testLockCut() throws Exception {
        try {
            Connection connection = dataSource.getConnection();
            DistributTransaction transaction = new DistributTransaction( "2q4" , "1234234" );

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println( "线程启动休眠" );
                        Thread.sleep( 5000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println( "线程启动解锁" );

                    transaction.getTask().signalTask();
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                                       try {
                        DistributConnection connectionTemp = new DistributConnection( connection , transaction );
                        System.out.println( "数据库连接：" + connection.isClosed() );

                        transaction.getTask().waitTask( );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }).start();







        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception( e );
        }
    }




}
