package org.itstack.demo.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class NettyServer2 {

    public static void main(String[] args) {
        new NettyServer2().bind(7379);
    }

    private void bind(int port) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        // NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SYstemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors()*2));
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());
            ChannelFuture future = b.bind(port).sync();
            System.out.println("itstack-demo-netty server start done. { konw }");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }
}