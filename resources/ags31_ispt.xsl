<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<html>
	<head>
	<title>Ispt Table</title>
	<link rel="stylesheet" type="text/css" href="/css/ags_format.css"  />
	<script type="text/javascript" src="/js/ags_java.js"></script>
	</head>
	<body>
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to top of test (m)</th>
	<th>Number of blows for seating drive ()</th>
	<th>Number of blows for main test drive ()</th>
	<th>Total pentration of seating drive and test drive (mm)</th>
	<th>SPT'N' value ()</th>
	<th>SPT reported result ()</th>
	<th>Casing depth at time of test (m)</th>
	<th>Depth to water at time of test (m)</th>
	<th>Type of SPT Test ()</th>
	<th>Self-weight penetration (mm)</th>
	<th>Number of blows for 1st Increment (Seating) ()</th>
	<th>Number of blows for 2nd Increment (Seating) ()</th>
	<th>Number of blows for 3rd Increment (Test) ()</th>
	<th>Number of blows for 4th Increment (Test) ()</th>
	<th>Number of blows for 5th Increment (Test) ()</th>
	<th>Number of blows for 6th Increment (Test) ()</th>
	<th>Penetration for 1st Increment (Seating Drive) (mm)</th>
	<th>Penetration for 2nd Increment (Seating Drive) (mm)</th>
	<th>Penetration for 3rd Increment (Test) (mm)</th>
	<th>Penetration for 4th Increment (Test) (mm)</th>
	<th>Penetration for 5th Increment (Test) (mm)</th>
	<th>Penetration for 6th Increment (Test) (mm)</th>
	<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole/Geol/Ispt"/>
	</table>
	</body>
	</html> 
</xsl:template>
<xsl:template match="Ispt">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IsptTop"/></td>
		<td><xsl:value-of select="IsptSeat"/></td>
		<td><xsl:value-of select="IsptMain"/></td>
		<td><xsl:value-of select="IsptNpen"/></td>
		<td><xsl:value-of select="IsptNval"/></td>
		<td><xsl:value-of select="IsptRep"/></td>
		<td><xsl:value-of select="IsptCas"/></td>
		<td><xsl:value-of select="IsptWat"/></td>
		<td><xsl:value-of select="IsptType"/></td>
		<td><xsl:value-of select="IsptSwp"/></td>
		<td><xsl:value-of select="IsptInc1"/></td>
		<td><xsl:value-of select="IsptInc2"/></td>
		<td><xsl:value-of select="IsptInc3"/></td>
		<td><xsl:value-of select="IsptInc4"/></td>
		<td><xsl:value-of select="IsptInc5"/></td>
		<td><xsl:value-of select="IsptInc6"/></td>
		<td><xsl:value-of select="IsptPen1"/></td>
		<td><xsl:value-of select="IsptPen2"/></td>
		<td><xsl:value-of select="IsptPen3"/></td>
		<td><xsl:value-of select="IsptPen4"/></td>
		<td><xsl:value-of select="IsptPen5"/></td>
		<td><xsl:value-of select="IsptPen6"/></td>
	</tr>
</xsl:template>

</xsl:stylesheet>
