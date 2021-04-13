package com.camunda.poc.starter;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@SpringBootApplication
@EnableProcessApplication("spring-boot-starter")
public class CamundaApplication {

	private final Logger LOGGER = Logger.getLogger(CamundaApplication.class.getName());

	public static void main(String... args) {
	    SpringApplication.run(CamundaApplication.class, args);
	}

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) throws SQLException {

		LOGGER.info("\n\n ********************** post app hook *********************** \n\n ");

		Map<String, String> settings = new HashMap<>();
		settings.put("hibernate.connection.url", url);
		settings.put("hibernate.connection.username", userName);
		settings.put("hibernate.connection.password", password);
		settings.put("hibernate.show_sql", "true");
		settings.put("hibernate.format_sql", "true");

		ServiceRegistry serviceRegistry =
				new StandardServiceRegistryBuilder()
						.applySettings(settings).build();

		MetadataSources metadata = new MetadataSources(serviceRegistry);
		metadata.addAnnotatedClass(ServiceRequestEntity.class);

		EnumSet<TargetType> enumSet = EnumSet.of(TargetType.DATABASE);

		SchemaExport export = new SchemaExport()
				.setDelimiter(";")
				.setFormat(true);

		export.execute(enumSet, SchemaExport.Action.CREATE, metadata.buildMetadata());

		LOGGER.info("\n\n **** Tables Created ****** \n\n");

	}
}