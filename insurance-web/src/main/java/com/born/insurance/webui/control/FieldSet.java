package com.born.insurance.webui.control;

import java.util.*;

public class FieldSet extends AbstractComponent {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
protected Legend legend=new Legend();
  protected List ComponentList=new ArrayList();
  /**
   *
   */
  public FieldSet() {

  }
  /**
   *
   * @param ID
   */
  public FieldSet(String ID) {
    super(ID);
  }
  /**
   *
   * @param legend
   */
  public void setLegend(Legend legend){
    this.legend=legend;
  }
  /**
   *
   * @return
   */
  public Legend getLegend(){
    return this.legend;
  }
  /**
   *
   * @param ComponentList
   */
  public void setComponentList(List ComponentList){
    this.ComponentList=ComponentList;
  }
  /**
   *
   * @return
   */
  public List getComponentList(){
    return this.ComponentList;
  }
  /**
   *
   * @param Component
   */
  public void addComponent(AbstractComponent Component){
    this.ComponentList.add(Component);
  }
  /**
   *
   * @return
   */
  public String getElementHtml(){
    String Html="<fieldset "+super.getElementHtml()+">"+this.getLegend().getInnerHtml();
    for(int i=0;i<this.getComponentList().size();i++){
      AbstractComponent component=(AbstractComponent)this.getComponentList().get(i);
      Html=Html+component.getInnerHtml();
    }
    Html=Html+"</fieldset>";
    return Html;
  }
  public static void main(String[] args) {
    FieldSet fieldSet1 = new FieldSet();
  }


}