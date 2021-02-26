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
	
	<th>Depth to the TOP of stratum (m)</th>
	<th>Depth to the BASE of stratum ()</th>
	<th>General Description of stratum ()</th>
	<th>Legend Code ()</th>
	<th>Geology Code ()</th>
	<th>Secondary Geology Code ()</th>
	
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
	<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole/Geol"/>
	</table>
	</body>
	</html> 
</xsl:template>
<xsl:template match="Geol">
	<xsl:apply-templates select ="Ispt">
		<xsl:with-param name="GeolTop" select ="GeolTop"/>
		<xsl:with-param name="GeolBase" select ="GeolBase"/>
		<xsl:with-param name="GeolDesc" select ="GeolDesc"/>
		<xsl:with-param name="GeolLeg" select ="GeolLeg"/>
		<xsl:with-param name="GeolGeol" select ="GeolGeol"/>
		<xsl:with-param name="GeolGeo2" select ="GeolGeo2"/>
	</xsl:apply-templates>
	
</xsl:template>
<xsl:template match="Ispt">
	<xsl:param name = "GeolTop" />
	<xsl:param name = "GeolBase" />
	<xsl:param name = "GeolDesc" />
	<xsl:param name = "GeolLeg" />
	<xsl:param name = "GeolGeol" />
	<xsl:param name = "GeolGeo2" />
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		
		<td><xsl:value-of select="$GeolTop"/></td>
		<td><xsl:value-of select="$GeolBase"/></td>
		<td><xsl:value-of select="$GeolDesc"/></td>
		<td><xsl:value-of select="$GeolLeg"/></td>
		<td><xsl:value-of select="$GeolGeol"/></td>
		<td><xsl:value-of select="$GeolGeo2"/></td>
		
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
