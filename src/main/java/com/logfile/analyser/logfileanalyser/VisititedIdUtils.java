package com.logfile.analyser.logfileanalyser;

import java.util.HashMap;

public class VisititedIdUtils {

	private HashMap<String, ServerLogEntry> idAndServerMap;
	private static VisititedIdUtils singletonInstance;

	private VisititedIdUtils() {
		idAndServerMap = new HashMap<String, ServerLogEntry>();
	}

	public static VisititedIdUtils getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new VisititedIdUtils();
		}
		return singletonInstance;
	}

	public HashMap<String, ServerLogEntry> getIds() {
		return idAndServerMap;
	}
}
