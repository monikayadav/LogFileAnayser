package com.logfile.analyser.logfileanalyser;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The application for processing the server log file entries.
 * 
 * @author Monika.Yadav
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class LogFileAnalyserApplication {
	private static final Logger log = LoggerFactory.getLogger(LogFileAnalyserApplication.class);

	public static void main(String[] args) throws FileNotFoundException {

		if (args.length == 0) {
			log.error("No Json file path provided");
			throw new FileNotFoundException();
		}
		String inFilePath = args[0];
		File inFile = new File(inFilePath);
		if (!inFile.exists()) {
			log.error("Json file does not exist. Provided Path:" + inFilePath);
			throw new FileNotFoundException();
		}
		log.info("PATH:" + inFilePath);
		System.setProperty("inFilePath", inFilePath);
		SpringApplication.run(LogFileAnalyserApplication.class, args);
	}

}
