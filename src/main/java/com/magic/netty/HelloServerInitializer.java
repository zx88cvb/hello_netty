package com.magic.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器 channel注册后 会执行相应初始化方法
 * Created by Administrator on 2018/11/27.
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel>{
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过SocketChannel 获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();

        // 通过管道添加handler
        // HttpServerCodec 由netty自己提供的助手类 可以理解为拦截器
        // 当请求服务端 需要做解码 响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 添加自定义的助手类 返回hello netty
        pipeline.addLast("customHandler", new CustomHandler());
    }
}
