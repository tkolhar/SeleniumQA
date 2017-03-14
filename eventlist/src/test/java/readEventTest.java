import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

public class readEventTest {
 
	public String basUrl="http://localhost:8080/eventlist/events.jsp";
	public WebDriver driver;
	public Logger log;
	
	 
@BeforeMethod
public void beforeMethod() {
	   System.out.println("@BeforeMethod");
	   System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
	   driver = new ChromeDriver();
	   driver.get(basUrl);
	   log = Logger.getLogger("my.log");
	   log.setLevel(Level.ALL);
	   log.info("Started Test");
	   
}


@Test(priority=3,description="Test to Read the Records Quarterly")
public void readRecordQuarter() throws Exception {
	    
        WebElement element = driver.findElement(By.id("option-3"));  
        JavascriptExecutor  executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(5000);
        Assert.assertTrue(element.getAttribute("value").equalsIgnoreCase("quarter"));
        log.info("Checking records on " + element.getAttribute("value") + " by quarter basis");
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("month"));
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("week"));
        int j =1;
        while(j <= 10)
        {
        	element = driver.findElement(By.xpath("//*[@id=\"next-date\"]/i"));
        	if(element.isDisplayed())
        	{
        		executor = (JavascriptExecutor)driver;
        		executor.executeScript("arguments[0].click();", element);
        		Thread.sleep(100);
        		element = driver.findElement(By.xpath("//*[@id=\"startDate\"]"));
        	    String value  = element.getText();
        		element = driver.findElement(By.xpath("//*[@id=\"endDate\"]"));
        		String value1 = element.getText();
        		log.info("Reading records Between Start Date " + value + " and Next date : "+ value1);
        	    j++;
            }
        	else
        	{
        		try
        		{
        		 Assert.fail("Cannot check records for next date");
        		}catch(AssertionError e)
        		{
        		   log.info(e.getMessage());
        		}
        		
        	}
        }
}  



@Test(priority=1,description="Test to Read the Records Weekly")
public void readRecordWeek() throws Exception {
	    
        WebElement element = driver.findElement(By.id("option-1"));  
        JavascriptExecutor  executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(5000);
        Assert.assertTrue(element.getAttribute("value").equalsIgnoreCase("week"));
        log.info("Checking records on " + element.getAttribute("value") + " by week basis");
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("month"));
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("quarter"));
        int j =1;
        while(j <= 10)
        {
        	element = driver.findElement(By.xpath("//*[@id=\"next-date\"]/i"));
        	if(element.isDisplayed())
        	{
        		executor = (JavascriptExecutor)driver;
        		executor.executeScript("arguments[0].click();", element);
        		Thread.sleep(100);
        		element = driver.findElement(By.xpath("//*[@id=\"startDate\"]"));
        	    String value  = element.getText();
        		element = driver.findElement(By.xpath("//*[@id=\"endDate\"]"));
        		String value1 = element.getText();
        		log.info("Reading records Between Start Date " + value + " and Next date : "+ value1);
        	    j++;
            }
        	else
        	{
        		try
        		{
        		 Assert.fail("Cannot check records for next date");
        		}catch(AssertionError e)
        		{
        		   log.info(e.getMessage());
        		}
        		
        	}
        }
}    


@Test(priority=2,description="Test to Read the Records Monthly")
public void readRecordMonth() throws Exception {
	    
        WebElement element = driver.findElement(By.id("option-2"));
        JavascriptExecutor  executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(5000);
        Assert.assertTrue(element.getAttribute("value").equalsIgnoreCase("month"));
        log.info("Checking records on " + element.getAttribute("value") + " by month basis");
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("week"));
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("quarter"));
        int j =1;
        while(j <= 10)
        {
        	element = driver.findElement(By.xpath("//*[@id=\"next-date\"]/i"));
        	if(element.isDisplayed())
        	{
        		executor = (JavascriptExecutor)driver;
        		executor.executeScript("arguments[0].click();", element);
        		Thread.sleep(100);
        		element = driver.findElement(By.xpath("//*[@id=\"startDate\"]"));
        	    String value  = element.getText();
        		element = driver.findElement(By.xpath("//*[@id=\"endDate\"]"));
        		String value1 = element.getText();
        		log.info("Reading records Between Start Date " + value + " and Next date : "+ value1);
        	    j++;
            }
        	else
        	{
        		try
        		{
        		 Assert.fail("Cannot check records for next date");
        		}catch(AssertionError e)
        		{
        		   log.info(e.getMessage());
        		}
        		
        	}
        }
} 


