package com.nike.ntc_cn.db;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nike.ntc_cn.db.T_ExercisePagesControl.M_ExercisePages;

public final class T_ExerciseControl extends DBControl{
	/****************************************************************************************************************************/
	enum Exercises {_id, name, title, duration, equipment, footnote, thumbnail_medium, thumbnail_small, video_extension, video_name, archive, ;
		/** All columns in Shortcuts table. */
		static final String[] COLUMNS = initColumns();

		/** table name. */
		static final String TABLE_NAME = "exercises";

		private static String[] initColumns() {
			Exercises[] vals = Exercises.values();
			String[] columns = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				columns[i] = vals[i].fullName;
			}
			return columns;
		}

		/** table name + column name. */
		public final String fullName;

		/** Initialize full name. */
		Exercises() {
			fullName = TABLE_NAME + "." + name();
		}
	}

	public static final String createTableSql = " CREATE TABLE exercises (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT ,title TEXT ," +
			"duration INTEGER ,equipment TEXT ,footnote TEXT ,thumbnail_medium TEXT ,thumbnail_small TEXT ,video_extension TEXT ," +
			"video_name TEXT ,archive TEXT , UNIQUE (name) ON CONFLICT REPLACE)";
	
	/********************************************************* 数据库相关 结束*******************************************************************/
	
	
	public class M_Exercises {
		public int _id;
		public String name;
		public String title;
		public int duration;
		public String equipment;
		public String footnote;
		public String thumbnail_medium;
		public String thumbnail_small;
		public String video_extension;
		public String video_name;
		public String archive;
		
		public List<M_ExercisePages> exercisePages;

		@Override
		public String toString() {
			return "M_Exercises [_id=" + _id + ", name=" + name + ", title="
					+ title + ", duration=" + duration + ", equipment="
					+ equipment + ", footnote=" + footnote
					+ ", thumbnail_medium=" + thumbnail_medium
					+ ", thumbnail_small=" + thumbnail_small
					+ ", video_extension=" + video_extension + ", video_name="
					+ video_name + ", archive=" + archive + ", exercisePages="
					+ exercisePages + "]";
		}
		
	}
	
	
    /** 单例 */
    private static volatile T_ExerciseControl instance = null;
	
    public T_ExerciseControl(Context context, Executor logExecutor,
			SQLiteOpenHelper openHelper) {
    	super(context, logExecutor, openHelper);
	}

	public static T_ExerciseControl getInstance(Context context) {
        if (instance == null) {
            context = context.getApplicationContext();
            ThreadFactory logThreadFactory = Executors.defaultThreadFactory();
            Executor logExecutor = Executors.newSingleThreadExecutor(logThreadFactory);
            SQLiteOpenHelper openHelper = DbOpenHelper.getInstance(context, DBControl.DB_NAME, DBControl.DB_VERSION,
                    logExecutor);
            instance = new T_ExerciseControl(context, logExecutor, openHelper);
        }
        return instance;
    }
	
	public M_Exercises getExercisesByName(String name) {
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
        final Cursor cursor = db.query(Exercises.TABLE_NAME, Exercises.COLUMNS, " name = '" + name + "'", null, null, null, null);
        M_Exercises exercises = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
            	exercises = new M_Exercises();
                int idIndex = cursor.getColumnIndex(Exercises._id.name());
                int nameIndex = cursor.getColumnIndex(Exercises.name.name());
                int titleIndex = cursor.getColumnIndex(Exercises.title.name());
                int durationIndex = cursor.getColumnIndex(Exercises.duration.name());
                int equipmentIndex = cursor.getColumnIndex(Exercises.equipment.name());
                int footnoteIndex = cursor.getColumnIndex(Exercises.footnote.name());
                int thumbnail_mediumIndex = cursor.getColumnIndex(Exercises.thumbnail_medium.name());
                int thumbnail_smallIndex = cursor.getColumnIndex(Exercises.thumbnail_small.name());
                int video_extensionIndex = cursor.getColumnIndex(Exercises.video_extension.name());
                int video_nameIndex = cursor.getColumnIndex(Exercises.video_name.name());
                int archiveIndex = cursor.getColumnIndex(Exercises.archive.name());
  
                exercises._id = cursor.getInt(idIndex);
                exercises.name = cursor.getString(nameIndex);
                exercises.title = cursor.getString(titleIndex);
                exercises.duration = cursor.getInt(durationIndex);
                exercises.equipment = cursor.getString(equipmentIndex);
                exercises.footnote = cursor.getString(footnoteIndex);
                exercises.thumbnail_medium = cursor.getString(thumbnail_mediumIndex);
                exercises.thumbnail_small = cursor.getString(thumbnail_smallIndex);
                exercises.video_extension = cursor.getString(video_extensionIndex);
                exercises.video_name = cursor.getString(video_nameIndex);
                exercises.archive = cursor.getString(archiveIndex);
                
                exercises.exercisePages = T_ExercisePagesControl.getInstance(mContext).getExercisePagesByExerciseName(name);
            }
            cursor.close();
        }
		
		return exercises;
	}
	
	
}
