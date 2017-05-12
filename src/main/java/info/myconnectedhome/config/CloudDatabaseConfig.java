package info.myconnectedhome.config;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import info.myconnectedhome.model.Docs;
@Configuration
@EnableJpaRepositories("info.myconnectedhome.repository")
@Profile("cloud")
public class CloudDatabaseConfig extends AbstractCloudConfig {

    /**
     * (Step 1) Parses the local environment variable VCAP_SERVICES (containing cloud information) and provides a
     * DataSource. The superclass {@link AbstractCloudConfig}, part of the Spring Cloud plugin, is used for this.
     */
    @Bean
    public DataSource dataSource() {
        /*
         * Load BasicDbcpPooledDataSourceCreator before TomcatJdbcPooledDataSourceCreator. Also see the following link
         * for a detailled discussion of this issue:
         * https://stackoverflow.com/questions/36885891/jpa-eclipselink-understanding-classloader-issues
         */
        List<String> dataSourceNames = Arrays.asList("BasicDbcpPooledDataSourceCreator",
                "TomcatJdbcPooledDataSourceCreator", "HikariCpPooledDataSourceCreator",
                "TomcatDbcpPooledDataSourceCreator");
        DataSourceConfig dbConfig = new DataSourceConfig(dataSourceNames);
        return connectionFactory().dataSource(dbConfig);
    }

    /**
     * (Step 2a) Based on a {@link DataSource} (provided using the method above), provides a factory to create
     * {@link javax.persistence.EntityManager} instances (a core class of JPA). Also see
     * {@link EntityManagerFactoryProvider}.
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        return EntityManagerFactoryProvider.get(dataSource, Docs.class.getPackage().getName());
    }

    /**
     * (Step 2b) Based on an {@link EntityManagerFactory} (provided using the method above), provides a
     * {@link JpaTransactionManager} (another core class of JPA).
     */
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}