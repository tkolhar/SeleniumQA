import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

 


import static com.jayway.restassured.RestAssured.*;
//import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import groovy.json.JsonException;

public class eventIT {

	public Logger log; 
	public String json;
	public Response res;
	public String basUrl="http://localhost:8080/eventlist/events.jsp";
	public static final String filePath = "C:\\assignment_data_short.json";
	public WebDriver driver;
	public String id;
	
	
	@BeforeMethod
	public void beforeMethod() {
		   System.out.println("@BeforeMethod");
		   log = Logger.getLogger("my.log");
		   System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		   driver = new ChromeDriver();
		   driver.get(basUrl);
		   log.setLevel(Level.ALL);
		   log.info("Started Test");
		   
	}
	
    @Test(priority=1)
    public void validateAdd() throws IOException {
    	 try {
    	
    		    // read the json file
    		    FileReader reader = new FileReader(filePath);
    		    JSONParser jsonParser = new JSONParser();
    		    JSONArray jsonArr = (JSONArray) jsonParser.parse(reader);
    		    int len = jsonArr.size();
    		    for(int i=0;i<1;i++)
    		    {
    		    	 JSONObject rootObject = (JSONObject)jsonArr.get(i);
    		    	 String event_date = (String) rootObject.get("event_date");
    		    	 String event_type = (String) rootObject.get("event_type");
    		    	 String event_summary = (String) rootObject.get("event_summary");
    		    	 String event_size = (String) rootObject.get("event_size");
    		    	 String event_details = (String) rootObject.get("event_details");
    		    	
    		    	
    		 	    WebElement element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[5]/button"));
    		 	    JavascriptExecutor executor = (JavascriptExecutor)driver;
    		        executor.executeScript("arguments[0].click();", element);
    		        Thread.sleep(1000);
    		   
    		        
    		        //element.click();
    		        
    		        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-date\"]"));
    		        element.sendKeys(event_date);
    		        Thread.sleep(1000);
    		        
    		        element = driver.findElement(By.xpath( "//*[@id=\"dialog-event-type\"]"));
    		        element.sendKeys(event_type);
    		        Thread.sleep(1000);
    		       
    		        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-summary\"]"));
    		        element.sendKeys(event_summary);
    		        Thread.sleep(1000);
    		        
    		        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-metric\"]"));
    		        element.sendKeys(event_size);
    		        Thread.sleep(10000);
    		        
    		       element = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
    		       element.sendKeys(event_details);
    		        Thread.sleep(5000);
    		        
    		        element = driver.findElement(By.xpath("//*[@id=\"btnSave\"]"));
    		        executor = (JavascriptExecutor)driver;
    		        executor.executeScript("arguments[0].click();", element);
    		        Thread.sleep(500);
    		        try{
    		           Assert.assertTrue(isTextPresent("Event created successfully"),"Could not create event");
    		           log.info("Event created successfully");
    		           Thread.sleep(10000);      
    		        }catch(AssertionError e)
    		        {
    		            log.info(e.getMessage());
    		        }
    		         
    		        element = driver.findElement(By.xpath("/html/body/div[1]/div/header[1]/div/nav/a[4]"));
    		        executor = (JavascriptExecutor)driver;
    		        executor.executeScript("arguments[0].click();", element);
    		        Thread.sleep(5000);
    		       
    		         List <WebElement> elm = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr"));
    		         int rowSize = elm.size();
    		         boolean flag = false;
    		          for(int j=1;j<rowSize;j++)
    		         {
    		           By lastRow=By.xpath("/html/body/div/main/table/tbody/tr["+ j +"]"+"/td[3]");
    		           String event_type_added = driver.findElement(lastRow).getText();
    		        //   log.info(event_type + " " + event_type_added);
    		           if(event_type_added.compareToIgnoreCase(event_type) == 0)
    		           {
    		        	     flag = true;
    		        	     break;
    		           }
    		         }  
    		          try{
      		              Assert.assertTrue(flag,"Error Adding event in database");
      		              log.info("Event is Added in Database. Can be found in Diagnostics Page");
      		              Thread.sleep(2000);     
      		          }catch(AssertionError e)
      		          {
      		            log.info(e.getMessage());
      		         }
    		        driver.get(basUrl);     
    		} 	
    		 }catch(Exception e)
    	 {
    			 log.info(e.getMessage());
    	 }
    } 	 
    
    
    @Test(priority=24)
    public void validateUpdate() throws JsonException,InterruptedException {
    	
    	 driver.get("http://localhost:8080/eventlist/diagnostics.jsp");
         List <WebElement> elm = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr"));
         int rowSize = elm.size();
         By lastRow=By.xpath("/html/body/div/main/table/tbody/tr["+ rowSize +"]"+"/td[1]");
         String id = driver.findElement(lastRow).getText();
        
         
    	 res = post("http://localhost:8080/eventlist/api/v1/events?eventId="+id+"&eventType=test&eventDetails=test&eventSummary&eventDate=Dec 15, 2015&eventMetric=18");            
         Assert.assertEquals(200, res.getStatusCode());
         json = res.asString();
         try
         {
          Assert.assertTrue(isTextPresentApi("Event updated successfully"),"Error updating the Event");
          log.info("Event is updated successfully."); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
         driver.get("http://localhost:8080/eventlist/diagnostics.jsp");
         elm = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr"));
         rowSize = elm.size();
         driver.get(basUrl);     
} 	


	   @Test(priority=24)
	    public void validateDelete() throws JsonException,InterruptedException {
	    	
	    	 driver.get("http://localhost:8080/eventlist/diagnostics.jsp");
	         List <WebElement> elm = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr"));
	         int rowSize = elm.size();
	         By lastRow=By.xpath("/html/body/div/main/table/tbody/tr["+ rowSize +"]"+"/td[1]");
	         String id = driver.findElement(lastRow).getText();
	    	 res = delete("http://localhost:8080/eventlist/api/v1/events?eventId="+id);            
	         Assert.assertEquals(200, res.getStatusCode());
	         json = res.asString();
	         try
	         {
	          Assert.assertTrue(isTextPresentApi("Event deleted successfully"),"Error deleted the Event");
	          log.info("Event is deleted successfully."); 
	         }catch(AssertionError e)
	         {
	         	 log.info(e.getMessage());
	         }
	         driver.get("http://localhost:8080/eventlist/diagnostics.jsp");
	         elm = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr"));
	         rowSize = elm.size();
	         boolean flag = true;
	         for(int i=1;i<=rowSize;i++)
	         {
	          lastRow=By.xpath("/html/body/div/main/table/tbody/tr["+ i +"]"+"/td[1]");
	          String event_id = driver.findElement(lastRow).getText();
		           if(event_id.compareToIgnoreCase(id) == 0 )
		           {
		        	   flag = false;
		        	   break;
		           }            
	         }
	         try
	         {
	        	  Assert.assertTrue(flag," Event not deleted from database");
	        	  log.info("Event deleted from database. Not found in Diagnostics Page");
	         }catch(AssertionError e)
	         {
	        	 log.info(e.getMessage());
	         }
	     
	        driver.get(basUrl);     
	} 	

	
	 @Test(priority=24)
	 public void validateEventPage() throws JsonException,InterruptedException {
	    	
	    	 
	         WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div/header[1]/div/span"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" /html/body/div[1]/div/header[1]/div/nav/a[1]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" /html/body/div[1]/div/header[1]/div/nav/a[2]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" /html/body/div[1]/div/header[1]/div/nav/a[3]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" /html/body/div[1]/div/header[1]/div/nav/a[4]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" /html/body/div[1]/div/header[2]/div/span"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"datepicker\"]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"prev-date\"]/i"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" //*[@id=\"next-date\"]/i"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"startDate\"]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"endDate\"]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" //*[@id=\"period-type-group\"]/label[2]/span[4]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" //*[@id=\"period-type-group\"]/label[3]/span[4]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath(" //*[@id=\"period-type-group\"]/label[1]/span[4]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[1]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[2]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[3]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[4]"));
	         Assert.assertTrue(element.isDisplayed());
	         element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[5]/button"));
	         Assert.assertTrue(element.isDisplayed());
	       	       
	} 	
	
	
    @Test(priority=26)
    public void validateAddMetric() throws IOException {
    	 try {
    	
    		   
    		    	 String event_date = "2016-11-12";
    		    	 String event_type = "check metric";
    		    	 String event_summary = "check metric";
    		    	 String event_size = "test metric"; 
    		    	 String event_details = "check metric is a number";
    		    	 String event_size_int = "27";
    		    	
    		    	
    		 	    WebElement element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[5]/button"));
    		 	   JavascriptExecutor executor = (JavascriptExecutor)driver;
    		        executor.executeScript("arguments[0].click();", element);
    		        Thread.sleep(1000);
    		   
    		        
    		        //element.click();
    		        
    		        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-date\"]"));
    		        element.sendKeys(event_date);
    		        Thread.sleep(1000);
    		        
    		        element = driver.findElement(By.xpath( "//*[@id=\"dialog-event-type\"]"));
    		        element.sendKeys(event_type);
    		        Thread.sleep(1000);
    		       
    		        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-summary\"]"));
    		        element.sendKeys(event_summary);
    		        Thread.sleep(1000);
    		        
    		        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-metric\"]"));
    		        element.sendKeys(event_size);
    		        Thread.sleep(5000);
    		        Assert.assertTrue(isTextPresent("Input is not a number!"),"Added event metric which is not a number");
 		            log.info("Event Metric Validated");
 		            element.clear();
 		            element.sendKeys(event_size_int);
    		        Thread.sleep(10000);
    		        
    		        element = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
    		        element.sendKeys(event_details);
    		        Thread.sleep(5000);
    		        
    		        element = driver.findElement(By.xpath("//*[@id=\"btnSave\"]"));
    		        executor = (JavascriptExecutor)driver;
    		        executor.executeScript("arguments[0].click();", element);
    		        Thread.sleep(500);
    		        try{
    		           Assert.assertTrue(isTextPresent("Event created successfully"),"Could not create event");
    		           log.info("Event created successfully");
    		           Thread.sleep(10000);      
    		        }catch(AssertionError e)
    		        {
    		            log.info(e.getMessage());
    		        }
    		         
    		        element = driver.findElement(By.xpath("/html/body/div[1]/div/header[1]/div/nav/a[4]"));
    		        executor = (JavascriptExecutor)driver;
    		        executor.executeScript("arguments[0].click();", element);
    		        Thread.sleep(5000);
    		       
    		         List <WebElement> elm = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr"));
    		         int rowSize = elm.size();
    		         log.info(Integer.toString(rowSize));
    		         boolean flag = false;
    		         for(int i=1;i<rowSize;i++)
    		         {
    		           By lastRow=By.xpath("/html/body/div/main/table/tbody/tr["+ i +"]"+"/td[3]");
    		           String event_type_added = driver.findElement(lastRow).getText();
    		           if(event_type_added.compareToIgnoreCase(event_type) == 0)
    		           {
    		        	     flag = true;
    		        	     break;
    		           }
    		         }  
    		          try{
      		              Assert.assertTrue(flag,"Error Adding event in database");
      		              log.info("Event is Added in Database. Can be found in Diagnostics Page");
      		              Thread.sleep(2000);     
      		          }catch(AssertionError e)
      		          {
      		            log.info(e.getMessage());
      		         }
    	 }catch(Exception e)
    	 {
    		  log.info(e.getMessage());
    	 }
    } 	 
    
    
  
    protected boolean isTextPresent(String text){
        try{
        	
            boolean b = driver.getPageSource().contains(text);
            return b;
        }
        catch(Exception e){
            return false;
        }
    }
    
    protected boolean isTextPresentApi(String text){
        try{
        	
            boolean b = res.asString().contains(text);
            return b;
        }
        catch(Exception e){
            return false;
        }
    }
    
    @AfterMethod
    public void afterMethod() {
    	System.out.println("@AfterMethod");
    	driver.quit();
    }
}   