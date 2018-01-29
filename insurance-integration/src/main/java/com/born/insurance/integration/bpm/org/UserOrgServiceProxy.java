package com.born.insurance.integration.bpm.org;

public class UserOrgServiceProxy implements com.born.insurance.integration.bpm.org.UserOrgService {
  private String _endpoint = null;
  private com.born.insurance.integration.bpm.org.UserOrgService userOrgService = null;
  
  public UserOrgServiceProxy() {
    _initUserOrgServiceProxy();
  }
  
  public UserOrgServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserOrgServiceProxy();
  }
  
  private void _initUserOrgServiceProxy() {
    try {
      userOrgService = (new com.born.insurance.integration.bpm.org.UserOrgServiceImplServiceLocator()).getUserOrgServiceImplPort();
      if (userOrgService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userOrgService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userOrgService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userOrgService != null)
      ((javax.xml.rpc.Stub)userOrgService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.insurance.integration.bpm.org.UserOrgService getUserOrgService() {
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService;
  }
  
  public String removeUserOrgRel(String userAccount, String orgCode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserOrgRel(userAccount, orgCode);
  }

  public String addUserRoleRel(String userAccount, String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserRoleRel(userAccount, roleAlias);
  }

  public String addOrUpdatePos(String posInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdatePos(posInfo);
  }

  public String addOrUpdateJob(String jobInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateJob(jobInfo);
  }

  public String deleteParam(String paramKey) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteParam(paramKey);
  }

  public String addUserPosRel(String userAccount, String posCode, String orgType, String posType) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserPosRel(userAccount, posCode, orgType, posType);
  }

  public String addOrUpdateUser(String userInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateUser(userInfo);
  }

  public String addUserOrgRel(String userAccount, String orgCode, String userPerms, String orgPerms, String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserOrgRel(userAccount, orgCode, userPerms, orgPerms, roleAlias);
  }

  public String removeUserRel(String upUserAccount, String downUserAccount) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserRel(upUserAccount, downUserAccount);
  }

  public String deleteOrg(String code) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteOrg(code);
  }

  public String removeUserPosRel(String userAccount, String posCode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserPosRel(userAccount, posCode);
  }

  public String addUserRel(String upUserAccount, String downUserAccount) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserRel(upUserAccount, downUserAccount);
  }

  public String addOrUpdateRole(String roleInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateRole(roleInfo);
  }

  public String editParamValue(String type, String key, String paramValue) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.editParamValue(type, key, paramValue);
  }

  public String deleteJob(String jobcode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteJob(jobcode);
  }

  public String removeUserRoleRel(String userAccount, String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserRoleRel(userAccount, roleAlias);
  }

  public String removeOrgRoleRel(String orgCode, String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeOrgRoleRel(orgCode, roleAlias);
  }

  public String deleteRole(String alias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteRole(alias);
  }

  public String deletePos(String posCode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deletePos(posCode);
  }

  public String addOrUpdateOrg(String orgInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateOrg(orgInfo);
  }

  public String addOrUpdateParam(String paramInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateParam(paramInfo);
  }

  public String getParam() throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.getParam();
  }

  public String addOrgRoleRel(String orgCode, String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrgRoleRel(orgCode, roleAlias);
  }

  public String deleteUser(String account) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteUser(account);
  }
  
  
}