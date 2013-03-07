package com.nike.ntc_cn.db;

public final class T_ExercisePagesControl {

	enum ExercisePages {
		_id, exercise_name, title, instructions, sort_order, archive, background_image, ;
		
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "exercise_pages";

		private static String[] initColumns() {
			ExercisePages[] vals = ExercisePages.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		ExercisePages() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE exercise_pages (_id INTEGER PRIMARY KEY AUTOINCREMENT, exercise_name TEXT ," +
			"title TEXT ,instructions TEXT ,sort_order INTEGER ,archive TEXT ,background_image TEXT )";

}
