
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:hidden path="monthlyBillId" />

<table id="row" class="table">
	
	<tbody>
		<tr>
			<th>
				<spring:message code = "monthlyBill.createMoment"/>
			</th>
			<td>
				<fmt:formatDate value="${monthlyBill.createMoment }" pattern="dd/MM/yyyy HH:mm" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="monthlyBill.payMoment" />
			</th>
			<td>
				<fmt:formatDate value="${monthlyBill.payMoment }" pattern="dd/MM/yyyy HH:mm" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="monthlyBill.cost" />
			</th>
			<td>
				<jstl:out value="${monthlyBill.cost }" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="monthlyBill.description" />
			</th>
			<td>
				<jstl:out value="${monthlyBill.description }" />
			</td>
		</tr>
	</tbody>

</table>
<html>
<security:authorize access="hasRole('SPONSOR')">
<jstl:if test="${ empty monthlyBill.payMoment}">	
			<a href="sponsor/monthlybill/pay.do?monthlyBillId=${monthlyBillId }"><spring:message code="monthlyBill.pay" /></a>
		</jstl:if>
</security:authorize>
</html>