<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
<html>
  <head>
  <title>AGS Data Dictionaries</title>
  <link rel="stylesheet" type="text/css" href="css/ags_format.css"  />
  <script type="text/javascript" src="js/ags_java.js"></script>
  </head>
  <body>
 
  <h2><a onclick="toggleVisible('AGS3.1a')">AGS Data Dictionary AGS 3.1a</a></h2>
  <h2><a onclick="toggleVisible('AGS4.03')"> AGS Data Dictionary AGS 4.03</a></h2>
  <h2><a onclick="toggleVisible('AGS4.04')"> AGS Data Dictionary AGS 4.04</a></h2>
  <h2><a onclick="toggleVisible('Abbrevs')">Abbrevs</a></h2>
  <h2><a onclick="toggleVisible('Codes')"> Codes</a></h2>
  
  <xsl:apply-templates select="/root/DictionaryAGSML"/>
  <div id="Abbrevs">
  <h2>AGS Abbreviations <xsl:value-of select="/root/ReferenceAGSML/version/dictionary"/> </h2>
  <xsl:apply-templates select="/root/ReferenceAGSML/Abbrs"/>
  </div>
  <div id="Codes">
  <h2>AGS Codes <xsl:value-of select="/root/ReferenceAGSML/version/dictionary"/> </h2>
  <xsl:apply-templates select="/root/ReferenceAGSML/Codes"/>
  </div>
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
