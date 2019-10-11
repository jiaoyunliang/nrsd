package com.rsd.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class LocalMapClient extends TimerTask implements CacheClient{

	private ConcurrentMap<String, Item> map = new ConcurrentHashMap<String, Item>();



	private static final Logger logger = LoggerFactory.getLogger(LocalMapClient.class);
	
	public LocalMapClient() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000 * 120, (1000 * 60) * 15);
	}

	@Override
	public void cleanup() {
		
		logger.debug("清除前缓存总数量"+map.size());
		
		for(String key : map.keySet()){
			
			Item item = map.get(key);
			
			logger.debug("缓存key"+key+"设置的过期秒数"+item.failureTime+"过期时间:"+DateUtil.formatTime((item.createTime+(item.failureTime*1000))));
			if(item.failureTime > 0 && ((System.currentTimeMillis() - item.createTime)/ 1000) > (item.failureTime+30) )
				map.remove(key);
		}
		
		logger.debug("清除后缓存总数量"+map.size());
	}

	@Override
	public void set(String key, String value) {
		Item item = new Item();
		item.createTime = System.currentTimeMillis();
		item.val = value.getBytes();
		map.put(key, item);		
	}

	@Override
	public String get(String key) {
		Item item = map.get(key);
		if(item != null){
			if(item.failureTime == 0)
				return Transcoder.decodeString(item.getVal());
			
			if(item.failureTime > 0 && ((System.currentTimeMillis() - item.createTime)/ 1000) < item.failureTime )
				return Transcoder.decodeString(item.getVal());
		}		
		
		return null;
	}

	@Override
	public Long incr(String key) {
		return null;
	}

	@Override
	public Long decr(String key) {
		return null;
	}

	@Override
	public void del(String... keys) {
		for(String key :keys){
			map.remove(key);
		}
	}

	@Override
	public void del(byte[]... keys) {
		for(byte[] key :keys){
			String strKey = Transcoder.decodeString(key);
			map.remove(strKey);
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		Item item = this.map.get(key);
		
		if(item != null){
			item.createTime = System.currentTimeMillis();
			item.failureTime = seconds;
		}
		else{
			return -1l;
		}
		
		return item.failureTime;
	}

	@Override
	public Long lpush(String key, String value) {
		return null;
	}

	@Override
	public Long rpush(byte[] key, byte[] value) {
		return null;
	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		return null;
	}

	@Override
	public List<byte[]> lrange(byte[] key, int start, int end) {
		return null;
	}

	@Override
	public Boolean exists(String key) {
		boolean flag = true;
		
		if(map.containsKey(key)){
		  Item item = map.get(key);
		  
		  if(item != null && 
				  item.failureTime > 0 && 
				  ((System.currentTimeMillis() - item.createTime)/ 1000) > item.failureTime )
			  flag = false;
		}
		
		return flag;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return null;
	}

	@Override
	public String hget(String key, String field) {
		return null;
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		return null;
	}

	@Override
	public Long llen(String key) {
		return null;
	}

	@Override
	public void set(byte[] key, byte[] value) {
		String strKey = Transcoder.decodeString(key);
		Item item = new Item();
		item.createTime = System.currentTimeMillis();
		item.val = value;
	
		map.put(strKey, item);
	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {
		return null;
	}

	@Override
	public byte[] get(byte[] key) {
		String strKey = Transcoder.decodeString(key);
		Item item = map.get(strKey);
		if(item != null){
			if(item.getFailureTime() == 0)
				return item.getVal();
			
			if(item.getFailureTime() > 0 && ((System.currentTimeMillis() - item.getCreateTime())/ 1000) < item.failureTime )
				return item.getVal();
		}
		
		return null;
	}

	@Override
	public Long lpush(byte[] key, byte[] value) {
		return null;
	}

	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		return null;
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {
		return null;
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		return null;
	}

	@Override
	public List<byte[]> sort(String key, String patterns) {
		return null;
	}

	@Override
	public long hincrBy(String key, String field, long value) {
		return 0;
	}

	@Override
	public long hdel(String key, String field) {
		return 0;
	}

	@Override
	public Long hdel(byte[] key, byte[] field) {
		return null;
	}

	@Override
	public long setnx(String key, String value) {
		return 0;
	}

	@Override
	public void zadd(String key, double score, String member) {
		
	}

	@Override
	public Long hset(String key, String field, String value) {
		return null;
	}

	@Override
	public Set<String> keys(String pattern) {
		
		return null;
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		return null;
	}

	@Override
	public Long expire(byte[] key, int seconds) {
		String strKey = Transcoder.decodeString(key);
		Item item = this.map.get(strKey);
		
		if(item != null){
			item.createTime = System.currentTimeMillis();
			item.failureTime = seconds;
		}else{
			return -1l;
		}
		
		return item.failureTime;
	}

	@Override
	public Boolean exists(byte[] key) {
		return true;
	}

	@Override
	public Set<byte[]> zrange(byte[] key, int start, int end) {
		return null;
	}

	@Override
	public Set<String> zrange(String key, int start, int end) {
		return null;
	}

	@Override
	public String lpop(String key) {
		return null;
	}

	@Override
	public byte[] lpop(byte[] key) {
		return null;
	}

	@Override
	public String rpop(String key) {
		return null;
	}

	@Override
	public byte[] rpop(byte[] key) {
		return null;
	}

	@Override
	public Long rpush(String key, String value) {
		return null;
	}

	@Override
	public <T> T rpop(String key, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> T lpop(String key, Class<T> clazz) {
		return null;
	}

	@Override
	public Long lpush(String key, Object value) {
		return null;
	}

	@Override
	public Long rpush(String key, Object value) {
		return null;
	}

	@Override
	public Long sadd(String key, Object value) {
		return null;
	}

	@Override
	public Long srem(String key, Object members) {
		return null;
	}

	@Override
	public String spop(String key) {
		return null;
	}

	@Override
	public byte[] spop(byte[] key) {
		return null;
	}

	static class Item{
		private long createTime;
		private long failureTime;
		private byte[] val;
		public long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}
		public long getFailureTime() {
			return failureTime;
		}
		public void setFailureTime(long failureTime) {
			this.failureTime = failureTime;
		}
		public byte[] getVal() {
			return val;
		}
		public void setVal(byte[] val) {
			this.val = val;
		}
	}

	@Override
	public void run() {
		this.cleanup();
	}

	@Override
	public Map<String, Item> getMap() {
		// TODO Auto-generated method stub
		return map;
	}
}
