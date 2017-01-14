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
	access="hasRole('NUTRITIONIST')">


	<form:form	action="nutritionist/endorser/edit.do"	modelAttribute="endorser"> 
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="curricula"/>
		
		<form:label path="name">	
			<spring:message code="endorser.name"/>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"/>
		<br/>
		
		<form:label path="homePage">	
			<spring:message code="endorser.homePage"/>
		</form:label>
		<form:textarea path="homePage"/>
		<form:errors cssClass="error" path="homePage"/>
		<br/>
		
		<form:label path="curricula"><spring:message code="endorser.curricula"/></form:label>
			<form:select path="curricula" multiple="false">
				<form:options items="${curriculas}" itemLabel="title" itemValue="id"/>
			</form:select>
		<form:errors cssClass="error" path="curricula" />
		<br/>
		
		<input type="submit" name="save" value="<spring:message code="endorser.save"/>" /> &nbsp;
		<jstl:if test="${endorser.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="endorser.delete"/>" /> &nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="endorser.cancel" />"
			onclick="javascript: window.location.replace('nutritionist/curricula/display.do')" />
		<br />
	</form:form>

</security:authorize>