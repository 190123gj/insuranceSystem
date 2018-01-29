package com.born.insurance.ws.info.projectSetup;

import com.born.insurance.ws.info.common.BaseToStringInfo;

public class ProjectSetupCatalogInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6882105217723432351L;
	
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
