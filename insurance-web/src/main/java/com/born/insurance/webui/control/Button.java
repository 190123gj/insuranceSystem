package com.born.insurance.webui.control;


/**
 * <p>Title:Button control </p>
 * <p>Description:the button control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:bornsoft </p>
 * @�ᴺ��
 * @version 1.0
 */

public class Button extends AbstractComponent {


  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
   * <p>Description: create a button control</p>
   * @param id: the control id.
   */
  public Button(String id){
    super(id);
    this.name = id;
  }
  /**
   * <p>Description: create a button control</p>
   * @param id
   * @param Text
   */
  public Button(String id,String Text){
    super(id);
    this.name = id;
    this.text=Text;
  }

  /**
   * <p>Description: get the html code of the button control</p>
   * @return the html code of the button control
   */
  protected String getElementHtml()
  {
  	String result="<input type=\"button\" "+pairAttribute("value",this.getText())+super.getElementHtml()+" >";
    return result;
  }
	public static void main(String[] args)
  	{
		Button btn=new Button("btn1");
	  	IServerEvent se=new ServerEvent("Onclick");
	  	se.setFuncName("TestServerFunc()");
	  	se.setClassName("TestClass");
	  	se.setMethodName("TestMethod");
	  	btn.addEvent(se);
	  	IEvent ie=new Event("OnChange");
	  	ie.setFuncName("TestClientFunc()");
	  	btn.addEvent(ie);
	  	btn.setText("hfei");
	  	System.out.println(btn.getInnerHtml());
  }

}