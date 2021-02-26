<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
>
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
<xsl:param name="group"/>
<xsl:param name="groups"/>
<xsl:param name="data"/>
<xsl:param name="options"/>
<xsl:param name="version"/>
<xsl:template match="/">
<html>
	<head>
		<title>Ground Engineering Data GIS table data</title>
		<link rel="stylesheet" type="text/css" href="../css/ags_format.css"  />
		<script type="text/javascript" src="../js/ags_java.js"></script>
	</head>
	<body>
		<xsl:comment>
			parameters passed
			host:<xsl:value-of select="$host"/>
			host:<xsl:value-of select="$host2"/>
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
			group:<xsl:value-of select="$group"/>
			groups:<xsl:value-of select="$groups"/>
			data:<xsl:value-of select="$data"/>
		</xsl:comment>
		<xsl:if test="$tables='ge_project' or $tables='' or normalize-space($tables)=''">
		<h2> ge_project table details </h2>
			<table>
				<tr>
				<th>Id</th>
				<th>groupId</th>
				
				<th>name</th>
				<th>description</th>
				<th>keywords</th>
				
				<th>start_date</th>
				<th>end_date</th>
				
				<th>locName</th>
				<th>locAddress</th>
				<th>locPostCode</th>
				<th>locMapReference</th>
				<th>locEast</th>
				<th>locNorth</th>
				<th>locLevel</th>
				<th>datumProjection</th>
				<th>locLatitude</th>
				<th>locLongitude</th>
				<th>locHeight</th>
				</tr>
				<xsl:apply-templates select="geXML/group/project | geXML/project | /project |*/project"/>
			</table>
		</xsl:if>
		<xsl:if test="$tables='ge_group' or $tables='' or normalize-space($tables)=''">
		<h2> ge_group table details </h2>
			<table>
				<tr>
				<th>Id</th>
				
				<th>name</th>
				<th>description</th>
				<th>keywords</th>
				
				<th>start_date</th>
				<th>end_date</th>
				
				<th>locName</th>
				<th>locAddress</th>
				<th>locPostCode</th>
				<th>locMapReference</th>
				<th>locEast</th>
				<th>locNorth</th>
				<th>locLevel</th>
				<th>datumProjection</th>
				<th>locLatitude</th>
				<th>locLongitude</th>
				<th>locHeight</th>
				</tr>
				<xsl:apply-templates select="geXML/group | /group | */group"/>
			</table>
		</xsl:if>
		<xsl:if test="$tables='ge_data' or $tables='' or normalize-space($tables)=''">
		<h2> ge_data table details </h2>
			<table>
				<tr>
				<th>Id</th>
				<th>projectId</th>
				
				<th>locName</th>
				<th>locAddress</th>
				<th>locPostCode</th>
				<th>locMapReference</th>
				<th>locEast</th>
				<th>locNorth</th>
				<th>locLevel</th>
				<th>datumProjection</th>
				<th>locLatitude</th>
				<th>locLongitude</th>
				<th>locHeight</th>
				
				<th>filename</th>
				<th>filesize</th>
				<th>fileext</th>
				<th>locHeight</th>
				<th>filename</th>
				<th>filesize</th>
				<th>fileext</th>
				<th>description</th>
				<th>keywords</th>
				
				<th>cstatus</th>
				<th>pstatus</th>
				<th>qstatus</th>
				<th>version</th>
				<th>vstatus</th>
				</tr>
				<xsl:apply-templates select="geXML/group/project/data | geXML/project/data | /project/data | /data |*/data"/>
			</table>
		</xsl:if>
	</body>
</html> 
</xsl:template>
<xsl:template match="group">
	<tr>
		<td><xsl:value-of select="id"/></td>
		
		<td><xsl:value-of select="name"/></td>
		<td><xsl:value-of select="description"/></td>
		<td><xsl:value-of select="keywords"/></td>
		
		<td><xsl:value-of select="locName"/></td>
		<td><xsl:value-of select="locAddress"/></td>
		<td><xsl:value-of select="locPostCode"/></td>
		<td><xsl:value-of select="locMapReference"/></td>
		<td><xsl:value-of select="locEast"/></td>
		<td><xsl:value-of select="locNorth"/></td>
		<td><xsl:value-of select="locGL"/></td>
		<td><xsl:value-of select="datumProjection"/></td>
		<td><xsl:value-of select="locLatitude"/></td>
		<td><xsl:value-of select="locLongitude"/></td>
		<td><xsl:value-of select="locHeight"/></td>
		<td>
		<a>
				<xsl:attribute name="href">
				<xsl:value-of select="$host"/><xsl:value-of select="$group"/>
				</xsl:attribute><xsl:value-of select="name"/>
		</a>
		</td>
	</tr>
</xsl:template>
<xsl:template match="project">
	<tr>
		<td><xsl:value-of select="id"/></td>
		<td><xsl:value-of select="groupId"/></td>
		
		<td><xsl:value-of select="name"/></td>
		<td><xsl:value-of select="description"/></td>
		<td><xsl:value-of select="keywords"/></td>
		
		<td><xsl:value-of select="locName"/></td>
		<td><xsl:value-of select="locAddress"/></td>
		<td><xsl:value-of select="locPostCode"/></td>
		<td><xsl:value-of select="locMapReference"/></td>
		<td><xsl:value-of select="locEast"/></td>
		<td><xsl:value-of select="locNorth"/></td>
		<td><xsl:value-of select="locGL"/></td>
		<td><xsl:value-of select="datumProjection"/></td>
		<td><xsl:value-of select="locLatitude"/></td>
		<td><xsl:value-of select="locLongitude"/></td>
		<td><xsl:value-of select="locHeight"/></td>
		<td>
		<a>
				<xsl:attribute name="href">
				<xsl:value-of select="$host"/><xsl:value-of select="$project"/>
				</xsl:attribute><xsl:value-of select="name"/>
		</a>
		</td>
	</tr>
</xsl:template>
<xsl:template match="data">
	<tr>
		<td><xsl:value-of select="id"/></td>
		<td><xsl:value-of select="projectid"/></td>
		
		<td><xsl:value-of select="locName"/></td>
		<td><xsl:value-of select="locAddress"/></td>
		<td><xsl:value-of select="locPostCode"/></td>
		<td><xsl:value-of select="locMapReference"/></td>
		<td><xsl:value-of select="locEast"/></td>
		<td><xsl:value-of select="locNorth"/></td>
		<td><xsl:value-of select="locGL"/></td>
		<td><xsl:value-of select="datumProjection"/></td>
		<td><xsl:value-of select="locLatitude"/></td>
		<td><xsl:value-of select="locLongitude"/></td>
		<td><xsl:value-of select="locHeight"/></td>
		
		<td><xsl:value-of select="filename"/></td>
		<td><xsl:value-of select="filesize"/></td>
		<td><xsl:value-of select="fileext"/></td>
		<td><xsl:value-of select="filetype"/></td>
		<td><xsl:value-of select="description"/></td>
		<td><xsl:value-of select="keywords"/></td>
		
		<td><xsl:value-of select="cstatus"/></td>
		<td><xsl:value-of select="pstatus"/></td>
		<td><xsl:value-of select="qstatus"/></td>
		<td><xsl:value-of select="version"/></td>
		<td><xsl:value-of select="vstatus"/></td>
		<td>
		<a>
				<xsl:attribute name="href">
				<xsl:value-of select="$host2"/><xsl:value-of select="Id"/>
				</xsl:attribute><xsl:value-of select="filename"/>
		</a>
		</td>
	</tr>
</xsl:template>
</xsl:stylesheet>