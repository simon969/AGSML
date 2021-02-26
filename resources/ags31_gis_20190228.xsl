<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
>
<xsl:param name="host"/>
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
<xsl:param name="version"/>

<xsl:template match="/">
<html>
	<head>
		<title>AGSML GIS table data</title>
		<link rel="stylesheet" type="text/css" href="../css/ags_format.css"  />
		<script type="text/javascript" src="../js/ags_java.js"></script>
	</head>
	<body>
		<xsl:comment>
			parameters passed
			host:<xsl:value-of select="$host"/>
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
		</xsl:comment>
		<xsl:if test="$tables='proj' or $tables='' or normalize-space($tables)=''">
		<h2> Project Table Details </h2>
			<table>
				<tr>
				<th>Project Name:</th>
				<th>Project Id:</th>
				<th>Project Client:</th>
				<th>Project Eng:</th>
				<th>Project Producer:</th>
				<th>Project Date:</th>
				<th>Project Datum</th>
				<th>Project Coordinate System</th>
				<th>Project Memo</th>
				<th>min Easting (m)</th>
				<th>max Easting (m)</th>
				<th>min Northing (m)</th>
				<th>max Northing (m)</th>
				<th>min GL (m)</th>
				<th>max GL (m)</th>
				<th>Hyperlink</th>
				</tr>
				<xsl:apply-templates select="/DataStructureAGSML/Proj | /Proj | /agsml/Proj"/>
			</table>
		</xsl:if>
		<xsl:if test="$tables='hole' or $tables='' or normalize-space($tables)=''">
		<h2> Hole Table Details </h2>
			<table>
				<tr>
				<th>HoleId</th>
				<th>HoleType</th>
				<th>HoleDepth</th>
				<th>Easting (m)</th>
				<th>Northing (m)</th>
				<th>Ground Level(m)</th>
				<th>Hyperlink</th>
				</tr>
				<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole | /Hole | /agsml/Proj/Hole"/>
			</table>
		</xsl:if>
	</body>
</html> 
</xsl:template>
<xsl:template match="Proj">
	<tr>
		<td><xsl:value-of select="ProjName"/></td>
		<td><xsl:value-of select="ProjId"/></td>
		<td><xsl:value-of select="ProjClnt"/></td>
		<td><xsl:value-of select="ProjEng"/></td>
		<td><xsl:value-of select="ProjProd"/></td>
		<td><xsl:value-of select="ProjDate"/></td>
		<td><xsl:value-of select="ProjGrid"/></td>
		<td><xsl:value-of select="ProjCoor"/></td>
		<td><xsl:value-of select="ProjMemo"/></td>
		<td><xsl:value-of select="$minHoleNatE"/></td>
		<td><xsl:value-of select="$maxHoleNatE"/></td>
		<td><xsl:value-of select="$minHoleNatN"/></td>
		<td><xsl:value-of select="$maxHoleNatN"/></td>
		<td><xsl:value-of select="$minHoleGL"/></td>
		<td><xsl:value-of select="$maxHoleGL"/></td>
		<td>
		<a>
				<xsl:attribute name="href">
				<xsl:value-of select="$host"/><xsl:value-of select="$project"/>
				</xsl:attribute><xsl:value-of select="ProjName"/>
		</a>
		</td>
	</tr>
</xsl:template>
<xsl:template match="Hole">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="HoleType"/></td>
		<td><xsl:value-of select="HoleFDep"/></td>
		<td><xsl:value-of select="HoleNatE"/></td>
		<td><xsl:value-of select="HoleNatN"/></td>
		<td><xsl:value-of select="HoleGL"/></td>
		<td>
		<a>
				<xsl:attribute name="href">
				<xsl:value-of select="$host"/><xsl:value-of select="$hole"/>&amp;holes=<xsl:value-of select="HoleId"/>
				</xsl:attribute><xsl:value-of select="HoleId"/>
		</a>
		</td>
	</tr>
</xsl:template>

<xsl:variable name="maxHoleNatN">
  <xsl:for-each select="/DataStructureAGSML/Proj/Hole">
    <xsl:sort select="HoleNatN" data-type="number" order="descending"/>
    <xsl:if test="position() = 1"><xsl:value-of select="HoleNatN"/></xsl:if>
  </xsl:for-each>
</xsl:variable>
<xsl:variable name="minHoleNatN">
  <xsl:for-each select="/DataStructureAGSML/Proj/Hole">
    <xsl:sort select="HoleNatN" data-type="number" order="ascending"/>
    <xsl:if test="position() = 1"><xsl:value-of select="HoleNatN"/></xsl:if>
  </xsl:for-each>
</xsl:variable>
<xsl:variable name="maxHoleNatE">
  <xsl:for-each select="/DataStructureAGSML/Proj/Hole">
    <xsl:sort select="HoleNatE" data-type="number" order="descending"/>
    <xsl:if test="position() = 1"><xsl:value-of select="HoleNatE"/></xsl:if>
  </xsl:for-each>
</xsl:variable>
<xsl:variable name="minHoleNatE">
  <xsl:for-each select="/DataStructureAGSML/Proj/Hole">
    <xsl:sort select="HoleNatE" data-type="number" order="ascending"/>
    <xsl:if test="position() = 1"><xsl:value-of select="HoleNatE"/></xsl:if>
  </xsl:for-each>
</xsl:variable>
<xsl:variable name="maxHoleGL">
  <xsl:for-each select="/DataStructureAGSML/Proj/Hole">
    <xsl:sort select="HoleGL" data-type="number" order="descending"/>
    <xsl:if test="position() = 1"><xsl:value-of select="HoleGL"/></xsl:if>
  </xsl:for-each>
</xsl:variable>
<xsl:variable name="minHoleGL">
  <xsl:for-each select="/DataStructureAGSML/Proj/Hole">
    <xsl:sort select="HoleGL" data-type="number" order="ascending"/>
    <xsl:if test="position() = 1"><xsl:value-of select="HoleGL"/></xsl:if>
  </xsl:for-each>
</xsl:variable>
</xsl:stylesheet>