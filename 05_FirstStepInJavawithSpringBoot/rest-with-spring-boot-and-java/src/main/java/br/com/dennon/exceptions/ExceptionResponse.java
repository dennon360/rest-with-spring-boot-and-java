package br.com.dennon.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date timestap;
	private String message;
	private String details;

	public ExceptionResponse(Date timesap, String message, String details) {
		this.timestap = timesap;
		this.message = message;
		this.details = details;
	}

	public Date getTimestap() {
		return timestap;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}
