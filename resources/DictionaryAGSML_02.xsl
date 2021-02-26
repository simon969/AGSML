<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"><xsl:template match="/">
<html>
  <head>
  <title>AGS Data Dictionaries</title>
  <link rel="stylesheet" type="text/css" href="../css/ags_format.css"  />
  <script type="text/javascript" src="../js/ags_java.js"></script>
  </head>
  <body>
   <xsl:apply-templates select="/root/DictionaryAGSML"/>
 </body>
</html>
</xsl:template>

<xsl:template match="DictionaryAGSML">
 <table>
			<th>table</th>
			<th>name</th>
			<th>description</th>
			<th>units</th>
			<th>example</th>
			<th>ags_version</th>
			<th>ags_name</th>
			<th>agsml_name</th>
			<th>gint_name</th>
 <xsl:apply-templates select="table">
 <xsl:with-param name="ags_version" select="@agsversion" />
 </xsl:apply-templates>
</table>
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
 <xsl:param name="ags_version" />
 <xsl:apply-templates select="field">
 <xsl:with-param name="table_name" select="@name" />
 <xsl:with-param name="ags_version" select="$ags_version" />
 </xsl:apply-templates>
</xsl:template>

<xsl:template match="field">
<xsl:param name="table_name" />
<xsl:param name="ags_version" />
	<tr>
		<td><xsl:value-of select="$table_name"/></td>
		<td><xsl:value-of select="@name"/></td>
		<td><xsl:value-of select="description"/></td>  
		<td><xsl:value-of select="unit"/></td> 
		<td><xsl:value-of select="example"/></td>
		<td><xsl:value-of select="$ags_version"/></td>
		<td><xsl:value-of select="ags/name"/></td>
		<td><xsl:value-of select="agsml/name"/></td>
		<td><xsl:value-of select="gint/name"/></td>
	</tr>
</xsl:template>

<xsl:template match="Abbrs/AbbrHdng">
    <h3><xsl:value-of select="@hdng"/> Abbreviations</h3>
	<table>
	<th>AbbrCode</th>
	<th>AbbrDescription</th>
	<xsl:apply-templates select="Abbr" /> 
	</table>
</xsl:template>

<xsl:template match="Abbr">
	<tr>
		<td><xsl:value-of select="AbbrCode"/></td>
		<td><xsl:value-of select="AbbrDesc"/></td>
	</tr>
</xsl:template>

 <xsl:template match="Codes">
 
	<table>
	<th>Code</th>
	<th>Description</th>
	<xsl:apply-templates select="Code" /> 
	</table>
</xsl:template> 

<xsl:template match="Code">
	<tr>
		<td><xsl:value-of select="CodeCode"/></td>
		<td><xsl:value-of select="CodeDesc"/></td>
	</tr>
</xsl:template>

</xsl:stylesheet>
