package com.nike.ntc_cn.db;

public final class T_SavedWorkoutControl {

	enum SavedWorkout {
		_id, workout_name, workout_title, workout_level, workout_goal, workout_duration, timestamp, ;
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "saved_workouts";

		private static String[] initColumns() {
			SavedWorkout[] vals = SavedWorkout.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		SavedWorkout() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE saved_workouts (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"workout_name TEXT ,workout_title TEXT ,workout_level TEXT ," +
			"workout_goal TEXT ,workout_duration INTEGER ,timestamp INTEGER )";

}
