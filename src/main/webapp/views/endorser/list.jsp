<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<input type="button" id="buttonCreate"
		value="<spring:message code="endorser.create"/>" />
	
		<script type="text/javascript">
			$(document).ready(function(){
				$("#buttonCreate").click(function(){
					window.location.replace('nutritionist/endorser/create.do');
				});
				
				$("#buttonCreate").onsubmit(function(){
					window.location.replace('nutritionist/endorser/create.do');
				});
			});
		</script>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="endorsers" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<display:column>
		<a href="nutritionist/endorser/edit.do?endorserId=${row.id}"><spring:message code="endorser.edit" /></a>
	</display:column>

	<spring:message code="endorser.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	<spring:message code="endorser.homePage" var="homePageHeader" />
	<display:column property="homePage" title="${homePageHeader}" sortable="false" />
	
</display:table>