package org.itstack.demo.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer2 extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("url report start:");
        System.out.println("url report message: have a client connect current server");
        System.out.println("url report ip: " + ch.localAddress().getHostString());
        System.out.println("url report port:" + ch.localAddress().getPort());
        System.out.println("url report finish");

        ch.pipeline().addLast(new MyServerHandler());
    }
}