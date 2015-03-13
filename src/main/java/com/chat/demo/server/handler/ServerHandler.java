package com.chat.demo.server.handler;

import com.chat.demo.common.Constans;
import com.chat.redis.RedisPool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wang on 15-3-13.
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    private Logger logger= Logger.getLogger(ServerHandler.class);

    private Jedis jedis=RedisPool.getJedis();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        List<Channel> list=new ArrayList<Channel>();
        list.add(ctx.channel());
        Constans.channels.put("clients",list);
/*        Session session=new Session();
        session.setId(1);
        session.setChannel(ctx.channel());
        logger.info("A Client Connected " + ctx.channel().remoteAddress());
        logger.info("CTX:"+ctx);
        //add  channel to redis
        byte[] bytes= SerialUtil.serialize(session);
        jedis.lpush("clients".getBytes(), bytes);*/
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("Received Message:" + o.toString());
        channelHandlerContext.channel().writeAndFlush("SERVER REPLY: hello," + o.toString());
        // begin broadcast
        List<Channel> list=Constans.channels.get("clients");
        for (Channel channel:list){
            channel.writeAndFlush("BROADCAST:"+o.toString());
        }
      /*  List<byte[]> list=jedis.lrange("clients".getBytes(),0,-1);
        for (int i=0;i<list.size();i++){
            Session session= (Session) SerialUtil.unserialize(list.get(i));
            session.getChannel().writeAndFlush("BROADCAST:"+o.toString());
        }*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
