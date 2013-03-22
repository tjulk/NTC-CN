package com.nike.ntc_cn.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nike.ntc_cn.TutorialLevelActivity;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;

public final class T_WorkoutControl extends DBControl{
	
	private Context mContext;
	
	protected T_WorkoutControl(Context context, Executor executor,
			SQLiteOpenHelper openHelper) {
		super(context, executor, openHelper);
		
		mContext = context;
	}
	
	public class M_Workouts {
		
		public static final String ARCHIVE_STANDARD = "standard";
		public static final String ARCHIVE_DOWNLOADED = "downloaded";
		public static final String ARCHIVE_DOWNLOADING = "downloading";
		
		
		public int _id;
		public String name;
		public String title;
		public String goal;
		public String level;
		public int duration;
		public String equipment;
		public String completed_audio_file;
		public String archive;
		
		public List<M_Exercises> exercisesList;
		
		@Override
		public String toString() {
			return "M_Workouts [_id=" + _id + ", name=" + name + ", title="
					+ title + ", goal=" + goal + ", level=" + level
					+ ", duration=" + duration + ", equipment=" + equipment
					+ ", completed_audio_file=" + completed_audio_file
					+ ", archive=" + archive + ", exercisesList="
					+ exercisesList + "]";
		}
	}

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
	
	
	private static T_WorkoutControl instance;
	
	public static T_WorkoutControl getInstance(Context context) {
        if (instance == null) {
            context = context.getApplicationContext();
            ThreadFactory logThreadFactory = Executors.defaultThreadFactory();
            Executor logExecutor = Executors.newSingleThreadExecutor(logThreadFactory);
            SQLiteOpenHelper openHelper = DbOpenHelper.getInstance(context, DBControl.DB_NAME, DBControl.DB_VERSION,
                    logExecutor);
            instance = new T_WorkoutControl(context, logExecutor, openHelper);
        }
        return instance;
    }
	
	public int changeWorkoutStatus(String archive, String workoutName) {
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(Workouts.archive.name(), archive);
		return db.update(Workouts.TABLE_NAME, values, " name = '" + workoutName + "' " , null);
	}
	
