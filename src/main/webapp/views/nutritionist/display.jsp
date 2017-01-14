<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" 
	name="nutritionist" id="row">
	
	
	<spring:message code="nutritionist.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true"/>
	
	<spring:message code="nutritionist.surname" var="surnameHeader"/>
	<display:column property="surname" title="${surnameHeader}" sortable="false"/>
	
	<spring:message code="nutritionist.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false"/>
	
	<spring:message code="nutritionist.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="false"/>
	
	<spring:message code="nutritionist.postalAddress" var="postalAddressHeader"/>
	<display:column property="postalAddress" title="${postalAddressHeader}" sortable="false"/>
	
	
</display:table>