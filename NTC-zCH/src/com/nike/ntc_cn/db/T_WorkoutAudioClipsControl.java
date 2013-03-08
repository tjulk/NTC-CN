package com.nike.ntc_cn.db;

public final class T_WorkoutAudioClipsControl {

	enum WorkoutAudioClips {
		_id, workout_name, audio_clip_name, is_intro, start_time, archive, ;
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "workout_audio_clips";

		private static String[] initColumns() {
			WorkoutAudioClips[] vals = WorkoutAudioClips.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		WorkoutAudioClips() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = "CREATE TABLE workout_audio_clips (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
			" workout_name TEXT ,audio_clip_name TEXT ,is_intro INTEGER ,start_time REAL ,archive TEXT )";
	
	public class M_WorkoutAudioClips {
		public int _id;
		public String workout_name;
		public String audio_clip_name;
		public int is_intro;
		public float start_time;
		public String archive;
	}

}
