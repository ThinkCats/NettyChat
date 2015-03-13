package com.chat.demo.server;

import com.chat.demo.server.handler.ChildHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

/**
 * Created by wang on 15-3-13.
 */
public class Server {
    private Logger logger=Logger.getLogger(Server.class);
    private static String IP="127.0.0.1";
    private static int PORT= 8888;
    private int GROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
    private int THREADSIZE= 4;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(GROUPSIZE);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(THREADSIZE);

    public void run(){
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChildHandler());
        try {
            ChannelFuture future = bootstrap.bind(IP, PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            logger.info(" finally...");
            shutdown();
        }
        logger.info(" Server Started ...");
    }

    public void shutdown(){
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}



