<?xml version="1.0" encoding="UTF-16"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:param name="host"/>
<xsl:param name="host2"/>
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
<xsl:param name="image"/>
<xsl:param name="dictionary"/>
<xsl:param name="version"/>
<xsl:param name="script"/>

<xsl:template match="/">
	<html>
	<head>
	<title>Project Summary</title>
	<link rel="stylesheet" type="text/css" href="/css/ags_format.css"  />
	<link rel="stylesheet" type="text/css" href="../css/ags_format.css"  />
	<script type="text/javascript" src="/js/ags_java.js"></script>
	<script type="text/javascript" src="../js/ags_java.js"></script>
	<xsl:comment>
			parameters passed
			host:<xsl:value-of select="$host"/>
			host2:<xsl:value-of select="$host2"/>
			host3:<xsl:value-of select="$host2"/>
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
			image:<xsl:value-of select="$image"/>
			dictionary:<xsl:value-of select="$dictionary"/>
			script:<xsl:value-of select="@script"/>
	</xsl:comment>
	</head>
	<body>
	<h2>AGS Development</h2>
	<h3>This project details the developement of and AGS Reference Dictionary Library for converting from AGS to various other data formats</h3>
	<p>The AGS data structure inception was to allow the accurate transfer of ground investigation data between software pckages. This reduced the risk of error due to duplicate data entry. 
	Dictionaries have been created for each of the evolving AGS date structures to allow the viewing and processing of data across various platforms.</p>  
	<h4>AGS Version 3.1</h4>
			<p><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$dictionary"/>&amp;version=3.1
			</xsl:attribute>Dictionary AGS 3.1
			</a></p>
	
	<h4>AGS Version 4.03</h4>
			<p><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$dictionary"/>&amp;version=4.03
			</xsl:attribute>Dictionary AGS 4.03
			</a></p>
	<h4>AGS Version 4.04</h4>
		<p><a>
			<xsl:attribute name="href">
			<xsl:value-of select="$host"/><xsl:value-of select="$dictionary"/>&amp;version=4.04
			</xsl:attribute>AGS 4.04
		</a></p>
	</body>
	</html> 
</xsl:template>
</xsl:stylesheet>