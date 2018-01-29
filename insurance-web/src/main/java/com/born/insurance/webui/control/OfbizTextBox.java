package com.born.insurance.webui.control;
import java.util.*;
/**
 * <p>
 * Title: The OfbizTextBox Control
 * </p>
 * <p>
 * Description: The OfbizTextBox Control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * @author: <a href="mailto:qch.net@163.com">�ᴺ�� </a>
 * 
 * @version 1.0
 */
public class OfbizTextBox extends TextBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean ConvertDataEnable = false;
	protected boolean IsInteger = false;
	protected boolean IsPositive = false;
	protected boolean IsNumber = false;
	protected boolean IsCode = false;
	protected boolean FormatChangeEnable = false;
	protected boolean IsConvertZero = false;
	protected String ValidExpression = "";
	protected String ListSeparator = ",";
	protected String onKeyDownFun = "";
	protected String onBlurFun = "";
	protected int DecimalDigits = 2;
	protected int SignNum = 5;
	private Map ValidExpressionMap = new HashMap();
	private boolean BooleanDecimalDigits = false;
	private boolean BooleanSignNum = false;
	private boolean BooleanListSeparator = false;
	/**
	 * ֧��null
	 */
	private boolean sustainNull = false;
	//protected String OnChangeEventName="onBlur";
	/**
	 * <p>
	 * Description: create a OfbizTextBox control
	 * </p>
	 * 
	 * @param id:
	 *            the control id
	 */
	public OfbizTextBox(String id) {
		super(id);
		this.setWidth("150");
		this.setMaxLength(60);
		this.OnChangeEventName = "onBlurEvent";
	}
	/**
	 * ����ConvertDataEnableΪtrue����ؼ������һ��ת��������ֵΪ��Ӧ��λ��׼���ú�����onBlur�¼�����ʱ����
	 * 
	 * @param ConvertDataEnable
	 */
	public void setConvertDataEnable(boolean ConvertDataEnable) {
		this.ConvertDataEnable = ConvertDataEnable;
	}
	public boolean getConvertDataEnable() {
		return this.ConvertDataEnable;
	}
	/**
	 * ����С�������λ��Ĭ��Ϊ2
	 * 
	 * @param DecimalDigits
	 */
	public void setDecimalDigits(int DecimalDigits) {
		this.DecimalDigits = DecimalDigits;
		this.BooleanDecimalDigits = true;
	}
	/**
	 * ��ȡ��֤ʹ�õ�С��λ��
	 * 
	 * @return ��֤ʹ�õ�С��λ��
	 */
	public int getDecimalDigits() {
		return this.DecimalDigits;
	}
	/**
	 * set event
	 * 
	 * @param event
	 */
	public void setEvent(IEvent event) {
		if (event.getEventName().toUpperCase().equals("ONKEYDOWN")) {
			this.onKeyDownFun = event.getFuncName();
		} else if (event.getEventName().toUpperCase().equals("ONBLUR")) {
			this.onBlurFun = event.getFuncName();
		} else {
			super.setEvent(event);
		}
	}
	/**
	 * get event
	 * 
	 * @param eventname
	 * @return event
	 */
	public IEvent getEvent(String eventname) {
		if (eventname.toUpperCase().equals("ONKEYDOWN")) {
			Event okdEvent = new Event(eventname);
			okdEvent.setFuncName(this.onKeyDownFun);
			return okdEvent;
		} else if (eventname.toUpperCase().equals("ONBLUR")) {
			Event oblEvent = new Event(eventname);
			oblEvent.setFuncName(this.onBlurFun);
			return oblEvent;
		} else {
			return super.getEvent(eventname);
		}
	}
	/**
	 * ����FormatChangeEnableΪtrue����ؼ������һ��ת�������ֵΪ�ֽ��ʽ�ĺ���ú�����onBlur�¼�����ʱ���� *
	 * 
	 * @param FormatChangeEnable
	 */
	public void setFormatChangeEnable(boolean FormatChangeEnable) {
		this.FormatChangeEnable = FormatChangeEnable;
	}
	/**
	 * ��ȡ�Ƿ������ֽ��ʽת������
	 * 
	 * @return true/false
	 */
	public boolean getFormatChangeEnable() {
		return this.FormatChangeEnable;
	}
	/**
	 * ����CodeOnlyΪtrue���������ֻ�������ֺ���ĸ�����ۺź��»��ߡ�
	 * 
	 * @param IsCode
	 */
	public void setIsCode(boolean IsCode) {
		this.IsCode = IsCode;
	}
	/**
	 * ��ȡ�Ƿ����ô�����֤
	 * 
	 * @return true/false
	 */
	public boolean getIsCode() {
		return this.IsCode;
	}
	/**
	 * �ж��Ƿ�0ת��Ϊ��ֵ
	 * 
	 * @param IsConvertZero
	 */
	public void setIsConvertZero(boolean IsConvertZero) {
		this.IsConvertZero = IsConvertZero;
	}
	/**
	 * ��ȡ�Ƿ�0ת��Ϊ��
	 * 
	 * @return true/false
	 */
	public boolean getIsConvertZero() {
		return this.IsConvertZero;
	}
	/**
	 * �ؼ�ֻ��������Ȼ��
	 * 
	 * @param IsInteger
	 */
	public void setIsInteger(boolean IsInteger) {
		this.IsInteger = IsInteger;
	}
	/**
	 * ��ȡ�Ƿ�ֻ��������Ȼ��Ĳ���ֵ
	 * 
	 * @return IsInteger
	 */
	public boolean getIsInteger() {
		return this.IsInteger;
	}
	/**
	 * ����NumberOnlyΪtrue���������ֻ���ܼ��̰���0-9��С���͸��ţ���ֻ��������
	 * 
	 * @param IsNumber
	 */
	public void setIsNumber(boolean IsNumber) {
		this.IsNumber = IsNumber;
	}
	/**
	 * ��ȡ�Ƿ�������ֵ��֤
	 * 
	 * @return true/false
	 */
	public boolean getIsNumber() {
		return this.IsNumber;
	}
	/**
	 * ����PositiveΪtrue���������ֻ���ܼ��̰���0-9��С��㣬��ֻ��������
	 * 
	 * @param IsPositive
	 */
	public void setIsPositive(boolean IsPositive) {
		this.IsPositive = IsPositive;
	}
	/**
	 * ��ȡ�Ƿ����÷Ǹ���֤
	 * 
	 * @return true/false
	 */
	public boolean getIsPositive() {
		return this.IsPositive;
	}
	/**
	 * ���������ֽ��ʽ�ķָ�����
	 * 
	 * @param ListSeparator
	 */
	public void setlistSeparator(String ListSeparator) {
		this.ListSeparator = ListSeparator;
		this.BooleanListSeparator = true;
	}
	/**
	 * ��ȡ�ֽ��ʽ�ķָ���
	 * 
	 * @return �ֽ��ʽ�ָ���
	 */
	public String getlistSeparator() {
		return this.ListSeparator;
	}
	/**
	 * ���ý�λ��׼��Ĭ��Ϊ5
	 * 
	 * @param SignNum
	 */
	public void setSignNum(int SignNum) {
		this.SignNum = SignNum;
		this.BooleanSignNum = true;
	}
	/**
	 * ��ȡ��λ��׼
	 * 
	 * @return ��λ��׼
	 */
	public int getSignNum() {
		return this.SignNum;
	}
	/**
	 * ������֤���ʽ����������Ϊ"Number"(������֤),"SerialNumber"(�����֤),"Email"(�ʼ���ַ��֤),"Postal"(�ʱ���֤),"PhoneNum"(�绰������֤),"RequiredField"(�ǿ���֤),"Code"(�Ƿ��ַ���֤),"TrimSpace"(ȥ���ո�)),"������ʽ"(�Զ�����֤)
	 * ��������setValidExpression("Email"),�ؼ������һ����֤�ű������������ֵ����Email��ʽ����ֵ�����
	 * ������ʽ�У���б�ܣ���˫��ţ�����ת���ַ�
	 */
	public void setValidExpression(String ComponentConstant_OfbizTextBox_Valid) {
		this.ValidExpression = ComponentConstant_OfbizTextBox_Valid;
	}
	/**
	 * ��ȡ�ı������֤��ʽ
	 * 
	 * @return �ı������֤��ʽ
	 */
	public String getValidExpression() {
		return this.ValidExpression;
	}
	/**
	 * ���õ�������Ա�����ʱ���Լ�����ȼ�
	 */
	protected void SetAttributePRI() {
		if (this.IsCode == true) {
			this.IsPositive = false;
			this.IsNumber = false;
		}
		if (this.BooleanDecimalDigits == true || this.BooleanSignNum == true) {
			this.ConvertDataEnable = true;
		}
		if (this.BooleanListSeparator == true) {
			this.FormatChangeEnable = true;
		}
		if (this.ConvertDataEnable == true || this.FormatChangeEnable == true) {
			this.IsNumber = true;
		}
		if (this.IsNumber == true) {
			this.IsPositive = false;
		}
		if (this.ValidExpression
				.equals(ComponentConstant.OfbizTextBox_Valid_Postal)) {
			this.setMaxLength(6);
			this.setIsInteger(true);
		}
		if (this.ValidExpression
				.equals(ComponentConstant.OfbizTextBox_Valid_Number)) {
			this.setIsNumber(true);
		}
		if (this.ValidExpression
				.equals(ComponentConstant.OfbizTextBox_Valid_SerialNumber)) {
			this.setIsCode(true);
		}
		if (this.FormatChangeEnable == true) {
			this.setSignNum(5);			
			this.setConvertDataEnable(true);
		}
		if (this.ValidExpression
				.equals(ComponentConstant.OfbizTextBox_Valid_Number)) {
			this.setIsNumber(true);
		}
		if (this.IsInteger == true || this.IsPositive == true) {
			this.ValidExpression = ComponentConstant.OfbizTextBox_Valid_Number;
		}
	}
	/**
	 * Get the control html ��ȡ������html������¼���������֤����͸�ʽת������
	 * 
	 * @return Html of OfbizTextBox
	 */
	protected String getElementHtml() {
		/**
		 * onKeyDown event����������漰�ĺ�����μ�JS/OfbizTextBox.js
		 */
		SetAttributePRI();
		//����ΪԤ������
		this.sourceAttributes.put("setValue", "OfbizTextBox_SetValue");
		this.sourceAttributes.put("getValue", "OfbizTextBox_GetValue");
		this.sourceAttributes.put("setProperty", "OfbizTextBox_setProperty");
		this.sourceAttributes.put("getProperty", "OfbizTextBox_getProperty");
		if(this.sustainNull)
		{
			this.setAttribute("sustainNull","true");
		}
		
		//Add Attribute from BaseClass AbstractComponent
		if (this.IsConvertZero == true) {
			this.sourceAttributes.put("IsConvertZero", String
					.valueOf(this.IsConvertZero));
		}
		if (this.IsPositive == true || this.IsNumber == true
				|| this.IsCode == true || this.IsInteger == true
				|| !this.onKeyDownFun.equals("")) {
			String onKeyDownHtml = "";
			if (this.IsInteger == true) {
				onKeyDownHtml = "return Integer(this,event);";
				this.sourceAttributes.put("IsNumber", String
						.valueOf(this.IsNumber));
				this.setStyle("text-align", "right");
			}
			//Add Positive Function script
			//ֻ���������ֺ�С���
			if (this.IsPositive == true) {
				onKeyDownHtml = "return Positive(this,event);";
				this.sourceAttributes.put("IsNumber", String
						.valueOf(this.IsNumber));
				this.setStyle("text-align", "right");
			}
			//Add NumberOnly Function script
			//ֻ���������֣�С���͸���
			if (this.IsNumber == true) {
				onKeyDownHtml = "return NumberOnly(this,event);";
				this.sourceAttributes.put("IsNumber", String
						.valueOf(this.IsNumber));
				this.setStyle("text-align", "right");
			}
			//Add CodeOnly Function script
			//ֻ���������֣���ĸ�����ۺţ��»���
			if (this.IsCode == true) {
				onKeyDownHtml = "return CodeOnly(event);";
			}
			Event onKeyDownEvent = new Event("onKeyDown");
			if (!this.onKeyDownFun.equals("")) {
				if (!this.onKeyDownFun.endsWith(";")) {
					this.onKeyDownFun = this.onKeyDownFun + ";";
				}
				onKeyDownHtml = this.onKeyDownFun + onKeyDownHtml;
			}
			onKeyDownEvent.setFuncName(onKeyDownHtml);
			super.setEvent(onKeyDownEvent);
		}
		/**
		 * onBlur event����������漰�ĺ�����μ�JS/OfbizTextBox.js
		 */
		String onBlurHtml = "";
		//����OnBlur�¼�����
		//this.sourceAttributes
		if (this.IsConvertZero == true) {
			onBlurHtml = onBlurHtml + "ZeroToNull(this);";
		}
		if (!this.ValidExpression.equals("")) {
			this.InitValidExpressionMap();
			onBlurHtml = onBlurHtml
					+ (String) this.getValidExpression(this.ValidExpression);
		}
		//Add ConvertData Function script��������ֵ����onBlur�¼�����ʱת��Ϊ��Ӧ��λ��׼�ĸ�ʽ
		if (this.ConvertDataEnable == true) {
			onBlurHtml = onBlurHtml + "ConvertData(this);";
			this.sourceAttributes.put("DecimalDigits", String
					.valueOf(this.DecimalDigits));
			this.sourceAttributes.put("SignNum", String.valueOf(this.SignNum));
		}
		//Add convertMoneyFormat Function script��������ֵ����onBlur�¼�����ʱת��Ϊ�ֽ��ʽ
		if (this.FormatChangeEnable == true) {
			onBlurHtml = onBlurHtml + "convertMoneyFormat(this);";
			this.sourceAttributes.put("listSeparator", this.ListSeparator);
		}
		if (this.getMaxLength()!=0) {
			onBlurHtml = onBlurHtml + "OfbizTextBox_CutByMaxLength(this);";
		}
		onBlurHtml = onBlurHtml + "OfbizTextBox_SetTitle(this);";
		onBlurHtml=onBlurHtml + this.onBlurFun;
		IEvent onchangeEvent=this.getOnChangeEvent();		
		if(onchangeEvent!=null)
		{
			onBlurHtml=onBlurHtml+onchangeEvent.getFuncName();
		}
		Event onBlurEvent = new Event("onBlur");
		onBlurEvent.setFuncName(onBlurHtml);
		super.setEvent(onBlurEvent);
		if (this.FormatChangeEnable == true) {
			this.sourceAttributes.put("onFocus", "RevertData(this);");
			/*if(this.getEvent("onFocus")!=null)
			{
				IEvent onfocus=this.getEvent("onFocus");
				onfocus.setFuncName("RevertData(this);"+onfocus.getEventName());
			}
			else
			{
				
			}*/
		}
		return super.getElementHtml();
	}
	/**
	 * Get the OfbizTextBox Script with Javascript file path
	 * ��ȡ�ű�JS/OfbizTextBox.js��·��
	 * 
	 * @return " <script>..... </script>"
	 */
	protected String getScriptHtml() {
		if(!this.isMakeScript)
		{
			return "";
		}
		String html = super.getScriptHtml() + OfbizTextBoxJS.getOfbizTextBoxJS();
		return html;
	}
	/**
	 * ������֤���ʽ��Ӧ����֤�������ƣ����������������֮��Ȼ����js�ļ�������뺯��ɽ�����֤
	 * 
	 * @param ValidExpression
	 * @param ValidExpressionFunction
	 */
	public void setValidExpression(String ValidExpression,
			String ValidExpressionFunction) {
		this.ValidExpressionMap.put(ValidExpression, ValidExpressionFunction);
	}
	/**
	 * ��ȡ��֤���ʽ��Ӧ����֤������ƣ����û���ҵ���Ӧ�ĺ�����ƽ�Ĭ��Ϊ�����Ա��ʽ��Ϊ������ʽ����֤����
	 * 
	 * @param Expression
	 * @return
	 */
	public String getValidExpression(String Expression) {
		if (this.ValidExpressionMap.get(Expression) != null) {
			return (String) this.ValidExpressionMap.get(Expression);
		} else if (this.ValidExpressionMap
				.get(ComponentConstant.OfbizTextBox_Valid_RegexValid) != null) {
			return (String) this.ValidExpressionMap
					.get(ComponentConstant.OfbizTextBox_Valid_RegexValid);
		} else {
			return null;
		}
	}
	/**
	 * ��ʼ����֤���ʽMap�������֤���ʽ��Ӧ�ĺ������
	 */
	protected void InitValidExpressionMap() {
		//��֤�Ƿ���һ���Ϸ������� ע����֤ʧ�ܽ���������
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_Number,
				"NumberValidator(this);");
		//��֤�Ƿ���һ���Ϸ��ı�ţ����ֺ���ĸ�� ע����֤ʧ�ܽ���������
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_SerialNumber,
				"SerialNumberValidator(this);");
		//��֤�Ƿ����ʼ���ַ�û����� ע����֤ʧ�ܽ���������
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_Email,
				"EmailValidator(this);");
		//�ʱ���֤ ע����֤ʧ�ܽ���������
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_Postal,
				"PostalValidator(this);");
		//�绰������֤ ע����֤ʧ�ܽ���������
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_PhoneNum,
				"PhoneNumValidator(this);");
		//�ÿؼ�������֤
		this.setValidExpression(
				ComponentConstant.OfbizTextBox_Valid_RequiredField,
				"RequiredFieldValidator(this);");
		//�Ƿ��ַ���֤��ע����֤ʧ�ܽ���������
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_Code,
				"CodeValidator(this);");
		//trimȥ���ַ��еĿո�
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_TrimSpace,
				"TrimSpace(this);");
		//��������������������ʽ��֤������ǰthis.ValidExpression��ֵ����һ��������ʽ
		this.setValidExpression(ComponentConstant.OfbizTextBox_Valid_RegexValid,
				"RegexValid(this,'" + this.ValidExpression + "');");
	}
	public boolean isSustainNull() {
		return sustainNull;
	}
	public void setSustainNull(boolean sustainNull) {
		this.sustainNull = sustainNull;
	}
}
