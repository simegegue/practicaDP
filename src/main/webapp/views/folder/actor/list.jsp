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
	name="folders" requestURI="/folder/actor/listall.do" id="row">
	
	<!-- Action links -->

	
	<!-- Attributes -->

	<spring:message code="folder.name" var="name" />
	<display:column property="name" title="${name}"
		sortable="true" />
		
	<spring:message code="folder.numberMessages" var="numberMessages" />
	<display:column title="${numberMessages}">
		<jstl:out value="${row.messages.size()}"></jstl:out>
	</display:column>

	<display:column>
		<jstl:if test="${row.predefined == false}">
		<a href="folder/actor/edit.do?folderId=${row.id}" ><spring:message code="folder.edit.link" /></a>
		</jstl:if>
	</display:column>
	

	<display:column>
		<a href="message/actor/list.do?folderId=${row.id}" ><spring:message code="folder.messages.link" /></a>
	</display:column>
		
	
</display:table>
<input type="button" value="<spring:message code="folder.create.link" />"
	onclick="javascript: location.replace('folder/actor/create.do')" />