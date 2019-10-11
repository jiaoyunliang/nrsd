package com.rsd.utils;

public class FileUploadState {

	private String oldFileName;
	private String newFileName;
	private String path;
	private boolean success;
	private String message;
	
	public String getFullName() {
		return path + newFileName;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
