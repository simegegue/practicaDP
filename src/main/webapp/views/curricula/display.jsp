<%--
 * display.jsp
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
<security:authorize access="hasRole('NUTRITIONIST')">

	
	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="curricula" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	
		<display:column>
			<a href="nutritionist/curricula/edit.do?curriculaId=${row.id}"><spring:message code="curricula.edit" /></a>
		</display:column>	
		
		<spring:message code="curricula.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />		

		<spring:message code="curricula.photo" var="photoHeader" />
		<display:column property="photo" title="${photoHeader}" sortable="false" />
	
		<spring:message code="curricula.educationSection" var="educationSectionHeader" />
		<display:column property="educationSection" title="${educationSectionHeader}" sortable="true" />
	
		<spring:message code="curricula.experienceSection" var="experienceSectionHeader" />
		<display:column property="experienceSection" title="${experienceSectionHeader}" sortable="false" />
		
		<spring:message code="curricula.hobbySection" var="hobbySectionHeader" />
		<display:column property="hobbySection" title="${hobbySectionHeader}" sortable="true" />
		
		<display:column>
			<a href="nutritionist/endorser/list.do"><spring:message code="curricula.endorser" /></a>
		</display:column>
		
	</display:table>
	
	<jstl:if test="${empty nutritionist.curricula}">
		<p><spring:message code="curricula.empty"/></p>
		<br/>
		<input type="button" id="buttonCreate"
		value="<spring:message code="curricula.create"/>" />
	
		<script type="text/javascript">
			$(document).ready(function(){
				$("#buttonCreate").click(function(){
					window.location.replace('nutritionist/curricula/create.do');
				});
				
				$("#buttonCreate").onsubmit(function(){
					window.location.replace('nutritionist/curricula/create.do');
				});
			});
		</script>
	</jstl:if>
	
	
</security:authorize>