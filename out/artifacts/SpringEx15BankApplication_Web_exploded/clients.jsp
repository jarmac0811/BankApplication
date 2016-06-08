<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="bank.css">
<title>Clients</title>
</head>
<body>

	<%-- Name: <a href="/client?id=${client.id}"><c:out value="${client.name}"/></a> --%>
	<table width="230" id="results">
		<tr>
			<th>City</th>
			<th width="200">Client name</th>

		</tr>

		<c:forEach var="client" items="${clients}">

			<%--    Name: <c:out value="${client.name}"/>   --%>
			<%--    City: <c:out value="${client.city}"/> --%>


			<tr>
				<td><c:out value="${client.city}" /></td>
				<td><a href="/client?id=${client.id}"><c:out
							value="${client.name}" /></a></td>

			</tr>
			<tr>
		</c:forEach>
	</table>
	<br>

</body>
</html>