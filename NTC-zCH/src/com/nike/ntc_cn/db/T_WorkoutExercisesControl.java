package com.nike.ntc_cn.db;

public final class T_WorkoutExercisesControl {

	enum WorkoutExercises {
		_id, workout_name, exercise_name, sort_order, archive, ;
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "workout_exercises";

		private static String[] initColumns() {
			WorkoutExercises[] vals = WorkoutExercises.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		WorkoutExercises() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE workout_exercises (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"workout_name TEXT ,exercise_name TEXT ,sort_order INTEGER ,archive TEXT )";

}
