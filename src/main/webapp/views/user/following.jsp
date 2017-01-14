<%--
 * browse.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="users" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true"/>
	
	<spring:message code="user.surname" var="surnameHeader"/>
	<display:column property="surname" title="${surnameHeader}" sortable="false"/>
	
	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${email}" sortable="false"/>
	
	<spring:message code="user.phone" var="phoneHeader" />
	<display:column property="phone" title="${phone}" sortable="false"/>
	
	<spring:message code="user.postalAddress" var="postalAddressHeader"/>
	<display:column property="postalAddress" title="${postalAddress}" sortable="false"/>
	
	<display:column>
		<a href="user/displayProfile.do?userId=${row.id }"><spring:message code="user.viewProfile" /></a>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
		<a href="user/userUnfollow.do?userId=${row.id }"><spring:message code="user.unfollow" /></a>
		</display:column>			
	</security:authorize>
	
	<security:authorize access="hasRole('NUTRITIONIST')">
		<display:column>
		<a href="user/nutritionistUnfollow.do?userId=${row.id }"><spring:message code="user.unfollow" /></a>
		</display:column>			
	</security:authorize>
	

	
</display:table>