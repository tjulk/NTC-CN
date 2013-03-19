package com.nike.ntc_cn.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class T_WorkoutAudioClipsControl extends DBControl{

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
	
	private static T_WorkoutAudioClipsControl instance;
	
	public T_WorkoutAudioClipsControl(Context context, Executor logExecutor,
			SQLiteOpenHelper openHelper) {
		super(context, logExecutor, openHelper);
	}
	
	public static T_WorkoutAudioClipsControl getInstance(Context context) {
        if (instance == null) {
            context = context.getApplicationContext();
            ThreadFactory logThreadFactory = Executors.defaultThreadFactory();
            Executor logExecutor = Executors.newSingleThreadExecutor(logThreadFactory);
            SQLiteOpenHelper openHelper = DbOpenHelper.getInstance(context, DBControl.DB_NAME, DBControl.DB_VERSION,
                    logExecutor);
            instance = new T_WorkoutAudioClipsControl(context, logExecutor, openHelper);
        }
        return instance;
    }

	public List<M_WorkoutAudioClips> getWorkoutAudioClipsListByWorkoutname(
			String workoutName) {
		List<M_WorkoutAudioClips> list = new ArrayList<M_WorkoutAudioClips>();
		
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
		
		Cursor  cursor = db.query(WorkoutAudioClips.TABLE_NAME,WorkoutAudioClips.COLUMNS,
				WorkoutAudioClips.workout_name + " = '" + workoutName + "'" , null, null, null, " _id ");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(WorkoutAudioClips._id.name());
                int workout_nameIndex = cursor.getColumnIndex(WorkoutAudioClips.workout_name.name());
                int audio_clip_nameIndex = cursor.getColumnIndex(WorkoutAudioClips.audio_clip_name.name());
                int is_introIndex = cursor.getColumnIndex(WorkoutAudioClips.is_intro.name());
                int start_timeIndex = cursor.getColumnIndex(WorkoutAudioClips.start_time.name());
                int archiveIndex = cursor.getColumnIndex(WorkoutAudioClips.archive.name());
                do {
                	M_WorkoutAudioClips workoutAudioClips = new M_WorkoutAudioClips();
                	workoutAudioClips._id = cursor.getInt(idIndex);
                	workoutAudioClips.workout_name = cursor.getString(workout_nameIndex);
                	workoutAudioClips.audio_clip_name = cursor.getString(audio_clip_nameIndex);
                	workoutAudioClips.is_intro = cursor.getInt(is_introIndex);
                	workoutAudioClips.start_time = cursor.getInt(start_timeIndex);
                	workoutAudioClips.archive = cursor.getString(archiveIndex);
                	list.add(workoutAudioClips);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
		
		return list;
	}

}
