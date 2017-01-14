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


	<form:form	action="nutritionist/curricula/edit.do"	modelAttribute="curricula"> 
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="endorsers"/>
		
		<form:label path="title">	
			<spring:message code="curricula.title"/>
		</form:label>
		<form:input path="title"/>
		<form:errors cssClass="error" path="title"/>
		<br/>
		
		<form:label path="photo">	
			<spring:message code="curricula.photo"/>
		</form:label>
		<form:input path="photo"/>
		<form:errors cssClass="error" path="photo"/>
		<br/>
		
		<form:label path="educationSection">	
			<spring:message code="curricula.educationSection"/>
		</form:label>
		<form:textarea path="educationSection"/>
		<form:errors cssClass="error" path="educationSection"/>
		<br/>
		
		<form:label path="experienceSection">	
			<spring:message code="curricula.experienceSection"/>
		</form:label>
		<form:textarea path="experienceSection"/>
		<form:errors cssClass="error" path="experienceSection"/>
		<br/>
		
		<form:label path="hobbySection">	
			<spring:message code="curricula.hobbySection"/>
		</form:label>
		<form:textarea path="hobbySection"/>
		<form:errors cssClass="error" path="hobbySection"/>
		<br/>
		
		<input type="submit" name="save" value="<spring:message code="curricula.save"/>" /> &nbsp;
		<jstl:if test="${curricula.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="curricula.delete"/>" /> &nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="curricula.cancel" />"
			onclick="javascript: window.location.replace('nutritionist/curricula/display.do')" />
		<br />
	</form:form>

</security:authorize>