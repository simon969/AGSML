<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<html>
	<head>
	<title>ge_data table</title>
	<link rel="stylesheet" type="text/css" href="/css/ags_format.css"  />
	<script type="text/javascript" src="/js/ags_java.js"></script>
	</head>
	<body>
	<table id="ge_data">
		<tr>
			
			<th>Name</th>
			<th>Address</th>
			<th>PostCode</th>
			<th>Map Reference</th>
			<th>Eastings</th>
			<th>Northings</th>
			<th>Level</th>
			<th>Datum Projection</th>
			<th>Latitude</th>
			<th>Longitude</th>
			<th>Ellipsoid Height</th>
			<th>Folder</th>

			<th>Created By</th>
			<th>Created DateTme</th>
			<th>Last Edited By</th>
			<th>Last Edited DateTime</th>
			<th>Operations</th>

			<th>File Name</th>
			<th>File Size (bytes)</th>
			<th>File Extension</th>
			<th>File Content Type</th>
			<th>Last Modified Date</th>
			<th>Description</th>
			<th>Keywords</th>
			<th>ProjectId</th>
			<th>Confidentiality</th>
			<th>Publish Status</th>
			<th>Qualitative Status</th>
			<th>Version</th>
			<th>Version Status</th>
		</tr>			
 		<xsl:apply-templates select="geXML/project/data"/>
	</table>
	</body>
	</html> 
</xsl:template>

<xsl:template match="data">
		<tr>
			<td><xsl:value-of select="locName"/></td>
			<td><xsl:value-of select="locAddress"/></td>
			<td><xsl:value-of select="locPostCode"/></td>
			<td><xsl:value-of select="locMapReference"/></td>
			<td><xsl:value-of select="locEast"/></td>
			<td><xsl:value-of select="locNorth"/></td>
			<td><xsl:value-of select="locLevel"/></td>
			<td><xsl:value-of select="datumProjection"/></td>
			<td><xsl:value-of select="locLatitude"/></td>
			<td><xsl:value-of select="locLongitude"/></td>
			<td><xsl:value-of select="locHeight"/></td>
			<td><xsl:value-of select="folder"/></td>
			
			<td><xsl:value-of select="createdId"/></td>
			<td><xsl:value-of select="createdDT"/></td>
			<td><xsl:value-of select="editedId"/></td>
			<td><xsl:value-of select="editedDT"/></td>
			<td><xsl:value-of select="operations"/></td>
			
			<td><a> <xsl:attribute name="href">
					<xsl:value-of select="href" />
					</xsl:attribute>
					<xsl:value-of select="filename"/>
				</a></td>
			<td><xsl:value-of select="filesize"/></td>
			<td><xsl:value-of select="fileext"/></td>
			<td><xsl:value-of select="filetype"/></td>
			<td><xsl:value-of select="filedate"/></td>
			<td><xsl:value-of select="description"/></td>
			<td><xsl:value-of select="keywords"/></td>
			<td><xsl:value-of select="projectId"/></td>
			<td><xsl:value-of select="cstatus"/></td>
			<td><xsl:value-of select="pstatus"/></td>
			<td><xsl:value-of select="qstatus"/></td>
			<td><xsl:value-of select="version"/></td>
			<td><xsl:value-of select="vstatus"/></td>
		</tr>
</xsl:template>
</xsl:stylesheet>