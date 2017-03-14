Overview
===========
This is a database-back web application developed using Java Servlets that provides
a list and detail view of events.


Project Scope
==============
The provided web app is a small single-page app that displays a list of paged event data with a record detail view. The goal of this assignment is to develop an automated test harness to validate the correctness of this app.

Project Requirements
==============
The web app requirements supports the following requirements:

- The web application should provide a single web page displaying a clean and user-friendly user interface that displays a time-scoped range of event data as specified by the user.
- The web page should load quickly so as to provide a good user experience (< 1 second) and when/where necessary, display secondary loading indicators when loading data from the server.
- The user should not be prevented from adjusting input control values to display different data even while previous data is still loading.
- The fictitious event data structure to be displayed is as follows:
  - event_date : Date String (YYYY-MM-DD)
  - event_type : String
  - event_summary : String
  - event_metric : Integer
  - event_details : String [large data only to be shown when showing the "detail view"]
- The main page should display sufficient controls and information to:
  - Select a range of dates for which data will be displayed
    - This should be specified as a start date (first day of the period) and the period (week, month, quarter). You are free to define the controls/mechanism by which these are specified. Here are some sample user specifications and the resulting date ranges:
      - "Sunday, Apr 24, 2016", Week -> [2010-04-24, 2010-04-30]
      - "Jan, 2010", Month -> [2010-01-01, 2010-01-31]
      - "Apr, 2010", "Quarter"" -> [2010-04-01, 2010-6-30]
  - Navigate to the previous and next time period
  - Navigate between pages of result data if the number of records for the period exceeds 25 items
  - Show all but the "event_details" fields for each record in the range
  - Allow the user to click on an item to show a "detail view" of the item.
- Data should be fetched from the server in an economical and performant manner, and "large data fields" should only be fetched when displaying the "detail view" for an item.
- The user can add, update, and delete events from the user interface.
- An HTTP-based API is supported at "/api/v1/events" that allows create, read, update, and delete operations. 

Files Listing
============================
The zip file expands to the following file listing:

WebContent/ - JSP files: events.jsp, upload.jsp, index.jsp, diagnostics.jsp
WebContent/META-INF/context.xml - JDBC connection info for the events database
WebContent/WEB-INF/database.sql - SQL script to create the events database
WebContent/WEB-INF/web.xml      - deployment descriptor for webapp
WebContent/scripts/main.js      - client-side code
WebContent/scripts/libs/        - client-side library code
WebContent/styles               - CSS
src/test                        - testing code
src/main                        - webapp server code
pom.xml                         - Maven project



Pre-Requisites
======================
You will need the following software installed and available on your machine:

* JDK 1.7+ - The java compiler and runtime environment.
* Tomcat 8 - The web application server.
* Maven 3.3.9 - The Maven build automation tool.




Step-by-Step Setup Instructions
=================================
1 Make sure you have the required software installed
2 In a command prompt, navigate to the folder where you extracted the eventlist.zip file
  * The root of this folder will contain the pom.xml file
3 Enter: mvn clean install -DskipTests -DskipITs
4 An "eventlist-0.0.1-SNAPSHOT.war" file will be created in a subfolder named "target".
5 Copy the "eventlist-0.0.1-SNAPSHOT.war" to your tomcat installation's webapps folder
6 Rename "eventlist-0.0.1-SNAPSHOT.war" to "eventlist.war" and start tomcat.
  * A new eventsDatabase Derby DB database will be created and initialized if necessary.
7 Populate your database with the Upload Page (http://localhost:8080/eventlist/upload.jsp)
  * This is a synchronous operation and can take some time depending on the input file size.
8 Browse your events with the Events Page (http://localhost:8080/eventlist/events.jsp)


Third-Party Dependencies
=================================
The following third-party tools and libraries were use in this project.

Derby Db - http://db.apache.org/derby/ - for relational data storage
Google GSON - https://github.com/google/gson - for json serialization/deserialization
JodaTime - http://www.joda.org/joda-time/ - for working with DateTimes
jQuery 1.12.3 - https://jquery.com/ - for simplifying JavaScript code
Date.js 1.0 Alpha-1 - http://www.datejs.com/ - for working with Dates in JavaScript
Pikaday 1.4.0 - https://github.com/dbushell/Pikaday - for starting date input
Google Material Design Lite - https://github.com/google/material-design-lite - for design and UX
js-grid 1.5.2 - http://js-grid.com/ js-grid