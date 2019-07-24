package com.self.transac.distribut_client.client;

import com.alibaba.fastjson.JSONObject;
import com.self.transac.distribut_client.common.de_en_code.DataDecoder;
import com.self.transac.distribut_client.common.de_en_code.DataEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TransactionClientLinkTest implements InitializingBean {

    public static TransactionClientHandlerLinkTest clientTest;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            start( "10.200.16.36" , 8888 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void start( String hostName , Integer port ) throws InterruptedException {
        clientTest = new TransactionClientHandlerLinkTest();
        EventLoopGroup eventGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group( eventGroup ).channel(NioSocketChannel.class ).
                option(ChannelOption.TCP_NODELAY , true ).
                handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel( SocketChannel socketChannel ) throws Exception{
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast( new LoggingHandler( LogLevel.DEBUG ));

                        pipeline.addLast( "decoder" , new DataDecoder( Object.class ));
                        pipeline.addLast( "encoder" , new DataEncoder( Object.class ));
                        pipeline.addLast( "handler" , clientTest );

                    }
                });
        ChannelFuture channelFuture = bootstrap.connect( hostName , port ).sync();

        System.out.println( "客户端启动完毕：" + channelFuture.getClass() );


    }

    public void send(JSONObject jsonObject ){
        clientTest.call( jsonObject );
    }
}
