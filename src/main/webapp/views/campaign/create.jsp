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

<security:authorize access="hasRole('SPONSOR')">

<form:form	action="campaign/sponsor/create.do"	modelAttribute="campaign"> 
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="sponsor"/>
		<form:hidden path="starred"/>
		<form:hidden path="banners"/>
		
		<form:label path="startDate">	
			<spring:message code="campaign.startDate"/>
		</form:label>
		<form:input path="startDate"/>
		<form:errors cssClass="error" path="startDate"/>
		<br/>
		
		<form:label path="endDate">	
			<spring:message code="campaign.endDate"/>
		</form:label>
		<form:input path="endDate"/>
		<form:errors cssClass="error" path="endDate"/>
		<br/>
			
		<input type="submit" name="save" value="<spring:message code="campaign.save"/>" /> &nbsp;
		
		<input type="button" name="cancel"
			value="<spring:message code="campaign.cancel" />"
			onclick="javascript: relativeRedir('campaign/sponsor/list.do')" />
		<br />
	</form:form>

</security:authorize>