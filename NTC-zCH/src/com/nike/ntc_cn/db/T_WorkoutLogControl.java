package com.nike.ntc_cn.db;

public final class T_WorkoutLogControl {

	enum WorkoutLog {
		_id, workout_name, workout_title, workout_duration, workout_level, workout_goal, completed_duration, is_complete
		, reward_name, qualified, start_time, end_time, music_track_ids;
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "workout_log";

		private static String[] initColumns() {
			WorkoutLog[] vals = WorkoutLog.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		WorkoutLog() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE workout_log (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"workout_name TEXT ,workout_title TEXT ," +
			"workout_duration INTEGER ,workout_level TEXT ,workout_goal TEXT ,completed_duration INTEGER ," +
			"is_complete INTEGER ,reward_name TEXT ,qualified INTEGER ,start_time INTEGER ,end_time INTEGER ,music_track_ids TEXT )";

}
