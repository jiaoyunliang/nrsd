package com.rsd.utils;

public class ElementBean {
	
	private Integer type;
	
	private String content;
	//图片
	public static final Integer TYPE_IMAGE = 0;
	//文字
	public static final Integer TYPE_CHUNK = 1 ;
	//段落文字
	public static final Integer TYPE_PARAGRAPH = 2;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
