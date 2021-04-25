<%@ page import="com.cabolabs.ehrserver.openehr.ehr.Ehr" %><%@ page import="com.cabolabs.security.Organization" %>
<div class="form-group ${hasErrors(bean: ehr, field: 'subject.value', 'has-error')}">
  <label class="control-label"><g:message code="ehr.list.attr.subject.value" /></label>
  <g:textField name="subject.value" value="${ehr?.subject?.value}" class="form-control" />
</div>
<div class="form-group ${hasErrors(bean: ehr, field: 'organizationUid', 'has-error')}">
  <label class="control-label"><g:message code="organization.uid.attr" default="Organization" /></label>
  <g:userHasAnyRole roles="ROLE_ADMIN">
    <g:select name="organizationUid" from="${Organization.list()}"
              optionKey="uid" optionValue="name" value="${ehr.organizationUid}" class="form-control" />
  </g:userHasAnyRole>
  <g:userDoesntHaveRole role="ROLE_ADMIN">
    <g:selectWithCurrentUserOrganizations name="organizationUid" value="${ehr.organizationUid}" class="form-control" />
  </g:userDoesntHaveRole>
</div>
