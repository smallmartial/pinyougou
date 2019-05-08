package entity;

import java.io.Serializable;

public class Result implements Serializable {
	/**
	 * 返回结果封装
	 * 
	 * @author Administrator
	 *
	 */

	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;

	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

}
