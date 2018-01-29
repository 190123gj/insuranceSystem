package com.born.insurance.webui.control;

/**
 * <p>Title: the submit button control</p>
 * <p>Description: the submit button control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: bornsoft</p>
 * @author: �ᴺ��
 * @version 1.0
 */

public class Submit extends AbstractComponent{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * <p>Description: create the submit button control</p>
   * @param id: the control id
   */
  public Submit(String id){
    super(id);
    this.name = id;
  }
  /**
   * <p>Description: create the submit button control</p>
   * @param id
   * @param Text
   */
  public Submit(String id,String Text){
      super(id);
      this.name = id;
      this.text=Text;
    }

  /**
   * get the html code of the control
   * @return the html code of the control
   */
  protected String getElementHtml(){
    if(!("").equals(this.getText())){
      return "<input type=\"submit\" " +
          Submit.pairAttribute("value", this.getText()) + super.getElementHtml() +
          " >";
    }
    else{
      return "<input type=\"submit\" " + super.getElementHtml() + " >";
    }

  }

}
