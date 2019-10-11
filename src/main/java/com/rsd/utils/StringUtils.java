package com.rsd.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtils {
	
	static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 
	 * 截取目标字符串（即：target）最后一个标识符（即：separator）前的子串
	 *
	 * @created 2012-11-5 下午5:56:29
	 *
	 * @param target	目标字符串
	 * @param separator	标识符
	 * @return
	 */
	public static String substringBeforeLastIgnoreCase(String target, String separator) {
		if ("".equals(target))
			return target;
		if ("".equals(separator))
			return target;
		String tempStr = target.toUpperCase();
		String tempSeparator = separator.toUpperCase();

		int index = tempStr.lastIndexOf(tempSeparator);
		if (index == -1)
			return target;
		return target.substring(0, index);
	}
	
	/**
	 * 
	 * 单引号（“'”）逃逸，用于构建SQL语句时使用
	 *
	 * @created 2012-11-5 下午5:55:44
	 *
	 * @param target	目标字符串
	 * @return
	 */
	public static String singleQuotoAndEscape(String target) {
		if (!isEmpty(target)) {
			StringBuffer t = new StringBuffer(target.length() + 3);
			t.append("'");
			t.append(target.replaceAll("'", "''"));
			t.append("'");
			return t.toString();
		}
		return "''";
	}
	
	/**
	 * 
	 * 字符串非空判断
	 *
	 * @created 2012-11-5 下午5:55:27
	 *
	 * @param target	目标字符串
	 * @return
	 */
	public static final boolean isEmpty(String input) {
		return input == null || input.length() == 0 || input.trim().length() == 0;
	}

	/**
	 * 
	 * 字符串非空判断
	 *
	 * @created 2012-11-13 下午5:16:23
	 *
	 * @param input	目标字符串
	 * @return
	 */
	public static final boolean isNotEmpty(String input) {
		return !isEmpty(input);
	}
	
	/**
	 * 
	 * 字符串左补字符到固定长度（如：高位补0到10位）
	 *
	 * @created 2012-11-21 上午11:20:41
	 *
	 * @param input 原字符串
	 * @param padCode 补充字符
	 * @param toLength 到固定长度
	 * @return 补充完字符后字符串
	 */
	public static final String lpad(String input, String padCode, int toLength) {
		if (input != null && input.length() < toLength) {
			StringBuffer sb = new StringBuffer(input);
			while (sb.length() < toLength) {
				sb.insert(0, padCode);
			}
			return sb.toString();
		} else {
			return input;
		}
	}
	
	/**
	 * 
	 * 字符串右补字符到固定长度（如：地位补0到10位）
	 *
	 * @created 2012-11-21 上午11:20:41
	 *
	 * @param input 原字符串
	 * @param padCode 补充字符
	 * @param toLength 到固定长度
	 * @return 补充完字符后字符串
	 */
	public static final String rpad(String input, String padCode, int toLength) {
		if (input != null && input.length() < toLength) {
			StringBuffer sb = new StringBuffer(input);
			while (sb.length() < toLength) {
				sb.append(padCode);
			}
			return sb.toString();
		} else {
			return input;
		}
	}
	
	/**
	 * 
	 * 字符串首字母大小
	 *
	 * @created 2013年10月31日 下午1:46:06
	 *
	 * @param target
	 * @return
	 */
	public static final String firstToUpperCase(String target){
		return target.substring(0,1).toUpperCase() + target.substring(1, target.length());
	}
	
	/**
	 * 
	 * 获取字符串所占字符数量
	 *
	 * @created 2013年11月5日 下午4:13:14
	 *
	 * @param str
	 * @return
	 */
	public static int count(String str) {
		int length = 0;
		str = str.trim();
		if (isEmpty(str)) {
			return length;
		}
		char[] temp = str.toCharArray();
		for (char t : temp) {
			if (String.valueOf(t).matches("^[\\u4e00-\\u9fa5]*$")) {
				length += 2;
			} else if (String.valueOf(t).matches("^\\S*$")) {
				length += 1;
			}
		}
		return length;
	}
	
	public static String timestamp2string(Timestamp timestamp) {
		if(timestamp == null) {
			return null;
		}
		return yyyyMMdd.format(timestamp);
	}
	
	public static String date2string(java.sql.Date date) {
		if(date == null) {
			return null;
		}
		return yyyyMMdd.format(date);
	}
	
	/**
	 * String str = "a,b,c,d";
	 * 处理之后
	 * str = "'a','b','c','d'";
	 * 应用场景： sql语句的IN（%s）时，如果是"a,b,c,d"形式，这时我们需要转换成"'a','b','c','d'"
	 * 注意：在mysql中字段定义是varchar类型的需要转换。int型不用转换。
	 * @created 2013年12月4日 下午2:56:38
	 *
	 * @param str
	 * @return
	 */
	public static String embraceComma(String str) {
		StringBuffer sb = new StringBuffer("'");
		char[] chars = str.toCharArray();
		for(char c : chars) {
			switch(c) {
			case 44 :
				sb.append("'").append(c).append("'");
				break;
			case 32 :
				break;
			case 0 :
				break;
			default:
				sb.append(c);
			}
		}
		sb.append("'");
		return sb.toString();
	}
	
	/**
	 * 将list转换成string，如"a,b,c,d" "123,345,567,789"
	 *
	 * @created 2014年1月15日 上午10:45:44
	 *
	 * @param list
	 * @return
	 */
	public static String surroundByComma(List<?> list) {
		String split = "";
		if(list != null && !list.isEmpty()) {
			for(Object obj : list) {
				split += String.valueOf(obj) + ",";
			}
			split = split.substring(0, split.lastIndexOf(","));
		}
		return split;
	}
	
    public static String[] substringsBetween(final String str, final String open, final String close) {
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return new String[0];
        }
        final int closeLen = close.length();
        final int openLen = open.length();
        final List<String> list = new ArrayList<String>();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            final int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }
            list.add(str.substring(start, end));
            pos = end + closeLen;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String [list.size()]);
    }	
	
	/**
	 * 字符串模板
	 * "尊敬的客户${customerName}你好！本次消费金额${amount}，您帐户${accountNumber}上的余额为${balance}";
	 * @param template
	 * @param model
	 * @return
	 */
	public static String stringTemplate(String template,Map<String,Object> model){
		 
        for(Map.Entry<String,Object> o :model.entrySet()){
        	if(o.getValue() != null)
        		template=template.replaceAll("\\$\\{"+o.getKey()+"}", o.getValue().toString());
        	else
        		template=template.replaceAll("\\$\\{"+o+"}", "");
        }
                                                                                                      
        return template;
	}
	/**
	 * 字符串模板
	 * "尊敬的客户${0}你好！本次消费金额${1}，您帐户${2}上的余额为${3}";
	 * @param template
	 * @param model
	 * @return
	 */
	public static String stringTemplate(String template,String... args){
        
		for(int i =0; i < args.length;i++){
        	
        	if(args[i] != null)
        		template=template.replaceAll("\\$\\{"+i+"}", args[i]);
        	else
        		template=template.replaceAll("\\$\\{"+i+"}", "");
        }
                                                                                                      
        return template;
	}	
	/**
	 * 生成指定位数的随机数
	 * 
	 * @param length
	 *            位数
	 * @return
	 */
	public static long generateIdentifyingCode(int length) {
		int max = 1;
		int min = 1;
		switch (length) {
		case 2:
			max = 99;
			min = 10;
			break;
		case 3:
			max = 999;
			min = 100;
			break;
		case 4:
			max = 9999;
			min = 1000;
			break;
		case 5:
			max = 99999;
			min = 10000;
			break;
		case 6:
			max = 999999;
			min = 100000;
			break;
		case 7:
			max = 9999999;
			min = 1000000;
			break;
		case 8:
			max = 99999999;
			min = 10000000;
			break;
		case 9:
			max = 999999999;
			min = 100000000;
			break;
		default:
			max = 999999;
			min = 100000;
			break;
		}
		long ll = Math.round(Math.random() * (max - min) + min);
		return ll;
	}
	/**
     * @return 形如 yyyyMMddHHmmssSSS-Z0000019558195832297 的(38位)保证唯一的递增的序列号字符串，
     * 主要用于数据库的主键，方便基于时间点的跨数据库的异步数据同步。
     * 前半部分是currentTimeMillis，后半部分是nanoTime（正数）补齐20位的字符串，
     * 如果通过System.nanoTime()获取的是负数，则通过nanoTime = nanoTime+Long.MAX_VALUE+1;
     * 转化为正数或零。
     */
    public static String getTimeMillisSequence(){
        long nanoTime = System.nanoTime();
        String preFix="";
        if (nanoTime<0){
            preFix="A";//负数补位A保证负数排在正数Z前面,解决正负临界值(如A9223372036854775807至Z0000000000000000000)问题。
            nanoTime = nanoTime+Long.MAX_VALUE+1;
        }else{
            preFix="Z";
        }
        String nanoTimeStr = String.valueOf(nanoTime);
         
        int difBit=String.valueOf(Long.MAX_VALUE).length()-nanoTimeStr.length();
        
        for (int i=0;i<difBit;i++){
            preFix = preFix+"0";
        }
        nanoTimeStr = preFix+nanoTimeStr;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS"); //24小时制
        String timeMillisSequence=sdf.format(System.currentTimeMillis())+"-"+nanoTimeStr; 
         
        return timeMillisSequence;      
    }
    
    public static void main(String[] args){
    	System.out.println(StringUtils.stringTemplate("asd${1}sdf${0}", "33",""));
    }
}
