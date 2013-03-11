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

import com.nike.ntc_cn.db.T_ExerciseControl.Exercises;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;


public final class T_WorkoutExercisesControl extends DBControl{

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

	public class M_WorkoutExercises {
		public int _id;
		public String workout_name;
		public String exercise_name;
		public int sort_order;
		public String archive;
		@Override
		public String toString() {
			return "M_WorkoutExercises [_id=" + _id + ", workout_name="
					+ workout_name + ", exercise_name=" + exercise_name
					+ ", sort_order=" + sort_order + ", archive=" + archive
					+ "]";
		}
	}
	
	
	private static T_WorkoutExercisesControl instance;
	
	public T_WorkoutExercisesControl(Context context, Executor logExecutor,
			SQLiteOpenHelper openHelper) {
		super(context, logExecutor, openHelper);
	}

	public static T_WorkoutExercisesControl getInstance(Context context) {
        if (instance == null) {
            context = context.getApplicationContext();
            ThreadFactory logThreadFactory = Executors.defaultThreadFactory();
            Executor logExecutor = Executors.newSingleThreadExecutor(logThreadFactory);
            SQLiteOpenHelper openHelper = DbOpenHelper.getInstance(context, DBControl.DB_NAME, DBControl.DB_VERSION,
                    logExecutor);
            instance = new T_WorkoutExercisesControl(context, logExecutor, openHelper);
        }
        return instance;
    }
	
	//获取workout 与 每隔 exercise之间的关联 
	public List<M_Exercises> getExercisesListByWorkoutname(String workoutName) {
		
		List<M_Exercises> list = new ArrayList<M_Exercises>();
		
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
		
		Cursor  cursor = db.query(WorkoutExercises.TABLE_NAME,WorkoutExercises.COLUMNS,
				WorkoutExercises.workout_name + " = '" + workoutName + "'" , null, null, null, " sort_order ");
		
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                final int exercisenameIndex = cursor.getColumnIndex(WorkoutExercises.exercise_name.name());
                do {
                	//根据每个workout-exercise获取exercise的name，然后获取具体exercises
                	final String exerciseName =  cursor.getString(exercisenameIndex);
                	final M_Exercises exercise = T_ExerciseControl.getInstance(mContext).getExercisesByName(exerciseName);
                	if (exercise != null)
                		list.add(exercise);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
		
		return list;
	}
	
	
}
