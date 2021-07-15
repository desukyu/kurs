package io.kabanyo.kurs.exception;

public class KursException extends RuntimeException {

	public static final String FAILED_TO_CONNECT = "Failed to connect";
	public static final String LAST_UPDATE_DATE_NOT_FOUND = "Last Update date is not found";
	public static final String FAILED_TO_PARSE_NUMBER = "Failed to parse number";

	public KursException(String message) {
		super(message);
	}
}
