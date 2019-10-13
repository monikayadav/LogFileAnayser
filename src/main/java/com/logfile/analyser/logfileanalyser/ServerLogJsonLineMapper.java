package com.logfile.analyser.logfileanalyser;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineMapper;

public class ServerLogJsonLineMapper implements LineMapper<ServerLogEntry> {
	private static final Logger log = LoggerFactory.getLogger(ServerLogJsonLineMapper.class);

	@Override
	public ServerLogEntry mapLine(String line, int lineNumber) throws JSONException {
		JSONObject jsonObj = new JSONObject(line);
		ServerLogEntry se = null;
		if (jsonObj != null) {
			if (!jsonObj.isNull("type")) {
				se = new ServerLogEntry(jsonObj.getString("id"), jsonObj.getString("state"),
						jsonObj.getLong("timestamp"), jsonObj.getString("type"), jsonObj.getString("host"));
			} else {
				se = new ServerLogEntry(jsonObj.getString("id"), jsonObj.getString("state"),
						jsonObj.getLong("timestamp"), null, null);
			}
		}
		log.debug("Read server log entry {}", se.toString());
		return se;
	}

}
