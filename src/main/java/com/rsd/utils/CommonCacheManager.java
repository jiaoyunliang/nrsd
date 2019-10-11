package com.rsd.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 缓存管理类,为系统提供统一的缓存调用方法.
 * 
 * @author jyl
 * 
 */
public abstract class CommonCacheManager {

	private final static Log logger = LogFactory.getLog(CommonCacheManager.class);
	private static CacheClient cache = null;

	private static String cache_app = null;

	// private static ConfigurationManager manager = null;

	static {
		try {
			cache = new LocalMapClient();
			// cache = new RedisCacheClient("127.0.0.1:6379");
			// manager = new ConfigurationManager("redis");
			cache_app = "local";// manager.getProperty("cache-app");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public synchronized static boolean del(String key) {
		key = cache_app + "_" + key;
		cache.del(key);
		return true;
	}

	public synchronized static long incr(String key, int seconds) {
		if (cache.exists(key)) {
			cache.expire(key, seconds);
		}
		return cache.incr(key);
	}

	public synchronized static boolean incrKeyWithTime(String key, int seconds) {
		if (exists(key)) {
			Integer value = Integer.parseInt(get(key).toString()) + 1;
			if (value >= 0) {
				set(key, value.toString(), seconds);
			} else {
				set(key, 1, seconds);
			}
		} else {
			// 如果为空 那么不处理 直接重新查询
			// set(key,"1",seconds);
		}
		return true;
	}

	public synchronized static boolean decrWithTime(String key, int seconds) {
		if (exists(key)) {
			Integer value = Integer.parseInt(get(key).toString()) - 1;
			if (value <= 0) {
				set(key, 0, seconds);
			} else {
				set(key, value, seconds);
			}

		} else {
			// 如果为空 那么不处理 直接重新查询
			// set(key, "0", seconds);
		}
		return true;
	}

	public static boolean exists(String key) {
		key = cache_app + "_" + key;
		return cache.exists(key);

	}

	public static Object get(String key) {
		return get(key, Object.class);
	}

	/**
	 * 更新缓存值
	 * 
	 * @param key
	 *            要设置的值得名称
	 * @param value
	 *            要设置的值
	 * @param expire
	 *            超时时间 单位为秒
	 */
	public synchronized static void set(String key, Object value, Integer expire) {
		key = cache_app + "_" + key;
		try {
			byte[] bkey = Transcoder.encodeString(key);

			byte[] bval = Transcoder.encodeObject(value);

			cache.set(bkey, bval);

		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/**
	 * 设置缓存值
	 * 
	 * @param key
	 *            要设置的值得名称
	 * @param value
	 *            要设置的值
	 * @param expire
	 *            超时时间 单位为秒
	 */
	public synchronized static void put(String key, Object value, Integer expire) {
		key = cache_app + "_" + key;
		try {

			byte[] bkey = Transcoder.encodeString(key);

			byte[] bval = Transcoder.encodeObject(value);

			cache.set(bkey, bval);

			cache.expire(key, expire);

		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/**
	 * 设置缓存值
	 * 
	 * @param key
	 *            要设置的值得名称
	 * @param value
	 *            要设置的值
	 */

	public synchronized static void put(String key, Object value) {
		key = cache_app + "_" + key;
		try {

			byte[] bkey = Transcoder.encodeString(key);

			byte[] bval = Transcoder.encodeObject(value);

			cache.set(bkey, bval);

		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	public synchronized static <T> T get(String key, Class<T> clazz) {
		key = cache_app + "_" + key;
		try {
			byte[] bkey = Transcoder.encodeString(key);

			byte[] bval = cache.get(bkey);

			if (bval != null) {
				return Transcoder.decodeObject(bval, clazz);
			} else {
				cache.del(bkey);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	/**
	 * 取出队列中的值
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T lpop(String key, Class<T> clazz) {
		key = cache_app + "_" + key;
		return cache.lpop(key, clazz);
	}

	/**
	 * 取出队列中的值
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T spop(String key, Class<T> clazz) {
		key = cache_app + "_" + key;
		byte[] bkey = Transcoder.encodeString(key);
		byte[] result = cache.spop(bkey);
		return Transcoder.decodeObject(result, clazz);
	}

	/**
	 * 取出队列中所有的值
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> Set<T> zrange(String key, Integer start, Integer end, Class<T> clazz) {
		key = cache_app + "_" + key;
		byte[] bkey = Transcoder.encodeString(key);
		Set<byte[]> result = cache.zrange(bkey, start, end);
		Set<T> tlist = new HashSet<T>();
		for (byte[] r : result) {
			tlist.add(Transcoder.decodeObject(r, clazz));
		}
		return tlist;
	}

	/**
	 * 取出队列中所有的值
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return Set<T>
	 */
	public static <T> List<T> lrange(String key, Integer start, Integer end, Class<T> clazz) {
		key = cache_app + "_" + key;
		byte[] bkey = Transcoder.encodeString(key);
		List<byte[]> result = cache.lrange(bkey, start, end);
		List<T> tlist = new ArrayList<T>();
		for (byte[] r : result) {
			tlist.add(Transcoder.decodeObject(r, clazz));
		}
		return tlist;
	}

	/**
	 * 删除队列中 指定的值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long srem(String key, Object value) {
		key = cache_app + "_" + key;
		return cache.srem(key, value);
	}

	/**
	 * 序列中添加一个值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long lpush(String key, Object value) {
		key = cache_app + "_" + key;
		return cache.lpush(key, value);
	}

	/**
	 * 序列中添加一个值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long sadd(String key, Object value) {
		key = cache_app + "_" + key;
		return cache.sadd(key, value);
	}



	public static void main(String[] args) {
		// CommonCacheManager.sadd("new_msg_users", "43");
		System.out.println(CommonCacheManager.spop("new_msg_users", String.class));
	}
}