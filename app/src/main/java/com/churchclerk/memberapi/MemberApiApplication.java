/**
 * 
 */
package com.churchclerk.memberapi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author dongp
 *
 */
@EntityScan({"com.churchclerk"})
@ComponentScan({"com.churchclerk"})
@SpringBootApplication
public class MemberApiApplication {

	private static Logger logger = LoggerFactory.getLogger(MemberApiApplication.class);

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MemberApiApplication.class, args);
	}

}
