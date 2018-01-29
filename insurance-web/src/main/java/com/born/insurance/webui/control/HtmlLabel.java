package com.born.insurance.webui.control;

public class HtmlLabel extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HtmlLabel(String id) {
		super(id);
		this.name = null;
		this.setElementType("label");
	}

	protected String getElementHtml() {
		StringBuffer innerHTML = new StringBuffer();
		innerHTML.append("<" + this.getElementType() + super.getElementHtml() + ">");
		if (this.getText() != null && !("").equals(this.getText()))
		{
			innerHTML.append(this.getText());
		}
		for (int index = 0; index < this.components.size(); index++)
		{
			innerHTML.append(((AbstractComponent) this.components.get(index)).getInnerHtml());
		}
		innerHTML.append(this.assembleEndTag());
		return innerHTML.toString();
	}
}
