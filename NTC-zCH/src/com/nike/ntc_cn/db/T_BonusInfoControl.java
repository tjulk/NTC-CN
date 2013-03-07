package com.nike.ntc_cn.db;

public final class T_BonusInfoControl {

	enum BonusInfo {
		_id, name, content_name, title, subtitle, unlock_time, uri, is_downloadable, audio_file, image, facebook_title, facebook_text,
		twitter_status, workout_title, workout_duration;
		
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "bonus_info";

		private static String[] initColumns() {
			BonusInfo[] vals = BonusInfo.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		BonusInfo() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE bonus_info (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"name TEXT ,content_name TEXT ,title TEXT ,subtitle TEXT ,unlock_time INTEGER ," +
			"uri TEXT ,is_downloadable INTEGER ,audio_file TEXT ,image TEXT ,facebook_title TEXT ," +
			"facebook_text TEXT ,twitter_status TEXT ,workout_title TEXT ,workout_duration INTEGER )";

}
