package com.born.insurance.webui.table;

import java.text.FieldPosition;

public class DecimalFormatBorn extends java.text.DecimalFormat {
	
	private static final long serialVersionUID = 1125903898293643060L;
	
	public DecimalFormatBorn(String pattern) {
		super(pattern);
	}
	
	@Override
	public StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition) {
		number = number + 0.000000000009;
		return super.format(number, result, fieldPosition);
	}
	
}
