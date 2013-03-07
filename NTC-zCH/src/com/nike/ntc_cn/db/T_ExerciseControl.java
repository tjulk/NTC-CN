package com.nike.ntc_cn.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

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
	
	
}
