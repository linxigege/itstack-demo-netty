package org.itstack.demo.netty.aio;

import io.netty.channel.ChannelHandlerAdapter;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class ChannelHandler2 {

    private AsynchronousSocketChannel channel;
    private Charset charset;

    public ChannelHandler2(AsynchronousSocketChannel channel,Charset charset){
        this.channel = channel;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg){
        byte[] bytes = msg.toString().getBytes(charset);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }
}