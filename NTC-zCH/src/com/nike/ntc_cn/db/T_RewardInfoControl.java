package com.nike.ntc_cn.db;

public final class T_RewardInfoControl {

	enum RewardInfo {
		_id, name, content_name, title, threshold, uri, is_downloadable ,audio_file,image,facebook_title,facebook_text,
		twitter_status,unlock_notification_message,reward_notification_message,product_finder_message,product_finder_url;
		
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "reward_info";

		private static String[] initColumns() {
			RewardInfo[] vals = RewardInfo.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		RewardInfo() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE reward_info (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT ,content_name TEXT ," +
			"title TEXT ,threshold INTEGER ,uri TEXT ,is_downloadable INTEGER ,audio_file TEXT ,image TEXT ,facebook_title TEXT ," +
			"facebook_text TEXT ,twitter_status TEXT ,unlock_notification_message TEXT ," +
			"reward_notification_message TEXT ,product_finder_message TEXT ,product_finder_url TEXT )";

}
