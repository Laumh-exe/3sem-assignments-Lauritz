package app.HotelExercise.Config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Entities.Role;
import app.HotelExercise.Entities.Room;
import app.HotelExercise.Entities.User;
import app.HotelExercise.Exceptions.ApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateConfig {
    private static EntityManagerFactory emf;
    private static boolean isIntegrationTest = false; // this flag is set for testing purposes

    public static void setTestMode(boolean isTest) {
        HibernateConfig.isIntegrationTest = isTest;
    }

    private static EntityManagerFactory emfTest;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = createEMF(false);
        }
        return emf;
    }

    public static EntityManagerFactory getEntityManagerFactoryForTest() {
        if (emfTest == null){
            setTestMode(true);
            emfTest = createEMF(true);
        }   
        return emfTest;
    }

    // TODO: IMPORTANT: Add Entity classes here for them to be registered with
    // Hibernate
    private static void getAnnotationConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Hotel.class);
        configuration.addAnnotatedClass(Room.class);
    }

    private static EntityManagerFactory createEMF(boolean forTest) {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            // Set the properties
            setBaseProperties(props);
            if (forTest || isIntegrationTest) {
                props = setTestProperties(props);
            } else if (System.getenv("DEPLOYED") != null) {
                setDeployedProperties(props);
            } else {
                props = setDevProperties(props);
            }
            configuration.setProperties(props);
            getAnnotationConfiguration(configuration);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            EntityManagerFactory emf = sf.unwrap(EntityManagerFactory.class);
            return emf;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static String getDBName() {
        if (isIntegrationTest) {
            return Utils.getPropertyValue("test.db.name", "properties-from-pom.properties"); // REMEMBER TO ADD NAME TO
                                                                                             // POM LIKE THIS:
                                                                                             // <test.db.name>test_db</test.db.name>
        } else {
            return Utils.getPropertyValue("db.name", "properties-from-pom.properties");
        }
    }

    private static Properties setBaseProperties(Properties props) {
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.use_sql_comments", "true");
        return props;
    }

    private static Properties setDeployedProperties(Properties props) {
        props.setProperty("hibernate.connection.url", System.getenv("CONNECTION_STR") + getDBName());
        props.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
        return props;
    }

    private static Properties setDevProperties(Properties props) {
        props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + getDBName());
        props.put("hibernate.connection.username", "postgres");
        props.put("hibernate.connection.password", "postgres");
        return props;
    }

    private static Properties setTestProperties(Properties props) {
        // props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        props.put("hibernate.connection.url", "jdbc:tc:postgresql:15.3-alpine3.18:///test_db");
        props.put("hibernate.connection.username", "postgres");
        props.put("hibernate.connection.password", "postgres");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        return props;
    }

    /**
     * Purpose: Utility class to read properties from a file
     * Author: Thomas Hartmann
     */
    public class Utils {
        public static String getPropertyValue(String propName, String ressourceName) {
            // REMEMBER TO BUILD WITH MAVEN FIRST. Read the property file if not deployed
            // (else read system vars instead)
            // Read from ressources/config.properties or from pom.xml depending on the
            // ressourceName
            try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(ressourceName)) { // "config.properties"
                                                                                                     // or
                                                                                                     // "properties-from-pom.properties"
                Properties prop = new Properties();
                prop.load(is);
                return prop.getProperty(propName);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new ApiException(500, String.format(
                        "Could not read property %s. Did you remember to build the project with MAVEN?", propName));
            }
        }
    }
}
