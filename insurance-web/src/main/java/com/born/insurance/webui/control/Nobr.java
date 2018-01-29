package com.born.insurance.webui.control;

public class Nobr extends AbstractComponent {
    public Nobr() {
        this.setElementType("nobr");
    }
    protected String getElementHtml()
    {
        String str="<"+this.getElementType()+ super.getElementHtml()+">"+this.getText();
        for(int i=0;i<this.components.size();i++)
        {
           str+=((IComponent)this.components.get(i)).getInnerHtml();
        }
        return str+this.assembleEndTag();
    }

}