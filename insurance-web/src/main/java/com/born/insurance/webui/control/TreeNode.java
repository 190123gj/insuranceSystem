/*
 * Created on 2003-12-4
 * author: He Kun
 */
package com.born.insurance.webui.control;

/**
 * <Title>TreeNode<br>
 * <Descripiton>TreeNode<br>
 * <Company>bornsoft
 * @author He Kun
 *2003-12-4 13:06:33
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class TreeNode
{
	protected String id="";
	protected String text="";
	protected String url="";
	protected String target="";
	protected boolean checked=false;	
	protected int index=-1;
	
	private TreeNode parent;
	private TreeNode root;
	private Tree	 ownerTree;
	private String imageName=null;
	
	private List children=new ArrayList();		//�ӽڵ��б�
	
	
	/**
	 * ���캯����һ���ڵ�
	 * @param id
	 * @param text
	 */
	public TreeNode(String id,String text)
	{
		this.id=id;
		this.text=text;
	}
	/**
	 * ��ýڵ�����ӽڵ�
	 * @param id
	 * @param text
	 * @return
	 */
	public TreeNode addNode(String id,String text)
	{
		TreeNode node=new TreeNode(id, text);
		node.setParent(this);
		node.setRoot(this.getRoot());
		node.setOwnerTree(this.ownerTree);	
		//Ϊ�������нڵ㽨�� ��id���ŵ�������id�ظ�����ǰ��ͬid�ڵ㽫������
		Map treeNodeMap=this.ownerTree.getNodeMap();
		treeNodeMap.put(id, node);
		TreeNode node2=(TreeNode)treeNodeMap.get(id);
		node2.setIndex(this.children.size());	
		this.children.add(node2);
		return node;
	}
	/**
	 * Ϊ���ĸ�ڵ���ӽڵ㣬
	 * @param id
	 * @param text
	 * @param ownerTree	�ڵ����ڵ���
	 * @return ����ӵĽڵ�
	 */
	TreeNode addNode(String id,String text,Tree ownerTree)
	{
		TreeNode node=new TreeNode(id, text);
		node.setParent(this);
		node.setRoot(this.getRoot());
		node.setOwnerTree(ownerTree);	
		//Ϊ�������нڵ㽨�� ��id���ŵ�������id�ظ�����ǰ��ͬid�ڵ㽫������
		Map treeNodeMap=ownerTree.getNodeMap();
		treeNodeMap.put(id, node);
		TreeNode node2=(TreeNode)treeNodeMap.get(id);	
		node2.setIndex(this.children.size());		
		this.children.add(node2);
		return node;
	}
	/**
	 * ���idɾ��һ���ڵ�
	 * @param id
	 */
	public void removeNode(String id)
	{
		TreeNode node=(TreeNode)this.getOwnerTree().getNodeMap().get(id);		
		if(node!=null)
		{
			int nodeIndex=node.getIndex();
			this.getOwnerTree().getNodeMap().remove(id);
			this.children.remove(nodeIndex);
		}		
	}
	/**
	 * ����������ӽڵ㼯����ɾ��һ���ӽڵ�
	 * @param index
	 */
	public void removeNode(int index)
	{
		TreeNode node=this.getChild(index);
		if(node!=null)
		{	this.getOwnerTree().getNodeMap().remove(node.getId()); 
			this.children.remove(index);
		}	
	}
	
	/**
	 * ��ȡָ��id���ӽڵ������
	 * @param id
	 * @return
	 */
	public TreeNode getChild(String id)
	{
		return ((TreeNode)this.getOwnerTree().getNodeMap().get(id));		
	}
	/**
	 * ��ȡָ��������ֽڵ������
	 * @param index
	 * @return
	 */
	public TreeNode getChild(int index)
	{
		return ((TreeNode)this.children.get(index));	
	}
	/**
	 * ��ȡ�ýڵ�ͬ����ǰһ���ڵ�
	 * @return
	 */
	public TreeNode getPrevious()
	{
		if(this.isFirst())
			return null;
		else
			return ((TreeNode)this.getParent().getChild(this.getIndex()-1));		
	}
	/**
	 * ��ȡ�ýڵ�ͬ������һ���ڵ�
	 * @return
	 */
	public TreeNode getNext()
	{
		if(this.isLast())
			return null;
		else
			return ((TreeNode)this.parent.getChild(this.getIndex()+1));		
	}
	/**
	 * �Ƿ����ֽڵ�
	 * @return
	 */
	public boolean hasChildren()
	{
		return this.children.size()>0;
	}
	/**
	 * ��ͬ���ڵ㼯�����Ƿ��ǵ�һ���ڵ�
	 * @return
	 */
	public boolean isFirst()
	{
		int nodeIndex=this.getIndex();
		return nodeIndex==0;
	}
	/**
	 * ��ͬ���ڵ㼯�����Ƿ������һ���ڵ�
	 * @return
	 */
	public boolean isLast()
	{
		int nodeIndex=this.getIndex();
		return nodeIndex==this.getParent().getChildren().size()-1;
	}
	/**
	 * ���ؽڵ�id
	 * @return �ڵ�id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * ���ؽڵ�����
	 * @return �ڵ�����
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * ���ýڵ�id
	 * @param string �ڵ�id
	 */
	public void setId(String string)
	{
		id = string;
	}

	/**
	 * ���ýڵ�����
	 * @param string �ڵ�����
	 */
	public void setText(String string)
	{
		text = string;
	}
	
	

	/**
	 * ��ȡ�ýڵ����ڵ�������
	 * @return �ýڵ����ڵ�������
	 */
	public Tree getOwnerTree()
	{
		return ownerTree;
	}

	/**
	 * ��ȡ�ýڵ�ĸ��ڵ�
	 * @return �ýڵ����ڵ�������
	 */
	public TreeNode getParent()
	{
		return parent;
	}

	/**
	 * @return
	 */
	TreeNode getRoot()
	{
		return root;
	}

	/**
	 * ���ýڵ����ڵ�������
	 * @param tree
	 */
	public void setOwnerTree(Tree tree)
	{
		ownerTree = tree;
	}

	/**
	 * ���øýڵ�ĸ��ڵ�
	 * @param node
	 */
	public void setParent(TreeNode node)
	{
		parent = node;
	}

	/**
	 * @param node
	 */
	 void setRoot(TreeNode node)
	{
		root = node;
	}

	/**
	 * ���ظýڵ���ӽڵ��б�
	 * @return �ýڵ���ӽڵ��б�
	 */
	public List getChildren()
	{
		return children;
	}

	/**
	 * ���øýڵ���ӽڵ��б�
	 * @param list �ýڵ���ӽڵ��б�
	 */
	public void setChildren(List list)
	{
		children = list;
	}

	/**
	 * ��ȡ�ýڵ��target����
	 * @return �ýڵ��target����
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * ��ȡ�ýڵ��url����
	 * @return
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * ���øýڵ��target����
	 * @param string
	 */
	public void setTarget(String string)
	{
		target = string;
		if(this.ownerTree!=null)
				this.ownerTree.enableTarget=true;
	}

	/**
	 * ���øýڵ��url����
	 * @param string
	 */
	public void setUrl(String string)
	{
		url = string;
		if(this.ownerTree!=null)
			this.ownerTree.enableUrl=true;
	}

	/**
	 * ��ȡ�ýڵ���ͬ���ڵ㼯���е�����
	 * @return
	 */
	int getIndex()
	{
		return index;
	}

	/**
	 * @param i
	 */
	void setIndex(int i) 
	{
		index = i;
	}

	/**
	 * ��ȡ�ڵ����ԣ��Ƿ�ѡ��
	 * @return
	 */
	public boolean getChecked()
	{
//		if(this.parent!=null && this.parent.getChecked()==true)
//			checked=true;
		return checked;
	}

	/**
	 *  ���ýڵ����ԣ��Ƿ�ѡ��
	 * @param b
	 */
	public void setChecked(boolean b)
	{
		checked = b;
	}

	/**
	 * @return
	 */
	public String getImageName()
	{
		return imageName;
	}

	/**
	 * @param string
	 */
	public void setImageName(String string)
	{
		imageName = string;
	}

}
