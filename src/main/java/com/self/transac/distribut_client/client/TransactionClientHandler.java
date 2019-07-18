package com.self.transac.distribut_client.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.transac.distribut_client.transactional.DistributTransaction;
import com.self.transac.distribut_client.transactional.DistributTransactionManager;
import com.self.transac.distribut_client.transactional.TransactionType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static com.self.transac.distribut_client.transactional.DistributTransactionManager.*;

public class TransactionClientHandler extends ChannelInboundHandlerAdapter {


    private ChannelHandlerContext context;
    public void channelActive( ChannelHandlerContext ctx ){
        System.out.println( ctx );
        context = ctx;
    }



    public synchronized void channelRead(ChannelHandlerContext ctx , Object msg ) throws Exception{
        //有服务端告知状态
        System.out.println( "接收数据" + msg );
        JSONObject jsonObject = JSON.parseObject( (String) msg );
        String groupId = jsonObject.getString( "groupId" );
        String command = jsonObject.getString( "command" );

        System.out.println( "接收commond:" + command );
        DistributTransaction txTransaction = DistributTransactionManager.getLbTransaction( groupId );

        if( command.equals( "rollback" )){
            txTransaction.setTransactionType(TransactionType.rollback );
        }else if( command.equals( "commit" )){
            txTransaction.setTransactionType(TransactionType.comit );
        }
        //唤醒
        txTransaction.getTask().signalTask();

    }


    public synchronized Object call( JSONObject data ){
        context.writeAndFlush( data.toJSONString() );
        return null;
    }
}
