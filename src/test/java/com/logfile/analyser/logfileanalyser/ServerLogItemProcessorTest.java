package com.logfile.analyser.logfileanalyser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServerLogItemProcessorTest {
	ServerLogItemProcessor serverLogItemProcessor = new ServerLogItemProcessor();

	@Test
	public void testTimeUnderTollerence() throws Exception {
		ServerLogEntry serverLogEntry1 = new ServerLogEntry("1", "STARTED", 1491377495, "APPLICATION_LOG", "12345");
		serverLogItemProcessor.process(serverLogEntry1);
		assertNull(serverLogEntry1.getAlert());
		ServerLogEntry serverLogEntry2 = new ServerLogEntry("1", "FINISHED", 1491377495, "APPLICATION_LOG", "12345");
		serverLogItemProcessor.process(serverLogEntry2);
		assertNull(serverLogEntry2.getAlert());
	}

	@Test
	public void testTimeOutOfTollerence() throws Exception {
		ServerLogEntry serverLogEntry1 = new ServerLogEntry("2", "STARTED", 1491377455, "APPLICATION_LOG", "12345");
		serverLogItemProcessor.process(serverLogEntry1);
		assertNull(serverLogEntry1.getAlert());
		ServerLogEntry serverLogEntry2 = new ServerLogEntry("2", "FINISHED", 1491377495, "APPLICATION_LOG", "12345");
		serverLogItemProcessor.process(serverLogEntry2);
		assertTrue(serverLogEntry2.getAlert());
	}

	@Test
	public void testTimeUnderTollerenceIrrespectiveOfOrder() throws Exception {
		ServerLogEntry serverLogEntry1 = new ServerLogEntry("3", "FINISHED", 1491377496, "APPLICATION_LOG", "12345");
		serverLogItemProcessor.process(serverLogEntry1);
		assertNull(serverLogEntry1.getAlert());
		ServerLogEntry serverLogEntry2 = new ServerLogEntry("3", "STARTED", 1491377495, "APPLICATION_LOG", "12345");
		serverLogItemProcessor.process(serverLogEntry2);
		assertNull(serverLogEntry2.getAlert());
	}

}
