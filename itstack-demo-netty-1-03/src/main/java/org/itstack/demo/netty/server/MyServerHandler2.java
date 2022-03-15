package org.itstack.demo.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler2  extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx)throws  Exception{
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("url report start");
        System.out.println("url report message: have one client url resource connect current server");
        System.out.println("url report ip ");
        System.out.println("url report port ");
        System.out.println("url report finish");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        System.out.println("receive message!!");
    }
}