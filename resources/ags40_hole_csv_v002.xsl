<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:param name="host" />
<xsl:param name="host_file" />
<xsl:param name="host_view" />
<xsl:param name="user" />
<xsl:param name="hole" />
<xsl:param name="holes" />
<xsl:param name="project" />
<xsl:param name="projects" />
<xsl:param name="geol" />
<xsl:param name="geols" />
<xsl:param name="table" />
<xsl:param name="tables" />
<xsl:param name="options" />
<xsl:param name="version" />
<xsl:param name="image" />
<xsl:param name="dictionary" />
<xsl:param name="css" />
<xsl:param name="script" />
<xsl:param name="url_loca" />
<xsl:output method="text" encoding="iso-8859-1"/>
<xsl:variable name="delimiter" select="','"/>
 
<xsl:template match="/">

<html>
<head>
</head>
<body>
<table>
	<tr>
		<th>Location identifier ()</th><xsl:value-of select="$delimiter"/>
		<th>type of activity ()</th><xsl:value-of select="$delimiter"/>
		<th>Status of information relating to this position ()</th><xsl:value-of select="$delimiter"/>
		<th>National Grid Location Easting (m)</th><xsl:value-of select="$delimiter"/>
		<th>National Grid Location Northing (m)</th><xsl:value-of select="$delimiter"/>
		<th>National grid referencing system used (m)</th><xsl:value-of select="$delimiter"/>
		<th>Ground Level (m)</th><xsl:value-of select="$delimiter"/>
		<th>Remarks ()</th><xsl:value-of select="$delimiter"/>
		<th>Final depth (m)</th><xsl:value-of select="$delimiter"/>
		<th>Started Date (dd/mmm/yyyy)</th><xsl:value-of select="$delimiter"/>
		<th>Purpose of activity at this location ()</th><xsl:value-of select="$delimiter"/>
		<th>Reason for activity termination ()</th><xsl:value-of select="$delimiter"/>
		<th>Ended Date (dd/mm/yyyy)</th><xsl:value-of select="$delimiter"/>
		<th>OSGB letter grid reference ()</th><xsl:value-of select="$delimiter"/>
		<th>Local grid x co-ordinate (m)</th><xsl:value-of select="$delimiter"/>
		<th>Local grid y co-ordinate (m)</th><xsl:value-of select="$delimiter"/>
		<th>Local grid z co-ordinate (m)</th><xsl:value-of select="$delimiter"/>
		<th>Local datum referencing system used ()</th><xsl:value-of select="$delimiter"/>
		<th>Local grid referencing system used ()</th><xsl:value-of select="$delimiter"/>
		<th>National Grid Easting of end of traverse (m)</th><xsl:value-of select="$delimiter"/>
		<th>National Grid Northing of end of traverse (m)</th><xsl:value-of select="$delimiter"/>
		<th>Ground level relative to datum of end of traverse (m)</th><xsl:value-of select="$delimiter"/>
		<th>Local grid easting of end of traverse (m)</th><xsl:value-of select="$delimiter"/>
		<th>Local grid northing of end of traverse (m)</th><xsl:value-of select="$delimiter"/>
		<th>Local elevation of end of traverse (m)</th><xsl:value-of select="$delimiter"/>
		<th>Latitude (deg(decimal))</th><xsl:value-of select="$delimiter"/>
		<th>Longitude (deg(decimal))</th><xsl:value-of select="$delimiter"/>
		<th>Latitude of end of traverse (m)</th><xsl:value-of select="$delimiter"/>
		<th>Longitude of end of traverse ()</th><xsl:value-of select="$delimiter"/>
		<th>Projection Format ()</th><xsl:value-of select="$delimiter"/>
		<th>Method of Location ()</th><xsl:value-of select="$delimiter"/>
		<th>Location sub division within project ()</th><xsl:value-of select="$delimiter"/>
		<th>Hole cluster reference number ()</th><xsl:value-of select="$delimiter"/>
		<th>Alignment Identifier ()</th><xsl:value-of select="$delimiter"/>
		<th>Offset of hole from assigned alignment (m)</th><xsl:value-of select="$delimiter"/>
		<th>Chainage of hole on assigned alignment (m)</th><xsl:value-of select="$delimiter"/>
		<th>Reference to or details of algorithm used to calculate local grid reference</th><xsl:value-of select="$delimiter"/>
		<th>local ground levels or chainage ()</th><xsl:value-of select="$delimiter"/>
		<th>Associated file reference ()</th><xsl:value-of select="$delimiter"/>
		<th>National Datum Referencing System used ()</th><xsl:value-of select="$delimiter"/>
		<th>Original Hole Id ()</th><xsl:value-of select="$delimiter"/>
		<th>Original Job Reference ()</th><xsl:value-of select="$delimiter"/>
		<th>Originating Company ()</th>
		<xsl:text>&#xa;</xsl:text>
      </tr>

<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole | 
							 /Proj/Hole | 
							 /agsml/Proj/Hole |
							 /agsml/dbo.ge_data/Proj/Hole" />
</table>
</body>
</html>
</xsl:template>

<xsl:template match="Hole">	
	<tr>
	<td><xsl:value-of select="HoleId"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleType"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleStat"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleNatE"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleNatN"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleGref"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleGL"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleRem"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleFDep"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleStar"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HolePurp"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleTerm"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleEndD"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLett"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLocX"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLocY"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLocZ"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleDatm"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLRef"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleEtrv"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleNtrv"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLtrv"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleXtrl"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleYtrl"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleZtrl"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLat"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLong"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleElat"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleElon"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLlz"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLocm"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleLoca"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleClst"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleAlID"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleOffs"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleCnge"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleTran"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="FileFSet"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleNatD"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleOrid"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleOrJo"/></td><xsl:value-of select="$delimiter"/>
	<td><xsl:value-of select="HoleOrCo"/></td><xsl:value-of select="$delimiter"/>
	<xsl:text>&#xa;</xsl:text>
	</tr>
</xsl:template>

</xsl:stylesheet>