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
	access="hasRole('ADMIN')">
	<form:form	action="administrator/category/edit.do"	modelAttribute="category"> 
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="recipes"/>
		<form:hidden path="subCategories"/>
		<form:hidden path="administrator"/>
		
		<form:label path="superCategory"><spring:message code="category.supercategory"/></form:label>
			<form:select path="superCategory" multiple="false">
				<form:option value="0" label="----" />
				<form:options items="${superCategory}" itemLabel="name" itemValue="id"/>
			</form:select>
		<form:errors cssClass="error" path="superCategory" />
		<br/>
		
		<form:label path="name">	
			<spring:message code="category.name"/>
		</form:label>
		<form:input path="name" readonly="false"/>
		<form:errors cssClass="error" path="name"/>
		<br/>
		
		<form:label path="description">	
			<spring:message code="category.description"/>
		</form:label>
		<form:input path="description" readonly="false"/>
		<form:errors cssClass="error" path="description"/>
		<br/>
		
		<form:label path="picture">	
			<spring:message code="category.picture"/>
		</form:label>
		<form:input path="picture" readonly="false"/>
		<form:errors cssClass="error" path="picture"/>
		<br/>
		
		<form:label path="tags">	
			<spring:message code="category.tags"/>
		</form:label>
		<form:input path="tags" readonly="false"/>
		<form:errors cssClass="error" path="tags"/>
		<br/>
		
		<br/>
		<input type="submit" name="save" value="<spring:message code="category.save"/>" /> &nbsp;
		<jstl:if test="${category.id != 0 && empty category.recipes}">
			<input type="submit" name="delete" value="<spring:message code="category.delete"/>" 
			onclick="javascript: window.location.replace('administrator/category/list.do')"/> &nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="category.cancel" />"
			onclick="javascript: window.location.replace('administrator/category/list.do')" />
		<br />

</form:form>
</security:authorize>