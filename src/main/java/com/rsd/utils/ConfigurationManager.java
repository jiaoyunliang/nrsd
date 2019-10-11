package com.rsd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ConfigurationManager{

	private Properties properties;
	String configPath = ""; // 配置文件路径
	private static ConcurrentMap<String,ConfigurationManager> loadedMap = new ConcurrentHashMap<String,ConfigurationManager>();
	private static Set<String> loadedSet = new HashSet<String>();
	private static String sync = "sync";
	private static Logger logger = LogManager.getLogger(ConfigurationManager.class);
	
	static{
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				synchronized (sync) {
					logger.debug("开始清除配置文件!");
					loadedMap.clear();
					for(String app:loadedSet){
						try {
							loadedMap.put(app,new ConfigurationManager(app));
						} catch (Exception e) {
							logger.debug("{}配置文件不存在",app);
							logger.error(e,e);
						}
					}
					logger.debug("完成清除配置文件!");
				}
			}
			
			
		}, 1000*30, (1000 * 60) * 30);
	}
	
	/**
	 * 有参构造方法
	 * @param app
	 * @throws Exception
	 */
	private ConfigurationManager(String app) throws Exception {
		
		String appFile = app + ".properties";
		String enviPath = System.getenv("BJYS_CONFIG_PATH"); // 获取环境变量配置的信息
		
		if (StringUtils.isEmpty(enviPath)) {
			configPath = System.getProperty("user.dir") + File.separator + appFile;
		} else {
			configPath = enviPath + File.separator + appFile;
		}
		
		if(loadedMap.containsKey(app)){
			properties = loadedMap.get(app).getProperties();
		}else{
			properties = new Properties();
			if (new File(configPath).exists()) {
				FileInputStream inputStream = null;
				try{
					inputStream = new FileInputStream(configPath);
				properties.load(inputStream);
				}finally{
					inputStream.close();
				}
			} else {
				InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(appFile);
				properties.load(inputStream);
			}
			loadedMap.put(app, this);
		}
	}
 	
	public Properties getProperties() {
		return properties;
	}

	public void clearProperties() {
		properties.clear();
	}


	/**
	 * 
	 * 根据配置文件名获取该文件管理对象
	 *
	 * @created 2013年11月6日 上午10:42:46
	 *
	 * @param app
	 * @return
	 * @throws Exception
	 */
	public static ConfigurationManager getManager(String app) throws Exception {
		ConfigurationManager config = null;
		
		loadedSet.add(app);
		
		if(loadedMap.containsKey(app)){
			config = loadedMap.get(app);
		}else{
			config = new ConfigurationManager(app);
			loadedMap.put(app, config); 
		}
		
		return config;
		
	}

	/**
	 * 
	 * 根据属性名获取属性值
	 *
	 * @created 2013年11月6日 上午10:40:37
	 *
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 
	 * 设置属性值
	 *
	 * @created 2013年11月6日 上午10:41:03
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Object setProperty(String key, String value) {
		return properties.setProperty(key, value);
	}
	/**
	 * 取得所有的key
	 * @return
	 */
	public List<Object> getKeys(){
		List<Object> keys = new ArrayList<Object>();
		keys.addAll(properties.keySet());
		return keys;
	}
	/**
	 * 
	 * 根据属性名获取int类型的属性值
	 *
	 * @created 2013年11月6日 上午10:41:14
	 *
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		String val = properties.getProperty(key);
		if (NumberUtils.isNumber(val)) {
			return Integer.parseInt(val);
		}
		return 0;
	}

	public long getLong(String key) {
		String val = properties.getProperty(key);
		if (NumberUtils.isNumber(val)) {
			return Long.parseLong(val);
		}
		return 0;
	}
 
}