@Test(priority=4,description="Test to click on an item to show a \"detail view\" of item")
public void readDetailView() throws Exception {
	    
        WebElement element = driver.findElement(By.id("option-2"));
        JavascriptExecutor  executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(5000);
        Assert.assertTrue(element.getAttribute("value").equalsIgnoreCase("month"));
        log.info("Checking records on " + element.getAttribute("value") + " by month basis");
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("week"));
        Assert.assertFalse(element.getAttribute("value").equalsIgnoreCase("quarter"));
        element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[2]/table/tbody/tr/td[1]"));
        if(element.isDisplayed())
        {
               executor = (JavascriptExecutor)driver;
               executor.executeScript("arguments[0].click();", element);
               Thread.sleep(5000);
               
               WebElement elm = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
               if(elm.isDisplayed())
               {
            	
            	  try
            	  {
            	   Assert.assertTrue(elm.isDisplayed(),"Detailed View of Event not found");
            	   log.info("Event can be viewed in detail");
            	   element = driver.findElement(By.xpath("//*[@id=\"btnClose\"]"));
            	   executor = (JavascriptExecutor)driver;
            	   executor.executeScript("arguments[0].click();", element);   
            	   Thread.sleep(10000);
            	  }
            	  catch(AssertionError e)
            	  {
            	      log.info(e.getMessage());
            	  }
               }
               else
               {
            	    log.info("Sorry No Records There to View.Please Add Some records to View Details");
               }		
        }
} 

@Test(priority=5)
public void addRecordPositive() throws Exception {
	    
	    String event_date = "2016-12-24";
	    String event_type = "test event type";
	    String event_summary = "test event summary";
	    String event_metric = "36";
	    String event_details = "test event details";
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
        element.sendKeys(event_metric);
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
        element.sendKeys(event_details);
        Thread.sleep(5000);
        
        element = driver.findElement(By.xpath("//*[@id=\"btnSave\"]"));
        executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        //Thread.sleep(3);
        try{
           Assert.assertTrue(isTextPresent("Event created successfully"),"Could not create event");
           log.info("Event created successfully");
           Thread.sleep(10000);      
        }catch(AssertionError e)
        {
            log.info(e.getMessage());
        }
} 


@Test(priority=6)
public void invalidDateNegative() throws Exception {
	    
       
	    String event_date = "12-12-2015";
	    String event_type ="invalid date format type";
        String event_summary = "invalid date format summary";
	    String event_metric = "39";
	    String event_details = "invalid date format details";
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
        element.sendKeys(event_metric);
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
        element.sendKeys(event_details);
        Thread.sleep(5000);
        
        element = driver.findElement(By.xpath("//*[@id=\"btnSave\"]"));
        executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        try{
            Assert.assertFalse(isTextPresent("Event created successfully"),"Data got added even though Date is in invalid format");
            log.info("Data does not get added when Date is in invalid format");
        }catch(AssertionError e)
        {
        	 log.info(e.getMessage());
        }
        Assert.assertTrue(checkDateFormat(event_date),"Date is in invalid format.Should be \'yyyy-MM-dd\'");
        log.info("Date is in invalid format.Should be \'yyyy-MM-dd\'");        
        Thread.sleep(10000);      
} 

@Test(priority=7)
public void emptyDetailsNegative() throws Exception {
	    
       
	    String event_date = "2015-12-15";
	    String event_type ="";
        String event_summary = "";
	    String event_metric = "43";
	    String event_details = "";
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
        element.sendKeys(event_metric);
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
        element.sendKeys(event_details);
        Thread.sleep(5000);
        
        element = driver.findElement(By.xpath("//*[@id=\"btnSave\"]"));
        executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        try
        {
         Assert.assertFalse(isTextPresent("Event created successfully"),"Created event with empty fields");
         log.info("Data does not get added when some fields are empty");
        }catch(AssertionError e)
        {
        	 log.info(e.getMessage());
        }
        Assert.assertTrue(isEmpty(event_type,event_summary,event_details),"Event Details, Type and Summary are empty");
        log.info("Event details, Type, and Summary cannot be empty"); 
        Thread.sleep(10000);      
} 

