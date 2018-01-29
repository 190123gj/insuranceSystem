package com.born.insurance.web.controller.base;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

import com.yjf.common.lang.util.money.Money;

public class CommonBindingInitializer extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			Money money = new Money(text.replace(",",""));
			super.setValue(money);
		} else {
			super.setValue(null);
		}
	}

	@Override
	public boolean supportsCustomEditor() {
		return true;
	}

}
