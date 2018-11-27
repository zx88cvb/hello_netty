package com.magic.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送一个请求 服务器返回Hello Netty
 * Created by Administrator on 2018/11/27.
 */
public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
        // 定义一对线程组
        // 主线程组 用于接受客户端连接 但不做任何处理
        EventLoopGroup parentGroup = new NioEventLoopGroup();

        // 从线程组 用于执行 处理
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            // netty 服务器创建 ServerBootstrap 是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)    // 设置主从线程组
                            .channel(NioServerSocketChannel.class)  // 设置nio双向通道
                            .childHandler(new HelloServerInitializer());    // 子处理器 childGroup

            // 启动server 并且设置8088为启动端口 同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            // 监听关闭的 channel 设置为同步
            channelFuture.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