	public M_Workouts getWorkoutByName(String name) {
		M_Workouts workoutsFreq = new M_Workouts();
		String selection = ("name = '" + name + "'");
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
		Cursor  cursor = db.query(Workouts.TABLE_NAME,Workouts.COLUMNS, selection , null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(Workouts._id.name());
                int nameIndex = cursor.getColumnIndex(Workouts.name.name());
                int titleIndex = cursor.getColumnIndex(Workouts.title.name());
                int goalIndex = cursor.getColumnIndex(Workouts.goal.name());
                int levelIndex = cursor.getColumnIndex(Workouts.level.name());
                int durationIndex = cursor.getColumnIndex(Workouts.duration.name());
                int equipmentIndex = cursor.getColumnIndex(Workouts.equipment.name());
                int completed_audio_fileIndex = cursor.getColumnIndex(Workouts.completed_audio_file.name());
                int archiveIndex = cursor.getColumnIndex(Workouts.archive.name());
            	workoutsFreq._id = cursor.getInt(idIndex);
            	workoutsFreq.name = cursor.getString(nameIndex);
            	workoutsFreq.title = cursor.getString(titleIndex);
            	workoutsFreq.goal = cursor.getString(goalIndex);
            	workoutsFreq.level = cursor.getString(levelIndex);
            	workoutsFreq.duration = cursor.getInt(durationIndex);
            	workoutsFreq.equipment = cursor.getString(equipmentIndex);
            	workoutsFreq.completed_audio_file = cursor.getString(completed_audio_fileIndex);
            	workoutsFreq.archive = cursor.getString(archiveIndex);
            }
        }
		return workoutsFreq;
	}
	
	
	//获取workout列表
	public List<M_Workouts> getWorkoutsList(String goal, String level) {
		List<M_Workouts> lastFreqs = new ArrayList<M_Workouts>();
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
		
		db.beginTransaction();
		
		
		String selection = ("goal = '" + goal + "' and level = '" + level + "'");
		if (goal.equals(TutorialLevelActivity.GOAL_FOCOUS))
			selection = ("goal = '" + goal + "' and level = ''");
		
		Cursor  cursor = db.query(Workouts.TABLE_NAME,Workouts.COLUMNS, selection , null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(Workouts._id.name());
                int nameIndex = cursor.getColumnIndex(Workouts.name.name());
                int titleIndex = cursor.getColumnIndex(Workouts.title.name());
                int goalIndex = cursor.getColumnIndex(Workouts.goal.name());
                int levelIndex = cursor.getColumnIndex(Workouts.level.name());
                int durationIndex = cursor.getColumnIndex(Workouts.duration.name());
                int equipmentIndex = cursor.getColumnIndex(Workouts.equipment.name());
                int completed_audio_fileIndex = cursor.getColumnIndex(Workouts.completed_audio_file.name());
                int archiveIndex = cursor.getColumnIndex(Workouts.archive.name());
                
                do {
                	M_Workouts workoutsFreq = new M_Workouts();
                	workoutsFreq._id = cursor.getInt(idIndex);
                	workoutsFreq.name = cursor.getString(nameIndex);
                	workoutsFreq.title = cursor.getString(titleIndex);
                	workoutsFreq.goal = cursor.getString(goalIndex);
                	workoutsFreq.level = cursor.getString(levelIndex);
                	workoutsFreq.duration = cursor.getInt(durationIndex);
                	workoutsFreq.equipment = cursor.getString(equipmentIndex);
                	workoutsFreq.completed_audio_file = cursor.getString(completed_audio_fileIndex);
                	workoutsFreq.archive = cursor.getString(archiveIndex);
                	
                	//workoutsFreq.exercisesList = T_WorkoutExercisesControl.getInstance(mContext).getExercisesListByWorkoutname(workoutsFreq.name);
                    
                    lastFreqs.add(workoutsFreq);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        
        
        db.endTransaction();
		
		return lastFreqs;
	}

	public List<M_Workouts> getAllUnDownloadWorkouts() {
		List<M_Workouts> lastFreqs = new ArrayList<M_Workouts>();
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
		String selection = ("archive = '" + M_Workouts.ARCHIVE_STANDARD + "' or  archive = '" + M_Workouts.ARCHIVE_DOWNLOADING + "'");
		
		db.beginTransaction();
		Cursor  cursor = db.query(Workouts.TABLE_NAME,Workouts.COLUMNS, selection , null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(Workouts._id.name());
                int nameIndex = cursor.getColumnIndex(Workouts.name.name());
                int titleIndex = cursor.getColumnIndex(Workouts.title.name());
                int goalIndex = cursor.getColumnIndex(Workouts.goal.name());
                int levelIndex = cursor.getColumnIndex(Workouts.level.name());
                int durationIndex = cursor.getColumnIndex(Workouts.duration.name());
                int equipmentIndex = cursor.getColumnIndex(Workouts.equipment.name());
                int completed_audio_fileIndex = cursor.getColumnIndex(Workouts.completed_audio_file.name());
                int archiveIndex = cursor.getColumnIndex(Workouts.archive.name());
                
                do {
                	M_Workouts workoutsFreq = new M_Workouts();
                	workoutsFreq._id = cursor.getInt(idIndex);
                	workoutsFreq.name = cursor.getString(nameIndex);
                	workoutsFreq.title = cursor.getString(titleIndex);
                	workoutsFreq.goal = cursor.getString(goalIndex);
                	workoutsFreq.level = cursor.getString(levelIndex);
                	workoutsFreq.duration = cursor.getInt(durationIndex);
                	workoutsFreq.equipment = cursor.getString(equipmentIndex);
                	workoutsFreq.completed_audio_file = cursor.getString(completed_audio_fileIndex);
                	workoutsFreq.archive = cursor.getString(archiveIndex);
                    lastFreqs.add(workoutsFreq);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
		
		return lastFreqs;
	}
	
	

}
