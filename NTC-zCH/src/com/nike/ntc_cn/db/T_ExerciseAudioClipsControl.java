package com.nike.ntc_cn.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl.M_WorkoutAudioClips;
import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl.WorkoutAudioClips;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class T_ExerciseAudioClipsControl extends DBControl{

	protected T_ExerciseAudioClipsControl(Context context, Executor executor,
			SQLiteOpenHelper openHelper) {
		super(context, executor, openHelper);
	}

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
	
	public class M_ExerciseAudioClips {
		public int _id;
		public String exercise_name;
		public String audio_clip_name;
		public float start_time;
		public String archive;
	}
	
	
	private static T_ExerciseAudioClipsControl instance;
	
	public static T_ExerciseAudioClipsControl getInstance(Context context) {
        if (instance == null) {
            context = context.getApplicationContext();
            ThreadFactory logThreadFactory = Executors.defaultThreadFactory();
            Executor logExecutor = Executors.newSingleThreadExecutor(logThreadFactory);
            SQLiteOpenHelper openHelper = DbOpenHelper.getInstance(context, DBControl.DB_NAME, DBControl.DB_VERSION,
                    logExecutor);
            instance = new T_ExerciseAudioClipsControl(context, logExecutor, openHelper);
        }
        return instance;
    }
	
	public List<M_ExerciseAudioClips> getM_ExerciseAudioClipsListByExercisename(
			String exercisename) {
		List<M_ExerciseAudioClips> list = new ArrayList<M_ExerciseAudioClips>();
		
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
		
		Cursor  cursor = db.query(ExerciseAudioClips.TABLE_NAME,ExerciseAudioClips.COLUMNS,
				ExerciseAudioClips.exercise_name + " = '" + exercisename + "'" , null, null, null, " _id ");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(ExerciseAudioClips._id.name());
                int exercise_nameIndex = cursor.getColumnIndex(ExerciseAudioClips.exercise_name.name());
                int audio_clip_nameIndex = cursor.getColumnIndex(ExerciseAudioClips.audio_clip_name.name());
                int start_timeIndex = cursor.getColumnIndex(ExerciseAudioClips.start_time.name());
                int archiveIndex = cursor.getColumnIndex(ExerciseAudioClips.archive.name());
                do {
                	M_ExerciseAudioClips exerciseAudioClips = new M_ExerciseAudioClips();
                	exerciseAudioClips._id = cursor.getInt(idIndex);
                	exerciseAudioClips.exercise_name = cursor.getString(exercise_nameIndex);
                	exerciseAudioClips.audio_clip_name = cursor.getString(audio_clip_nameIndex);
                	exerciseAudioClips.start_time = cursor.getInt(start_timeIndex);
                	exerciseAudioClips.archive = cursor.getString(archiveIndex);
                	
                	list.add(exerciseAudioClips);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
		
		return list;
	}

}
