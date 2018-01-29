package com.born.insurance.integration.bpm.resources;

public class SystemResourcesServiceProxy implements com.born.insurance.integration.bpm.resources.SystemResourcesService {
  private String _endpoint = null;
  private com.born.insurance.integration.bpm.resources.SystemResourcesService systemResourcesService = null;
  
  public SystemResourcesServiceProxy() {
    _initSystemResourcesServiceProxy();
  }
  
  public SystemResourcesServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initSystemResourcesServiceProxy();
  }
  
  private void _initSystemResourcesServiceProxy() {
    try {
      systemResourcesService = (new com.born.insurance.integration.bpm.resources.SystemResourcesServiceImplServiceLocator()).getSystemResourcesServiceImplPort();
      if (systemResourcesService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)systemResourcesService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)systemResourcesService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (systemResourcesService != null)
      ((javax.xml.rpc.Stub)systemResourcesService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.insurance.integration.bpm.resources.SystemResourcesService getSystemResourcesService() {
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService;
  }
  
  public String getMenuResByAccount(String sysAlias, String account) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getMenuResByAccount(sysAlias, account);
  }

  public String getMenuFunctionResByAccount(String sysAlias, String account) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getMenuFunctionResByAccount(sysAlias, account);
  }

  public String getAllResByAccount(String sysAlias, String account) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getAllResByAccount(sysAlias, account);
  }

  public String getRoleByFunc(String sysAlias, String func) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getRoleByFunc(sysAlias, func);
  }

  public String getRoleByUrl(String sysAlias, String url) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getRoleByUrl(sysAlias, url);
  }
  
  
}