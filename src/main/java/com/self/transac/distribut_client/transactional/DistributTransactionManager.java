package com.self.transac.distribut_client.transactional;

import com.alibaba.fastjson.JSONObject;

import com.self.transac.distribut_client.client.TransactionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DistributTransactionManager {




    private static TransactionClient nettyClient;

    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();
    private static ThreadLocal<Integer>  transactionCount = new ThreadLocal<>();

    private static ThreadLocal<DistributTransaction> current = new ThreadLocal<>();

    @Autowired
    public void setNettyClient( TransactionClient nettyClient ){
        DistributTransactionManager.nettyClient = nettyClient;
    }

    public static String getCurrentGroupId() {
        return currentGroupId.get();
    }

    public static void setCurrentGroupId(String groupId) {
        currentGroupId.set( groupId );
    }

    public static Integer getTransactionCount() {
        return transactionCount.get();
    }

    public static void setTransactionCount(Integer count) {
         transactionCount.set( count );
    }

    public static synchronized Integer addTransactionCount(){
        int i = (transactionCount.get() == null ? 0 : transactionCount.get()) + 1 ;
        transactionCount.set( i );
        return i;
    }

    public static Map<String , DistributTransaction> LB_TRANSACION_MAP = new HashMap<>();


    public static String createTxTransactionGroup(){
        String groupId = UUID.randomUUID().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "groupId" , groupId );
        jsonObject.put( "command" , "create" );
        nettyClient.send( jsonObject );
        System.out.println( "创建事务组" );
        return groupId;
    }
    /** 创建事务*/
    public static DistributTransaction createLbTransaction(String groupId ){
        String transactionId = UUID.randomUUID().toString();
        DistributTransaction lbTransaction = new DistributTransaction( groupId , transactionId );

        LB_TRANSACION_MAP.put( groupId , lbTransaction );
        System.out.println( "创建事务" );
        current.set( lbTransaction );
        return lbTransaction;
    }

    /** 提交事务*/
    public static DistributTransaction addLbTransaction(DistributTransaction lbTransaction , Boolean isEnd , TransactionType transactionType){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "groupId" , lbTransaction.getGroupId() );
        jsonObject.put( "transactionId" , lbTransaction.getTransactionId() );
        jsonObject.put( "transactionType" , transactionType );
        jsonObject.put( "commond" , "add" );
        jsonObject.put( "isEnd" , isEnd );
        jsonObject.put( "transactionCount" , getTransactionCount() );


        lbTransaction.setTransactionType( transactionType );
        nettyClient.send( jsonObject );

        return null;
    }




    //通过groupID 拿到事务
    public static DistributTransaction getLbTransaction(String groupId ){
        return LB_TRANSACION_MAP.get( groupId );
    }

    /**获取当前线程的事务 */
    public static DistributTransaction getCurrent(){
        return current.get();
    }













}
