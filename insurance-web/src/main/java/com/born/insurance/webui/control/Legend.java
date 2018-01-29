package com.born.insurance.webui.control;

public class Legend extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Legend() {
	}

	/**
	 * 
	 * @param Text
	 */
	public Legend(String Text) {
		this.setText(Text);

	}

	/**
	 * 
	 * @return
	 */
	public String getElementHtml() {
		return "<Legend " + super.getElementHtml() + ">" + this.getText() + "</Legend>";
	}

	public static void main(String[] args) {
		Legend legend1 = new Legend();
	}

}