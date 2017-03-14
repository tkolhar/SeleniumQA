<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Upload JSON Data</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" type="text/css" href="scripts/lib/material.min.css">
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
            <header class="demo-header mdl-layout__header mdl-layout__header--scroll mdl-color--grey-100 mdl-color-text--grey-800">
                <div class="mdl-layout__header-row">
                    <span class="mdl-layout-title">JSON Data Upload Page</span>
                    <div class="mdl-layout-spacer"></div>
                </div>
            </header>
            <main class="mdl-layout__content">
                <div class="mdl-grid">
                    <div class="mdl-cell mdl-cell--2-col mdl-cell--hide-tablet mdl-cell--hide-phone"></div>
                    <div class="mdl-color--white mdl-shadow--4dp content mdl-color-text--grey-800 mdl-cell mdl-cell--8-col">
                        <div style="padding: 20px;">
                        <h3>Upload</h3>
                        <p>
                        <p>Use this form to upload your events in JSON format.</p>
                        <form action="upload" method="post" enctype="multipart/form-data">
                            <input type="text" name="description" />
                            <input type="file" name="file" />
                            <input type="submit" />
                        </form>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>