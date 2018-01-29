package com.born.insurance.web.controller.base;



import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Calendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;

public class CustomCalendarEditor extends PropertyEditorSupport {
	
	private final String[] dateFormat;
	
	private final String specifiedDateFormat = "yyyy-MM-dd HH:mm:ss";
	
	private final boolean allowEmpty;
	
	private final int exactDateLength;
	
	public CustomCalendarEditor(boolean allowEmpty, String... dateFormat) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}
	
	public CustomCalendarEditor(boolean allowEmpty, int exactDateLength, String... dateFormat) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}
	
	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		} else if (text != null && this.exactDateLength >= 0
					&& text.length() != this.exactDateLength) {
			throw new IllegalArgumentException("Could not parse date: it is not exactly"
												+ this.exactDateLength + "characters long");
		} else {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtils.parseDate(text, dateFormat));
				setValue(cal);
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException(
					"Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		}
	}
	
	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Calendar value = (Calendar) getValue();
		return (value != null ? DateFormatUtils.format(value, specifiedDateFormat) : "");
	}
}
