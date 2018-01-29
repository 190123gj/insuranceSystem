package com.born.insurance.integration.bpm.user;

public class UserDetailsServiceProxy implements com.born.insurance.integration.bpm.user.UserDetailsService {
  private String _endpoint = null;
  private com.born.insurance.integration.bpm.user.UserDetailsService userDetailsService = null;
  
  public UserDetailsServiceProxy() {
    _initUserDetailsServiceProxy();
  }
  
  public UserDetailsServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserDetailsServiceProxy();
  }
  
  private void _initUserDetailsServiceProxy() {
    try {
      userDetailsService = (new com.born.insurance.integration.bpm.user.UserDetailsServiceImplServiceLocator()).getUserDetailsServiceImplPort();
      if (userDetailsService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userDetailsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userDetailsService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userDetailsService != null)
      ((javax.xml.rpc.Stub)userDetailsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.insurance.integration.bpm.user.UserDetailsService getUserDetailsService() {
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService;
  }
  
  public com.born.insurance.integration.bpm.user.SysOrg loadPrimaryOrgByUsername(String userAccount) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadPrimaryOrgByUsername(userAccount);
  }

  public String findDeptInfoByDeptId(String deptId) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findDeptInfoByDeptId(deptId);
  }

  public String findUserByDeptId(String deptId) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByDeptId(deptId);
  }

  public String findUserByDeptCode(String deptCode) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByDeptCode(deptCode);
  }

  public com.born.insurance.integration.bpm.user.Job[] loadJobsByUsername(String userAccount) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadJobsByUsername(userAccount);
  }

  public com.born.insurance.integration.bpm.user.SysUser loadUserByUsername(String userAccount) throws java.rmi.RemoteException, com.born.insurance.integration.bpm.user.Exception{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadUserByUsername(userAccount);
  }

  public String login(String account, String password, String ip, String source) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.login(account, password, ip, source);
  }

  public String findUserByRoleAlias(String roleAlias) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByRoleAlias(roleAlias);
  }

  public String findDeptInfoByDeptCode(String deptCode) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findDeptInfoByDeptCode(deptCode);
  }

  public String findUserRelatedInfoByUserId(Long userId) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserRelatedInfoByUserId(userId);
  }

  public String findUserByJobCode(String jobCode) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByJobCode(jobCode);
  }

  public String loadUserOrgByFullName(String fullName) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadUserOrgByFullName(fullName);
  }

  public String updatePwd(String account, String password) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.updatePwd(account, password);
  }

  public String findUserByUserId(Long userId) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByUserId(userId);
  }

  public String loadOrgByName(String orgName) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadOrgByName(orgName);
  }
  
  
}