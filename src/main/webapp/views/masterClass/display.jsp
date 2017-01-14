<%--
 * display.jsp
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

<display:table name="masterClass"
	id="row"
	class="displaytag"
	pagesize="5">
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.promoted}">
				<a href="masterClass/promotedemote.do?masterClassId=${row.id}">
					<spring:message code="masterClass.demote" />
				</a>
			</jstl:when>
			<jstl:otherwise>
				<a href="masterClass/promotedemote.do?masterClassId=${row.id}">
					<spring:message code="masterClass.promote" />
				</a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>					
	</security:authorize>
	
	<spring:message code="masterClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true"/>
	
	<spring:message code="masterClass.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false"/>
	
	<spring:message code="masterClass.promoted" var="promotedHeader" />
	<display:column property="promoted" title="${promotedHeader}" sortable="false"/>
	
	<spring:message code="masterClass.registered" var="registeredHeader"/>
	<display:column property="registered" title="${registeredHeader}" sortable="false"/>
	
	<spring:message code="masterClass.learningMaterial"/>
	
	<jstl:forEach var="x" items="${masterClass.registered}">
		<jstl:if test="${x == userAccount}">
			<display:column>
				<a href="learningMaterial/browse.do?masterClassId=${row.id}"><spring:message code="masterClass.learningMaterial.browse" /></a>
			</display:column>	
	</jstl:if>
	</jstl:forEach>
	
	<security:authorize access="hasRole('COOK')">
		<display:column>
			<a href="learningMaterial/createVideo.do?masterClassId=${row.id}"><spring:message code="masterClass.learningMaterial.createVideo" /></a>
			<a href="learningMaterial/createText.do?masterClassId=${row.id}"><spring:message code="masterClass.learningMaterial.createText" /></a>
			<a href="learningMaterial/createPresentation.do?masterClassId=${row.id}"><spring:message code="masterClass.learningMaterial.createPresentation" /></a>
		</display:column>			
	</security:authorize>
	

</display:table>