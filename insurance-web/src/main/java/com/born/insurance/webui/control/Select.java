package com.born.insurance.webui.control;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>Title: The Select Control</p>
 * <p>Description:The Select Control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: bornsoft</p>
 * @author: QingJian
 * @version 1.0
 */

public class Select extends AbstractComponent  {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
   * Define the List of the Select control Options
   */
  private List Options=new ArrayList();
  public int SelectedIndex;
  /**
   * Create a Select control,the param Options is need
   * @param id
   * @param requst
   * @param Options

  public Select(String id, HttpServletRequest requst,List Options) {
    super(id, requst);
    this.Options=Options;
  }
  */
  public Select(String id) {
    super(id);
  }
  public Select(String id,List Options) {
    super(id);
    this.Options=Options;
  }
  /**
   * Set SelectedIndex
   * @param SelectedIndex
   */
  public void setSelectedIndex(int SelectedIndex){
    this.SelectedIndex=SelectedIndex;
  }
  /**
   * Get SelectedIndex
   * @return
   */
  public int getSelectedIndex(){
    return  this.SelectedIndex;
  }
  /**
   *
   * @param Options
   */
  public void setOptions(List Options){
    this.Options=Options;
  }
  /**
   *
   * @return
   */
  public List getOptions(){
    return this.Options;
  }
  /**
   *
   * @param index
   * @param option
   */
  public void setOption(int index,Option option){
    this.Options.set(index,option);
  }
  /**
   *
   * @param index
   * @return
   */
  public Option getOption(int index)
  {
      return (Option) Options.get(index);
    }
    /**
     *
     * @return
     */
  protected String getElementHtml(){
    StringBuffer htmlOptions=new StringBuffer(100);
    htmlOptions.append("<select "+super.getElementHtml()+" >");
    int OptionIndex;
    for(OptionIndex=0;OptionIndex<Options.size();OptionIndex++){
      if(OptionIndex==this.SelectedIndex){
        this.getOption(OptionIndex).setSelected(true);
      }
      htmlOptions.append(this.getOption(OptionIndex).getInnerHtml());
    }
    htmlOptions.append("</select>");
    return htmlOptions.toString();
  }
}


