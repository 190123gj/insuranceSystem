package com.born.insurance.integration.bpm.flow;

public class ProcessServiceProxy implements com.born.insurance.integration.bpm.flow.ProcessService {
	private String _endpoint = null;
	private com.born.insurance.integration.bpm.flow.ProcessService processService = null;
	
	public ProcessServiceProxy() {
		_initProcessServiceProxy();
	}
	
	public ProcessServiceProxy(String endpoint) {
		_endpoint = endpoint;
		_initProcessServiceProxy();
	}
	
	private void _initProcessServiceProxy() {
		try {
			processService = (new com.born.insurance.integration.bpm.flow.ProcessServiceImplServiceLocator())
				.getProcessServiceImplPort();
			if (processService != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) processService)._setProperty(
						"javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) processService)
						._getProperty("javax.xml.rpc.service.endpoint.address");
			}
			
		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}
	
	public String getEndpoint() {
		return _endpoint;
	}
	
	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (processService != null)
			((javax.xml.rpc.Stub) processService)._setProperty(
				"javax.xml.rpc.service.endpoint.address", _endpoint);
		
	}
	
	public com.born.insurance.integration.bpm.flow.ProcessService getProcessService() {
		if (processService == null)
			_initProcessServiceProxy();
		return processService;
	}
	
	@Override
	public String getTaskByTaskId(String taskId)
																	throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskByTaskId(taskId);
	}

	@Override
	public String getProCopyList(String userId, String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProCopyList(userId, json);
	}

	@Override
	public String getMyProcessRunByXml(String xml)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getMyProcessRunByXml(xml);
	}

	@Override
	public String defRecover(String userId, String json)
																						throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.defRecover(userId, json);
	}

	@Override
	public String getProcessRunByRunId(String runId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessRunByRunId(runId);
	}

	@Override
	public String getDestNodeHandleUsers(String taskId,
													String destNodeId)
																				throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getDestNodeHandleUsers(taskId, destNodeId);
	}

	@Override
	public String getCompletedMattersList(String userId, String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getCompletedMattersList(userId, json);
	}

	@Override
	public String setTaskVars(String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.setTaskVars(xml);
	}

	@Override
	public String getProcessOpinionByRunId(String runId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessOpinionByRunId(runId);
	}

	@Override
	public String getApprovalItems(String taskId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getApprovalItems(taskId);
	}

	@Override
	public String taskEndProcess(String userId, String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.taskEndProcess(userId, json);
	}

	@Override
	public String doNext(String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.doNext(xml);
	}

	@Override
	public String addSignUsers(String signUserIds, String taskId)
																								throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.addSignUsers(signUserIds, taskId);
	}

	@Override
	public String getPendingMattersList(String userId, String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getPendingMattersList(userId, json);
	}

	@Override
	public String setDefOtherParam(String defId, String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.setDefOtherParam(defId, json);
	}

	@Override
	public String canSelectedUser(String taskId)
																	throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.canSelectedUser(taskId);
	}

	@Override
	public String taskAssign(String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.taskAssign(json);
	}

	@Override
	public String isAllowAddSign(String account, String taskId)
																								throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.isAllowAddSign(account, taskId);
	}

	@Override
	public String getTasksByAccount(String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTasksByAccount(xml);
	}

	@Override
	public String getTaskFormUrl(String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskFormUrl(taskId);
	}

	@Override
	public String getBpmAllNode(String defId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getBpmAllNode(defId);
	}

	@Override
	public String getProcessRunByTaskId(String taskId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessRunByTaskId(taskId);
	}

	@Override
	public String getTaskOutNodes(String taskId)
																	throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskOutNodes(taskId);
	}

	@Override
	public String getProcessRun(String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessRun(xml);
	}

	@Override
	public String endProcessByTaskId(String taskId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.endProcessByTaskId(taskId);
	}

	@Override
	public String getBpmDefinitionByDefId(String defId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getBpmDefinitionByDefId(defId);
	}

	@Override
	public String getAlreadyMattersList(String userId, String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getAlreadyMattersList(userId, json);
	}

	@Override
	public String getBpmDefinition(String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getBpmDefinition(xml);
	}

	@Override
	public String getNextFlowNodes(String taskId, String account)
																								throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getNextFlowNodes(taskId, account);
	}

	@Override
	public String saveNode(String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.saveNode(json);
	}

	@Override
	public String getVariablesByRunId(String runId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getVariablesByRunId(runId);
	}

	@Override
	public String getStatusByRunidNodeId(String runId, String nodeId)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getStatusByRunidNodeId(runId, nodeId);
	}

	@Override
	public String taskCountersign(String userId, String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.taskCountersign(userId, json);
	}

	@Override
	public String getTaskNode(String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskNode(taskId);
	}

	@Override
	public String setAgent(String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.setAgent(json);
	}

	@Override
	public String assignUsers(String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.assignUsers(json);
	}

	@Override
	public String getVariablesByTaskId(String taskId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getVariablesByTaskId(taskId);
	}

	@Override
	public String start(String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.start(xml);
	}

	@Override
	public String getFreeJump(String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getFreeJump(taskId);
	}

	@Override
	public String getTasksByRunId(String runId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTasksByRunId(runId);
	}

	@Override
	public String isSelectPath(String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.isSelectPath(taskId);
	}

	@Override
	public String getTasksByAccountGroupByProcessName(String xml)
																						throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTasksByAccountGroupByProcessName(xml);
	}

	@Override
	public String getFinishTask(String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getFinishTask(xml);
	}

	@Override
	public String isAllowBack(String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.isAllowBack(taskId);
	}

	@Override
	public String getByBusinessKey(String businessKey)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getByBusinessKey(businessKey);
	}

	@Override
	public String getXml() throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getXml();
	}

	@Override
	public String getTaskNameByTaskId(String taskId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskNameByTaskId(taskId);
	}

	@Override
	public String getProTransMattersList(String userId, String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProTransMattersList(userId, json);
	}

	@Override
	public String getNodesByDefKey(String defKey)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getNodesByDefKey(defKey);
	}

	@Override
	public String getFinishTaskDetailUrl(String actInstId,
													String nodeKey)
																				throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getFinishTaskDetailUrl(actInstId, nodeKey);
	}

	@Override
	public String getAccordingMattersList(String userId, String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getAccordingMattersList(userId, json);
	}

	@Override
	public String getProcessOpinionByActInstId(String actInstId)
																					throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessOpinionByActInstId(actInstId);
	}
	
}