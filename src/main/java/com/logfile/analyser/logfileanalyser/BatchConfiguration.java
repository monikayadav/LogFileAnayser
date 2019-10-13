package com.logfile.analyser.logfileanalyser;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.separator.JsonRecordSeparatorPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Value(value = "${inFilePath}")
	String inFilePath;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public <ServerLogEntry> FlatFileItemReader<ServerLogEntry> reader() {
		FlatFileItemReader<ServerLogEntry> reader = new FlatFileItemReader<ServerLogEntry>();
		reader.setResource(new FileSystemResource(inFilePath));
		reader.setRecordSeparatorPolicy(new JsonRecordSeparatorPolicy());
		reader.setLineMapper((LineMapper<ServerLogEntry>) new ServerLogJsonLineMapper());
		return reader;
	}

	@Bean
	public ServerLogItemProcessor processor() {
		return new ServerLogItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<ServerLogEntry> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<ServerLogEntry>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ServerLogEntry>())
				.sql(IConstants.INSERT_STATEMENT).dataSource(dataSource).build();
	}

	@Bean
	public Job processServerLogEntryJob(Step loadAndProcessStep, JobCompletionNotificationListener listerner) {
		return jobBuilderFactory.get(IConstants.JOB_NAME).incrementer(new RunIdIncrementer()).listener(listerner)
				.flow(loadAndProcessStep).end().build();
	}

	@Bean
	public Step loadAndProcessStep(JdbcBatchItemWriter<ServerLogEntry> writer) {
		return stepBuilderFactory.get("loadAndProcessStep").<ServerLogEntry, ServerLogEntry>chunk(IConstants.CHUNK_SIZE)
				.reader(reader()).processor(processor()).writer(writer).build();
	}

	@Value("org/springframework/batch/core/schema-drop-hsqldb.sql")
	private Resource dropReopsitoryTables;

	@Value("/org/springframework/batch/core/schema-hsqldb.sql")
	private Resource dataReopsitorySchema;

	@Value("schema-all.sql")
	private Resource schemaSql;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(IConstants.DATABASE_DRIVER);
		dataSource.setUrl(IConstants.DATABASE_URL);
		dataSource.setUsername(IConstants.DATABASE_USER);
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) throws MalformedURLException {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

		databasePopulator.addScript(dropReopsitoryTables);
		databasePopulator.addScript(dataReopsitorySchema);

		databasePopulator.addScript(schemaSql);
		databasePopulator.setIgnoreFailedDrops(true);

		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator);

		return initializer;
	}

}
