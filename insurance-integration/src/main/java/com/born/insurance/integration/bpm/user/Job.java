/**
 * Job.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.user;

public class Job  extends com.born.insurance.integration.bpm.user.BaseModel  implements java.io.Serializable {
    private Short grade;

    private Long isdelete;

    private String jobcode;

    private String jobdesc;

    private Long jobid;

    private String jobname;

    private Long setid;

    public Job() {
    }

    public Job(
           Long createBy,
           java.util.Calendar createtime,
           Long updateBy,
           java.util.Calendar updatetime,
           Short grade,
           Long isdelete,
           String jobcode,
           String jobdesc,
           Long jobid,
           String jobname,
           Long setid) {
        super(
            createBy,
            createtime,
            updateBy,
            updatetime);
        this.grade = grade;
        this.isdelete = isdelete;
        this.jobcode = jobcode;
        this.jobdesc = jobdesc;
        this.jobid = jobid;
        this.jobname = jobname;
        this.setid = setid;
    }


    /**
     * Gets the grade value for this Job.
     *
     * @return grade
     */
    public Short getGrade() {
        return grade;
    }


    /**
     * Sets the grade value for this Job.
     *
     * @param grade
     */
    public void setGrade(Short grade) {
        this.grade = grade;
    }


    /**
     * Gets the isdelete value for this Job.
     *
     * @return isdelete
     */
    public Long getIsdelete() {
        return isdelete;
    }


    /**
     * Sets the isdelete value for this Job.
     *
     * @param isdelete
     */
    public void setIsdelete(Long isdelete) {
        this.isdelete = isdelete;
    }


    /**
     * Gets the jobcode value for this Job.
     *
     * @return jobcode
     */
    public String getJobcode() {
        return jobcode;
    }


    /**
     * Sets the jobcode value for this Job.
     *
     * @param jobcode
     */
    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }


    /**
     * Gets the jobdesc value for this Job.
     *
     * @return jobdesc
     */
    public String getJobdesc() {
        return jobdesc;
    }


    /**
     * Sets the jobdesc value for this Job.
     *
     * @param jobdesc
     */
    public void setJobdesc(String jobdesc) {
        this.jobdesc = jobdesc;
    }


    /**
     * Gets the jobid value for this Job.
     *
     * @return jobid
     */
    public Long getJobid() {
        return jobid;
    }


    /**
     * Sets the jobid value for this Job.
     *
     * @param jobid
     */
    public void setJobid(Long jobid) {
        this.jobid = jobid;
    }


    /**
     * Gets the jobname value for this Job.
     *
     * @return jobname
     */
    public String getJobname() {
        return jobname;
    }


    /**
     * Sets the jobname value for this Job.
     *
     * @param jobname
     */
    public void setJobname(String jobname) {
        this.jobname = jobname;
    }


    /**
     * Gets the setid value for this Job.
     *
     * @return setid
     */
    public Long getSetid() {
        return setid;
    }


    /**
     * Sets the setid value for this Job.
     *
     * @param setid
     */
    public void setSetid(Long setid) {
        this.setid = setid;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Job)) return false;
        Job other = (Job) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
            ((this.grade==null && other.getGrade()==null) ||
             (this.grade!=null &&
              this.grade.equals(other.getGrade()))) &&
            ((this.isdelete==null && other.getIsdelete()==null) ||
             (this.isdelete!=null &&
              this.isdelete.equals(other.getIsdelete()))) &&
            ((this.jobcode==null && other.getJobcode()==null) ||
             (this.jobcode!=null &&
              this.jobcode.equals(other.getJobcode()))) &&
            ((this.jobdesc==null && other.getJobdesc()==null) ||
             (this.jobdesc!=null &&
              this.jobdesc.equals(other.getJobdesc()))) &&
            ((this.jobid==null && other.getJobid()==null) ||
             (this.jobid!=null &&
              this.jobid.equals(other.getJobid()))) &&
            ((this.jobname==null && other.getJobname()==null) ||
             (this.jobname!=null &&
              this.jobname.equals(other.getJobname()))) &&
            ((this.setid==null && other.getSetid()==null) ||
             (this.setid!=null &&
              this.setid.equals(other.getSetid())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getGrade() != null) {
            _hashCode += getGrade().hashCode();
        }
        if (getIsdelete() != null) {
            _hashCode += getIsdelete().hashCode();
        }
        if (getJobcode() != null) {
            _hashCode += getJobcode().hashCode();
        }
        if (getJobdesc() != null) {
            _hashCode += getJobdesc().hashCode();
        }
        if (getJobid() != null) {
            _hashCode += getJobid().hashCode();
        }
        if (getJobname() != null) {
            _hashCode += getJobname().hashCode();
        }
        if (getSetid() != null) {
            _hashCode += getSetid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Job.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.webservice.platform.gcdata.com/", "job"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "grade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isdelete");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isdelete"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jobcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobdesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jobdesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jobid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jobname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("setid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "setid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
