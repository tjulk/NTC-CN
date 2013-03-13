package com.geniuseoe2012.lazyloaderdemo.cache;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "com.nike.ntc_cn/files/";
		} else {
			return CommonUtil.getRootFilePath() + "com.nike.ntc_cn/files";
		}
	}
}
