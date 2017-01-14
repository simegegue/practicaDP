<%--
 * browse.jsp
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

<input type="text" value="" id="spamWordRegister" />
<input type="button" id="buttonRegister"
	value="<spring:message code="spamWord.register"/>" />
	
<script type="text/javascript">
	$(document).ready(function(){
		$("#buttonRegister").click(function(){
			window.location.replace('administrator/spamWord/registerSpamWord.do?spamWordKey=' + $("#spamWordRegister").val());
		});
		
		$("#buttonRegister").onsubmit(function(){
			window.location.replace('administrator/spamWord/registerSpamWord.do?spamWordKey=' + $("#spamWordRegister").val());
		});
	});
</script>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="spamWords" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->

	<spring:message code="spamWord.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<display:column>
			<a href="administrator/spamWord/deleteSpamWord.do?spamWordKey=${row.name}"><spring:message code="spamWord.delete" /></a>
	</display:column>

</display:table>
