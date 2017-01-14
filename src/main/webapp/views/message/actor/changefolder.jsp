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

<form:form action="message/actor/changefolder.do?messageId=${m.id}" modelAttribute="m">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="moment" />
	<form:hidden path="recipient" />
	<form:hidden path="spam" />
	<form:hidden path="priority" />
		
	<form:label path="folder"><spring:message code="msg.folder" /></form:label>
		<form:select path="folder">
			<form:option label="------" value="0" />
			<form:options items="${folders}" itemLabel="name" itemValue="id" />
		</form:select>
	<form:errors cssClass="error" path="folder" />
	<br />
		
	<form:label path="subject"><spring:message code="msg.subject"/></form:label>
	<form:textarea path="subject" readOnly="true" />
	<form:errors cssClass="error" path="subject" />
	<br />
				
	<form:label path="body"><spring:message code="msg.body"/></form:label>
	<form:textarea path="body" readOnly="true" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="msg.changefolder.link" />" />	
	
	<input type="button" name="cancel" value="<spring:message code="msg.cancel.link" />" 
				onclick="javascript: location.replace('message/actor/list.do?folderId=${m.folder.id}')"/>
	
</form:form>