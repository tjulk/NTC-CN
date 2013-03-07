package com.nike.ntc_cn.db;

public final class T_DlcAvailableControl {

	enum DlcAvailable {
		_id, name, main_version, main_size, main_checksum, main_url, patch_version, patch_size, patch_checksum, image, patch_url,;
		
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "dlc_available";

		private static String[] initColumns() {
			DlcAvailable[] vals = DlcAvailable.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		DlcAvailable() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE dlc_available (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"name TEXT ,main_version INTEGER ,main_size INTEGER ," +
			"main_checksum INTEGER ,main_url TEXT ,patch_version INTEGER ," +
			"patch_size INTEGER ,patch_checksum INTEGER ,patch_url TEXT )";

}
