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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true" name="contests" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<spring:message code="contest.title" var="title" />
	<display:column property="title" title="${title}" sortable="true" />

	<spring:message code="contest.openingTime" var="openingTime" />
	<display:column property="openingTime" title="${openingTime}" sortable="true"><fmt:formatDate value="${contest.openingTime }" pattern="dd/MM/yyyy" /></display:column>

	<spring:message code="contest.closingTime" var="closingTime" />
	<display:column property="closingTime" title="${closingTime}" sortable="true" ><fmt:formatDate value="${contest.closingTime }" pattern="dd/MM/yyyy" /></display:column>

	<spring:message code="contest.recipes" />
	<display:column>
		<a href="contest/listrecipes.do?contestId=${row.id}"><spring:message code="contest.recipe"/></a>
	</display:column>
	
		<spring:message code="contest.winners" />
		
		<jstl:if test="${row.qualifies[1].position!=null and row.qualifies[1].position!=0}">
			<display:column>
				<a href="contest/listwinners.do?contestId=${row.id}"><spring:message code="contest.winners"/></a>
			</display:column>
		</jstl:if>
		
<security:authorize access="hasRole('ADMIN')">
	<jsp:useBean id="now" class="java.util.Date"/>
	<fmt:formatDate value="${now}" var="fecha" pattern="yyyy-MM-dd"/>
		<spring:message code="contest.winners" />
		<jstl:if test="${row.closingTime < fecha and (row.qualifies[1].position==null or row.qualifies[1].position==0)}">
			<display:column>
				<a href="contest/selectWinners.do?contestId=${row.id}"><spring:message code="contest.selectWinners"/></a>
			</display:column>
		</jstl:if>
</security:authorize>
</display:table>