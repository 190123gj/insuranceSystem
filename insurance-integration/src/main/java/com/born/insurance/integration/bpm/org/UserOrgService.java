/**
 * UserOrgService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.org;

public interface UserOrgService extends java.rmi.Remote {
    public String removeUserOrgRel(String userAccount, String orgCode) throws java.rmi.RemoteException;
    public String addUserRoleRel(String userAccount, String roleAlias) throws java.rmi.RemoteException;
    public String addOrUpdatePos(String posInfo) throws java.rmi.RemoteException;
    public String addOrUpdateJob(String jobInfo) throws java.rmi.RemoteException;
    public String deleteParam(String paramKey) throws java.rmi.RemoteException;
    public String addUserPosRel(String userAccount, String posCode, String orgType, String posType) throws java.rmi.RemoteException;
    public String addOrUpdateUser(String userInfo) throws java.rmi.RemoteException;
    public String addUserOrgRel(String userAccount, String orgCode, String userPerms, String orgPerms, String roleAlias) throws java.rmi.RemoteException;
    public String removeUserRel(String upUserAccount, String downUserAccount) throws java.rmi.RemoteException;
    public String deleteOrg(String code) throws java.rmi.RemoteException;
    public String removeUserPosRel(String userAccount, String posCode) throws java.rmi.RemoteException;
    public String addUserRel(String upUserAccount, String downUserAccount) throws java.rmi.RemoteException;
    public String addOrUpdateRole(String roleInfo) throws java.rmi.RemoteException;
    public String editParamValue(String type, String key, String paramValue) throws java.rmi.RemoteException;
    public String deleteJob(String jobcode) throws java.rmi.RemoteException;
    public String removeUserRoleRel(String userAccount, String roleAlias) throws java.rmi.RemoteException;
    public String removeOrgRoleRel(String orgCode, String roleAlias) throws java.rmi.RemoteException;
    public String deleteRole(String alias) throws java.rmi.RemoteException;
    public String deletePos(String posCode) throws java.rmi.RemoteException;
    public String addOrUpdateOrg(String orgInfo) throws java.rmi.RemoteException;
    public String addOrUpdateParam(String paramInfo) throws java.rmi.RemoteException;
    public String getParam() throws java.rmi.RemoteException;
    public String addOrgRoleRel(String orgCode, String roleAlias) throws java.rmi.RemoteException;
    public String deleteUser(String account) throws java.rmi.RemoteException;
}
