package com.yashwant.gahlot.Wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletApplication {

	public static void main(String[] args) {
		//		UserManagementApplication.class: The main class of the application.
//		args: The command-line arguments passed to the application.
		SpringApplication.run(WalletApplication.class, args);
	}

}

//In summary, this code sets up the basic structure of a Spring Boot application,
// enabling auto-configuration and component scanning, and starts the application by
// calling the run method of SpringApplication.

/*
    @Configuration is used to define bean definitions and configuration methods manually.
    @EnableAutoConfiguration enables automatic configuration of beans based on classpath and dependencies in a Spring Boot application.
    @ComponentScan scans for components (beans) within specified packages and includes them in the application context.
    @EnableAutoConfiguration and @ComponentScan are often used together in a Spring Boot application to leverage automatic configuration and component scanning capabilities [3].
    @EnableAutoConfiguration is an alternative to @Configuration when auto-configuration is desired, such as in Spring Boot applications [1].
* */




/*
@SpringBootApplication: This is the main annotation used to declare the Spring Boot application. It is a combination of three annotations:

    @Configuration: Indicates that the class provides bean configurations.
    @EnableAutoConfiguration: Enables auto-configuration of the Spring application context, attempting to guess and configure beans based on classpath settings.
    @ComponentScan: Enables component scanning within the specified base packages to discover beans and other components.
* */



/*
Example of : @Configuration
By using @Configuration, you can define and manage your beans explicitly in Java code rather than relying on XML configuration files. This gives you more flexibility and allows for easier refactoring and testing of your application

When a class is annotated with @Configuration, it becomes a source of bean definitions. The Spring container processes the class and generates Spring Beans based on the @Bean methods defined within that class [2]. These beans can be used throughout the application.

@Configuration
public class AppConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    // Other bean definition methods...
}
* */




/*
Example of @EnableAutoConfiguration

When @EnableAutoConfiguration is added to a configuration class, Spring Boot scans the classpath and automatically configures beans based on the detected dependencies. It uses a mechanism called "auto-configuration" to determine which beans to configure. Auto-configuration classes are regular Spring @Configuration beans

For example, if Spring Boot detects the presence of a specific database driver on the classpath and no manual configuration is provided, it will automatically configure a data source bean for that database [2]. Similarly, it can auto-configure other components such as security, messaging, caching, etc., based on the dependencies present in the application's classpath [3].


@SpringBootApplication
@EnableAutoConfiguration
public class MyApp {

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

    // Other application-specific configurations...
}
* */


/*
Example for ComponentScan

//The @ComponentScan annotation is used in the Spring framework to specify the packages that should be scanned for Spring-managed components, such as beans, controllers, services, etc. It works in conjunction with the @Configuration annotation to define the packages to be scanned.

//When used without any arguments, @ComponentScan tells Spring to scan the current package and all of its sub-packages [1]. However, you can provide specific package names or base package classes as arguments to the annotation to define custom scanning behavior.

//In the above example, the @ComponentScan annotation is added to the AppConfig class, which is annotated with @Configuration. It specifies the package com.example.myapp.components to be scanned for Spring components.

//Any classes annotated with @Component, @Controller, @Service, @Repository, or other custom annotations will be discovered and managed by the Spring container within the specified package and its sub-packages.

//By using @ComponentScan, you can define the scope of component scanning in your application, allowing Spring to automatically detect and instantiate beans based on the discovered components.

@Configuration
@ComponentScan("com.example.myapp.components")
public class AppConfig {
    // Other configuration code...
}
* */