package com.nike.ntc_cn.db;

public final class T_ExerciseAudioClipsControl {

	enum ExerciseAudioClips {
		_id, exercise_name, audio_clip_name, start_time, archive, ;
		
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "exercise_audio_clips";

		private static String[] initColumns() {
			ExerciseAudioClips[] vals = ExerciseAudioClips.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		ExerciseAudioClips() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE exercise_audio_clips (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
			" exercise_name TEXT ,audio_clip_name TEXT ,start_time REAL ,archive TEXT )";

}
