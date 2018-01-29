package com.born.insurance.ws.order.customer;

import com.born.insurance.ws.base.QueryPermissionPageBase;

public class CustomerProtocolQueryOrder extends QueryPermissionPageBase {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8336903962520612886L;

		//客户的类别
		private String customerType;
		
		//客户证件类型
		private String certType;

		//客户证件号码
		private String certNo;
		
		//协议状态
		private String status;
		
		//客户名称
		private String customerUserName;
		
		/**
		 * 协议的id
		 */
		private String id;
		
		//协议名称
		private String name ;
		
		public String getCustomerType() {
			return customerType;
		}

		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}

		public String getCertType() {
			return certType;
		}

		public void setCertType(String certType) {
			this.certType = certType;
		}

		public String getCertNo() {
			return certNo;
		}

		public void setCertNo(String certNo) {
			this.certNo = certNo;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCustomerUserName() {
			return customerUserName;
		}

		public void setCustomerUserName(String customerUserName) {
			this.customerUserName = customerUserName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		
}
