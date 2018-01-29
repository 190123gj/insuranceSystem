/**
 * SystemResourcesService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.resources;

public interface SystemResourcesService extends java.rmi.Remote {
    public String getMenuResByAccount(String sysAlias, String account) throws java.rmi.RemoteException;
    public String getMenuFunctionResByAccount(String sysAlias, String account) throws java.rmi.RemoteException;
    public String getAllResByAccount(String sysAlias, String account) throws java.rmi.RemoteException;
    public String getRoleByFunc(String sysAlias, String func) throws java.rmi.RemoteException;
    public String getRoleByUrl(String sysAlias, String url) throws java.rmi.RemoteException;
}
