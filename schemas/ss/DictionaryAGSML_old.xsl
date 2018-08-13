<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html>
  <head>
  <title>AGS Data Dictionary <xsl:value-of select="/root/DictionaryAGSML/version/dictionary"/></title>
  <link rel="stylesheet" type="text/css" href="ags_format.css" />
  </head>
  <body>
  
  
  <h2>AGSML Data Dictionary <xsl:value-of select="/root/DictionaryAGSML/version/dictionary"/> </h2>
  <xsl:apply-templates select="/root/DictionaryAGSML"/>
 
  <h2>AGSML Abbreviations <xsl:value-of select="/root/ReferenceAGSML/version/dictionary"/> </h2>
  <xsl:apply-templates select="/root/ReferenceAGSML/Abbrs"/>
  
  <h2>AGSML Codes <xsl:value-of select="/root/ReferenceAGSML/version/dictionary"/> </h2>
  <xsl:apply-templates select="/root/ReferenceAGSML/Codes"/>
 </body>
</html>
</xsl:template>
 
 <xsl:template match="table">
	<h3> <xsl:value-of select="@name" /> Table</h3>
	<table>
		<tr>
			<td><xsl:value-of select="@name" /></td>
			<td><xsl:value-of select="description"/></td>
			<td></td><td></td>
			<td><xsl:value-of select="ags/name"/></td>
			<td><xsl:value-of select="agsml/name"/></td>
			<td><xsl:value-of select="sql/name"/></td>
		</tr>
			<th>Name</th>
			<th>Description</th>
			<th>Units</th>
			<th>Example</th>
			<th>AGS</th>
			<th>AGSML</th>
			<th>SQL</th> 
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
		<td><xsl:value-of select="sql/name"/></td>
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
