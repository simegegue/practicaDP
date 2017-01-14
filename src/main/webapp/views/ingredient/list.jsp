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
	access="hasRole('NUTRITIONIST')">
		<display:table pagesize="5" class="displaytag" keepStatus="true" name="ingredients" requestURI="${requestURI}" id="row">
			
			<!-- Action links -->
			
			<!-- Attributes -->
			
	
			<display:column>
				<a href="nutritionist/ingredient/edit.do?ingredientId=${row.id}"><spring:message code="ingredient.edit" /></a>
			</display:column>			

			
			<spring:message code="ingredient.name" var="name" />
			<display:column property="name" title="${name}" sortable="true" />
		
			<spring:message code="ingredient.description" var="description" />
			<display:column property="description" title="${description}" sortable="false"><fmt:formatDate value="${contest.openingTime }" pattern="dd/MM/yyyy" /></display:column>
		
			<spring:message code="ingredient.picture" var="picture" />
			<display:column property="picture" title="${picture}" sortable="false" ><fmt:formatDate value="${contest.closingTime }" pattern="dd/MM/yyyy" /></display:column>
			
			<display:column>
				<a href="nutritionist/property/listing.do?ingredientId=${row.id}"><spring:message code="ingredient.property" /></a>
			</display:column>
			
			<display:column>
				<a href="nutritionist/ingredient/editP.do?ingredientId=${row.id}"><spring:message code="ingredient.addProperty" /></a>
			</display:column>				
			
		</display:table>
		
</security:authorize>