package com.chat.demo;

import com.chat.demo.server.Server;
import org.junit.Test;

/**
 * Created by wang on 15-3-13.
 */

public class TestServer {

    @Test
    public void testServer(){
        Server server=new Server();
        server.run();
    }
}
