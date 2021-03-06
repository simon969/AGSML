<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
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

<xsl:template match="/">
<html>
<head>
<title>Project Summary</title>
		<xsl:choose>
			<xsl:when test="contains($host,'localhost')">
				<link rel="stylesheet" type="text/css" href="/css/ags_format.css" />
				<script type="text/javascript" src="/js/ags_java.js" />
			</xsl:when>
			<xsl:otherwise>
				<link rel="stylesheet" type="text/css" href="../css/ags_format.css" />
				<script type="text/javascript" src="../js/ags_java.js" />
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="string-length($css)>0">
			<link rel="stylesheet" type="text/css">
			<xsl:attribute name="href"><xsl:value-of select="$host_file" /><xsl:value-of select="$css" /></xsl:attribute>
			</link>
		</xsl:if>
		<xsl:if test="string-length($script)>0">
			<script type="text/javascript">
			<xsl:attribute name="src"><xsl:value-of select="$host_file" /><xsl:value-of select="$script" /></xsl:attribute>
			</script>
		</xsl:if>
</head>
<body>
	<xsl:comment>
		content generated by xml stylesheet: ags40_project_holes_v001.xsl
		copywrite AECOM 2019
		author: Simon Thomson (simon.thomson@aecom.com)
		date: 26-12-2019
		parameters passed
		host:<xsl:value-of select="$host" />
		host_file:<xsl:value-of select="$host_file" />
		host_view:<xsl:value-of select="$host_view" />
		user:<xsl:value-of select="$user" />
		hole:<xsl:value-of select="$hole" />
		holes:<xsl:value-of select="$holes" />
		project:<xsl:value-of select="$project" />
		projects:<xsl:value-of select="$projects" />
		geol:<xsl:value-of select="$geol" />
		geols:<xsl:value-of select="$geols" />
		table:<xsl:value-of select="$table" />
		tables:<xsl:value-of select="$tables" />
		options:<xsl:value-of select="$options" />
		version:<xsl:value-of select="$version" />
		dictionary<xsl:value-of select="$dictionary" />
		image:<xsl:value-of select="$image"/>
		script:<xsl:value-of select="$script" />
		css:<xsl:value-of select="$css" />
	</xsl:comment>
<xsl:apply-templates select="/DataStructureAGSML/Proj | 
							 /Proj | 
							 /agsml/Proj |
							 /agsml/dbo.ge_data/Proj" />
</body>
</html>
</xsl:template>

<xsl:template match="Proj">
	<h2> Project Details </h2>

	<div style="position: absolute; top:0px; left:550px; width:200px; height:50px">
		<img >
			<xsl:attribute name="src"><xsl:value-of select="$host_file" /><xsl:value-of select="$image" /></xsl:attribute>
		</img>
	</div>

	<table>
	<tr><td>Project Name:</td><td><xsl:value-of select="ProjName" /></td></tr>
	<tr><td>Project Id:</td><td><xsl:value-of select="ProjId" /></td></tr>
	<tr><td>Project Client:</td><td><xsl:value-of select="ProjClnt" /></td></tr>
	<tr><td>Project Eng:</td><td><xsl:value-of select="ProjEng" /></td></tr>
	<tr><td>Project Producer:</td><td><xsl:value-of select="ProjProd" /></td></tr>
	<tr><td>Project Date:</td><td><xsl:value-of select="ProjDate" /></td></tr>
	<tr><td>Project Datum</td><td><xsl:value-of select="ProjGrid" /></td></tr>
	<tr><td>Project Coordinate System</td><td><xsl:value-of select="ProjCoor" /></td></tr>
	<tr><td>Project Memo</td><td><xsl:value-of select="ProjMemo" /></td></tr>
	</table>

	<h3>Hole Information (Hole)</h3>
	<table id="Hole">
		<th>Location identifier ()</th>
		<th>type of activity ()</th>
		<th>Status of information relating to this position ()</th>
		<th>National Grid Location Easting (m)</th>
		<th>National Grid Location Northing (m)</th>
		<th>National grid referencing system used (m)</th>
		<th>Ground Level (m)</th>
		<th>Remarks ()</th>
		<th>Final depth (m)</th>
		<th>Started Date (dd/mmm/yyyy)</th>
		<th>Purpose of activity at this location ()</th>
		<th>Reason for activity termination ()</th>
		<th>Ended Date (dd/mm/yyyy)</th>
		<th>OSGB letter grid reference ()</th>
		<th>Local grid x co-ordinate (m)</th>
		<th>Local grid y co-ordinate (m)</th>
		<th>Local grid z co-ordinate (m)</th>
		<th>Local datum referencing system used ()</th>
		<th>Local grid referencing system used ()</th>
		<th>National Grid Easting of end of traverse (m)</th>
		<th>National Grid Northing of end of traverse (m)</th>
		<th>Ground level relative to datum of end of traverse (m)</th>
		<th>Local grid easting of end of traverse (m)</th>
		<th>Local grid northing of end of traverse (m)</th>
		<th>Local elevation of end of traverse (m)</th>
		<th>Latitude (deg(decimal))</th>
		<th>Longitude (deg(decimal))</th>
		<th>Latitude of end of traverse (m)</th>
		<th>Longitude of end of traverse ()</th>
		<th>Projection Format ()</th>
		<th>Method of Location ()</th>
		<th>Location sub division within project ()</th>
		<th>Hole cluster reference number ()</th>
		<th>Alignment Identifier ()</th>
		<th>Offset of hole from assigned alignment (m)</th>
		<th>Chainage of hole on assigned alignment (m)</th>
		<th>Reference to or details of algorithm used to calculate local grid reference, local ground levels or chainage ()</th>
		<th>Associated file reference ()</th>
		<th>National Datum Referencing System used ()</th>
		<th>Original Hole Id ()</th>
		<th>Original Job Reference ()</th>
		<th>Originating Company ()</th>
		
		<xsl:apply-templates select="holes/Hole | Holes/Hole | Hole" />
		
		</table>
		
