<%--
 * index.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${banner ne 'No banner'}">
	<center>
		<div align="center">
			<img alt="banner" src="${banner }" style="width:730px;height:92px">
		</div>
	</center>
</jstl:if>

<jstl:if test="${not empty promoted }">
	<h3 ><spring:message code = "welcome.master.class.promoted"/><jstl:out value="${promoted.title } "></jstl:out><spring:message code = "welcome.master.class.promoted.b"/></h3>
</jstl:if>




<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 
