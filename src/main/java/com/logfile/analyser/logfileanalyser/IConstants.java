package com.logfile.analyser.logfileanalyser;

public interface IConstants {
	int CHUNK_SIZE = 100000;
	int TIME_TOLLEREANCE = 4;
	String JOB_NAME = "processServerLogEntryJob";
	String INSERT_STATEMENT = "INSERT INTO event(id,state,timestamp,type,host,alert) VALUES (:id,:state,:timestamp,:type,:host,:alert)";
	String DATABASE_URL = "jdbc:hsqldb:hsql://localhost/test";
	String DATABASE_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
	String DATABASE_USER = "SA";
}
