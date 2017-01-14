<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="user/register.do" modelAttribute="user">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount.authorities" value="USER" />
	<form:hidden path="following" />
	<form:hidden path="followers" />
	<form:hidden path="relationDislikes" />
	<form:hidden path="relationLikes" />
	<form:hidden path="comments" />
	<form:hidden path="recipes" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="sendedMessages" />
	<form:hidden path="receivedMessages" />
	
	<fieldset>
		<legend align="left">
			<spring:message code="user.userAccount" />
		</legend>
		<form:label path="userAccount.username">
			<spring:message code="user.userAccount.username"/>
		</form:label>
		<form:input path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username"/>
		<br/>
				
		<form:label path="userAccount.password">
			<spring:message code="user.userAccount.password"/>
		</form:label>
		<form:password path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password"/>
		<br/>
	</fieldset>

	<fieldset>
		<legend align="left">
			<spring:message code="user.personalInfo" />
		</legend>
		<form:label path="name">
			<spring:message code="user.name"/>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"/>
		<br/>
				
		<form:label path="surname">
			<spring:message code="user.surname"/>
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname"/>
		<br/>
		
		<form:label path="email">
			<spring:message code="user.email"/>
		</form:label>
		<form:input path="email"/>
		<form:errors cssClass="error" path="email"/>
		<br/>
		
		<form:label path="phone">
			<spring:message code="user.phone"/>
		</form:label>
		<form:input path="phone"/>
		<form:errors cssClass="error" path="phone"/>
		<br/>
		
		<form:label path="postalAddress">
			<spring:message code="user.postalAddress"/>
		</form:label>
		<form:input path="postalAddress"/>
		<form:errors cssClass="error" path="postalAddress"/>
		<br/>		
	</fieldset>
		<br/>
		<input type="submit" name="save" value='<spring:message code="user.register"/>'/>
		
		<input type="button" name="cancel" value='<spring:message code="user.cancel"/>' onclick="javascript: window.location.replace('welcome/index.do')"/>
</form:form>