package com.spring.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories
public class Neo4jConfig extends Neo4jConfiguration {
	@Autowired
	Environment env;

	public SessionFactory sessionFactory;

	@Override
	public SessionFactory getSessionFactory() {
		sessionFactory = new SessionFactory("com.spring.model");
		return sessionFactory;
	}
	
	public Session openSession() {
		return sessionFactory.openSession();
	}

	@Override
	@Bean(name="session")
	//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Session getSession() throws Exception {
		return super.getSession();
	}
}
