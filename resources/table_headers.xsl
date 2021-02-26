<?xml version="1.0" encoding="UTF-8"?>
<xml:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html>
  <head>
  <title>AGS Data Table Headers</title>
  <link rel="stylesheet" type="text/css" href="E:\Projects\NetBeansProjects\AGSML\schemas\ags_format.css" />
  </head>
  <body>
  <h2>Hole Table Headers <xsl:value-of select="DictionaryAGSML"/> </h2>
  <xsl:apply-templates/> 
  </body>
</html>
</xsl:template>

<xsl:template match="table">
<xsl:value-of select="agsml/name"/>

<table>
<xsl:for-each select="field">
<tr>
<td><xsl:value-of select="agsml/name"/></td>
<td><xsl:value-of select="description"/></td>
<td><xsl:value-of select="unit"/></td>
</tr>
</xsl:for-each>
</table>
</xsl:template>

</xsl:stylesheet>