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
	access="hasRole('SPONSOR')">


	<form:form	action="sponsor/edit.do"	modelAttribute="sponsor"> 
		<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="sendedMessages" />
	<form:hidden path="receivedMessages" />
	<form:hidden path="campaigns"/>
	<form:hidden path="monthlyBills"/>
	<form:hidden path="lastTimeManages" />

	<fieldset>
		<legend align="left">
			<spring:message code="sponsor.personalInfo" />
		</legend>
		<form:label path="name">
			<spring:message code="sponsor.name"/>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"/>
		<br/>
				
		<form:label path="surname">
			<spring:message code="sponsor.surname"/>
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname"/>
		<br/>
		
		<form:label path="email">
			<spring:message code="sponsor.email"/>
		</form:label>
		<form:input path="email"/>
		<form:errors cssClass="error" path="email"/>
		<br/>
		
		<form:label path="phone">
			<spring:message code="sponsor.phone"/>
		</form:label>
		<form:input path="phone"/>
		<form:errors cssClass="error" path="phone"/>
		<br/>
		
		<form:label path="postalAddress">
			<spring:message code="sponsor.postalAddress"/>
		</form:label>
		<form:input path="postalAddress"/>
		<form:errors cssClass="error" path="postalAddress"/>
		<br/>	
		
		<form:label path="companyName">
			<spring:message code="sponsor.companyName"/>
		</form:label>
		<form:input path="companyName"/>
		<form:errors cssClass="error" path="companyName"/>
		<br/>	
	</fieldset>
	
	<fieldset>
		<legend align="left">
			<spring:message code="sponsor.creditCard" />
		</legend>
		<form:label path="creditCard.holderName">
			<spring:message code="sponsor.creditCard.holderName"/>
		</form:label>
		<form:input path="creditCard.holderName"/>
		<form:errors cssClass="error" path="creditCard.holderName"/>
		<br/>
		
		<form:label path="creditCard.brandName">
			<spring:message code="sponsor.creditCard.brandName"/>
		</form:label>
		<form:input path="creditCard.brandName"/>
		<form:errors cssClass="error" path="creditCard.brandName"/>
		<br/>
		
		<form:label path="creditCard.number">
			<spring:message code="sponsor.creditCard.number"/>
		</form:label>
		<form:input path="creditCard.number"/>
		<form:errors cssClass="error" path="creditCard.number"/>
		<br/>
		
		<form:label path="creditCard.expirationMonth">
			<spring:message code="sponsor.creditCard.expirationMonth"/>
		</form:label>
		<form:input path="creditCard.expirationMonth"/>
		<form:errors cssClass="error" path="creditCard.expirationMonth"/>
		<br/>
		
		<form:label path="creditCard.expirationYear">
			<spring:message code="sponsor.creditCard.expirationYear"/>
		</form:label>
		<form:input path="creditCard.expirationYear"/>
		<form:errors cssClass="error" path="creditCard.expirationYear"/>
		<br/>
		
		<form:label path="creditCard.cvv">
			<spring:message code="sponsor.creditCard.cvv"/>
		</form:label>
		<form:input path="creditCard.cvv"/>
		<form:errors cssClass="error" path="creditCard.cvv"/>
		<br/>
	</fieldset>
	
		
		<input type="submit" name="save" value="<spring:message code="sponsor.save"/>" /> &nbsp;
		
		<input type="button" name="cancel"
			value="<spring:message code="sponsor.cancel" />"
			onclick="javascript: window.location.replace('welcome/index.do')" />
		<br />
	</form:form>

</security:authorize>