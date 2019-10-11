package com.rsd.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Set;

/**
 * @author tony
 * @data 2019-05-28
 * @modifyUser
 * @modifyDate
 */
public abstract class HttpSessionManager {


    private static Log logger = LogFactory.getLog(HttpSessionManager.class);

    private static CacheClient cache = null;

    public static int TIME_OUT = 60*30;

    private static Set<String> keys = new java.util.concurrent.ConcurrentSkipListSet<String>();

    static{
        try {
            cache = new LocalMapClient();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static Object get(String key){
        return get(key, Object.class);
    }

    private static String createKey(String key){

        String skey = null;

        try{
            RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();

            String sessionid = null;

            if(sessionid == null && attributes != null){
                sessionid = attributes.getSessionId();
            }
            keys.add(key);
            skey = sessionid + "_" + key;
            logger.debug("create session key="+skey);
            return skey;
        } catch (Exception e){
            logger.error(e,e);
        }
        return skey;

    }

    public synchronized static void removeBySessionId(String sessionId){
        for(String key:keys){
            cache.del(sessionId+ "_" + key);
        }
        cache.cleanup();
    }

    public synchronized static void copy(String oldSessionId,String newSeesionId){
        byte[] bkey = null;

        for(String key:keys){
            bkey = Transcoder.encodeString(oldSessionId+"_"+key);

            cache.set(Transcoder.encodeString(newSeesionId+"_"+key),cache.get(bkey));
            cache.expire(newSeesionId+"_"+key, TIME_OUT);
        }
    }

    public synchronized static void put(String key,Object value){
        try{
            byte[] bkey = Transcoder.encodeString(createKey(key));

            byte[] bval = Transcoder.encodeObject(value);
            cache.set(bkey, bval);
            cache.expire(bkey, TIME_OUT);
        }catch(Exception e){
            logger.error(e,e);
        }
    }

    public synchronized static void put(String key,Object value,int seconds){
        try{

            byte[] bkey = Transcoder.encodeString(createKey(key));

            byte[] bval = Transcoder.encodeObject(value);
            cache.set(bkey, bval);
            cache.expire(bkey, seconds);
        }catch(Exception e){
            logger.error(e,e);
        }
    }
    public synchronized static <T> T  get(String key,Class<T> clazz){
        try{
            byte[] bkey = Transcoder.encodeString(createKey(key));

            byte[] bval = cache.get(bkey);


            if(bval != null){
                return Transcoder.decodeObject(bval, clazz);
            }else{
                cache.del(bkey);
            }
        }catch(Exception e){
            logger.error(e,e);
        }
        return null;
    }

    public static void generateAccessToken(int seconds){
        put("ACCESS_TOKEN", RandomStringUtils.randomAlphanumeric(12),seconds);
    }

    public static String getAccessToken(){
        return get("ACCESS_TOKEN",String.class);
    }

    public static long expire(String key,int seconds){
        logger.debug("增加session时间  key="+key);
        return cache.expire(createKey(key), seconds);
    }

    public static void del(String key){
        cache.del(createKey(key));
    }

    public static void cleanUp(){
        cache.cleanup();
    }

    public synchronized static void refresh(){
        logger.debug("刷新session过期时间时间");
        for(String key:keys){
            expire(key,TIME_OUT);
        }
    }
}
