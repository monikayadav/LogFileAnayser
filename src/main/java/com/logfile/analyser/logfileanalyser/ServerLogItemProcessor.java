package com.logfile.analyser.logfileanalyser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Processor for processing entries in the log file. If the time lapsed in a
 * process is greater than the tollerance, then the alert flag is set for that
 * process
 * 
 * @author Monika.Yadav
 *
 */
public class ServerLogItemProcessor implements ItemProcessor<ServerLogEntry, ServerLogEntry> {
	private static final Logger log = LoggerFactory.getLogger(ServerLogItemProcessor.class);

	@Override
	public ServerLogEntry process(ServerLogEntry sl) throws Exception {
		ServerLogEntry ServerLogEntry = VisititedIdUtils.getInstance().getIds().get(sl.getId());
		if (ServerLogEntry != null) {
			long diff = Math.abs(sl.getTimestamp() - ServerLogEntry.getTimestamp());
			if (diff > IConstants.TIME_TOLLEREANCE) {
				sl.setAlert(true);
			}
			log.debug("Set alert for id {}", sl.getId());
			VisititedIdUtils.getInstance().getIds().remove(sl.getId());
		} else {
			VisititedIdUtils.getInstance().getIds().put(sl.getId(), sl);
		}
		log.debug("Processed server log entry {}", sl.toString());
		return sl;
	}

}
