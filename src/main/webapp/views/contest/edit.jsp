<%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize
	access="hasRole('ADMIN')">
	<form:form	action="administrator/contest/edit.do"	modelAttribute="contest"> 
	<jsp:useBean id="now" class="java.util.Date"/>
	<fmt:formatDate value="${now}" var="fecha" pattern="yyyy-MM-dd"/>
<jstl:choose>
<jstl:when test="${contest.openingTime<fecha and contest.closingTime>fecha }">

		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="qualifies"/>
		
		<form:label path="title">	
			<spring:message code="contest.title"/>
		</form:label>
		<form:input path="title" readonly="true"/>
		<form:errors cssClass="error" path="title"/>
		<br/>
		
		<form:label path="openingTime">	
			<spring:message code="contest.openingTime"/>
		</form:label>
		<form:input path="openingTime" readonly="true"/>
		<form:errors cssClass="error" path="openingTime"/>
		<br/>
		
		<form:label path="closingTime">	
			<spring:message code="contest.closingTime"/>
		</form:label>
		<form:input path="closingTime" var="closingTime"/>
		<form:errors cssClass="error" path="closingTime"/>
		<br/>
		<input type="submit" name="save" value="<spring:message code="contest.save"/>" /> &nbsp;
		<input type="button" name="cancel"
			value="<spring:message code="contest.cancel" />"
			onclick="javascript: window.location.replace('administrator/contest/list.do')" />
		<br />
</jstl:when>
<jstl:otherwise>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="qualifies"/>
		
		<form:label path="title">	
			<spring:message code="contest.title"/>
		</form:label>
		<form:input path="title"/>
		<form:errors cssClass="error" path="title"/>
		<br/>
		
		<form:label path="openingTime">	
			<spring:message code="contest.openingTime"/>
		</form:label>
		<form:input path="openingTime"/>
		<form:errors cssClass="error" path="openingTime"/>
		<br/>
		
		<form:label path="closingTime">	
			<spring:message code="contest.closingTime"/>
		</form:label>
		<form:input path="closingTime"/>
		<form:errors cssClass="error" path="closingTime"/>
		<br/>
		
		<input type="submit" name="save" value="<spring:message code="contest.save"/>" /> &nbsp;
		<jstl:if test="${contest.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="contest.delete"/>"
			onclick="javascript: window.location.replace('administrator/cook/list.do')" /> &nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="contest.cancel" />"
			onclick="javascript: window.location.replace('administrator/contest/list.do')" />
		<br />
</jstl:otherwise>
</jstl:choose>
</form:form>
</security:authorize>