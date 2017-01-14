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

<form:form action="message/actor/display.do" modelAttribute="messageDisplay">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folder" />	
	<form:hidden path="recipient" />
	<form:hidden path="sender" />
	<form:hidden path="spam" />
	<form:hidden path="priority" />


	
	<form:label path="moment"><spring:message code="msg.moment"/></form:label>
	<form:input path="moment" readOnly="true" />
	<br />


	
	<form:label path="subject"><spring:message code="msg.subject"/></form:label>
	<form:input path="subject" readOnly="true" />
	<br />
	
	<form:label path="body"><spring:message code="msg.body"/></form:label>
	<form:textarea path="body" readOnly="true" />
	<br />
	


	

	<security:authentication var="user" property="principal.id" />	
		<jstl:if test="${messageDisplay.recipient.userAccount.id==user}">
	<input type="button" value="<spring:message code="msg.reply.link" />"
	onclick="javascript: location.replace('message/actor/reply.do?messageId=${messageDisplay.id}')" />
		</jstl:if>

	<input type="submit" name="delete" value="<spring:message code="msg.delete.link" />"
	onclick="javascript: return confirm('<spring:message code="msg.confirmDelete" />')" />

		
	<input type="button" value="<spring:message code="msg.return.link" />"
	onclick="javascript: location.replace('message/actor/list.do?folderId=${messageDisplay.folder.id}')" />
	
</form:form>