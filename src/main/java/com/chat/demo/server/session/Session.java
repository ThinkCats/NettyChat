package com.chat.demo.server.session;

import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * Created by wang on 15-3-13.
 */
public class Session implements Serializable {
    private int id;
    private Channel channel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
