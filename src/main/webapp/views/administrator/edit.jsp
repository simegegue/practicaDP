<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">
	<form:form action="administrator/edit.do" modelAttribute="administrator">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="userAccount" />
		<form:hidden path="socialIdentities" />
		<form:hidden path="sendedMessages" />
		<form:hidden path="receivedMessages" />
		
		<fieldset>
			<legend align="left">
				<spring:message code="administrator.personalInfo" />
			</legend>
			<form:label path="name">
				<spring:message code="administrator.name"/>
			</form:label>
			<form:input path="name"/>
			<form:errors cssClass="error" path="name"/>
			<br/>
					
			<form:label path="surname">
				<spring:message code="administrator.surname"/>
			</form:label>
			<form:input path="surname"/>
			<form:errors cssClass="error" path="surname"/>
			<br/>
			
			<form:label path="email">
				<spring:message code="administrator.email"/>
			</form:label>
			<form:input path="email"/>
			<form:errors cssClass="error" path="email"/>
			<br/>
			
			<form:label path="phone">
				<spring:message code="administrator.phone"/>
			</form:label>
			<form:input path="phone"/>
			<form:errors cssClass="error" path="phone"/>
			<br/>		
			
			<form:label path="postalAddress">
			<spring:message code="administrator.postalAddress"/>
			</form:label>
			<form:input path="postalAddress"/>
			<form:errors cssClass="error" path="postalAddress"/>
		<br/>
		
		</fieldset>
			<br/>
			<input type="submit" name="save" value='<spring:message code="administrator.save"/>'/>
			
			<input type="button" name="cancel" value='<spring:message code="administrator.cancel"/>' onclick="javascript: window.location.replace('welcome/index.do')"/>
	</form:form>
</security:authorize>