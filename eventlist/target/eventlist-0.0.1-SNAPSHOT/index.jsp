<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!doctype html>
<!--
  Material Design Lite
  Copyright 2015 Google Inc. All rights reserved.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License
-->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
        <title>Edjuster Assignment</title>

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" type="text/css" href="scripts/lib/material.min.css">
        <style>

            .demo-ribbon {
                width: 100%;
                height: 40vh;
                background-color: #3F51B5;
                flex-shrink: 0;
            }


            .demo-header .mdl-layout__header-row {
                padding-left: 40px;
            }

            .demo-container {
                max-width: 1600px;
                width: calc(100% - 16px);
                margin: 0 auto;
            }

            .demo-content {
                border-radius: 2px;
                padding: 80px 56px;
                margin-bottom: 80px;
            }

            .demo-layout.is-small-screen .demo-content {
                padding: 40px 28px;
            }

            .demo-content h3 {
                margin-top: 48px;
            }

            .demo-footer {
                padding-left: 40px;
            }

            .demo-footer .mdl-mini-footer--link-list a {
                font-size: 13px;
            }
        </style>
    </head>
	<body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="demo-layout mdl-layout mdl-layout--fixed-header mdl-js-layout mdl-color--grey-100">
			<header class="mdl-layout__header">
				<div class="mdl-layout__header-row">
					<!-- Title -->
					<span class="mdl-layout-title">Events App</span>
					<!-- Add spacer, to align navigation to the right -->
					<div class="mdl-layout-spacer"></div>
					<!-- Navigation. We hide it in small screens. -->
					<nav class="mdl-navigation">
						<a class="mdl-navigation__link" href="index.jsp">Intro</a>
						<a class="mdl-navigation__link" href="events.jsp">Events</a>
						<a class="mdl-navigation__link" href="upload.jsp">Upload</a>
						<a class="mdl-navigation__link" href="diagnostics.jsp">Diagnostics</a>
					</nav>
				</div>
			</header>
            <main class="mdl-layout__content">
                <div class="demo-container mdl-grid">
                    <div class="mdl-cell mdl-cell--2-col mdl-cell--hide-tablet mdl-cell--hide-phone"></div>
                    <div class="demo-content mdl-color--white mdl-shadow--4dp content mdl-color-text--grey-800 mdl-cell mdl-cell--8-col">
                        <div class="demo-crumbs mdl-color-text--grey-500">
                            Documentation
                        </div>
                        <div>
                            <h3>Project Scope</h3>
                            <p>The provided web app is a small single-page app that displays a list of paged event data with a record detail view. The goal of this assignment is to develop an automated test harness to validate the correctness of this app. </p>
                            
                            <h3>Project Requirements</h3>
                            <p>The web app requirements supports the following requirements:</p>
                            <ul>
                                <li>The web application should provide a single web page displaying a clean and user-friendly user interface that displays a time-scoped range of event data as specified by the user.</li>
                                <li>The web page should load quickly so as to provide a good user experience (< 1 second) and when/where necessary, display secondary loading indicators when loading data from the server.</li>
                                <li>The user should not be prevented from adjusting input control values to display different data even while previous data is still loading.
                                <li>The fictitious event data structure to be displayed is as follows:
                                    <ul>
                                        <li>event_date : Date String (YYYY-MM-DD)</li>
                                        <li>event_type : String</li>
                                        <li>event_summary : String</li>
                                        <li>event_metric : Integer</li>
                                        <li>event_details : String   [large data only to be shown when showing the "detail view"]</li>
                                    </ul>
                                </li>
                                <li>The main page should display sufficient controls and information to:
                                    <ul>
                                        <li>Select a range of dates for which data will be displayed
                                            <ul>
                                                <li>This should be specified as a start date (first day of the period) and the period (week, month, quarter). You are free to define the controls/mechanism by which these are specified. Here are some sample user specifications and the resulting date ranges:
                                                    <ul>
                                                        <li>"Sunday,  Apr 24, 2016", Week  -> [2010-04-24, 2010-04-30]</li>
                                                        <li>"Jan, 2010", Month  -> [2010-01-01, 2010-01-31]</li>
                                                        <li>"Apr, 2010", "Quarter"" -> [2010-04-01, 2010-6-30]</li>
                                                    </ul>
                                                </li>
                                            </ul> 
                                        </li>
                                        <li>Navigate to the previous and next time period</li>
                                        <li>Navigate between pages of result data if the number of records for the period exceeds 25 items</li>
                                        <li>Show all but the "event_details" fields for each record in the range</li>
                                        <li>Allow the user to click on an item to show a "detail view" of the item.</li>
                                    </ul>
                                    <li>Data should be fetched from the server in an economical and performant manner, and "large data fields" should only be fetched when displaying the "detail view" for an item.</li>
                                    <li>The user can add, update, and delete events from the user interface.</li>
                                    <li>An HTTP-based API is supported at "/api/v1/events" that allows create, read, update, and delete operations.</li>
                                </ul>
                        </div>
                        <h3>Quick Start</h3>
                        <p>
                            You will need the following software installed and available on your machine. 
                        </p>
                        <b>Required Software</b>
                        <ul>
                            <li>JDK 1.7+ - The java compiler and runtime environment.</li>
                            <li>Tomcat 8 - The web application server.</li>
                            <li>Maven 3.3.9 - The Maven build automation tool. </li>
                        </ul>

                        <h3>Step-by-Step Setup Instructions</h3>
                        <ol>
                            <li>Make sure you have the required software installed</li>
                            <li>In a command prompt, navigate to the folder of the EventList project containing the <i>pom.xml</i> file</li>
                            <li>Enter: mvn clean install -DskipTests -DskipITs</li>
                            <li>In the <i>target</li> subfolder you will find a <i>eventlist-0.0.1-SNAPSHOT.war</i> file.
                            <li>Copy the <i>eventlist-0.0.1-SNAPSHOT.war</i> to your tomcat installation's <i>webapps</i> folder</li>
                            <li>Rename <i>eventlist-0.0.1-SNAPSHOT.war</i> to <i>eventlist.war</i> and start tomcat.
                                <p>A new <i>eventsDatabase</i> Derby DB database will be created and initialized if necessary.</p>
                            </li>
                            <li>Populate your database with the <a href="/eventlist/upload.jsp">Upload Page</a> (See <i>Test Data</i> section below)</li>
                            <li>Browse your events with the <a href="/eventlist/events.jsp">Events Page</a></li>
                        </ol>
                        <h3>Test Data</h3>
                        <p>Sample JSON encoded event data files to use (ZIP files hosted on Google Drive -- no login required):
                            <ul>
                        <li>Small text sample (50 records; ~700 KB zipped; ~2.5 MB extracted): <a href="http://tinyurl.com/zz8rjt6">http://tinyurl.com/zz8rjt6</a></li>
                        <li>Larger data set that will be used to evaluate the application (5000 records; ~69MB zipped; ~255 MB extracted): <a href="http://tinyurl.com/grefnxu">http://tinyurl.com/grefnxu</a></li>
                        </ul>
                        </p>
                        <h3>Testing</h3>
                        <ul>
                            <li>Todo: Set up an automated testing framework</li>
                        </ul>

                        <h3>Third-Party Dependencies</h3>
                        <p>The following third-party tools and libraries were use in this project.</p>

                        <ul>
                            <li>Derby Db - http://db.apache.org/derby/ - for relational data storage</li>
                            <li>Google GSON - https://github.com/google/gson - for json serialization/deserialization</li>
                            <li>JodaTime - http://www.joda.org/joda-time/ - for working with DateTimes </li>
                            <li>jQuery 1.12.3 - https://jquery.com/ - for simplifying JavaScript code</li>
                            <li>Date.js 1.0 Alpha-1 - http://www.datejs.com/ - for working with Dates in JavaScript</li>
                            <li>js-grid.js 1.5.2 - http://js-grid.com - for displaying and editing tabular data</li>
                            <li>Pikaday 1.4.0 - https://github.com/dbushell/Pikaday - for starting date input</li>
                            <li>Google Material Design Lite - https://github.com/google/material-design-lite - for design and UX</li>
                        </ul>
                    </div>
                </div>
            </main>
        </div>
        <script src="scripts/lib/material.min.js"></script>
    </body>
</html>
