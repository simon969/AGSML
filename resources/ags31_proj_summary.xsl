<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<html>
	<head>
	<title>Project Summary</title>
	<link rel="stylesheet" type="text/css" href="/css/ags_format.css"  />
	<script type="text/javascript" src="/js/ags_java.js"></script>
	</head>
	<body>
	<xsl:apply-templates select="/DataStructureAGSML/Proj"/>
	</body>
	</html> 
</xsl:template>
<xsl:template match="Proj">
	<h2> Project Details </h2>
	<table>
		<tr>
			<td>Project Name:</td>
			<td><xsl:value-of select="ProjName"/></td>
		</tr>
		<tr>
			<td>Project Id:</td>
			<td><xsl:value-of select="ProjId"/></td>
		</tr>
		<tr>
			<td>Project Client:</td>
			<td><xsl:value-of select="ProjClnt"/></td>
		</tr>
		<tr>
			<td>Project Eng:</td>
			<td><xsl:value-of select="ProjEng"/></td>
		</tr>
		<tr>
			<td>Project Producer:</td>
			<td><xsl:value-of select="ProjProd"/></td>
		</tr>
		<tr>
			<td>Project Date:</td>
			<td><xsl:value-of select="ProjDate"/></td>
		</tr>
		<tr>
			<td>Project Datum</td>
			<td><xsl:value-of select="ProjDatm"/></td>
		</tr>
		<tr>
			<td>Status of data within submission</td>
			<td><xsl:value-of select="ProjStat"/></td>
		</tr>
		<tr>
			<td>Project Memo</td>
			<td><xsl:value-of select="ProjMemo"/></td>
		</tr>
		<tr>
			<td>AGS version</td>
			<td><xsl:value-of select="ProjAGS"/></td>
		</tr>
	</table>
	<h2>Geospatial and Location Information</h2>
	<table>
			<th>HoleId</th>
			<th>Final Depth and Max Geol Depth Match</th>
			<th>Eastings Present</th>
			<th>Northings Present</th>
			<th>Ground Level Present</th>
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">locationInfo</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Stratification and Classification Testing (count)</h2>
	<table>
			<th>HoleId</th>
			<th>Geology</th>
			<th>Details</th>
			<th>Classification</th>
			<th>Samples</th>
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">strataClassification</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Drilling Information Records (count)</h2>
	<table>
			<th>HoleId</th>
			<th>Hole progress by time</th>
			<th>Rotary Core Information</th>
			<th>Discontinuity Information</th>
			<th>Drilling Observations</th>
			<th>Drilling Remarks</th>
			
			
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">drillInfo</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Insitu Strength and Stiffness Testing (count)</h2>
	<table>
			<th>HoleId</th>
			<th>Standard Penetration Tests</th>
			<th>Dynamic Probe Tests General</th>
			<th>Dynamic Probe Test</th>
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">insituStrengthStiffness</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Laboratory Strength and Stiffness Testing (count)</h2>
	<table>
			<th>HoleId</th>
			<th>Consolidation Test General</th>
			<th>Consolidation Test</th>
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">labStrengthStiffness</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Laboratory Index Testing</h2>
	<table>
			<th>HoleId</th>
			<th>Aggregate Abrasion Tests</th>
			<th>Aggregate Crusing Value Tests</th>
			<th>Aggregate Elongation Index Tests</th>
			<th>Aggregate Flakiness Value Tests</th>
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">labIndex</xsl:with-param>
			</xsl:apply-templates>
	</table>
	</xsl:template>


<xsl:template match="Hole">
<xsl:param name="testGroup"/>
<xsl:if test="$testGroup='strataClassification'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<td><xsl:value-of select="count(Geol)"/></td>
	<td><xsl:value-of select="count(Geol/Detl)"/></td>
	<td><xsl:value-of select="count(Geol/Clss)"/></td>
	<td><xsl:value-of select="count(Geol/Samp)"/></td>
	</tr>
</xsl:if>
<xsl:if test="$testGroup='labStrengthStiffness'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<td><xsl:value-of select="count(Geol/Cong)"/></td>
	<td><xsl:value-of select="count(Geol/Cons)"/></td>
	</tr>
</xsl:if>
<xsl:if test="$testGroup='insituStrengthStiffness'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<td><xsl:value-of select="count(Geol/Ispt)"/></td>
	<td><xsl:value-of select="count(Geol/Dprg)"/></td>
	<td><xsl:value-of select="count(Geol/Dprb)"/></td>
	</tr>
</xsl:if>
<xsl:if test="$testGroup='labIndex'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<td><xsl:value-of select="count(Geol/Aavt)"/></td>
	<td><xsl:value-of select="count(Geol/Acvt)"/></td>
	<td><xsl:value-of select="count(Geol/Aelo)"/></td>
	<td><xsl:value-of select="count(Geol/Aflk)"/></td>
	</tr>
</xsl:if>
<xsl:if test="$testGroup='drillInfo'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<td><xsl:value-of select="count(Geol/Ptim)"/></td>
	<td><xsl:value-of select="count(Geol/Core)"/></td>
	<td><xsl:value-of select="count(Geol/Disc)"/></td>
	<td><xsl:value-of select="count(Geol/Dobs)"/></td>
	<td><xsl:value-of select="count(Geol/Drem)"/></td>
	</tr>
</xsl:if>
<xsl:if test="$testGroup='locationInfo'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<xsl:variable name="MaxGeolDepth" select="descendant::GeolBase[last()]"/>
	<xsl:choose>
		<xsl:when test="$MaxGeolDepth = HoleFDep">
		<td>Yes</td>
		</xsl:when>
		<xsl:when test="$MaxGeolDepth != HoleFDep">
		<td>No</td>
		</xsl:when>
	</xsl:choose>
	<xsl:choose>
		<xsl:when test="HoleNatE !='' ">
		<td>Yes</td>
		</xsl:when>
		<xsl:when test="HoleNatE ='' ">
		<td>No</td>
		</xsl:when>
	</xsl:choose>
	<xsl:choose>
		<xsl:when test="HoleNatN !='' ">
		<td>Yes</td>
		</xsl:when>
		<xsl:when test="HoleNatN ='' ">
		<td>No</td>
		</xsl:when>
	</xsl:choose>
	<xsl:choose>
		<xsl:when test="HoleGL !='' ">
		<td>Yes</td>
		</xsl:when>
		<xsl:when test="HoleGL ='' ">
		<td>No</td>
		</xsl:when>
	</xsl:choose>
	</tr>
</xsl:if>
</xsl:template>
</xsl:stylesheet>