<%--
 * list.jsp
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

<!-- Listing grid -->
<security:authorize
	access="hasRole('ADMIN')">
		<display:table pagesize="5" class="displaytag" keepStatus="true" name="categories" requestURI="${requestURI}" id="row">
			
			<!-- Action links -->
			
			<!-- Attributes -->
			<spring:message code="category.picture" var="picture" />
			<display:column><img src="${row.picture}" width="60" height="60" ></display:column>
			
			<spring:message code="category.name" var="name" />
			<display:column property="name" title="${name}" sortable="true" />
		
			<spring:message code="category.description" var="description" />
			<display:column property="description" title="${description}" sortable="false" />
		
			
		
			<spring:message code="category.tags" var="tags" />
			<display:column property="tags" title="${tags}" sortable="false" />
			<spring:message code="category.supercategory" var="superCategory" />
			<display:column property="superCategory.name" title="${superCategory}" sortable="false"/>
			
			<spring:message code="category.subCategories" var="subCategories" />
			<display:column title="${subCategories}" sortable="false">${nomCategories[row]}</display:column>
			<display:column>
				<a href="administrator/category/edit.do?categoryId=${row.id}"><spring:message code="category.edit" /></a>
			</display:column>
		</display:table>
		<br/>
		<input type="button" name="create"
				value="<spring:message code="category.create" />"
				onclick="javascript: window.location.replace('administrator/category/create.do')" />
		
</security:authorize>