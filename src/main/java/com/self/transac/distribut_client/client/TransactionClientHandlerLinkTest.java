package com.self.transac.distribut_client.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.transac.distribut_client.transactional.DistributTransaction;
import com.self.transac.distribut_client.transactional.DistributTransactionManager;
import com.self.transac.distribut_client.transactional.TransactionType;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class TransactionClientHandlerLinkTest extends SimpleChannelInboundHandler<String> {


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
        System.out.println( "************channelReadTest 客户端收到消息:***************" + msg );
        //有服务端告知状态
        System.out.println( "channelReadTest 当前线程："+Thread.currentThread().getName()  );



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

        System.out.println( "向服务端发送：" + data);
        return channelFuture;
    }
}
