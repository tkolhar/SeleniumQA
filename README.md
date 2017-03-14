# SeleniumQA
UI and API Automation Selenium and API using rest assured Java


Test Automation Framework Event Web Application:

Development Tools Used:

Language Used: Java Jdk1.7+

Build Tool: Maven 4.0.0

Browser/UI Automation: Selenium HQ version 3.0.1

Browser Used for Testing: Chrome Version 56.0.2924.87 (64-bit)

Apache Tomcat: v8

API Automation: Rest-Assured version 2.9.0 (Java Plugin to handle Api)

Test Framework: TestNG version 6.8.8 (exactly same as Junit with additional features) 

IDE: Eclipse Neon.2 4.6.2

Maven is used to handle all the dependencies.

The pom.xml contains all the dependency details and plugin to be included.

For selenium, I had downloaded the it from official website and then included the jar files in the project.

If you are using Eclipse, then Right Click the Project -> Properties -> Java Build Path Tab -> Libraries Tab -> Click Add External Jars.

Here you can Browse for selenium-java-3.0.1 folder and include all the jar files found in lib directory as well as outside lib directory. 

Later update the pom.xml to handle the dependency for selenium.

Please download ChromeDriver and extract chromedriver.exe in C:\chromedriver_win32\chromedriver.exe. 

This location is important because for UI tests I have set the system property to locate the Chrome Driver in the above-mentioned path. 

For execution of API and UI tests I have used maven-surefire-plugin version 2.19.1

and for execution of Integration tests I have used maven-failsafe-plugin version 2.19.1.




Functional/unit Test Cases:

	Read

1.	Read Records Weekly.
i.	Obtain Records on Weekly basis.
ii.	Browse through a set of weeks.
2.	Read Records Monthly.
i.	Obtain Records on Monthly basis.
ii.	Browse through a set of months.
3.	Read Records Quarterly.
i.	Obtain Records on Quarterly basis.
ii.	Browse through a set of quarters.
4.	View Event Details.
i.	Click on the event item listed in the table.
ii.	Check if Event Details tab is found.
iii.	Close the View.

	Create Event

1.	Create Event Positive
2.	Create Event Negative – Invalid date format
3.	Create Event Negative – Metric not provided
4.	Create Event Negative – Provide large size length event type and event summary
5.	Create Event Negative – Provide empty fields for type,summary and details
6.	Create Event Negative – Add event with fields containing alphanumeric value



	Delete Event

1.	Delete Event Positive
2.	Delete Event Negative – no ID provided
3.	Delete Event Negative – invalid ID provided



	Update Event

1.	Update Event Positive
2.	Update Event Negative – provide empty event id provided
3.	Update Event Negative – invalid event id provided


Integration TestCases:


	Add Event

Happy Path
1.	Add Event from the json data set provided
2.	https://drive.google.com/open?id=0B0ALirawxXRsLUpJejNmaW42eWs
3.	Please not download and extract file C:\assignment_data_short.json
4.	This location is important because the file is pickedup from the above-mentioned location
5.	For each Added Event check if it is found in the database.
6.	So, try to retrieve it from diagnostics page. 
7.	Check if the event was actually added.

             Validate Metric is a Input Number:
1.	Add the event initially with string as event metric
2.	Browser gets updated with ‘Input is not a number’
3.	Validate the statement

	Update Event:       
     Happy Path
1.	Obtain event id to be updated
2.	Update the event
3.	Browse thru diagnostics page to see if the event is updated in database as well

	Delete Event
      Happy Path
      1 Obtain a valid event id to be deleted
      2. Delete the event
      3  Browse thru the diagnostics page to see if the event is deleted from the database as well.

	Event Page
      Happy Path
1.	Ensure that all the elements of the page are visible and correctly displayed.


        


