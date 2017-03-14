<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Events</title>

        <!-- Color the status bar on mobile devices -->
        <meta name="theme-color" content="#2F3BA2">

        <link rel="stylesheet" type="text/css" href="scripts/lib/material.min.css">

        <!-- Material Design icons -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

        <!-- Your styles -->
        <link rel="stylesheet" href="scripts/lib/pikaday.css">
        <link rel="stylesheet" type="text/css" href="scripts/lib/dialog-polyfill.css" />
        <link rel="stylesheet" href="styles/main.css"> 

        <script type="text/javascript" src="scripts/lib/jquery-1.12.3.min.js"></script>
        <link type="text/css" rel="stylesheet" href="scripts/lib/jsgrid.min.css" />
        <link type="text/css" rel="stylesheet" href="scripts/lib/jsgrid-theme.min.css" />


    </head>
    <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
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
			<header class="demo-header mdl-layout__header mdl-layout__header--scroll mdl-color--grey-100 mdl-color-text--grey-800">
                <div class="mdl-layout__header-row">
                    <span class="mdl-layout-title">Events Manager Page</span>
                    <div class="mdl-layout-spacer"></div>
                </div>
            </header>
            <main class="mdl-layout__content">
                <div class="mdl-grid" style="max-width: 980px;">
                    <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet">
                        <form action="#">
                            <div class="mdl-compact mdl-textfield mdl-js-textfield ">
                                <input class="mdl-textfield__input" type="text" id="datepicker"/>
                            </div>
                        </form>
                    </div>
                    <div class="mdl-cell mdl-cell--6-col mdl-cell--2-col-tablet">
                        <button id="prev-date" class=".mdl-compact mdl-button mdl-js-button mdl-button--icon"><i class="material-icons">&#xE5CB;</i></button>
                        <button id="next-date" class=".mdl-compact mdl-button mdl-js-button mdl-button--icon"><i class="material-icons">&#xE5CC;</i></button>  <span id="startDate"></span> to <span id="endDate"></span>
                    </div>
                    <div id="period-type-group" class="mdl-cell mdl-cell--3-col mdl-cell--4-col-tablet">
                        <label class=".mdl-compact mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-1">
                            <input type="radio" id="option-1" class="mdl-radio__button" name="period-type" value="week" checked>
                            <span class="mdl-radio__label">Week</span>
                        </label>
                        <label class=".mdl-compact mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-2">
                            <input type="radio" id="option-2" class="mdl-radio__button" name="period-type" value="month">
                            <span class="mdl-radio__label">Month</span>
                        </label>
                        <label class=".mdl-compact mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-3">
                            <input type="radio" id="option-3" class="mdl-radio__button" name="period-type" value="quarter">
                            <span class="mdl-radio__label">Quarter</span>
                        </label>
                    </div>
                </div>

                <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">
                    <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
                        <div id="jsGrid"></div>
                    </div>
                </section>
            </main>
        </div>
        <dialog class="mdl-dialog" id="dialog">
            <div class="mdl-dialog__content">
                <div>
                    <h5>Event Summary</h5>
                    <form action="#">
                        <input type="hidden" id="dialog-event-id"/>
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" style="display: block">
                            <input class="mdl-textfield__input" type="text" id="dialog-event-date">
                            <label class="mdl-textfield__label" for="dialog-event-date">Date (yyyy-MM-dd)...</label>
                        </div>  
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" style="display: block">
                            <input class="mdl-textfield__input" type="text" id="dialog-event-type">
                            <label class="mdl-textfield__label" for="dialog-event-type">Type...</label>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" style="display: block">
                            <input class="mdl-textfield__input" type="text" id="dialog-event-summary">
                            <label class="mdl-textfield__label" for="dialog-event-summary">Summary...</label>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" style="display: block">
                            <input class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="dialog-event-metric">
                            <label class="mdl-textfield__label" for="dialog-event-metric">Metric...</label>
                            <span class="mdl-textfield__error">Input is not a number!</span>
                        </div>
                        <h5>Event Details</h5>
                        <textarea id="modal-content"  rows="20" style="width:100%"></textarea>
                    </form>
                </div>

            </div>
            <div class="mdl-dialog__actions mdl-dialog__actions--full-width">
                <button id="btnSave" type="button" class="mdl-button">Save</button>
            </div>
            <div class="mdl-dialog__actions mdl-dialog__actions--full-width">
                <button id="btnClose" type="button" class="mdl-button close">Close</button>
            </div>
        </dialog>


        <!-- build:js(app/) ../../scripts/main.min.js -->
		<script src="scripts/lib/material.min.js"></script>
        <script src="scripts/lib/jquery-1.12.3.min.js"></script>
        <script type="text/javascript" src="scripts/lib/jsgrid.min.js"></script>    
        <script src="scripts/lib/pikaday.js"></script>
        <script src="scripts/lib/dialog-polyfill.js"></script>
        <script src="scripts/lib/date.js"></script>
        <script src="scripts/main.js"></script>
        <!-- endbuild -->
		<div aria-live="assertive" aria-atomic="true" aria-relevant="text" class="mdl-snackbar mdl-js-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
    </body>
</html>
