<%--
 * edit.jsp
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

<security:authorize
	access="hasRole('USER')">


	<form:form	action="user/edit.do"	modelAttribute="user"> 
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="userAccount"/>
		<form:hidden path="following" />
		<form:hidden path="followers" />
		<form:hidden path="relationDislikes" />
		<form:hidden path="relationLikes" />
		<form:hidden path="comments" />
		<form:hidden path="recipes" />
		<form:hidden path="socialIdentities" />
		<form:hidden path="sendedMessages" />
		<form:hidden path="receivedMessages" />

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
		
		<input type="submit" name="save" value="<spring:message code="user.save"/>" /> &nbsp;
		
		<input type="button" name="cancel"
			value="<spring:message code="user.cancel" />"
			onclick="javascript: window.location.replace('user/browse.do')" />
		<br />
	</form:form>

</security:authorize>