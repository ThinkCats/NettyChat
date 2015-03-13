package com.chat.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by wanglei on 15-2-25.
 */
public class RedisPool {
    private static String ADDR="localhost";
    private static int PORT=6379;
    private static int MAX_IDLE=200;
    private static long MAX_WAIT=10000;
    private static int TIME_OUT=10000;
    private static boolean TEST_ON_BORROW=true;

    private static JedisPool jedisPool;

    static {
        try {
            JedisPoolConfig config=new JedisPoolConfig();
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool=new JedisPool(config,ADDR,PORT,TIME_OUT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static Jedis getJedis(){
        try {
            if (jedisPool!=null){
                Jedis jedis=jedisPool.getResource();
                return jedis;
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringKey(String key){
        String value=null;
        Jedis jedis=null;
        try {
            jedis=getJedis();
            value=jedis.get(key);
        }catch (Exception e){
            returnResource(jedis);
        }
        return value;
    }

    public static void returnResource(final Jedis jedis){
        if (jedis!=null){
            jedisPool.returnResource(jedis);
        }
    }

}
