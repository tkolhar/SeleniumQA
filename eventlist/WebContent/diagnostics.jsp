
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
		<title>Diagnostics Page</title>
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
                    <span class="mdl-layout-title">Diagnostics Page</span>
                    <div class="mdl-layout-spacer"></div>
                </div>
            </header>
            <main class="mdl-layout__content">
				<table>
					<%@ page import="java.util.*" %>
					<%@ page import="java.sql.*" %>
					<%@ page import="javax.naming.*"%>
					<%@page import="javax.sql.DataSource"%>
					<%

						java.sql.Statement s;
						java.sql.ResultSet rs;
						java.sql.PreparedStatement pst;

						s = null;
						pst = null;
						rs = null;
						Context initContext = new InitialContext();
						Context envContext = (Context) initContext.lookup("java:comp/env");
						DataSource ds = (DataSource) envContext.lookup("jdbc/EventsDB");
						Connection con = ds.getConnection();

						String sql = "select * from events";// fetch first 10 rows only";
						try {
							s = con.createStatement();
							rs = s.executeQuery(sql);
					%>

					<%
						while (rs.next()) {
					%><tr>
						<td><%= rs.getInt("id")%></td>
						<td><%= rs.getDate("event_date")%></td>
						<td><%= rs.getString("event_type")%></td>
						<td><%= rs.getString("event_summary")%></td>
						<td><%= rs.getInt("event_size")%></td>
					</tr>
					<%
						}
					%>

					<%
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (rs != null) {
								rs.close();
							}
							if (s != null) {
								s.close();
							}
							if (con != null) {
								con.close();
							}
						}

					%>
			</main>
        </div>
    </body>
</html>