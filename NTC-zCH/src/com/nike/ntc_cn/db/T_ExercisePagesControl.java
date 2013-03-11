package com.nike.ntc_cn.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.nike.ntc_cn.db.T_ExerciseControl.Exercises;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class T_ExercisePagesControl extends DBControl{

	protected T_ExercisePagesControl(Context context, Executor executor,
			SQLiteOpenHelper openHelper) {
		super(context, executor, openHelper);
	}


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
	
	
	public class M_ExercisePages {
		public int _id;
		public String exercise_name;
		public String title;
		public String instructions;
		public String sort_order;
		public String archive;
		public String background_image;
		@Override
		public String toString() {
			return "M_ExercisePages [_id=" + _id + ", exercise_name="
					+ exercise_name + ", title=" + title + ", instructions="
					+ instructions + ", sort_order=" + sort_order
					+ ", archive=" + archive + ", background_image="
					+ background_image + "]";
		}
	}
	
    /** 单例 */
    private static volatile T_ExercisePagesControl instance = null;
	
	public static T_ExercisePagesControl getInstance(Context context) {
        if (instance == null) {
            context = context.getApplicationContext();
            ThreadFactory logThreadFactory = Executors.defaultThreadFactory();
            Executor logExecutor = Executors.newSingleThreadExecutor(logThreadFactory);
            SQLiteOpenHelper openHelper = DbOpenHelper.getInstance(context, DBControl.DB_NAME, DBControl.DB_VERSION,
                    logExecutor);
            instance = new T_ExercisePagesControl(context, logExecutor, openHelper);
        }
        return instance;
    }
	
	//通过exercise名称获取 对应图片页列表
	public List<M_ExercisePages> getExercisePagesByExerciseName(String name) {
		
		List<M_ExercisePages> list = new ArrayList<M_ExercisePages>();
		
		SQLiteDatabase db =  mOpenHelper.getReadableDatabase();
        final Cursor cursor = db.query(ExercisePages.TABLE_NAME, ExercisePages.COLUMNS, " exercise_name = '" + name + "'", null, null, null, null);
        M_ExercisePages exercisePages = null;
        
        if (cursor != null) {
            if (cursor.moveToFirst()) {
            	exercisePages = new M_ExercisePages();
            	
                int idIndex = cursor.getColumnIndex(ExercisePages._id.name());
                int exercise_nameIndex = cursor.getColumnIndex(ExercisePages.exercise_name.name());
                int titleIndex = cursor.getColumnIndex(ExercisePages.title.name());
                int instructionsIndex = cursor.getColumnIndex(ExercisePages.instructions.name());
                int sort_orderIndex = cursor.getColumnIndex(ExercisePages.sort_order.name());
                int archiveIndex = cursor.getColumnIndex(ExercisePages.archive.name());
                int background_imageIndex = cursor.getColumnIndex(ExercisePages.background_image.name());
  
                exercisePages._id = cursor.getInt(idIndex);
                exercisePages.exercise_name = cursor.getString(exercise_nameIndex);
                exercisePages.title = cursor.getString(titleIndex);
                exercisePages.instructions = cursor.getString(instructionsIndex);
                exercisePages.sort_order = cursor.getString(sort_orderIndex);
                exercisePages.archive = cursor.getString(archiveIndex);
                exercisePages.background_image = cursor.getString(background_imageIndex);
                
                list.add(exercisePages);
            }
            cursor.close();
        }
		
		return list;
	}

}
