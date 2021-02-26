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
			<td><xsl:value-of select="ProjGrid"/></td>
		</tr>
		<tr>
			<td>Project Coordinate System</td>
			<td><xsl:value-of select="ProjCoor"/></td>
		</tr>
		<tr>
			<td>Project Memo</td>
			<td><xsl:value-of select="ProjMemo"/></td>
		</tr>
	</table>
	<h2>Stratification and Classification Testing</h2>
	<table>
			<th>HoleId</th>
			<th>Max Depth (m)</th>
			<th>Strata (count)</th>
			<th>Details (count)</th>
			<th>Classification(count)</th>
			<th>Samples(count)</th>
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">StrataClassification</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Drilling Information</h2>
	<table>
			<th>HoleId</th>
			<th>Rotary Core Information (count))</th>
			<th>Discontinuity Information (count)</th>
			<th>Drilling Observations (count)</th>
			<th>Drilling Remarks (count)</th>
			
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">drillInfo</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Insitu Strength and Stiffness Testing</h2>
	<table>
			<th>HoleId</th>
			<th>Standard Penetration Tests (count)</th>
			<th>Dynamic Probe Tests General (count)</th>
			<th>Dynamic Probe Test (count)</th>
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">insituStrengthStiffness</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Laboratory Strength and Stiffness Testing</h2>
	<table>
			<th>HoleId</th>
			<th>Consolidation Test General (count)</th>
			<th>Consolidation Test (count)</th>
			<th>Ispt (count)</th>
			
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">labStrengthStiffness</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Laboratory Index Testing</h2>
	<table>
			<th>HoleId</th>
			<th>Aggregate Abrasion Tests (count))</th>
			<th>Aggregate Crusing Value Tests (count)</th>
			<th>Aggregate Elongation Index Tests(count)</th>
			<th>Aggregate Crusing Value Tests (count)</th>
			<th>Aggregate Crusing Value Tests (count)</th>
			
			<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole">
			<xsl:with-param name="testGroup">labIndex</xsl:with-param>
			</xsl:apply-templates>
	</table>
	</xsl:template>


<xsl:template match="Hole">
<xsl:param name="testGroup"/>
<xsl:if test="$testGroup='StrataClassification'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<td><xsl:value-of select="descendant::GeolBase[last()]"/></td>
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
<xsl:if test="$testGroup='DrillInfo'">
	<tr>
	<td><xsl:value-of select="HoleId"/></td>
	<td><xsl:value-of select="count(Geol/Core)"/></td>
	<td><xsl:value-of select="count(Geol/Disc)"/></td>
	<td><xsl:value-of select="count(Geol/Dobs)"/></td>
	<td><xsl:value-of select="count(Geol/Drem)"/></td>
	</tr>
</xsl:if>
</xsl:template>
</xsl:stylesheet>