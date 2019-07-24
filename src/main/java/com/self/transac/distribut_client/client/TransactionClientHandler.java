package com.self.transac.distribut_client.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.transac.distribut_client.transactional.DistributTransaction;
import com.self.transac.distribut_client.transactional.DistributTransactionManager;
import com.self.transac.distribut_client.transactional.TransactionType;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;


public class TransactionClientHandler extends SimpleChannelInboundHandler<String> {


    private ChannelHandlerContext context;


    @Override
    public void channelActive( ChannelHandlerContext ctx ){
        System.out.println( "**************客户端建立连接***************"+ctx.channel().remoteAddress() );
        context = ctx;
        ctx.fireChannelActive();
    }

    /** */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx1 , Object msg ) throws Exception{
        System.out.println( "************channelRead 客户端收到消息:***************" + msg );
        //有服务端告知状态
        System.out.println( "接收数据" + msg );
        JSONObject jsonObject = JSON.parseObject( msg.toString() );
        String groupId = jsonObject.getString( "groupId" );
        String command = jsonObject.getString( "command" );

        System.out.println( "接收commond:" + command );
        DistributTransaction txTransaction = DistributTransactionManager.getLbTransaction( groupId );

        if( command.equals( "rollback" )){
            txTransaction.setTransactionType(TransactionType.rollback );
            //唤醒
            txTransaction.getTask().signalTask();
        }else if( command.equals( "commit" )){
            txTransaction.setTransactionType(TransactionType.comit );
            //唤醒
            txTransaction.getTask().signalTask();
        }else if( command.equals( "add" )){
            txTransaction.setTransactionType(TransactionType.add );
        }

    }
    /** */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println( "************channelRead0 客户端收到消息:***************" + msg );

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println( "-----------client exception ------------" );
        cause.printStackTrace();
        ctx.close();
    }

    public synchronized Object call( JSONObject data ){
        ChannelFuture channelFuture = context.channel().writeAndFlush( data.toJSONString() );
//        context.channel().writeAndFlush(  "123456789" );
        System.out.println( "向服务端发送：" + data);
        return channelFuture;
    }
}
