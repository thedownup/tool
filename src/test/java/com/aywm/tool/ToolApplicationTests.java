package com.aywm.tool;

import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.RetCode;
import com.tictactec.ta.lib.meta.TaFuncSignature;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

class ToolApplicationTests {

    @Test
    public void contextLoads() {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup wokerGroup = new NioEventLoopGroup();
//
//        try{
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))
//                    .childHandler(new Websocker());
//
//            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
//            channelFuture.channel().closeFuture().sync();
//        }finally {
//            bossGroup.shutdownGracefully();
//            wokerGroup.shutdownGracefully();
//        }
    }

}
