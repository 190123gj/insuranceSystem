package com.born.insurance.ws.order.projectSetup;

import com.born.insurance.ws.base.QueryPermissionPageBase;

public class ProjectSetupCataLogQueryOrder extends QueryPermissionPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8851508202284794902L;
	
	  //========== properties ==========

		private long id;

		private long projectSetupId;

		private long catalogId;

		private String catalogName;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getProjectSetupId() {
			return projectSetupId;
		}

		public void setProjectSetupId(long projectSetupId) {
			this.projectSetupId = projectSetupId;
		}

		public long getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(long catalogId) {
			this.catalogId = catalogId;
		}

		public String getCatalogName() {
			return catalogName;
		}

		public void setCatalogName(String catalogName) {
			this.catalogName = catalogName;
		}
		
		

}
