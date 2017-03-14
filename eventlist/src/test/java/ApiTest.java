import static com.jayway.restassured.RestAssured.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import groovy.json.JsonException;



public class ApiTest {

	public Logger log; 
	public String json;
	public Response res;
	public String basUrl="http://localhost:8080/eventlist/diagnostics.jsp";
	public WebDriver driver;
	public String id;
	
	@BeforeMethod
	public void beforeMethod() {
		   System.out.println("@BeforeMethod");
		   log = Logger.getLogger("my.log");
		   log.setLevel(Level.ALL);
		   log.info("Started Test");
		   
	}
	
    @Test(priority=2)
    public void httpGetSingle() {
        res = get("http://localhost:8080/eventlist/api/v1/events?eventId="+id);            
        Assert.assertEquals(200, res.getStatusCode());
        String json = res.asString();
        if(isTextPresent("Not found"))
        {
        	try
        	{
              Assert.fail("Error Reading the event");		
        	}catch(AssertionError e)
        	{
        		log.info(e.getMessage());
        	}
        }
        else
        { 	
         JsonPath jp = new JsonPath(json);
         Assert.assertEquals(id, jp.get("eventId").toString());
         log.info("Event Id : " + jp.get("eventId") + "\rEvent Type: " + jp.get("eventType") + "\rEvent Summary : " + jp.get("eventSummary") + "\rEvent Details : " + jp.get("eventDetails") +"\rEvent Date : " + jp.get("eventDate") + "\rEvent Metric : " + jp.get("eventMetric"));
        }
    }
    
    @Test(priority=12)
    public void httpGetSingleNegative() {
        res = get("http://localhost:8080/eventlist/api/v1/events");  
        try
        {
          Assert.assertTrue(isTextPresent("Missing parameters detected"), "Tried to read event without the event id being provided");
          log.info("Cannot read event without event id provided");
        }catch(AssertionError e)
        {
        	log.info(e.getMessage());
        }       
    }
    
    @Test(priority=13)
    public void httpGetReadList() {
        res = get("http://localhost:8080/eventlist/api/v1/events?date=2016-12-01&period=week&filter[pageIndex]=2&filter[pageSize]=10");  
        try
        {
          Assert.assertEquals(200, res.getStatusCode());
          json = res.asString();
          JsonPath jp = new JsonPath(json);
          log.info("Found "+ jp.get("itemsCount").toString() + " Events");
          
        }catch(AssertionError e)
        {
        	log.info(e.getMessage());
        }       
    }
    
    @Test(priority=14)
    public void httpGetReadListNegative() {
        res = get("http://localhost:8080/eventlist/api/v1/events?date=2016-12-01&period=week&filter[pageIndex]=2");  
        Assert.assertEquals(500, res.getStatusCode());;
        try
        {
          
          Assert.assertTrue(isTextPresent("Missing parameters detected"), "Tried to read list with missing parameters");
          log.info("Cannot read list with parameters missing");
          
        }catch(AssertionError e)
        {
        	log.info(e.getMessage());
        }       
    }
    
