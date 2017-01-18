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
	access="hasRole('CRITIC')">
	<form:form	action="review/edit.do"	modelAttribute="review"> 
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="critic"/>
		
		<form:label path="recipe"><spring:message code="review.recipe"/></form:label>
			<form:select path="recipe" multiple="false">
				<form:option value="0" label="----" />
				<form:options items="${recipes}" itemLabel="title" itemValue="id"/>
			</form:select>
		<form:errors cssClass="error" path="recipe" />
		<br/>
		
		<form:label path="title">	
			<spring:message code="review.title"/>
		</form:label>
		<form:input path="title" readonly="false"/>
		<form:errors cssClass="error" path="title"/>
		<br/>
		
		<form:label path="text">	
			<spring:message code="review.text"/>
		</form:label>
		<form:input path="text" readonly="false"/>
		<form:errors cssClass="error" path="text"/>
		<br/>
		
		<form:label path="valoration">	
			<spring:message code="review.valoration"/>
		</form:label>
		<form:input path="valoration" readonly="false"/>
		<form:errors cssClass="error" path="valoration"/>
		<br/>
		
		<form:label path="authoredMoment">	
			<spring:message code="review.authoredMoment"/>
		</form:label>
		<form:input path="authoredMoment" readonly="true"/>
		<form:errors cssClass="error" path="authoredMoment"/>
		<br/>
		
		<br/>
		<input type="submit" name="save" value="<spring:message code="review.save"/>" /> &nbsp;
		<jstl:if test="${review.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="review.delete"/>" 
			onclick="javascript: window.location.replace('review/list.do')"/> &nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="category.cancel" />"
			onclick="javascript: window.location.replace('review/list.do')" />
		<br />

</form:form>
</security:authorize>