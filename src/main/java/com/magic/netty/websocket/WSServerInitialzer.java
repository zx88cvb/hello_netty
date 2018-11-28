package com.magic.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化器 channel注册后 会执行相应初始化方法
 * Created by Administrator on 2018/11/28.
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 通过SocketChannel 获得对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 通过管道添加handler
        // HttpServerCodec 由netty自己提供的助手类 可以理解为拦截器
        // 当请求服务端 需要做解码 响应到客户端做编码
        pipeline.addLast(new HttpServerCodec());

        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // HttpMessage 进行聚合   聚合成FullHttpRequest 或 FullHttpResponse
        // 对netty变成 都会使用到
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // ===================== 以上用于支持http协议 ==========================
        // websocket 服务器处理的协议 用于指定给客户端连接访问的路由 : /ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义
        pipeline.addLast(new ChatHandler());
    }
}
