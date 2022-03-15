package org.itstack.demo.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer2 extends ChannelInitializer<SocketChannel>{


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //decode machine
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // base used string change line sym, so this equals linebasedFrameDecoder
        // e.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,false,Delimiters.lineDelimiter()));
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // in the channel we add my receive data impl method
        channel.pipeline().addLast(new MyServerHandler());
    }
}