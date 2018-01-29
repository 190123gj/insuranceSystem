/*
 * Created on 2003-12-4
 * author: He Kun
 */
package com.born.insurance.webui.control;
/**
 * <Title>Tree<br>
 * <Descripiton>Tree<br>
 * <Company>bornsoft
 * @author He Kun
 *2003-12-4 13:08:46
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
public class Tree extends AbstractComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected TreeNode root = new TreeNode(null, "virtualRoot");
	public static final String NODE_CLICK_EVNET = "onNodeClick";
	protected boolean showScrollBar = true;
	protected boolean showImages = true;
	protected boolean showLines = true;
	protected boolean showCheckBox = false;
	protected boolean isShowUnaidedImage=false;
	protected int borderStyle;
	protected String idField;
	protected String textField;
	protected String parentField;
	protected String urlField;
	protected String targetField;
	protected String checkField;
	private int defaultFieldCount = 6;
	protected boolean enableUrl;
	protected boolean enableTarget;	
	public static String imagePath= ComponentUtil.getInstance().getImagesRequestPath() + "/";
	protected String baseImage;
	protected String branchNodeImage;
	protected String branchNodeOpenedImage;
	protected String nodeImage;
	private String emptyImage;
	private String lineImage;
	private String joinImage;
	private String joinBottomImage;
	private String plusImage;
	private String plusBottomImage;
	private String minusImage;
	private String minusBottomImage;
	private String cookieName = "";
	/**
	 * ѡ���¼�ʱ��ͬʱѡ���ϼ�
	 */
	private boolean isSelectedParentNode=false;
	public static final int TREE_BORDER_NONE = 0;
	public static final int TREE_BORDER_3D = 1;
	public static final String TREE_CSSClASS = "treeText"; //Ĭ��tree��ʽ
	
	private Map nodeMap = new HashMap();
	/**
	 * ���캯��
	 * @param id �����id
	 */
	public Tree(String id)
	{
		super(id);
		// TODO: ���³�ʼ��ʾ�����Դ������ʱ����һ�����ɾ��
		this.setShowScrollBar(true);
		this.setCssClass(TREE_CSSClASS);
		this.setBorderStyle(Tree.TREE_BORDER_3D);
		this.setBaseImage("tree_base.gif");
		this.setNodeImage("tree_page.gif");
		this.setBranchNodeImage("tree_folder.gif");
		this.setBranchNodeOpenedImage("tree_folderopen.gif");
		this.emptyImage = "tree_empty.gif";
		this.lineImage = "tree_line.gif";
		this.joinImage = "tree_join.gif";
		this.joinBottomImage = "tree_joinbottom.gif";
		this.plusImage = "tree_plus.gif";
		this.plusBottomImage = "tree_plusbottom.gif";
		this.minusImage = "tree_minus.gif";
		this.minusBottomImage = "tree_minusbottom.gif";
		
	}
	/**
	 * ��ȡ���ؼ���ʾ�����Դ
	 * @return	ʾ�����Դ
	 */
	public static List getSampleDataSource()
	{
		List nodeList = new ArrayList();
		Map root = new HashMap();
		int count = 5;
		root.put("id", "root");
		root.put("text", "Root�ڵ�");
		nodeList.add(root);
		for (int i = 0; i < count; i++) //���ڵ����count���ӽڵ�
		{
			Map node1 = new HashMap();
			node1.put("id", "node" + i);
			node1.put("text", "�ڵ�" + i);
			node1.put("parent", "root");
			node1.put("url", "http://www.sohu.com");
			node1.put("target", "f1");
			node1.put("check", "true");
			nodeList.add(node1);
			if (i == 3)
			{
				for (int j = 0; j < count - 2; j++) //���3���ڵ�����ӽڵ�
				{
					Map node2 = new HashMap();
					node2.put("id", "node" + i + "_" + j);
					node2.put("text", "�ڵ�" + i + "_" + j);
					node2.put("parent", "node3");
					nodeList.add(node2);
				}
			}
			if (i == 4)
			{
				for (int j = 0; j < count; j++) //���3���ڵ�����ӽڵ�
				{
					Map node2 = new HashMap();
					node2.put("id", "node" + i + "_" + j);
					node2.put("text", "�ڵ�" + i + "_" + j);
					node2.put("parent", "node4");
					nodeList.add(node2);
					if (j == 2)
					{
						for (int k = 0; k < count - 2; k++) //���3���ڵ�����ӽڵ�
						{
							Map node3 = new HashMap();
							node3.put("id", "node" + i + "_" + j + "-" + k);
							node3.put("text", "�ڵ�" + i + "_" + j + "-" + k);
							node3.put("parent", "node" + 4 + "_" + 2);
							nodeList.add(node3);
						}
					}
				}
			}
		}
		Map root2 = new HashMap();
		root2.put("id", "root2");
		root2.put("text", "Root2�ڵ�");
		nodeList.add(root2);
		for (int i = 0; i < count; i++) //���ڵ����count���ӽڵ�
		{
			Map node1 = new HashMap();
			node1.put("id", "node2" + i);
			node1.put("text", "�ڵ�2" + i);
			node1.put("parent", "root2");
			nodeList.add(node1);
		}
		return nodeList;
	}
	/**
	 * ����Tree���Դ�İ��ֶ�
	 * @param idField ���ڵ��id�ֶ�(����)
	 * @param textField ���ڵ��text�ֶ�(����)
	 * @param relationField ���ڵ�Ĺ�ϵ�ֶ�(����)����ϵ�ֶζ�Ӧ���ڵ��id
	 */
	public void setDataField(String idField, String textField, String parentField)
	{
		this.idField = idField;
		this.textField = textField;
		this.parentField = parentField;
		this.setAttribute("idField", idField);
		this.setAttribute("textField", textField);
		this.setAttribute("parentField", parentField);
	}
	/**
	 * ������Դ���һ����
	 * @return
	 */
	private Tree generateTree()
	{
		getChildren(root, (List) this.dataSource);
		return this;
	}
	/**
	 * �����Դ
	 */
	public void dataBind()
	{
		generateTree();
	}
	/**
	 * Ϊָ���ڵ�����ֽڵ���
	 * @param thisNode
	 * @param nodeList
	 */
	private void getChildren(TreeNode thisNode, List nodeList)
	{
		for (int i = 0; i < nodeList.size(); i++) //Ϊ��ڵ�����ӽڵ�
		{
			Map node = (Map) nodeList.get(i);
			String parent = (String) node.get(this.parentField);
			//System.out.println("find child of: "+thisNode.getText());
			String id = (String) node.get(this.idField);
			String text = (String) node.get(this.textField);
			String url = null;
			String target = null;
			boolean checked = false;
			if (this.urlField != null && !("").equals(this.urlField))
				url = (String) node.get(this.urlField);
			if (this.targetField != null && !("").equals(this.targetField))
				target = (String) node.get(this.targetField);
			if (this.checkField != null && !("").equals(this.checkField))
			{
				//System.out.println(node.get(this.checkField)==true);
				checked = Boolean.valueOf(node.get(this.checkField).toString()).booleanValue();
			}
			TreeNode child = null;
			//root
			if (thisNode.getText().equals("virtualRoot") && (parent == null || "".equals(parent)))
				child = thisNode.addNode(id, text, this);
			else if (parent != null && parent.equals(thisNode.getId()))
				child = thisNode.addNode(id, text);
			if (child != null)
			{
				if (url != null)
					child.setUrl(url);
				if (target != null)
					child.setTarget(target); //if(i==(nodeList.size()-1)) break;
				child.setChecked(checked);
				getChildren(child, nodeList);
			}
		}
	}
	/**
	 * ��ȡָ���ڵ�Ŀͻ������Դ
	 * @param node
	 * @param indent
	 * @return
	 */
	private String getClientDataSource(TreeNode node, int indent)
	{
		if (node == null)
			return "";
		StringBuffer strDataSource = new StringBuffer();
		//System.out.println(getIndentSpace(indent)+node.getId()+", "+node.getText());
		for (int i = 0; i < node.getChildren().size(); i++)
		{
			TreeNode tempNode = (TreeNode) node.getChildren().get(i);
			strDataSource.append(getIndentSpace(indent) + "[" + "'" + tempNode.getText() + "', '" + tempNode.getId() + "'");
			//	if(this.enableUrl==true )
			//{
			strDataSource.append(",'" + tempNode.getUrl() + "'");
			//		if(this.enableTarget==true )
			strDataSource.append(",'" + tempNode.getTarget() + "'");
			//	}
			//if(this.showCheckBox==true)
			strDataSource.append(",'" + tempNode.getChecked() + "'");
			if(tempNode.getImageName()!=null)
			{
				strDataSource.append(",'" +imagePath+tempNode.getImageName() + "'");
			}
			else
			{
				strDataSource.append(",'" +tempNode.getImageName() + "'");
			}
			
			if (tempNode.getChildren().size() > 0)
			{
				strDataSource.append(", \r\n");
				strDataSource.append(getClientDataSource(tempNode, indent + 2));
			}
			if (i == node.getChildren().size() - 1) //���һ����Ӷ���
				strDataSource.append(getIndentSpace(indent) + "]" + "\r\n");
			else
				strDataSource.append(getIndentSpace(indent) + "]," + "\r\n");
		}
		return strDataSource.toString();
	}
	/**
	 *  �������Ŀͻ������Դ
	 * @return
	 */
	private String getClientDataSource()
	{
		//		this.generateTree(); ����Դ�� dataBind
		String clientDataSouce = this.getClientDataSource(root, 1);
		if (clientDataSouce.endsWith(",")) //ȥ�����һ��','��
		{
			int i = clientDataSouce.lastIndexOf(",");
			clientDataSouce = clientDataSouce.substring(0, i);
		}
		return "[ \r\n" + clientDataSouce + "\r\n ] \r\n";
	}
	private String getClientTemplate()
	{
		StringBuffer strTpl = new StringBuffer();
		strTpl.append("  {\r\n");
		strTpl.append("	'target'  : '_blank',	// name of the frame links will be opened in\r\n");
		strTpl.append("							// other possible values are: _blank, _parent, _search, _self and _top\r\n");
		strTpl.append("\r\n");
		strTpl.append("	'icon_e'  : '" + imagePath + this.getEmptyImage() + "',  // empty image\r\n");
		strTpl.append("	'icon_l'  : '" + imagePath + this.getLineImage() + "',  // vertical line\r\n");
		strTpl.append("	\r\n");
		strTpl.append("	'icon_48' : '" + imagePath + this.baseImage + "',   // root icon normal\r\n");
		strTpl.append("	'icon_52' : '" + imagePath + this.baseImage + "',   // root icon selected\r\n");
		strTpl.append("	'icon_56' : '" + imagePath + this.baseImage + "',   // root icon opened\r\n");
		strTpl.append("	'icon_60' : '" + imagePath + this.baseImage + "',    // root icon selected\r\n");
		strTpl.append("	\r\n");
		strTpl.append("	'icon_16' : '" + imagePath + this.branchNodeImage + "',  // node icon normal\r\n");
		strTpl.append("	'icon_20' : '" + imagePath + this.branchNodeImage + "',// node icon selected\r\n");
		strTpl.append("	'icon_24' : '" + imagePath + this.branchNodeOpenedImage + "', // node icon opened\r\n");
		strTpl.append("	'icon_28' : '" + imagePath + this.branchNodeOpenedImage + "', // node icon selected opened\r\n");
		strTpl.append("\r\n");
		strTpl.append("	'icon_0'  : '" + imagePath + this.nodeImage + "', // leaf icon normal\r\n");
		strTpl.append("	'icon_4'  : '" + imagePath + this.nodeImage + "', // leaf icon selected\r\n");
		strTpl.append("	'icon_8'  : '" + imagePath + this.nodeImage + "', // leaf icon opened\r\n");
		strTpl.append("	'icon_12' : '" + imagePath + this.nodeImage + "', // leaf icon selected\r\n");
		strTpl.append("	\r\n");
		strTpl.append("	'icon_2'  : '" + imagePath + this.getJoinBottomImage() + "',// junction for leaf\r\n");
		strTpl.append("	'icon_3'  : '" + imagePath + this.getJoinImage() + "',       // junction for last leaf\r\n");
		strTpl.append("	'icon_18' : '" + imagePath + this.getPlusBottomImage() + "', // junction for closed node\r\n");
		strTpl.append("	'icon_19' : '" + imagePath + this.getPlusImage() + "',      // junctioin for last closed node\r\n");
		strTpl.append("	'icon_26' : '" + imagePath + this.getMinusBottomImage() + "',// junction for opened node\r\n");
		strTpl.append("	'icon_27' : '" + imagePath + this.getMinusImage() + "'     // junctioin for last opended node\r\n");
		strTpl.append("};");
		return strTpl.toString();
	}
	private String getIndentSpace(int count)
	{
		String str = "";
		for (int i = 0; i < count; i++)
		{
			str += " ";
		}
		return str;
	}
	private void setDataFieldCount()
	{
		//		if(this.enableUrl==true )
		//		{
		//			this.defaultFieldCount++;
		//			if(this.enableTarget==true )
		//				this.defaultFieldCount++;
		//		}
		//if(this.showCheckBox==true)
		//	this.defaultFieldCount++;
		this.setAttribute("fieldCount", String.valueOf(this.defaultFieldCount));
	}
	/**
	 * ���Tree��html����
	 */
	protected String getElementHtml()
	{
		//Debug.log("--------Tree ElementHtml Output:");
//		Map manager = new HashMap();
//		List selectedNodes = new ArrayList();
//		if (this.showCheckBox)
//		{
//			Iterator i = nodeMap.entrySet().iterator();
//			while (i.hasNext())
//			{
//				Map.Entry e = (Map.Entry) i.next();
//				TreeNode tempNode = (TreeNode) e.getValue();
//				String tempId = (String) e.getKey();
//				if (tempNode.getChecked())
//				{
//					manager.put(tempId, "check=true");
//				}
//				else
//				{
//					manager.put(tempId, "check=false");
//				}
//			}
//		}
		String hiddenValue = "";//HashMapManager.parseHash(manager);
		setDataFieldCount();
		this.setAttribute("isShowUnaidedImage", String.valueOf(this.isShowUnaidedImage));
		this.setAttribute("isSelectedParentNode", String.valueOf(this.isSelectedParentNode));
		
		String treeHtml = "";
		String treeItemsVar = "tree_items_" + this.getComponentId();
		String treeTplVar = "tree_tpl_" + this.getComponentId();
		String treeId = this.getComponentId();
		String treeSpan = this.getComponentId() + "_div";
		treeHtml += "<div " + super.getElementHtml() + ">  \r\n";
		//���treeHidden
		treeHtml += "<input type=hidden id='" + this.componentID + "_showCheckBox' name='" + this.componentID + "_showCheckBox' value='" + this.showCheckBox + "'> \r\n";
		treeHtml += "<input type=hidden id='" + this.componentID + "_hidden' name='" + this.componentID + "_hidden' value='" + hiddenValue + "'> \r\n";
		treeHtml += "<script language='JavaScript'>  \r\n";
		treeHtml += treeItemsVar + "=" + this.getClientDataSource() + ";   \r\n";
		treeHtml += treeTplVar + "=" + this.getClientTemplate() + ";   \r\n";
		treeHtml += "  var " + treeSpan + "=document.getElementById('" + treeId + "');      \r\n";
		treeHtml += "  var " + treeId + "=new tree('" + treeId + "'," + treeItemsVar + "," + treeTplVar + "," + treeSpan + ",'" + cookieName + "');    \r\n";
		treeHtml += "  " + treeId + ".show();    \r\n";
		treeHtml += "</script>  \r\n";
		treeHtml += "</div>  \r\n";
		return treeHtml;
	}
	/**
	 *
	 */
	protected String getScriptHtml()
	{
		///component/res/js/TreeJS.js
		//return "<script src='c:/myWeb/js/Tree.js' language='javascript' charset='gb2312'></script>"
		//+"<script src='c:/myWeb/js/HashMapJS.js' language='javascript' charset='gb2312'></script>";
		return super.getScriptHtml() + TreeJS.getTreeJS() + HashMapJS.getHashMapJS();
	}
	/**
	 * ������Ӹ�ڵ�.��Ҫָ���ڵ��url��target��checked���ԣ����ڷ��صĽڵ�����ϵ�����Ӧ����
	 * @param id �ڵ�id
	 * @param text	�ڵ�����
	 * @return	��ӵĽڵ����(TreeNode)
	 */
	public TreeNode addNode(String id, String text)
	{
		return this.root.addNode(id, text, this);
	}
	/**
	 * ��ȡ���ĸ�ڵ㡣һ���������ж����ڵ�
	 * @param id ��ڵ�id
	 * @return ��Ӧ�ĸ�ڵ����(TreeNode)
	 */
	public TreeNode getChild(String id)
	{
		return this.root.getChild(id);
	}
	/**
	 * ��ȡ���ĸ�ڵ㡣һ���������ж����ڵ�
	 * @param index	��ڵ�����
	 * @return	��Ӧ�ĸ�ڵ����(TreeNode)
	 */
	public TreeNode getChild(int index)
	{
		return this.root.getChild(index);
	}
	/**
	 * ��ݽڵ�idȡ�ڵ����(���ֲ��)
	 * @param id �ڵ�id
	 * @return	�ڵ����
	 */
	public TreeNode getNodeById(String id)
	{
		return (TreeNode) this.nodeMap.get(id);
	}
	/**
	 * ɾ�����ĸ�ڵ�
	 * @param id �ڵ�id
	 */
	public void removeNode(String id)
	{
		this.root.removeNode(id);
	}
	/**
	 * ɾ�����ĸ�ڵ�
	 * @param index �ڵ�����
	 */
	public void removeNode(int index)
	{
		this.root.removeNode(index);
	}
	private TreeNode getRoot(List nodeList)
	{
		for (int i = 0; i < nodeList.size(); i++)
		{
			Map node = (Map) nodeList.get(i);
			String parent = (String) node.get("parent");
			if (parent == null || ("").equals(parent)) //find root
			{
				String id = (String) node.get("id");
				String text = (String) node.get("text");
				TreeNode rootNode = new TreeNode(id, text);
				rootNode.setOwnerTree(this);
				rootNode.setParent(null);
				rootNode.setRoot(rootNode);
				return rootNode;
			}
		}
		return null;
	}
	/**
	 * �������ؼ��Ŀ��
	 */
	public void setWidth(String width)
	{
		super.setStyle("width", width);
	}
	/**
	 * �������ؼ��ĸ߶�
	 */
	public void setHeight(String height)
	{
		super.setStyle("height", height);
	}
	/**
	 * ����Tree��������еĽڵ㵥���¼����÷�������javascript��Ӧ��
	 * <br>���¼���һ�����ò��� node ,��?ǰ�����Ľڵ�
	 * @param funcName ��Ӧ���¼���javascript����
	 * @see com.bornsoft.core.gaui.component.Event
	 */
	public void setOnNodeClickEvent(String funcName)
	{
		super.addEvent(NODE_CLICK_EVNET, funcName);
	}
	/**
	 * ����Tree��������еĽڵ㵥���¼�,�÷�������bsh�������¼���Ӧ
	 * @param funcName	javascript ���׼������
	 * @param bshName ��Ӧ���¼���bsh
	 * @see com.bornsoft.core.gaui.component.ServerEvent
	 */
	public void setOnNodeClickEvent(String funcName, String bshName)
	{
		super.addEvent(NODE_CLICK_EVNET, funcName, bshName);
	}
	/**
	 * ����Tree��������еĽڵ㵥���¼�,�÷�������java Class�������¼���Ӧ
	 * @param funcName javascript ���׼������
	 * @param className ��Ӧ���¼���java��
	 * @param methodName ������Ӧ�ķ���
	 * @see com.bornsoft.core.gaui.component.ServerEvent
	 */
	public void setOnNodeClickEvent(String funcName, String className, String methodName)
	{
		super.addEvent(NODE_CLICK_EVNET, funcName, className, methodName);
	}
	/**
	 * ��ȡ���ԣ��Ƿ���ʾ������
	 * @return
	 */
	public boolean getShowScrollBar()
	{
		return showScrollBar;
	}
	/**
	 * �������ԣ��Ƿ���ʾ��������(Ĭ��ΪTrue)
	 * <br>��������Ϊfasleʱ��Tree�Ŀ�ȣ��߶�������Ч
	 * @param b
	 */
	public void setShowScrollBar(boolean b)
	{
		if (b)
			super.setStyle("overflow", "auto");
		else
			super.setStyle("overflow", "none");
		showScrollBar = b;
	}
	/**
	 * ��ȡTree�ı߿���ʽ
	 * @return	Tree�ı߿���ʽ
	 */
	public int getBorderStyle()
	{
		return borderStyle;
	}
	/**
	 * ��ȡTree�ı߿���ʽ
	 * @param i Tree�ı߿���ʽ:Tree.TREE_BORDER_3D,TREE_BORDER_NODE
	 */
	public void setBorderStyle(int style)
	{
		if (style == TREE_BORDER_3D) //3D���
		{
			this.setStyle("border-left", "solid   #999999 2px");
			this.setStyle("border-top", "solid   #999999 2px");
			this.setStyle("border-right", "solid #CCCCCC 1px");
			this.setStyle("border-bottom", "solid #CCCCCC 1px");
		}
		else //�ޱ߿���
			{
			this.setStyle("border-left", "");
			this.setStyle("border-top", "");
			this.setStyle("border-right", "");
			this.setStyle("border-bottom", "");
		}
		borderStyle = style;
	}
	/**
	 *��ȡ���Դ�󶨵Ľڵ�id�ֶ�����ֶε�ֵָ���˽ڵ��id����
	 * @return	���Դ�󶨵�id�ֶ���
	 */
	public String getIdField()
	{
		return idField;
	}
	/**
	 * ��ȡ���Դ�󶨵Ľڵ� parent�ֶ�����ֶε�ֵָ���˽ڵ�ĸ��ڵ�id
	 * @return	���Դ�󶨵�parent�ֶ���
	 */
	public String getParentField()
	{
		return parentField;
	}
	/**
	 * ��ȡ���Դ�󶨵Ľڵ� target�ֶ�����ֶε�ֵָ���˽ڵ��url�򿪵�λ��
	 * @return	���Դ�󶨵Ľڵ� target�ֶ���
	 */
	public String getTargetField()
	{
		return targetField;
	}
	/**
	 * ��ȡ���Դ�󶨵Ľڵ� text�ֶ�����ֶε�ֵָ���˽ڵ�text����
	 * @return ���Դ�󶨵Ľڵ� text�ֶ���
	 */
	public String getTextField()
	{
		return textField;
	}
	/**
	 * �������Դ�󶨵Ľڵ� url�ֶ�����ֶε�ֵָ���˽ڵ㵥��ʱҪ�򿪵�url
	 * @param string
	 */
	public void setTargetField(String string)
	{
		this.enableTarget = true;
		this.setAttribute("targetField", string);
		targetField = string;
	}
	/**
	 * ��ȡ���Դ�󶨵Ľڵ� url�ֶ�����ֶε�ֵָ���˽ڵ㵥��ʱҪ�򿪵�url
	 * @param string
	 */
	public void setUrlField(String string)
	{
		this.enableUrl = true;
		this.setAttribute("urlField", string);
		urlField = string;
	}
	/**
	 * ��ȡ���Դ�󶨵Ľڵ� url�ֶ�����ֶε�ֵָ���˽ڵ㵥��ʱҪ�򿪵�url
	 * @return ���Դ�󶨵Ľڵ� url�ֶ���
	 */
	public String getUrlField()
	{
		return urlField;
	}
	/**
	 *  ��ȡ���Դ�󶨵Ľڵ� check�ֶ�����ֶε�ֵָ���˽ڵ��ѡ��״̬	 *
	 * @return
	 */
	public String getCheckField()
	{
		return checkField;
	}
	/**
	 *  �������Դ�󶨵Ľڵ� check�ֶ�����ֶε�ֵָ���˽ڵ��ѡ��״̬
	 * ��showCheckBoxΪtrueʱyouxiao
	 * @param string
	 */
	public void setCheckField(String string)
	{
		this.setShowCheckBox(true);
		this.setAttribute("checkField", string);
		checkField = string;
	}
	/**
	 * �������ؼ��нڵ����ֵ���ʽ��Ĭ��ΪtreeText
	 */
	public void setCssClass(String cssClass)
	{
		this.setAttribute("treeCss", cssClass);
		super.setCssClass(cssClass);
	}
	/**
	 * ��ȡ�����нڵ��map����
	 * @return
	 */
	public Map getNodeMap()
	{
		return nodeMap;
	}
	/**
	 * ��ȡ����ֵ���Ƿ���ʾ�ڵ�ͼ��
	 * @return
	 */
	public boolean getShowImages()
	{
		return showImages;
	}
	/**
	 * ��ȡ����ֵ���Ƿ���ʾ����
	 * @return
	 */
	public boolean getShowLines()
	{
		return showLines;
	}
	/**
	 * ��������ֵ���Ƿ���ʾ�ڵ�ͼ��(Ĭ��Ϊtrue)
	 * @param b
	 */
	public void setShowImages(boolean b)
	{		
		showImages = b;
	}
	/**
	 * ��������ֵ���Ƿ���ʾ����(Ĭ��Ϊtrue)
	 * @param b
	 */
	public void setShowLines(boolean b)
	{
		this.setAttribute("showLines", String.valueOf(b));
		showLines = b;
	}
	/**
	 * ����Ҷ�ڵ�ͼ���ļ���(����·����)��
	 * <br> e.g. node.gif
	 * @param string Ҷ�ڵ�ͼ���ļ���
	 */
	public void setNodeImage(String string)
	{
		nodeImage = string;
	}
	/**
	 * ����֦�ڵ�չ��ʱ��ͼ���ļ���(����·����)��
	 * @param string
	 */
	public void setBranchNodeOpenedImage(String string)
	{
		branchNodeOpenedImage = string;
	}
	/**
	 * ����֦�ڵ��ͼ���ļ���(����·����)��
	 * @param string
	 */
	public void setBranchNodeImage(String string)
	{
		branchNodeImage = string;
	}
	/**
	 * ���ø�ڵ��ͼ���ļ���(����·����)��
	 * @param string
	 */
	public void setBaseImage(String string)
	{
		baseImage = string;
	}
	/**
	 * ��ȡ��ڵ��ͼ���ļ���(����·����)��
	 * @return
	 */
	public String getBaseImage()
	{
		return baseImage;
	}
	/**
	 * ��ȡ֦�ڵ��ͼ���ļ���(����·����)��
	 * @return
	 */
	public String getBranchNodeImage()
	{
		return branchNodeImage;
	}
	/**
	 * ����֦�ڵ�չ��ʱ��ͼ���ļ���(����·����)��
	 * @return
	 */
	public String getBranchNodeOpenedImage()
	{
		return branchNodeOpenedImage;
	}
	/**
	 * ����Ҷ�ڵ��ͼ���ļ���(����·����)��
	 * @return
	 */
	public String getNodeImage()
	{
		return nodeImage;
	}
	/**
	 * @return
	 */
	private String getEmptyImage()
	{
		return emptyImage;
	}
	/**
	 *
	 * @return
	 */
	private String getJoinBottomImage()
	{
		if (this.showLines == false)
			return getEmptyImage();
		else
			return joinBottomImage;
	}
	/**
	 * @return
	 */
	private String getJoinImage()
	{
		if (this.showLines == false)
			return getEmptyImage();
		else
			return joinImage;
	}
	/**
	 * @return
	 */
	private String getLineImage()
	{
		if (this.showLines == false)
			return getEmptyImage();
		else
			return lineImage;
	}
	/**
	 * @return
	 */
	private String getMinusBottomImage()
	{
		if (this.showLines == false)
			return "tree_minus2.gif";
		else
			return minusBottomImage;
	}
	/**
	 * @return
	 */
	private String getMinusImage()
	{
		if (this.showLines == false)
			return getMinusBottomImage();
		else
			return minusImage;
	}
	/**
	 * @return
	 */
	private String getPlusBottomImage()
	{
		if (this.showLines == false)
			return "tree_plus2.gif";
		else
			return plusBottomImage;
	}
	/**
	 * @return
	 */
	private String getPlusImage()
	{
		if (this.showLines == false)
			return getPlusBottomImage();
		else
			return plusImage;
	}
	/**
	 * ��ȡ����ֵ:�Ƿ��ڽڵ�ǰ��ʾcheckBox��
	 * @return
	 */
	public boolean getShowCheckBox()
	{
		return showCheckBox;
	}
	/**
	 * ��ȡ����ֵ:�Ƿ��ڽڵ�ǰ��ʾcheckBox��(Ĭ��Ϊfalse)
	 * <br> ������Ϊtrueʱ�����ڷ������˻�ȡ�ͻ���Tree��һ��ӳ����󣬸ö��󱣴��˿ͻ���Tree�Ľڵ�ѡ�����
	 * @param b
	 */
	public void setShowCheckBox(boolean b)
	{
		this.setAttribute("showCheckBox", String.valueOf(b));
		showCheckBox = b;
	}
	/**
	 * @return
	 */
	private boolean getEnableTarget()
	{
		return enableTarget;
	}
	/**
	 * @return
	 */
	private boolean getEnableUrl()
	{
		return enableUrl;
	}
	/**
	 * @param b
	 */
	void setEnableTarget(boolean b)
	{
		enableTarget = b;
	}
	/**
	 * @param b
	 */
	void setEnableUrl(boolean b)
	{
		enableUrl = b;
	}
	/**
	 * ��ȡѡ�нڵ��ϡ�
	 * <br>��showCheckBoxΪtrueʱ����ͨ��checkBox���ж�ѡ
	 * @return ѡ�нڵ���
	 */
	public List getSelectedNodes()
	{
		List selectedNodes = new ArrayList();
		Object[] keySet = nodeMap.keySet().toArray();
		int mapSize = keySet.length;
		for (int i = 0; i < mapSize; i++)
		{
			String key = (String) keySet[i];
			TreeNode node = (TreeNode) nodeMap.get(key);
			if (node.getChecked() == true)
				selectedNodes.add(node);
		}
		return selectedNodes;
	}
	/**
	 * ��ȡ�ͻ���Tree��ӳ�䣬��洢�˿ͻ���������Ҫ��Ϣ��һ����showCheckBox����Ϊtrue��������Tree��ѡʱʹ��
	 * @param request http����
	 * @param componentId	�ͻ���Tree��id
	 * @return	���ͻ���Tree��һ��Tree����
	 */
	public static Tree getClientTree(HttpServletRequest request, String componentId)
	{
		String treeData = request.getParameter(componentId + "_hidden");
		String strShowCheckBox = request.getParameter(componentId + "_showCheckbox");
		boolean blnShowCheckbox = false;
		if (strShowCheckBox != null && strShowCheckBox.equals("true"))
			blnShowCheckbox = true;
		if (treeData == null)
			return null;
		//treeData="root`id=root,text=Root�ڵ�,url=,target=,check=false,parent=~node1`id=node1,text=�ڵ�1,url=,target=,check=true,parent=root~node2`id=node2,text=�ڵ�2,url=,target=,check=true,parent=root~node3`id=node3,text=�ڵ�3,url=,target=,check=true,parent=root~node3_1`id=node3_1,text=�ڵ�3_1,url=,target=,check=true,parent=node3~node3_2`id=node3_2,text=�ڵ�3_2,url=,target=,check=true,parent=node3~node4`id=node4,text=�ڵ�4,url=,target=,check=true,parent=root~node4_1`id=node4_1,text=�ڵ�4_1,url=,target=,check=true,parent=node4~node4_2`id=node4_2,text=�ڵ�4_2,url=,target=,check=true,parent=node4~node4_2-1`id=node4_2-1,text=�ڵ�4_2-1,url=,target=,check=true,parent=node4_2~node4_2-2`id=node4_2-2,text=�ڵ�4_2-2,url=,target=,check=true,parent=node4_2~node4_3`id=node4_3,text=�ڵ�4_3,url=,target=,check=true,parent=node4~node4_4`id=node4_4,text=�ڵ�4_4,url=,target=,check=true,parent=node4~root2`id=root2,text=Root2�ڵ�,url=,target=,check=false,parent=~node21`id=node21,text=�ڵ�21,url=,target=,check=false,parent=root2~node22`id=node22,text=�ڵ�22,url=,target=,check=false,parent=root2~node23`id=node23,text=�ڵ�23,url=,target=,check=false,parent=root2~node24`id=node24,text=�ڵ�24,url=,target=,check=true,parent=root2";
		//remove first ~ flag
		if (treeData.startsWith("~"))
			treeData = treeData.substring(treeData.indexOf("~") + 1);
		String[] nodesData = StringUtils.split(treeData, "~"); //treeData.split("~");
		String[] oneNodeData;
		String[] oneNodeData2;
		String idField = "";
		String textField = "";
		String parentField = "";
		String urlField = "";
		String targetField = "";
		String checkField = "";
		List treeNodeList = new ArrayList();
		//		System.out.println("node count: "+nodesData.length);
		for (int i = 0; i < nodesData.length; i++)
		{
			oneNodeData = StringUtils.split(nodesData[i], "="); //nodesData[i].split("`");
			//			System.out.println("oneNodeData size: "+oneNodeData.length);
			//			System.out.println("id: "+oneNodeData[0]+"   data: "+oneNodeData[1]);
			oneNodeData2 = StringUtils.split(oneNodeData[1], ","); //oneNodeData[1].split(",");
			Map treeNodeMap = new HashMap();
			for (int j = 0; j < oneNodeData2.length; j++)
			{
				String[] oneNodeData3 = StringUtils.split(oneNodeData2[j], "="); //. oneNodeData2[j].split("=");
				String key = oneNodeData3[0];
				String value = "";
				if (oneNodeData3.length == 2)
					value = oneNodeData3[1];
				switch (j)
				{
					case 0 :
						idField = oneNodeData3[0];
						break;
					case 1 :
						textField = oneNodeData3[0];
						break;
					case 2 :
						urlField = oneNodeData3[0];
						break;
					case 3 :
						targetField = oneNodeData3[0];
						break;
					case 4 :
						checkField = oneNodeData3[0];
						break;
					case 5 :
						parentField = oneNodeData3[0];
						break;
				}
				treeNodeMap.put(key, value);
			}
			treeNodeList.add(treeNodeMap);
		}
		//		System.out.println("client tree:  "+treeNodeList);
		//		System.out.println("fields: ");
		System.out.println(idField);
		System.out.println(textField);
		System.out.println(parentField);
		System.out.println(urlField);
		System.out.println(targetField);
		System.out.println(checkField);
		Tree tree = new Tree(componentId);
		tree.setDataSource(treeNodeList);
		tree.setDataField(idField, textField, parentField);
		tree.setUrlField(urlField);
		tree.setTargetField(targetField);
		tree.setCheckField(checkField);
		tree.setShowCheckBox(blnShowCheckbox);
		tree.dataBind();
		//		System.out.println(tree.getInnerHtml());
		return tree;
	}
	public static Map getClientCheckedIdMap(HttpServletRequest request, String componentId)
	{
		String treeData = request.getParameter(componentId + "_hidden");
		System.out.println(treeData);
		System.out.println(request.getParameter(componentId + "_showCheckbox")+"----------");
		return HashMapManager.parseHidden(treeData);
	}
	/**
	 * @return
	 */
	public String getCookieName()
	{
		return cookieName;
	}
	/**
	 * @param string
	 */
	public void setCookieName(String string)
	{
		cookieName = string;
	}
	/**
	 * @return
	 */
	public boolean isShowUnaidedImage()
	{
		return isShowUnaidedImage;
	}

	/**
	 * @param b
	 */
	public void setShowUnaidedImage(boolean b)
	{
		isShowUnaidedImage = b;
	}

	/**
	 * ѡ���¼�ʱ��ͬʱѡ���ϼ�
	 * @return
	 */
	public boolean isSelectedParentNode()
	{
		return isSelectedParentNode;
	}

	/**
	 * @param b
	 */
	public void setSelectedParentNode(boolean b)
	{
		isSelectedParentNode = b;
	}

}
