<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="actor/register.do" modelAttribute="actor">
	<form:hidden path="id"/>
	<fieldset>
		<legend align="left">
			<spring:message code="actor.userAccount" />
		</legend>
		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username"/>
		</form:label>
		<form:input path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username"/>
		<br/>
				
		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password"/>
		</form:label>
		<form:input path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password"/>
		<br/>
		
		<form:label path="userAccount.authorities">
			<spring:message code="actor.userAccount.authorities"/>
		</form:label>
		<form:input path="userAccount.authorities"/>
		<form:errors cssClass="error" path="userAccount.authorities"/>
		<br/>
	</fieldset>

	<fieldset>
		<legend align="left">
			<spring:message code="actor.personalInfo" />
		</legend>
		<form:label path="name">
			<spring:message code="actor.name"/>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"/>
		<br/>
				
		<form:label path="surname">
			<spring:message code="actor.surname"/>
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname"/>
		<br/>
		
		<form:label path="email">
			<spring:message code="actor.email"/>
		</form:label>
		<form:input path="email"/>
		<form:errors cssClass="error" path="email"/>
		<br/>
		
		<form:label path="phone">
			<spring:message code="actor.phone"/>
		</form:label>
		<form:input path="phone"/>
		<form:errors cssClass="error" path="phone"/>
		<br/>		
	</fieldset>
		<br/>
		<input type="submit" name="save" value='<spring:message code="actor.register"/>'/>
		
		<input type="button" name="cancel" value='<spring:message code="actor.cancel"/>' onclick="javascript: window.locatin.replace('')"/>
</form:form>