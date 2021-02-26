<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<html>
	<head>
	<title>Hole Table</title>
	<link rel="stylesheet" type="text/css" href="/css/ags_format.css"  />
	<script type="text/javascript" src="/js/ags_java.js"></script>
	</head>
	<body>
	<table id="Hole">
		<tr>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to the TOP of stratum (m)</th>
		<th>Depth to the BASE of stratum ()</th>
		<th>General Description of stratum ()</th>
		<th>Legend Code ()</th>
		<th>Geology Code ()</th>
		<th>Secondary Geology Code ()</th>
		<th>Stratum reference shown on trial pit or traverse sketch ()</th>
		<th>Associated file reference ()</th>
		<xsl:apply-templates  select="Geol" />
		</tr>			
 		<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole/Geol"/>
	</table>
	</body>
	</html> 
</xsl:template>

<xsl:template match="Geol">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="GeolTop"/></td>
		<td><xsl:value-of select="GeolBase"/></td>
		<td><xsl:value-of select="GeolDesc"/></td>
		<td><xsl:value-of select="GeolLeg"/></td>
		<td><xsl:value-of select="GeolGeol"/></td>
		<td><xsl:value-of select="GeolGeo2"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
	</tr>
</xsl:template>
</xsl:stylesheet>