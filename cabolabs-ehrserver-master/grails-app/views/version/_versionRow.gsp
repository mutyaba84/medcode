<%--
usado desde contribution/_contributionRow.gsp y contribution/show.gsp
in: version
--%>

<g:set var="cindex" value="${version.data}" />
<tr>
  <td>
    <g:link controller="versionedComposition" action="show" id="${version.uid.split('::')[0]}">${version.uid}</g:link></td>
  <td>${cindex.category}</td>
  <td>${cindex.startTime}</td>
  <td>${cindex.templateId}</td>
  <td>${cindex.archetypeId}</td>
  <td>${version.commitAudit.changeType}</td>
  <td align="center">
    <input type="checkbox" name="versioned_object.uid" value="${version.objectId}" /><br/>
    <g:link controller="ehr" action="showComposition" id="${cindex.uid}" title="${message(code:'versionRow.action.showXML')} ${cindex.uid}" class="compoXml"><asset:image src="xml.png" class="icon" /></g:link>
    <g:link controller="ehr" action="showCompositionUI" id="${cindex.uid}" title="${message(code:'versionRow.action.showDoc')} ${cindex.uid}" class="showCompo"><asset:image src="doc.png" class="icon" /></g:link>
  </td>
</tr>