</xsl:template>

<xsl:template match="Hole">	
		<tr>
		<td>
				<xsl:element name="a">
					<xsl:attribute name="href">
						<xsl:value-of select="$host_view"/><xsl:value-of select="$hole"/>&amp;holes=<xsl:value-of select="HoleId" />
					</xsl:attribute>
					<xsl:value-of select="HoleId"/>
				</xsl:element>
		</td>
		<td><xsl:value-of select="HoleType"/></td>
		<td><xsl:value-of select="HoleStat"/></td>
		<td><xsl:value-of select="HoleNatE"/></td>
		<td><xsl:value-of select="HoleNatN"/></td>
		<td><xsl:value-of select="HoleGref"/></td>
		<td><xsl:value-of select="HoleGL"/></td>
		<td>
 			<xsl:if test="string-length(HoleRem) &gt; 12">
			<xsl:value-of select="concat(substring(HoleRem,1,12),'...')"/>
			</xsl:if>
		 	<xsl:if test="string-length(HoleRem) &lt; 13">
			<xsl:value-of select="HoleRem"/>
			</xsl:if> 
		</td>
		<td><xsl:value-of select="HoleFDep"/></td>
		<td><xsl:value-of select="HoleStar"/></td>
		<td><xsl:value-of select="HolePurp"/></td>
		<td><xsl:value-of select="HoleTerm"/></td>
		<td><xsl:value-of select="HoleEndD"/></td>
		<td><xsl:value-of select="HoleLett"/></td>
		<td><xsl:value-of select="HoleLocX"/></td>
		<td><xsl:value-of select="HoleLocY"/></td>
		<td><xsl:value-of select="HoleLocZ"/></td>
		<td><xsl:value-of select="HoleDatm"/></td>
		<td><xsl:value-of select="HoleLRef"/></td>
		<td><xsl:value-of select="HoleEtrv"/></td>
		<td><xsl:value-of select="HoleNtrv"/></td>
		<td><xsl:value-of select="HoleLtrv"/></td>
		<td><xsl:value-of select="HoleXtrl"/></td>
		<td><xsl:value-of select="HoleYtrl"/></td>
		<td><xsl:value-of select="HoleZtrl"/></td>
		<td><xsl:value-of select="HoleLat"/></td>
		<td><xsl:value-of select="HoleLong"/></td>
		<td><xsl:value-of select="HoleElat"/></td>
		<td><xsl:value-of select="HoleElon"/></td>
		<td><xsl:value-of select="HoleLlz"/></td>
		<td><xsl:value-of select="HoleLocm"/></td>
		<td><xsl:value-of select="HoleLoca"/></td>
		<td><xsl:value-of select="HoleClst"/></td>
		<td><xsl:value-of select="HoleAlID"/></td>
		<td><xsl:value-of select="HoleOffs"/></td>
		<td><xsl:value-of select="HoleCnge"/></td>
		<td><xsl:value-of select="HoleTran"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
		<td><xsl:value-of select="HoleNatD"/></td>
		<td><xsl:value-of select="HoleOrid"/></td>
		<td><xsl:value-of select="HoleOrJo"/></td>
		<td><xsl:value-of select="HoleOrCo"/></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>