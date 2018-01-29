package com.born.insurance.biz.convert;

import com.born.insurance.dal.dataobject.FormDO;
import com.born.insurance.util.FormUtil;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.yjf.common.lang.beans.cglib.BeanCopier;

/**
 * 将DO转化为其它对象
 * 
 * @author lirz
 *
 * 2016-4-20 下午4:07:58
 */
public class DoConvert {
	
	public static FormInfo convertFormInfo(FormDO form) {
		FormInfo formInfo = new FormInfo();
		BeanCopier.staticCopy(form, formInfo);
		formInfo.setFormCode(FormCodeEnum.getByCode(form.getFormCode()));
		formInfo.setStatus(FormStatusEnum.getByCode(form.getStatus()));
		formInfo.setFormExecuteInfo(FormUtil.parseTaskUserData(form.getTaskUserData()));
		return formInfo;
	}
	
}
