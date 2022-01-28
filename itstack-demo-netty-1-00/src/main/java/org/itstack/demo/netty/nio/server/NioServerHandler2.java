package org.itstack.demo.netty.nio.server;

import org.itstack.demo.netty.nio.ChannelAdapter;
import org.itstack.demo.netty.nio.ChannelHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class NioServerHandler2 extends ChannelAdapter{

    public NioServerHandler2(Selector selector,Charset charset) {
        super(selector,charset);
    }
    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            System.out.println("url reposrtAddress:" +ctx.channel().getLocalAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd"));
        ctx.writeAndFlush("hi iam had receive your message success!");
    }
}
