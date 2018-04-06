package hello.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableHystrixDashboard
//@EnableEurekaClient
//@EnableScheduling
@EnableCircuitBreaker

@SpringBootApplication
@RestController
public class ReadingApplication {


	@Autowired
	  private BookService bookService;
	  @RequestMapping("/to-read")
	  public String toRead() {
	    return bookService.readingList();
	  }

	  @Bean
	  public RestTemplate rest(RestTemplateBuilder builder) {
	    return builder.build();
	  }
	  
/*	  @Scheduled(initialDelay = 1000, fixedRate = 500)
		public void run() {
		  
		  bookService.readingList("http://35.158.105.119/health");
		 
	  }*/


	  public static void main(String[] args) {
	    SpringApplication.run(ReadingApplication.class, args);
	  }
	}