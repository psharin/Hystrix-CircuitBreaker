package hello.application;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;

@Service
public class BookService {

  private final RestTemplate restTemplate;
private static Date dateTime = new Date();
private boolean fallBackCall = false;
  public BookService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  @HystrixCommand(fallbackMethod = "reliable", commandProperties = {
		    //@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "18000"),
		    //@HystrixProperty(name = "hystrix.command.default.circuitBreaker.enabled", value = "true"),
		    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1")
		})

  public String readingList() {

	  System.out.println("Reading list entry ");
    URI uri = URI.create("http://localhost:8090/recommended");
    return this.restTemplate.getForObject(uri, String.class);

}
  @HystrixCommand(fallbackMethod = "testFallBack", commandProperties = {
		    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
		  ,
		    //@HystrixProperty(name = "hystrix.command.default.circuitBreaker.enabled", value = "true"),
		    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
		    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "20"),
		    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000")
		    //https://cloud.spring.io/spring-cloud-netflix/multi/multi__circuit_breaker_hystrix_clients.html

		})
  public String readingList(String url) {

	  System.out.println("Inside readingList with URL : "+new Date());
	/*  if(	  fallBackCall){System.out.println("Inside readingList with URL : After : "+(new Date().getTime()
			  - dateTime.getTime() )/1000%60  + "   Seconds");}*/
	  dateTime = new Date();
	  fallBackCall=false;
	  URI uri = URI.create(url);
    return this.restTemplate.getForObject(uri, String.class);

}
  public String testFallBack(String url){
	  fallBackCall = true;
	  System.out.println("Inside testFallBack : "+new Date());
	  return "This is from testFallBack";
  }

  public String reliable() {
	  System.out.println(new Date());
	  return "Server is Currently down, will up in a while .... ";
  }

}