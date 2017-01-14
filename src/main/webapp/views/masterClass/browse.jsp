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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="masterClasses" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<security:authorize access="hasRole('COOK')">
		<display:column>
			<a href="cook/masterClass/edit.do?masterClassId=${row.id}"><spring:message code="masterClass.edit" /></a>
		</display:column>			
	</security:authorize>
	
	<spring:message code="masterClass.cook.userAccount.userName" var="cookHeader" />
	<display:column property="cook.userAccount.username" title="${cookHeader}" sortable="true" />

	<spring:message code="masterClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="masterClass.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<security:authorize access="hasRole('COOK')">
		<display:column>
			<a href="learningMaterial/browse.do?masterClassId=${row.id}"><spring:message code="masterClass.learningMaterial.browse" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
		<jstl:choose>
			<jstl:when test="${!fn:contains(row.registered,pageContext.request.remoteUser)}">
				<display:column>
					<a href="masterClass/registerMC.do?masterClassId=${row.id}"><spring:message code="masterClass.register"/></a>
				</display:column>
			</jstl:when>
			<jstl:otherwise>
				<display:column>
					<a href="masterClass/unregisterMC.do?masterClassId=${row.id}"><spring:message code="masterClass.unregister"/></a>
				</display:column>
			</jstl:otherwise>
		</jstl:choose>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
		<display:column>
			<a href="masterClass/display.do?masterClassId=${row.id}"><spring:message code="masterClass.display" /></a>
		</display:column>
	</security:authorize>
</display:table>