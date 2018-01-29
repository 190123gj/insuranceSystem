/**
 * FlowService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.insurance.integration.bpm.flow;

public interface FlowService extends java.rmi.Remote {
    public String getTaskUsersByInstanceIdAndNodeId(String instanceId, String nodeId) throws java.rmi.RemoteException;
    public String getMyFlowListJson(String json) throws java.rmi.RemoteException;
    public String getFlowTypeList() throws java.rmi.RemoteException;
    public String getTaskUsers(String strTaskId) throws java.rmi.RemoteException;
    public String delDraftByRunIds(String runIds) throws java.rmi.RemoteException;
    public String getAlreadyMattersListJson(String json) throws java.rmi.RemoteException;
    public String getMyRequestListJson(String json) throws java.rmi.RemoteException;
    public String getMyCompletedListJson(String json) throws java.rmi.RemoteException;
    public String getMyDraftListJson(String json) throws java.rmi.RemoteException;
    public String readTask(String actInstId, String taskId) throws java.rmi.RemoteException;
    public String saveDraftByForm(String json) throws java.rmi.RemoteException, com.born.insurance.integration.bpm.flow.Exception;
}
