<%--
 * list.jsp
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

<!-- Listing grid -->
<security:authorize
	access="hasRole('ADMIN')">
		<display:table pagesize="5" class="displaytag" keepStatus="true" name="contests" requestURI="${requestURI}" id="row">
			
			<!-- Action links -->
			
			<!-- Attributes -->
			
	
			<display:column>
				<a href="administrator/contest/edit.do?contestId=${row.id}"><spring:message code="contest.edit" /></a>
			</display:column>			

			
			<spring:message code="contest.title" var="title" />
			<display:column property="title" title="${title}" sortable="true" />
		
			<spring:message code="contest.openingTime" var="openingTime" />
			<display:column title="${openingTime}" sortable="true"><fmt:formatDate value="${row.openingTime }" pattern="dd/MM/yyyy" /></display:column>
		
			<spring:message code="contest.closingTime" var="closingTime" />
			<display:column title="${closingTime}" sortable="true" ><fmt:formatDate value="${row.closingTime }" pattern="dd/MM/yyyy" /></display:column>
			
		</display:table>
		
</security:authorize>