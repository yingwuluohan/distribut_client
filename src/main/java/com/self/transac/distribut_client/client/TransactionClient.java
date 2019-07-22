package com.self.transac.distribut_client.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TransactionClient implements InitializingBean {

    public static TransactionClientHandler client;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    static {
        try {
            start( "127.0.0.1" , 8888 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void start( String hostName , Integer port ) throws InterruptedException {
        client = new TransactionClientHandler();

        EventLoopGroup eventGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group( eventGroup ).channel(NioSocketChannel.class ).
                option(ChannelOption.TCP_NODELAY , true ).
                handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel( SocketChannel socketChannel ) throws Exception{
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast( "decoder" , new StringDecoder());
                        pipeline.addLast( "encoder" , new StringEncoder());
                        pipeline.addLast( "handler" , client );

                    }
                });
        ChannelFuture channelFuture = bootstrap.connect( hostName , port ).sync();

        System.out.println( "客户端启动完毕：" + channelFuture.getClass() );


    }

    public void send(JSONObject jsonObject ){
        client.call( jsonObject );
    }
}
