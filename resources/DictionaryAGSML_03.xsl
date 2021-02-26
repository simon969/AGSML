<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:param name="host"/>
<xsl:param name="host_view"/>
<xsl:param name="host_file"/>
<xsl:param name="user"/>
<xsl:param name="hole"/>
<xsl:param name="holes"/>
<xsl:param name="project"/>
<xsl:param name="projects"/>
<xsl:param name="geol"/>
<xsl:param name="geols"/>
<xsl:param name="table"/>
<xsl:param name="tables"/>
<xsl:param name="options"/>
<xsl:param name="image"/>
<xsl:param name="dictionary"/>
<xsl:param name="version"/>
<xsl:param name="script"/>
<xsl:param name="css"/>
<xsl:param name="url_loca" />
<xsl:template match="/">
<html>
	<head>
		<title>AGS Data Dictionary <xsl:value-of select="$version"/> </title>
		<xsl:choose>
			<xsl:when test="contains($host,'localhost')">
			<link rel="stylesheet" type="text/css" href="../css/ags_format.css" />
			<script type="text/javascript" src="../js/ags_java.js" />
			</xsl:when>
			<xsl:otherwise>
			<link rel="stylesheet" type="text/css" href="css/ags_format.css" />
			<script type="text/javascript" src="js/ags_java.js" />
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="string-length($css)>0">
			<link rel="stylesheet" type="text/css">
			<xsl:attribute name="href"><xsl:value-of select="$host_file" /><xsl:value-of select="$css" /></xsl:attribute>
			</link>
		</xsl:if>
		<xsl:if test="string-length($script)>0">
			<script type="text/javascript">
			<xsl:attribute name="src"><xsl:value-of select="$host_file" /><xsl:value-of select="$script" /></xsl:attribute>
			</script>
		</xsl:if>
  </head>
  <body>
  		<xsl:comment>
			parameters passed
			host:<xsl:value-of select="$host"/>
			host_view:<xsl:value-of select="$host_view"/>
			host_file:<xsl:value-of select="$host_file"/>
			user:<xsl:value-of select="$user"/>
			hole:<xsl:value-of select="$hole"/>
			holes:<xsl:value-of select="$holes"/>
			project:<xsl:value-of select="$project"/>
			projects:<xsl:value-of select="$projects"/>
			geol:<xsl:value-of select="$geol"/>
			geols:<xsl:value-of select="$geols"/>
			table:<xsl:value-of select="$table"/>
			tables:<xsl:value-of select="$tables"/>
			options:<xsl:value-of select="$options"/>
			version:<xsl:value-of select="$version"/>
			image:<xsl:value-of select="$image"/>
			css:<xsl:value-of select="$css"/>
			script:<xsl:value-of select="$script"/>
			dictionary:<xsl:value-of select="$dictionary"/>
		</xsl:comment>
  <h2>AGS Data Dictionary <xsl:value-of select="$version"/> </h2>
  <div style="position: absolute; top:0px; left:550px; width:200px; height:50px">
		<img width="360" height="53" alt="ge_logo">
		<xsl:attribute name="src">
			<xsl:value-of select="$host2"/><xsl:value-of select="$image"/>
			</xsl:attribute>
		</img>
  </div> 
  			
			
  <xsl:if test="$version='3.1'">
     <xsl:apply-templates select="/root/DictionaryAGSML[@agsversion='3.1a']"/>
  </xsl:if>
  
  <xsl:if test="$version='4.03'">
  <xsl:apply-templates select="/root/DictionaryAGSML[@agsversion='4.03']"/>
  </xsl:if>
  
  <xsl:if test="$version='4.04'">
  <xsl:apply-templates select="/root/DictionaryAGSML[@agsversion='4.04']"/>
  </xsl:if>
  
  </body>
</html>
</xsl:template>

<xsl:template match="DictionaryAGSML">
 <div id="AGS{version/ags}">
 <xsl:apply-templates select="version"/>
 <xsl:apply-templates select="table"/>
 </div>
 </xsl:template>

 <xsl:template match="version">
	<table>
	    <th>Dictionary</th>
		<th>AGS</th>
		<th>AGSML</th>
		<th>GINT</th>
	<tr>
	<td>AGS Data Dictionary File Version <xsl:value-of select="dictionary"/> </td>
	<td><xsl:value-of select="ags"/></td>
	<td><xsl:value-of select="agsml"/></td>
	<td><xsl:value-of select="gint"/></td>
	</tr>
	</table>
</xsl:template>

 <xsl:template match="table">
	
	<h3> <xsl:value-of select="@name" /> Table</h3>
	<table>	
	<tr> <td colspan="7">
		Notes for Guidance: <xsl:value-of select="guidance"/>
		</td>
	</tr>

		<tr>
			<td><xsl:value-of select="@name" /></td>
			<td><xsl:value-of select="description"/></td>
			<td></td><td></td>
			<td><xsl:value-of select="ags/name"/></td>
			<td><xsl:value-of select="agsml/name"/></td>
			<td><xsl:value-of select="gint/name"/></td>
		</tr>
			<th>Name</th>
			<th>Description</th>
			<th>Units</th>
			<th>Example</th>
			<th>AGS</th>
			<th>AGSML</th>
			<th>GINT</th> 
	<xsl:apply-templates select="field"/>
	</table>

</xsl:template>

<xsl:template match="field">
	<tr>
		<td><xsl:value-of select="@name"/></td>
		<td><xsl:value-of select="description"/></td>  
		<td><xsl:value-of select="unit"/></td> 
		<td><xsl:value-of select="example"/></td>
		<td><xsl:value-of select="ags/name"/></td>
		<td><xsl:value-of select="agsml/name"/></td>
		<td><xsl:value-of select="gint/name"/></td>
	</tr>
</xsl:template>

</xsl:stylesheet>
