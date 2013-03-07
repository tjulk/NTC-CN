package com.nike.ntc_cn.db;

import java.util.concurrent.Executor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DbOpenHelper extends SQLiteOpenHelper {

	private String mPath;

	/** 单例. */
	private static DbOpenHelper mDbOpenHelper = null;

	private DbOpenHelper(Context context, String name, int version,
			Executor executor) {
		super(context, name, null, version);
	}

	/**
	 * 获得单例
	 * 
	 * @param context
	 *            Context
	 * @param name
	 *            Database name
	 * @param version
	 *            Database version
	 * @param executor
	 *            Executor
	 * @return {@link DbOpenHelper}
	 */
	public static DbOpenHelper getInstance(Context context, String name,
			int version, Executor executor) {
		if (mDbOpenHelper == null) {
			mDbOpenHelper = new DbOpenHelper(context, name, version, executor);
		}
		return mDbOpenHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		createExercisesTable(db);
		createSavedWorkoutsTable(db);
		createBonusInfoTable(db);
		createDlcInstalledTable(db);
		createDlcAvailabledTable(db);
		createExercisePagesTable(db);
		createWorkoutsTable(db);
		createWorkoutExerciseTable(db);
		createExerciseAudioClipsTable(db);
		createWorkoutAudioClipsTable(db);
		createWorkoutLogTable(db);
		createRewardInfoTable(db);

	}

	private void createRewardInfoTable(SQLiteDatabase db) {
		db.execSQL(T_RewardInfoControl.createTableSql);
	}

	private void createWorkoutLogTable(SQLiteDatabase db) {
		db.execSQL(T_WorkoutLogControl.createTableSql);
	}

	private void createWorkoutAudioClipsTable(SQLiteDatabase db) {
		db.execSQL(T_WorkoutAudioClipsControl.createTableSql);
	}

	private void createExerciseAudioClipsTable(SQLiteDatabase db) {
		db.execSQL(T_ExerciseAudioClipsControl.createTableSql);
	}

	private void createWorkoutExerciseTable(SQLiteDatabase db) {
		db.execSQL(T_WorkoutExercisesControl.createTableSql);
	}

	private void createWorkoutsTable(SQLiteDatabase db) {
		db.execSQL(T_WorkoutControl.createTableSql);
	}

	private void createExercisePagesTable(SQLiteDatabase db) {
		db.execSQL(T_ExercisePagesControl.createTableSql);
	}

	private void createDlcAvailabledTable(SQLiteDatabase db) {
		db.execSQL(T_DlcAvailableControl.createTableSql);
	}

	private void createDlcInstalledTable(SQLiteDatabase db) {
		db.execSQL(T_DlcInstalledControl.createTableSql);
	}

	private void createBonusInfoTable(SQLiteDatabase db) {
		db.execSQL(T_BonusInfoControl.createTableSql);
	}

	private void createExercisesTable(SQLiteDatabase db) {
		db.execSQL(T_ExerciseControl.createTableSql);
	}

	public void createSavedWorkoutsTable(SQLiteDatabase db) {
		db.execSQL(T_SavedWorkoutControl.createTableSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		mPath = db.getPath();
	}

}