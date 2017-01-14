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

	<form:form	action="campaign/sponsor/banner/create.do"	modelAttribute="banner"> 
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="displayed" />
		<form:hidden path="campaign" />
		
		<form:label path="image">	
			<spring:message code="banner.image"/>
		</form:label>
		<form:input path="image"/>
		<form:errors cssClass="error" path="image"/>
		<br/>
		
		<form:label path="maxDisplay">	
			<spring:message code="banner.maxDisplay"/>
		</form:label>
		<form:input path="maxDisplay"/>
		<form:errors cssClass="error" path="maxDisplay"/>
		<br/>
			
		<input type="submit" name="save" value="<spring:message code="banner.save"/>" /> &nbsp;
		
		<input type="button" name="cancel"
			value="<spring:message code="banner.cancel" />"
			onclick="javascript: relativeRedir('campaign/banner/list.do=${campaign.id}')" />
		<br />
	</form:form>
	

</security:authorize>