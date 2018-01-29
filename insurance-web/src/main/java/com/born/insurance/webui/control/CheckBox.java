package com.born.insurance.webui.control;

/**
 * <p>Title: The CheckBox Control</p>
 * <p>Description:The CheckBox Control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: bornsoft</p>
 * @author: qch
 * @version 1.0
 */

public class CheckBox
    extends AbstractComponent {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
protected boolean Checked;
  protected String Value="";

  /**
   * Create a checkbox only by id
   * @param id
   */
  public CheckBox(String id) {
  	super(id);
  	this.setValue("Y");
    this.name = id;
  }

  /**
   * set checkbox checked
   * @param Checked
   */
  public void setChecked(boolean Checked) {
    this.Checked = Checked;
  }

  /**
   * get boolvalue is or not checked
   * @return
   */
  public boolean getChecked() {
    return this.Checked;
  }

  /**
   * set checkbox's value
   * @param Value
   */
  public void setValue(String Value) {
    this.Value = Value;
  }

  /**
   * get set checkbox's value
   * @return value
   */
  public String getValue() {
    return this.Value;
  }

  /**
   * get the html code of checkbox control
   * @return
   */
  protected String getElementHtml() {
    String Html = "<input type=\"checkbox\" ";
    if (!this.getValue().equals("")) {
      this.sourceAttributes.put("value", this.getValue());
    }
    if (getChecked()) {
      Html = Html + " checked";
    }
    Html = Html + super.getElementHtml() + " >";
    return Html;
  }
}
