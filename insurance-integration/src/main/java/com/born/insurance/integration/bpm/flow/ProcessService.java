/**
 * ProcessService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.flow;

public interface ProcessService extends java.rmi.Remote {
	public String getTaskByTaskId(String taskId)
																	throws java.rmi.RemoteException;

	public String getProCopyList(String userId, String json)
																							throws java.rmi.RemoteException;

	public String getMyProcessRunByXml(String xml)
																		throws java.rmi.RemoteException;

	public String defRecover(String userId, String json)
																						throws java.rmi.RemoteException;

	public String getProcessRunByRunId(String runId)
																		throws java.rmi.RemoteException;

	public String getDestNodeHandleUsers(String taskId,
										 String destNodeId)
																				throws java.rmi.RemoteException;

	public String getCompletedMattersList(String userId, String json)
																									throws java.rmi.RemoteException;

	public String setTaskVars(String xml) throws java.rmi.RemoteException;

	public String getProcessOpinionByRunId(String runId)
																			throws java.rmi.RemoteException;

	public String getApprovalItems(String taskId)
																		throws java.rmi.RemoteException;

	public String taskEndProcess(String userId, String json)
																							throws java.rmi.RemoteException;

	public String doNext(String xml) throws java.rmi.RemoteException;

	public String addSignUsers(String signUserIds, String taskId)
																								throws java.rmi.RemoteException;

	public String getPendingMattersList(String userId, String json)
																									throws java.rmi.RemoteException;

	public String setDefOtherParam(String defId, String json)
																							throws java.rmi.RemoteException;

	public String canSelectedUser(String taskId)
																	throws java.rmi.RemoteException;

	public String taskAssign(String json) throws java.rmi.RemoteException;

	public String isAllowAddSign(String account, String taskId)
																								throws java.rmi.RemoteException;

	public String getTasksByAccount(String xml) throws java.rmi.RemoteException;

	public String getTaskFormUrl(String taskId) throws java.rmi.RemoteException;

	public String getBpmAllNode(String defId) throws java.rmi.RemoteException;

	public String getProcessRunByTaskId(String taskId)
																			throws java.rmi.RemoteException;

	public String getTaskOutNodes(String taskId)
																	throws java.rmi.RemoteException;

	public String getProcessRun(String xml) throws java.rmi.RemoteException;

	public String endProcessByTaskId(String taskId)
																		throws java.rmi.RemoteException;

	public String getBpmDefinitionByDefId(String defId)
																			throws java.rmi.RemoteException;

	public String getAlreadyMattersList(String userId, String json)
																									throws java.rmi.RemoteException;

	public String getBpmDefinition(String xml) throws java.rmi.RemoteException;

	public String getNextFlowNodes(String taskId, String account)
																								throws java.rmi.RemoteException;

	public String saveNode(String json) throws java.rmi.RemoteException;

	public String getVariablesByRunId(String runId)
																		throws java.rmi.RemoteException;

	public String getStatusByRunidNodeId(String runId, String nodeId)
																									throws java.rmi.RemoteException;

	public String taskCountersign(String userId, String json)
																							throws java.rmi.RemoteException;

	public String getTaskNode(String taskId) throws java.rmi.RemoteException;

	public String setAgent(String json) throws java.rmi.RemoteException;

	public String assignUsers(String json) throws java.rmi.RemoteException;

	public String getVariablesByTaskId(String taskId)
																			throws java.rmi.RemoteException;

	public String start(String xml) throws java.rmi.RemoteException;

	public String getFreeJump(String taskId) throws java.rmi.RemoteException;

	public String getTasksByRunId(String runId) throws java.rmi.RemoteException;

	public String isSelectPath(String taskId) throws java.rmi.RemoteException;

	public String getTasksByAccountGroupByProcessName(String xml)
																						throws java.rmi.RemoteException;

	public String getFinishTask(String xml) throws java.rmi.RemoteException;

	public String isAllowBack(String taskId) throws java.rmi.RemoteException;

	public String getByBusinessKey(String businessKey)
																			throws java.rmi.RemoteException;

	public String getXml() throws java.rmi.RemoteException;

	public String getTaskNameByTaskId(String taskId)
																		throws java.rmi.RemoteException;

	public String getProTransMattersList(String userId, String json)
																									throws java.rmi.RemoteException;

	public String getNodesByDefKey(String defKey)
																		throws java.rmi.RemoteException;

	public String getFinishTaskDetailUrl(String actInstId,
										 String nodeKey)
																				throws java.rmi.RemoteException;

	public String getAccordingMattersList(String userId, String json)
																									throws java.rmi.RemoteException;

	public String getProcessOpinionByActInstId(String actInstId)
																					throws java.rmi.RemoteException;
}
