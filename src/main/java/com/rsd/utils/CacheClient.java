
package com.rsd.utils;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现缓存客户端的封装
 * 
 * 
 */
public interface CacheClient {

	public void cleanup();

	public void set(String key, String value);

	public String get(String key);

	public Long incr(String key);

	public Long decr(String key);

	public void del(String... keys);
	
	public void del(byte[]... keys);

	public Long expire(String key, int seconds);

	public Long lpush(String key, String value);

	public Long lpush(String key, Object value);
	
	public Long rpush(byte[] key, byte[] value);

	public Long rpush(String key, Object value);
	
	public Long rpush(String key, String value);
	
	public String lpop(String key);
	
	public String spop(String key);
	
	public byte[] spop(byte[] key);
	
	public byte[] lpop(byte[] key);

	public String rpop(String key);
	
	public <T> T rpop(String key, Class<T> clazz);

	public <T> T lpop(String key, Class<T> clazz);

	public byte[] rpop(byte[] key);

	public List<String> lrange(String key, int start, int end);

	public List<byte[]> lrange(byte[] key, int start, int end);

	public Set<byte[]> zrange(byte[] key, int start, int end);

	public Set<String> zrange(String key, int start, int end);

	public Boolean exists(String key);

	public Boolean exists(byte[] key);

	public String hmset(String key, Map<String, String> hash);

	public Map<String, String> hgetAll(String key);

	public String hget(String key, String field);

	public byte[] hget(byte[] key, byte[] field);

	public Long llen(String key);

	public void set(byte[] key, byte[] value);

	public String setex(byte[] key, int seconds, byte[] value);

	public byte[] get(byte[] key);

	public Long lpush(byte[] key, byte[] value);

	public String hmset(byte[] key, Map<byte[], byte[]> hash);

	public Long hset(byte[] key, byte[] field, byte[] value);

	public Map<byte[], byte[]> hgetAll(byte[] key);

	public List<byte[]> sort(String key, String patterns);

	public long hincrBy(String key, String field, long value);

	public long hdel(String key, String field);

	public Long hdel(byte[] key, byte[] field);

	public long setnx(String key, String value);

	void zadd(String key, double score, String member);

	public Long hset(String key, String field, String value);

	public Set<String> keys(String pattern);

	public Set<byte[]> keys(byte[] pattern);

	Long expire(byte[] key, int seconds);

	public Long sadd(String key, Object value);
	
	public Long srem(String key, Object members);

	public Map<String, LocalMapClient.Item> getMap();
}