    @Test(priority=15)
    public void httpDelete() throws JsonException,InterruptedException {
    	
    	 res = delete("http://localhost:8080/eventlist/api/v1/events?eventId="+id);            
         Assert.assertEquals(200, res.getStatusCode());
         json = res.asString();
         try
         {
          Assert.assertTrue(isTextPresent("Event deleted successfully"),"Error Deleting the Event");
          log.info("Event is deleted successfully."); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
    }		
    
    @Test(priority=5)
    public void httpDeleteNegative() throws JsonException,InterruptedException {
    	
    	 res = delete("http://localhost:8080/eventlist/api/v1/events?eventId="+id);            
         
         json = res.asString();
         try
         {
          Assert.assertFalse(isTextPresent("Event deleted successfully"),"Trying to Delete event which does not Exist");
          log.info("Event is deleted successfully."); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
    }		
    
    @Test(priority=7)
    public void httpDeleteEmpty() throws JsonException,InterruptedException {
    	
    	 res = delete("http://localhost:8080/eventlist/api/v1/events");            
         
         json = res.asString();
         try
         {
          Assert.assertEquals(500,res.getStatusCode());
          Assert.assertTrue(isTextPresent("Error deleting event"),"Trying to Delete event where to eventId is not provided");
          log.info("Error Deleting the event."); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
    }		
    protected boolean isTextPresent(String text){
        try{
        	
            boolean b = res.asString().contains(text);
            return b;
        }
        catch(Exception e){
            return false;
        }
    }
    
    @Test(priority=1)
    public void httpPost() throws JsonException,InterruptedException {
    	
    	 res = post("http://localhost:8080/eventlist/api/v1/events?eventType=test&eventDetails=test&eventSummary=test&eventDate=2016-12-01&eventMetric=9");            
         Assert.assertEquals(200, res.getStatusCode());
         json = res.asString();
         try
         {
          Assert.assertTrue(isTextPresent("Event created successfully"),"Error creating the Event");
          log.info("Event is created successfully."); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
         System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		 driver = new ChromeDriver();
		 driver.get(basUrl);
         List <WebElement> element = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr"));
         int rowSize = element.size();
         By lastRow=By.xpath("/html/body/div/main/table/tbody/tr["+ rowSize +"]"+"/td[1]");
         id = driver.findElement(lastRow).getText();
         driver.quit();
   
    	
    }		
    
    @Test(priority=3)
    public void httpPostUpdate() throws JsonException,InterruptedException {
    	
    	 res = post("http://localhost:8080/eventlist/api/v1/events?eventId="+id+"&eventType=test&eventDetails=test&eventSummary&eventDate=Dec 12, 2015&eventMetric=9");            
         Assert.assertEquals(200, res.getStatusCode());
         json = res.asString();
         try
         {
          Assert.assertTrue(isTextPresent("Event updated successfully"),"Error updating the Event");
          log.info("Event is updated successfully."); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
    }	
    
    @Test(priority=10)
    public void httpPostUpdateNegative() throws JsonException,InterruptedException {
    	
    	 res = post("http://localhost:8080/eventlist/api/v1/events?eventId="+id+"&eventType=test&eventDetails=test&eventSummary&eventDate=2015-12-12&eventMetric=9");            
         json = res.asString();
         try
         {
          Assert.assertFalse(isTextPresent("Event updated successfully"),"Updated the event with Invalid date format");
          log.info("Cannot update event with invalid Date format"); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
    }	
    
    @Test(priority=11)
    public void httpPostUpdateNegativeNoMetric() throws JsonException,InterruptedException {
    	
    	 res = post("http://localhost:8080/eventlist/api/v1/events?eventId="+id+"&eventType=test&eventDetails=test&eventSummary&eventDate=Dec 12, 2015");            
         json = res.asString();
         try
         {
          Assert.assertFalse(isTextPresent("Event updated successfully"),"Event updated with no metric value provided");
          log.info("Cannot update event with no metric value"); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
    }	
    @Test(priority=8)
    public void httpPostNegative() throws JsonException,InterruptedException {
    	
    	 res = post("http://localhost:8080/eventlist/api/v1/events?eventType=test&eventDetails=test&eventSummary&eventDate=12-12-2015&eventMetric=9");            
         json = res.asString();
         try
         {
          Assert.assertFalse(isTextPresent("Event created successfully"),"Tried to Create a event with invalid Date format");
          log.info("Cannot create event with invalid date format."); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
         
    }		
    
    @Test(priority=9)
    public void httpPostNegativeNoMetric() throws JsonException,InterruptedException {
    	
    	 res = post("http://localhost:8080/eventlist/api/v1/events?eventType=test&eventDetails=test&eventSummary&eventDate=2015-03-03");            
         json = res.asString();
         try
         {
          Assert.assertTrue(isTextPresent("Error creating event"),"Tried to Create a event with empty event metric");
          log.info("Cannot create event with empty metric"); 
         }catch(AssertionError e)
         {
         	 log.info(e.getMessage());
         }
         
    }		    
}    