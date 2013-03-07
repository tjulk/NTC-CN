package com.nike.ntc_cn.db;

public final class T_WorkoutControl {

	enum Workouts {
		_id, name, title, goal, level, duration, equipment, completed_audio_file
		, archive;
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "workouts";

		private static String[] initColumns() {
			Workouts[] vals = Workouts.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		Workouts() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE workouts (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"name TEXT ,title TEXT ,goal TEXT ,level TEXT ,duration INTEGER ," +
			"equipment TEXT ,completed_audio_file TEXT ,archive TEXT )";

}
