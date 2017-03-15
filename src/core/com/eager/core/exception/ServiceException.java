package com.eager.core.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3731105337376283691L;

	public ServiceException() {
		super();
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ServiceException(String arg0) {
		super(arg0);
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
	}

}
