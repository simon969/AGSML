<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
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
		<title>Project Summary</title>
		<link rel="stylesheet" type="text/css" href="/css/ags_format.css"  />
		<script type="text/javascript" src="/js/ags_java.js"></script>
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
		<xsl:apply-templates select="/DataStructureAGSML/Proj | /Proj | /agsml/Proj"/>
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
	<h2>Stratification and Samples</h2>
	<table>
			<th>HoleId</th>
			<th>Max Depth (m)</th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Geol
			</xsl:attribute>Strata (Geol)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Detl
			</xsl:attribute>Stratum detail descriptions (Detl)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Samp
			</xsl:attribute>Samples(Samp)
			</a></th>
			<xsl:apply-templates select="Hole">
			<xsl:with-param name="testGroup">strataDetailSamples</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Drilling Information</h2>
	<table>
			<th>HoleId</th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Bkfl
			</xsl:attribute>Backfill details (Bkfl)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Core
			</xsl:attribute>Rotary core information (Core)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Cdia
			</xsl:attribute>Casing diameter by depth (Cdia)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Chis
			</xsl:attribute>Chiselling details (Chis)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Dobs
			</xsl:attribute>Drilling observations (Dobs)</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Drem
			</xsl:attribute>Drilling remarks (Drem)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Flsh
			</xsl:attribute>Rotary core flush (Flsh)
			</a></th>
			
			<xsl:apply-templates select="Hole">
			<xsl:with-param name="testGroup">drillInfo</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Insitu Strength and Stiffness Testing</h2>
	<table>
			<th>HoleId</th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Ispt
			</xsl:attribute>Standard Penetration Tests (Ispt)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Dprg
			</xsl:attribute>Dynamic Probe Tests General (count)
			</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Dprb
			</xsl:attribute>Dynamic Probe Test (count)
			</a></th>
			
			<xsl:apply-templates select="Hole">
			<xsl:with-param name="testGroup">insituStrengthStiffness</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Laboratory Strength and Stiffness Testing</h2>
	<table>
			<th>HoleId</th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Cbrg
			</xsl:attribute>California Bearing Ratio General (Cbrg)</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Cbrt
			</xsl:attribute>California Bearing Ratio Test(Cbrt)</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Cong
			</xsl:attribute>Consolidation test general (Cong)</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Cont
			</xsl:attribute>Consolidation test (Cont)</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Trig
			</xsl:attribute>Triaxial general (Trig)</a></th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Trix
			</xsl:attribute>Triaxial (Trix)</a></th>
			
			<xsl:apply-templates select="Hole">
			<xsl:with-param name="testGroup">labStrengthStiffness</xsl:with-param>
			</xsl:apply-templates>
	</table>
	<h2>Laboratory Index Testing</h2>
	<table>
			<th>HoleId</th>
			<th><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$table"/>&amp;tables=Clss
			</xsl:attribute>Classification(Clss)</a></th>
			
			<xsl:apply-templates select="Hole">
			<xsl:with-param name="testGroup">labIndex</xsl:with-param>
			</xsl:apply-templates>
	</table>
</xsl:template>


<xsl:template match="Hole">
<xsl:param name="testGroup"/>
	<xsl:if test="$testGroup='strataDetailSamples'">
		<tr>
		<td>
			<a>
				<xsl:attribute name="href">
				<xsl:value-of select="$host"/><xsl:value-of select="$hole"/>&amp;holes=<xsl:value-of select="HoleId"/>
				</xsl:attribute><xsl:value-of select="HoleId"/>
			</a>
		</td>
		<td><xsl:value-of select="descendant::GeolBase[last()]"/></td>
		<td><xsl:value-of select="count(Geol)"/></td>
		<td><xsl:value-of select="count(Geol/Detl)"/></td>
		<td><xsl:value-of select="count(Geol/Samp)"/></td>
		</tr>
	</xsl:if>
	<xsl:if test="$testGroup='labStrengthStiffness'">
		<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="count(Geol/Cbrg)"/></td>
		<td><xsl:value-of select="count(Geol/Cbrt)"/></td>
		<td><xsl:value-of select="count(Geol/Cong)"/></td>
		<td><xsl:value-of select="count(Geol/Cons)"/></td>
		<td><xsl:value-of select="count(Geol/Trig)"/></td>
		<td><xsl:value-of select="count(Geol/Trix)"/></td></tr>
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
		<td><xsl:value-of select="count(Geol/Clss)"/></td>
		</tr>
	</xsl:if>
	<xsl:if test="$testGroup='drillInfo'">
		<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="count(Bkfl)"/></td>
		<td><xsl:value-of select="count(Core)"/></td>
		<td><xsl:value-of select="count(Cdia)"/></td>
		<td><xsl:value-of select="count(Chis)"/></td>
		<td><xsl:value-of select="count(Dobs)"/></td>
		<td><xsl:value-of select="count(Drem)"/></td>
		<td><xsl:value-of select="count(Flsh)"/></td>
		</tr>
	</xsl:if>
</xsl:template>
</xsl:stylesheet>