package com.born.insurance.biz.convert;

import com.yjf.common.lang.beans.cglib.Converter;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 包装类型 <-> 基础类 非Money -> Money 会使Money变成0
 * @author wuzj
 *
 */
public class UnBoxingConverter implements Converter {
	
	public static UnBoxingConverter getInstance() {
		return new UnBoxingConverter();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object convert(Object value, Class target, Object context) {
		String tagetName = target.getName();
		if ("byte".equals(tagetName)) {
			tagetName = "java.lang.Byte";
		} else if ("short".equals(tagetName)) {
			tagetName = "java.lang.Short";
		} else if ("int".equals(tagetName)) {
			tagetName = "java.lang.Integer";
		} else if ("long".equals(tagetName)) {
			tagetName = "java.lang.Long";
		} else if ("float".equals(tagetName)) {
			tagetName = "java.lang.Float";
		} else if ("double".equals(tagetName)) {
			tagetName = "java.lang.Double";
		} else if ("boolean".equals(tagetName)) {
			tagetName = "java.lang.Boolean";
		} else if ("char".equals(tagetName)) {
			tagetName = "java.lang.Character";
		}
		if (value == null) {
			return null;
		} else if (tagetName.equals(value.getClass().getName())) {
			return value;
		} else if (!(value instanceof Money)
					&& "com.yjf.common.lang.util.money.Money".equals(tagetName)) {
			return new Money(0, 0);
		} else {
			return null;
		}
	}
	
	//	public static void main(String[] args) {
	//		Bean1 b1 = new Bean1();
	//		Bean2 b2 = new Bean2();
	//		
	//		BeanCopier.staticCopy(b1, b2, getInstance());
	//		
	//		System.out.println(b1);
	//		System.out.println(b2);
	//	}
}

//class Bean1 {
//	private Integer i = 12;
//	
//	private Long l = 13l;
//	
//	private Double d = 12.12;
//	
//	private Double d1 = 13.13;
//	
//	private BooleanEnum isDel = BooleanEnum.IS;
//	
//	private Money m1 = new Money(12, 0);
//	
//	public Integer getI() {
//		return this.i;
//	}
//	
//	public void setI(Integer i) {
//		this.i = i;
//	}
//	
//	public Double getD() {
//		return this.d;
//	}
//	
//	public void setD(Double d) {
//		this.d = d;
//	}
//	
//	public Double getD1() {
//		return this.d1;
//	}
//	
//	public void setD1(Double d1) {
//		this.d1 = d1;
//	}
//	
//	public BooleanEnum getIsDel() {
//		return this.isDel;
//	}
//	
//	public void setIsDel(BooleanEnum isDel) {
//		this.isDel = isDel;
//	}
//	
//	public Money getM1() {
//		return this.m1;
//	}
//	
//	public void setM1(Money m1) {
//		this.m1 = m1;
//	}
//	
//	public Long getL() {
//		return this.l;
//	}
//	
//	public void setL(Long l) {
//		this.l = l;
//	}
//	
//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
//	}
//}
//
//class Bean2 {
//	
//	private int i = 11;
//	
//	private double d = 11.11;
//	
//	private long l = 0;
//	
//	private Money d1 = new Money(12, 0);
//	
//	private BooleanEnum isDel;
//	
//	private Money m1 = new Money(13, 0);
//	
//	public long getL() {
//		return this.l;
//	}
//	
//	public void setL(long l) {
//		this.l = l;
//	}
//	
//	public int getI() {
//		return this.i;
//	}
//	
//	public void setI(int i) {
//		this.i = i;
//	}
//	
//	public double getD() {
//		return this.d;
//	}
//	
//	public void setD(double d) {
//		this.d = d;
//	}
//	
//	public Money getD1() {
//		return this.d1;
//	}
//	
//	public void setD1(Money d1) {
//		this.d1 = d1;
//	}
//	
//	public BooleanEnum getIsDel() {
//		return this.isDel;
//	}
//	
//	public void setIsDel(BooleanEnum isDel) {
//		this.isDel = isDel;
//	}
//	
//	public Money getM1() {
//		return this.m1;
//	}
//	
//	public void setM1(Money m1) {
//		this.m1 = m1;
//	}
//	
//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
//	}
//}
