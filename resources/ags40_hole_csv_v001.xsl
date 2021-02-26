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

<xsl:template match="/">
		"Location identifier ()","type of activity ()","Status of information relating to this position ()","National Grid Location Easting (m)","National Grid Location Northing (m)","National grid referencing system used (m)","Ground Level (m)","Remarks ()","Final depth (m)","Started Date (dd/mmm/yyyy)","Purpose of activity at this location ()","Reason for activity termination ()","Ended Date (dd/mm/yyyy)","OSGB letter grid reference ()","Local grid x co-ordinate (m)","Local grid y co-ordinate (m)","Local grid z co-ordinate (m)","Local datum referencing system used ()","Local grid referencing system used ()","National Grid Easting of end of traverse (m)","National Grid Northing of end of traverse (m)","Ground level relative to datum of end of traverse (m)","Local grid easting of end of traverse (m)","Local grid northing of end of traverse (m)","Local elevation of end of traverse (m)","Latitude (deg(decimal))","	Longitude (deg(decimal))","Latitude of end of traverse (m)","Longitude of end of traverse ()","Projection Format ()","Method of Location ()","Location sub division within project ()","Hole cluster reference number ()","Alignment Identifier ()","Offset of hole from assigned alignment (m)","Chainage of hole on assigned alignment (m)","Reference to or details of algorithm used to calculate local grid reference","local ground levels or chainage ()",Associated file reference ()","National Datum Referencing System used ()","Original Hole Id ()","Original Job Reference ()","Originating Company ()"
<xsl:apply-templates select="/DataStructureAGSML/Proj/Hole | 
							 /Proj/Hole | 
							 /agsml/Proj/Hole |
							 /agsml/dbo.ge_data/Proj/Hole" />
</xsl:template>

<xsl:template match="Hole">	
		"<xsl:value-of select="HoleId"/>","<xsl:value-of select="HoleType"/>","<xsl:value-of select="HoleStat"/>","<xsl:value-of select="HoleNatE"/>","<xsl:value-of select="HoleNatN"/>","<xsl:value-of select="HoleGref"/>","<xsl:value-of select="HoleGL"/>","<xsl:value-of select="HoleRem"/>","<xsl:value-of select="HoleFDep"/>","<xsl:value-of select="HoleStar"/>","<xsl:value-of select="HolePurp"/>","<xsl:value-of select="HoleTerm"/>","<xsl:value-of select="HoleEndD"/>","<xsl:value-of select="HoleLett"/>","<xsl:value-of select="HoleLocX"/>","<xsl:value-of select="HoleLocY"/>","<xsl:value-of select="HoleLocZ"/>","<xsl:value-of select="HoleDatm"/>","<xsl:value-of select="HoleLRef"/>","<xsl:value-of select="HoleEtrv"/>","<xsl:value-of select="HoleNtrv"/>","<xsl:value-of select="HoleLtrv"/>","<xsl:value-of select="HoleXtrl"/>","<xsl:value-of select="HoleYtrl"/>","<xsl:value-of select="HoleZtrl"/>","<xsl:value-of select="HoleLat"/>","<xsl:value-of select="HoleLong"/>","<xsl:value-of select="HoleElat"/>","<xsl:value-of select="HoleElon"/>","	<xsl:value-of select="HoleLlz"/>","<xsl:value-of select="HoleLocm"/>","<xsl:value-of select="HoleLoca"/>","<xsl:value-of select="HoleClst"/>","<xsl:value-of select="HoleAlID"/>","<xsl:value-of select="HoleOffs"/>","<xsl:value-of select="HoleCnge"/>","<xsl:value-of select="HoleTran"/>","<xsl:value-of select="FileFSet"/>","<xsl:value-of select="HoleNatD"/>","<xsl:value-of select="HoleOrid"/>","<xsl:value-of select="HoleOrJo"/>","<xsl:value-of select="HoleOrCo"/>"
</xsl:template>

</xsl:stylesheet>