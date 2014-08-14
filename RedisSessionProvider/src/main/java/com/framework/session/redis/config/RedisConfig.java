package com.framework.session.redis.config;

import com.frameworkLog.factory.LogFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

/**
 *
 * @author nelson
 */
public class RedisConfig {

    private RedisConfig() {
    }
    private static final Logger logger = LogFactory.getInstance().getLogger(RedisConfig.class);
    private static Jedis sessionJedis;
    private static  String IP = "192.168.1.105";
    private static  int PORT = 6379;
    private static String PASSWORD="123456";
    private static int TIMEOUT = 10000;

    static {
        Reader reader = null;
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("redissession.properties");
        Properties property = new Properties();
        try {
            reader = new InputStreamReader(is, "UTF-8");
            property.load(reader);
            IP = property.getProperty("ip");
            PORT = Integer.parseInt(property.getProperty("port"));
            PASSWORD = property.getProperty("password");
            TIMEOUT = Integer.parseInt(property.getProperty("connectionTimeout"));
        } catch (Exception ex) {
            throw new RuntimeException("加载配置redissession.properties出错", ex);
        }
        logger.debug("IP={}", IP);
        logger.debug("PORT={}", PORT);
        logger.debug("PASSWORD={}", PASSWORD);
        logger.debug("TIMEOUT={}", TIMEOUT);
        sessionJedis = new Jedis(IP, PORT,TIMEOUT);
        sessionJedis.auth(PASSWORD);
        logger.info("清楚所有的缓存。。。。。");
        sessionJedis.flushAll();
        Set<String> keys = sessionJedis.keys("*");
        logger.debug("keys:{}", keys);
        if (!keys.isEmpty()) {
            sessionJedis.del(keys.toArray(new String[]{}));
        }
        keys = sessionJedis.hkeys("*");
        logger.debug("hkeys:{}", keys);
        if (!keys.isEmpty()) {
            sessionJedis.del(keys.toArray(new String[]{}));
        }
    }

    public static Jedis buildSessionJedis() {
        return sessionJedis;
    }
}