@Test(priority=8)
public void nonAlphanumericDetailsNegative() throws Exception {
	    
       
	    String event_date = "2015-12-16";
	    String event_type ="@$#^#%#@^@^";
        String event_summary = "@#%#^!$#@";
	    String event_metric = "26";
	    String event_details = "#%#@^@#^#@@&@&";
	    WebElement element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[5]/button"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(1000);
        
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
        element.sendKeys(event_metric);
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
        element.sendKeys(event_details);
        Thread.sleep(5000);
        
        element = driver.findElement(By.xpath("//*[@id=\"btnSave\"]"));
        executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        try
        {
         Assert.assertFalse(isTextPresent("Event created successfully"),"Created event with non-alphanumeric values");
         log.info("Data does not get added when some fields are not alphanumeric");
         
        }catch(AssertionError e)
        {
        	 log.info(e.getMessage());
        }
        Assert.assertTrue(isNotAlphanumeric(event_type,event_summary,event_details),"Event Details, Type and Summary must be alphanumeric");
        log.info("Event details, Type, and Summary are not alphanumeric."); 
        Thread.sleep(10000);      
} 

@Test(priority=9)
public void longLengthDetailsNegative() throws Exception {
	    
       
	    String event_date = "2015-12-16";
	    int len = 1000;
	    String test_long = "";
	    for(int i=0;i<len;i++)
	    {
	    	test_long += "a";
	    }
	    String event_metric = "27";
	    WebElement element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[1]/table/tbody/tr[1]/th[5]/button"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(1000);
   
        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-date\"]"));
        element.sendKeys(event_date);
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath( "//*[@id=\"dialog-event-type\"]"));
        element.sendKeys(test_long);
        Thread.sleep(1000);
       
        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-summary\"]"));
        element.sendKeys(test_long);
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath("//*[@id=\"dialog-event-metric\"]"));
        element.sendKeys(event_metric);
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath("//*[@id=\"modal-content\"]"));
        element.sendKeys(test_long);
        Thread.sleep(5000);
        
        element = driver.findElement(By.xpath("//*[@id=\"btnSave\"]"));
        executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        try
        {
         Assert.assertTrue(isTextPresent("Event created successfully"),"Cannot create event with large length values");
         log.info("Added Record for large length string"); 
        }catch(AssertionError e)
        {
        	 log.info(e.getMessage());
        }
        Thread.sleep(10000);      
} 

@Test(priority=15)
public void deleteRecord() throws Exception {
	    
	   WebElement element = driver.findElement(By.xpath("//*[@id=\"next-date\"]/i"));
       JavascriptExecutor  executor = (JavascriptExecutor)driver;
       executor.executeScript("arguments[0].click();", element);
       Thread.sleep(10000);
       executor = (JavascriptExecutor)driver;
       executor.executeScript("arguments[0].click();", element);
       Thread.sleep(10000);
       
       element = driver.findElement(By.xpath("//*[@id=\"jsGrid\"]/div[2]/table/tbody/tr[1]/td[5]/input"));
       if(element.isDisplayed())
       {
        executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(5000);
        checkAlert();
        try
        {
         Assert.assertTrue(isTextPresent("Event deleted successfully"),"Error Deleting the event");
         log.info("Event deleted successfully");
         Thread.sleep(1000);
        }
        catch(AssertionError e)
        {
            log.info(e.getMessage());
	    }
        }
       else
       {
    	    log.info("No records found to delete within specified Date Range.");
       }
       
}




public void checkAlert() {
    try {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    } catch (Exception e) {
        //exception handling
    }
}

public boolean isNotAlphanumeric(String evnttp,String evntsumry,String evntdtls)
{
	if(StringUtils.isAlphanumeric(evnttp) && StringUtils.isAlphanumeric(evntsumry) && StringUtils.isAlphanumeric(evntdtls))
	{
		return false;
	}
	else
	{
	 return true;
	}
	
}

public boolean isEmpty(String eventtype,String eventsmry, String eventdtls)
{
	    if(eventtype.isEmpty() && eventsmry.isEmpty() && eventdtls.isEmpty())
	    {
	    	return true;
	    }
	    else
	    {
	        return false;
	    }
}

public boolean checkDateFormat(String eventdt)
{
    Date date = null;
    try {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    date = sdf.parse(eventdt);
    if (!eventdt.equals(sdf.format(date))) {
        date = null;
    }
} catch (ParseException ex) {
    ex.printStackTrace();
}
if (date == null) {
    return true;
} else {
    return false;
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


@AfterMethod
public void afterMethod() {
	System.out.println("@AfterMethod");
	driver.quit();
}
} 
