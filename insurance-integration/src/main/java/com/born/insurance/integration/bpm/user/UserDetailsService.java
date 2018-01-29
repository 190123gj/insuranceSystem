/**
 * UserDetailsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.user;

public interface UserDetailsService extends java.rmi.Remote {
    public com.born.insurance.integration.bpm.user.SysOrg loadPrimaryOrgByUsername(String userAccount) throws java.rmi.RemoteException;
    public String findDeptInfoByDeptId(String deptId) throws java.rmi.RemoteException;
    public String findUserByDeptId(String deptId) throws java.rmi.RemoteException;
    public String findUserByDeptCode(String deptCode) throws java.rmi.RemoteException;
    public com.born.insurance.integration.bpm.user.Job[] loadJobsByUsername(String userAccount) throws java.rmi.RemoteException;
    public com.born.insurance.integration.bpm.user.SysUser loadUserByUsername(String userAccount) throws java.rmi.RemoteException, com.born.insurance.integration.bpm.user.Exception;
    public String login(String account, String password, String ip, String source) throws java.rmi.RemoteException;
    public String findUserByRoleAlias(String roleAlias) throws java.rmi.RemoteException;
    public String findDeptInfoByDeptCode(String deptCode) throws java.rmi.RemoteException;
    public String findUserRelatedInfoByUserId(Long userId) throws java.rmi.RemoteException;
    public String findUserByJobCode(String jobCode) throws java.rmi.RemoteException;
    public String loadUserOrgByFullName(String fullName) throws java.rmi.RemoteException;
    public String updatePwd(String account, String password) throws java.rmi.RemoteException;
    public String findUserByUserId(Long userId) throws java.rmi.RemoteException;
    public String loadOrgByName(String orgName) throws java.rmi.RemoteException;
}
