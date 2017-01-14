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
	name="folder.messages" requestURI="/message/actor/list.do" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="msg.moment" var="moment" />
	<display:column property="moment" title="${moment}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="msg.sender" var="sender" />
	<display:column property="sender.name" title="${sender}"
		sortable="true" />

	<spring:message code="msg.recipient" var="recipient" />
	<display:column property="recipient.name" title="${recipient}"
		sortable="true" />
		
	<spring:message code="msg.subject" var="subject" />
	<display:column property="subject" title="${subject}"
		sortable="false" />

	<spring:message code="msg.folder" var="folder" />
	<display:column property="folder.name" title="${folder}"
		sortable="false" />		
		
	<!-- Action links -->

	<display:column>
		<a href="message/actor/display.do?messageId=${row.id}" ><spring:message code="msg.display.link" /></a>
	</display:column>
		<display:column>
		<a href="message/actor/changefolder.do?messageId=${row.id}" ><spring:message code="msg.changefolder.link" /></a>
	</display:column>
</display:table>