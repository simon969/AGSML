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
			<th>type of hole ie cable percussion or rotary core ()</th>
			<th>Location sub division within project ()</th>
			<th>National Grid Location Easting (m)</th>
			<th>National Grid Location Northing (m)</th>
			<th>Local grid x co-ordinate (m)</th>
			<th>Local grid y co-ordinate (m)</th>
			<th>Local grid z co-ordinate (m)</th>
			<th>Ground Level (m)</th>
			<th>Final depth (m)</th>
			<th>Started Date (dd/mmm/yyyy)</th>
			<th> ()</th>
			<th>Remarks ()</th>
			<th>Ended Date (dd/mm/yyyy)</th>
			<th>Backfilled date (dd/mm/yyyy)</th>
			<th> ()</th>
			<th>Description of execution process ie rig details ()</th>
			<th>Orintation of hole (deg)</th>
			<th>hole Inclination (deg)</th>
			<th>Support provided to hole during construction ()</th>
			<th>Hole stability during construction ()</th>
			<th>Hole Length (m)</th>
			<th>Width of hole (m)</th>
			<th>Hole cluster reference number ()</th>
			<th>Ordnance Survey letter grid reference ()</th>
			<th>Latitude (deg(decimal))</th>
			<th>Longitude (deg(decimal))</th>
			<th>National Grid Easting of end of traverse (m)</th>
			<th>National Grid Northing of end of traverse (m)</th>
			<th>Ground level relative to datum of end of traverse (m)</th>
			<th>Associated file reference ()</th>
		</tr>			
 		<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole"/>
	</table>
	</body>
	</html> 
</xsl:template>

<xsl:template match="Hole">
		<tr>
			<td>
			<xsl:element name="a">
					<xsl:attribute name="href">
						URLTRANSFORMID1&holeId=
						<xsl:value-of select="DataStructureAGSML/Hole/HoleId"/>
					</xsl:attribute>
						<xsl:value-of select="DataStructureAGSML/Hole/HoleId"/>
				</xsl:element>
			<xsl:value-of select="HoleId"/>
			</td>
			
			<td><xsl:value-of select="HoleType"/></td>
			<td><xsl:value-of select="HoleLoca"/></td>
			<td><xsl:value-of select="HoleNatE"/></td>
			<td><xsl:value-of select="HoleNatN"/></td>
			<td><xsl:value-of select="HoleLocX"/></td>
			<td><xsl:value-of select="HoleLocY"/></td>
			<td><xsl:value-of select="HoleLocZ"/></td>
			<td><xsl:value-of select="HoleGL"/></td>
			<td><xsl:value-of select="HoleFDep"/></td>
			<td><xsl:value-of select="HoleStar"/></td>
			<td><xsl:value-of select="HoleLog"/></td>
			<td><xsl:value-of select="HoleRem"/></td>
			<td><xsl:value-of select="HoleEndD"/></td>
			<td><xsl:value-of select="HoleBackD"/></td>
			<td><xsl:value-of select="HoleCrew"/></td>
			<td><xsl:value-of select="HoleExc"/></td>
			<td><xsl:value-of select="HoleOrnt"/></td>
			<td><xsl:value-of select="HoleIncl"/></td>
			<td><xsl:value-of select="HoleShor"/></td>
			<td><xsl:value-of select="HoleStab"/></td>
			<td><xsl:value-of select="HoleDimL"/></td>
			<td><xsl:value-of select="HoleDimW"/></td>
			<td><xsl:value-of select="HoleCst"/></td>
			<td><xsl:value-of select="HoleLett"/></td>
			<td><xsl:value-of select="HoleLat"/></td>
			<td><xsl:value-of select="HoleLong"/></td>
			<td><xsl:value-of select="HoleEtrv"/></td>
			<td><xsl:value-of select="HoleNtrv"/></td>
			<td><xsl:value-of select="HoleLtrv"/></td>
			<td><xsl:value-of select="FileFSet"/></td>
		</tr>
</xsl:template>
</xsl:stylesheet>