<%--
 * browse.jsp
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

<jstl:if test="${not empty videos}">
	
	<display:table name="videos"
		id="row"
		class="displaytag"
		pagesize="5">
		
		<security:authorize access="hasRole('COOK')">
			<display:column>
				<a href="learningMaterial/editVideo.do?videoId=${row.id}"><spring:message code="learningMaterial.editVideo" /></a>
			</display:column>			
		</security:authorize>
		
		<spring:message code="learningMaterial.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true"/>
		
		<spring:message code="learningMaterial.abstrac" var="abstracHeader" />
		<display:column property="abstrac" title="${abstracHeader}" sortable="false"/>
		
		<spring:message code="learningMaterial.attachment" var="attachmentHeader" />
		<display:column property="attachment" title="${attachmentHeader}" sortable="false"/>
		
		<spring:message code="learningMaterial.video.identifier" var="identifierHeader" />
		<display:column property="identifier" title="${identifierHeader}" sortable="false"/>
		
	</display:table>
</jstl:if>
<jstl:if test="${not empty texts}">
	<display:table name="texts"
		id="row"
		class="displaytag"
		pagesize="5">
		
		<security:authorize access="hasRole('COOK')">
			<display:column>
				<a href="learningMaterial/editText.do?textId=${row.id}"><spring:message code="learningMaterial.editText" /></a>
			</display:column>			
		</security:authorize>
		
		<spring:message code="learningMaterial.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true"/>
		
		<spring:message code="learningMaterial.abstrac" var="abstracHeader" />
		<display:column property="abstrac" title="${abstracHeader}" sortable="false"/>
		
		<spring:message code="learningMaterial.attachment" var="attachmentHeader" />
		<display:column property="attachment" title="${attachmentHeader}" sortable="false"/>
		
		<spring:message code="learningMaterial.text.body" var="bodyHeader" />
		<display:column property="body" title="${bodyHeader}" sortable="false"/>
		
	</display:table>
</jstl:if>	
<jstl:if test="${not empty presentations}">
	<display:table name="presentations"
		id="row"
		class="displaytag"
		pagesize="5">
	
		<security:authorize access="hasRole('COOK')">
			<display:column>
				<a href="learningMaterial/editPresentation.do?presentationId=${row.id}"><spring:message code="learningMaterial.editPresentation" /></a>
			</display:column>			
		</security:authorize>
	
		<spring:message code="learningMaterial.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true"/>
		
		<spring:message code="learningMaterial.abstrac" var="abstracHeader" />
		<display:column property="abstrac" title="${abstracHeader}" sortable="false"/>
		
		<spring:message code="learningMaterial.attachment" var="attachmentHeader" />
		<display:column property="attachment" title="${attachmentHeader}" sortable="false"/>
		
		<spring:message code="learningMaterial.presentation.path" var="pathHeader" />
		<display:column property="path" title="${pathHeader}" sortable="false"/>
		
	</display:table>
</jstl:if>