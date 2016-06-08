<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="form" action="saveClient" method="POST">
	<input type="hidden" name="id" value="${client.id}"> Name: <input
		type="text" name="name" value="${client.name}" /><br> City: <input
		type="text" name="city" value="${client.city}" /><br> Gender: <input
		type="radio" name="gender" ${client.gender=="MALE"?"checked":""} />Male
	<input type="radio" name="gender"
		${client.gender=="FEMALE"?"checked":""} />Female<br> Email: <input
		type="text" name="email" value="${client.email}" /><br> Balance:
	<input type="text" name="balance" value="${client.balance}" /><br>
	<input type="submit" />
</form>