package com.self.transac.distribut_client.common.de_en_code;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


public class FrameDecoder extends ByteToMessageDecoder{
    int lengthFeildLength = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        if(in.readableBytes()<lengthFeildLength)return null;//
        int index = in.readerIndex();
        int len = in.readInt();//解析次数包中对象的大小
        if(in.readableBytes()<len){//数据包的内容不全
            in.readerIndex(index);//重置readerIndex
            return null;
        }
        return in.readRetainedSlice(len);//截取完整的一个转码对象。
    }
}
