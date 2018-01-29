package com.born.insurance.integration.bpm.flow;

public class FlowServiceProxy implements com.born.insurance.integration.bpm.flow.FlowService {
  private String _endpoint = null;
  private com.born.insurance.integration.bpm.flow.FlowService flowService = null;
  
  public FlowServiceProxy() {
    _initFlowServiceProxy();
  }
  
  public FlowServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initFlowServiceProxy();
  }
  
  private void _initFlowServiceProxy() {
    try {
      flowService = (new com.born.insurance.integration.bpm.flow.FlowServiceImplServiceLocator()).getFlowServiceImplPort();
      if (flowService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)flowService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)flowService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (flowService != null)
      ((javax.xml.rpc.Stub)flowService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.insurance.integration.bpm.flow.FlowService getFlowService() {
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService;
  }
  
  public String getTaskUsersByInstanceIdAndNodeId(String instanceId, String nodeId) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getTaskUsersByInstanceIdAndNodeId(instanceId, nodeId);
  }

  public String getMyFlowListJson(String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyFlowListJson(json);
  }

  public String getFlowTypeList() throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getFlowTypeList();
  }

  public String getTaskUsers(String strTaskId) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getTaskUsers(strTaskId);
  }

  public String delDraftByRunIds(String runIds) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.delDraftByRunIds(runIds);
  }

  public String getAlreadyMattersListJson(String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getAlreadyMattersListJson(json);
  }

  public String getMyRequestListJson(String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyRequestListJson(json);
  }

  public String getMyCompletedListJson(String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyCompletedListJson(json);
  }

  public String getMyDraftListJson(String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyDraftListJson(json);
  }

  public String readTask(String actInstId, String taskId) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.readTask(actInstId, taskId);
  }

  public String saveDraftByForm(String json) throws java.rmi.RemoteException, com.born.insurance.integration.bpm.flow.Exception{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.saveDraftByForm(json);
  }
  
  
}