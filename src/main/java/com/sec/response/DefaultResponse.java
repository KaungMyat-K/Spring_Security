package com.sec.response;

import java.io.Serializable;

public class DefaultResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Object data;
	private String message;
	private boolean isactive;

	public DefaultResponse() {
		super();
	}

	public DefaultResponse(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
}