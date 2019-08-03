package com.self.transac.distribut_client.common.de_en_code;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundBuffer;

/**
 * @Created by yingwuluohan on 2019/7/29.
 * @Company
 *
 * 防socket 字节流攻击解包过程
 *
 */
public class SocketHitHackDecode {

    private int BASE_LENTH = 2 + 4 + 4 + 2 ;

//    public Object decode(ChannelHandlerContext arg , Channel channel ,
//                         ChannelBuffer buffer ){
//        //可读长度必须大于基本长度
//        if( buffer.readbyte() >= BASE_LENTH ){
//            //防止socket攻击
//            //如果内存中字节的长度大于 2018 ，则skipBytes
//
//        }
//
//
//    }










}
