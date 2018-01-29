/**
 * BaseModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.user;

public class BaseModel  implements java.io.Serializable {
    private Long createBy;

    private java.util.Calendar createtime;

    private Long updateBy;

    private java.util.Calendar updatetime;

    public BaseModel() {
    }

    public BaseModel(
           Long createBy,
           java.util.Calendar createtime,
           Long updateBy,
           java.util.Calendar updatetime) {
           this.createBy = createBy;
           this.createtime = createtime;
           this.updateBy = updateBy;
           this.updatetime = updatetime;
    }


    /**
     * Gets the createBy value for this BaseModel.
     *
     * @return createBy
     */
    public Long getCreateBy() {
        return createBy;
    }


    /**
     * Sets the createBy value for this BaseModel.
     *
     * @param createBy
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }


    /**
     * Gets the createtime value for this BaseModel.
     *
     * @return createtime
     */
    public java.util.Calendar getCreatetime() {
        return createtime;
    }


    /**
     * Sets the createtime value for this BaseModel.
     *
     * @param createtime
     */
    public void setCreatetime(java.util.Calendar createtime) {
        this.createtime = createtime;
    }


    /**
     * Gets the updateBy value for this BaseModel.
     *
     * @return updateBy
     */
    public Long getUpdateBy() {
        return updateBy;
    }


    /**
     * Sets the updateBy value for this BaseModel.
     *
     * @param updateBy
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }


    /**
     * Gets the updatetime value for this BaseModel.
     *
     * @return updatetime
     */
    public java.util.Calendar getUpdatetime() {
        return updatetime;
    }


    /**
     * Sets the updatetime value for this BaseModel.
     *
     * @param updatetime
     */
    public void setUpdatetime(java.util.Calendar updatetime) {
        this.updatetime = updatetime;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof BaseModel)) return false;
        BaseModel other = (BaseModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.createBy==null && other.getCreateBy()==null) ||
             (this.createBy!=null &&
              this.createBy.equals(other.getCreateBy()))) &&
            ((this.createtime==null && other.getCreatetime()==null) ||
             (this.createtime!=null &&
              this.createtime.equals(other.getCreatetime()))) &&
            ((this.updateBy==null && other.getUpdateBy()==null) ||
             (this.updateBy!=null &&
              this.updateBy.equals(other.getUpdateBy()))) &&
            ((this.updatetime==null && other.getUpdatetime()==null) ||
             (this.updatetime!=null &&
              this.updatetime.equals(other.getUpdatetime())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCreateBy() != null) {
            _hashCode += getCreateBy().hashCode();
        }
        if (getCreatetime() != null) {
            _hashCode += getCreatetime().hashCode();
        }
        if (getUpdateBy() != null) {
            _hashCode += getUpdateBy().hashCode();
        }
        if (getUpdatetime() != null) {
            _hashCode += getUpdatetime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.webservice.platform.gcdata.com/", "baseModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createtime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createtime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updateBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatetime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updatetime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
