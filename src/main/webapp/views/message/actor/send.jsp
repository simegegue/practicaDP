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

<form:form action="message/actor/send.do" modelAttribute="m">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="folder" />
	<form:hidden path="moment" />
	
	<jstl:if test="${m.recipient == null}">
	
	<form:label path="recipient"><spring:message code="msg.recipient"/></form:label>
		<form:select path="recipient" >
			<form:option label="------" value="0"/>
			<form:options items="${actors}" itemLabel="name" itemValue="id"/>
		</form:select>
	<form:errors cssClass="error" path="recipient" />
		
	<br />
	
	</jstl:if>
	
	<jstl:if test="${m.recipient != null}">
		<form:hidden path="recipient" />
	</jstl:if>
	
	<form:label path="subject"><spring:message code="msg.subject"/></form:label>
	<form:textarea path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
				
	<form:label path="body"><spring:message code="msg.body"/></form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="priority"><spring:message code="msg.priority"/></form:label>
		<form:select path="priority" >
			<form:option label="------" value="0"/>
			<form:option label="LOW" value="LOW"/>
			<form:option label="NEUTRAL" value="NEUTRAL" />
			<form:option label="HIGH" value="HIGH" />
		</form:select>
	<form:errors cssClass="error" path="priority" />
				
					
	<input type="submit" name="save" value="<spring:message code="msg.send.link" />" />
			
	<input type="button" name="cancel" value="<spring:message code="msg.cancel.link" />" 
				onclick="javascript: location.replace('welcome/index.do')"/>
	
</form:form>