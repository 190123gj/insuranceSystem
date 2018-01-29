/**
 * SystemResourcesServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.resources;

public class SystemResourcesServiceImplServiceLocator extends org.apache.axis.client.Service implements com.born.insurance.integration.bpm.resources.SystemResourcesServiceImplService {

    public SystemResourcesServiceImplServiceLocator() {
    }


    public SystemResourcesServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SystemResourcesServiceImplServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SystemResourcesServiceImplPort
    private String SystemResourcesServiceImplPort_address = "http://192.168.45.195:29440/BORN-BPM/service/SystemResourcesService";

    public String getSystemResourcesServiceImplPortAddress() {
        return SystemResourcesServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String SystemResourcesServiceImplPortWSDDServiceName = "SystemResourcesServiceImplPort";

    public String getSystemResourcesServiceImplPortWSDDServiceName() {
        return SystemResourcesServiceImplPortWSDDServiceName;
    }

    public void setSystemResourcesServiceImplPortWSDDServiceName(String name) {
        SystemResourcesServiceImplPortWSDDServiceName = name;
    }

    public com.born.insurance.integration.bpm.resources.SystemResourcesService getSystemResourcesServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SystemResourcesServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSystemResourcesServiceImplPort(endpoint);
    }

    public com.born.insurance.integration.bpm.resources.SystemResourcesService getSystemResourcesServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.born.insurance.integration.bpm.resources.SystemResourcesServiceImplServiceSoapBindingStub _stub = new com.born.insurance.integration.bpm.resources.SystemResourcesServiceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getSystemResourcesServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSystemResourcesServiceImplPortEndpointAddress(String address) {
        SystemResourcesServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.born.insurance.integration.bpm.resources.SystemResourcesService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.born.insurance.integration.bpm.resources.SystemResourcesServiceImplServiceSoapBindingStub _stub = new com.born.insurance.integration.bpm.resources.SystemResourcesServiceImplServiceSoapBindingStub(new java.net.URL(SystemResourcesServiceImplPort_address), this);
                _stub.setPortName(getSystemResourcesServiceImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("SystemResourcesServiceImplPort".equals(inputPortName)) {
            return getSystemResourcesServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.webservice.platform.gcdata.com/", "SystemResourcesServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.webservice.platform.gcdata.com/", "SystemResourcesServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("SystemResourcesServiceImplPort".equals(portName)) {
            setSystemResourcesServiceImplPortEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
