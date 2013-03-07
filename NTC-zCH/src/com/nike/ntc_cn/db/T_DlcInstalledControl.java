package com.nike.ntc_cn.db;

public final class T_DlcInstalledControl {

	enum DlcInstalled {
		_id, name, main_version, main_size, main_checksum, main_checked, 
		patch_version, patch_size, patch_checksum, patch_checked, content_installed,;
		
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "dlc_installed";

		private static String[] initColumns() {
			DlcInstalled[] vals = DlcInstalled.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		DlcInstalled() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE dlc_installed (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT ," +
			"main_version INTEGER ,main_size INTEGER ,main_checksum INTEGER ,main_checked INTEGER ," +
			"patch_version INTEGER ,patch_size INTEGER ,patch_checksum INTEGER ,patch_checked INTEGER ,content_installed INTEGER )";

}
