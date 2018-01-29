package com.born.insurance.util;

import java.security.KeyPair;
import java.security.PublicKey;


public class PublicKeyMap {
	private String modulus;
	private String exponent;
	public String getModulus() {
		return modulus;
	}
	public void setModulus(String modulus) {
		this.modulus = modulus;
	}
	public String getExponent() {
		return exponent;
	}
	public void setExponent(String exponent) {
		this.exponent = exponent;
	}
	@Override
	public String toString() {
		return "PublicKeyMap [modulus=" + modulus + ", exponent=" + exponent
				+ "]";
	}
	
}
