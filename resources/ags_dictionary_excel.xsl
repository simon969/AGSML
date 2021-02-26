<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">

    <Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
     xmlns:o="urn:schemas-microsoft-com:office:office"
     xmlns:x="urn:schemas-microsoft-com:office:excel"
     xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
     xmlns:html="http://www.w3.org/TR/REC-html40">

     <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
      <Author>tbarbedo</Author>
      <Created>2009-05-29T18:21:48Z</Created>
      <Version>12.00</Version>
     </DocumentProperties>
     <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
      <WindowHeight>8895</WindowHeight>
      <WindowWidth>18015</WindowWidth>
      <WindowTopX>0</WindowTopX>
      <WindowTopY>105</WindowTopY>
      <ProtectStructure>False</ProtectStructure>
      <ProtectWindows>False</ProtectWindows>
     </ExcelWorkbook>
     <Styles>
      <Style ss:ID="Default" ss:Name="Normal">
       <Alignment ss:Vertical="Bottom"/>
       <Borders/>
       <Font ss:FontName="Calibri" x:Family="Swiss" ss:Size="11" ss:Color="#000000"/>
       <Interior/>
       <NumberFormat/>
       <Protection/>
      </Style>
     </Styles>
     <Worksheet ss:Name="Sheet1">
      <Table ss:ExpandedColumnCount="1" ss:ExpandedRowCount="5000" x:FullColumns="1"
       x:FullRows="1" ss:DefaultRowHeight="15">
	   
	    	<th>table</th>
			<th>name</th>
			<th>description</th>
			<th>units</th>
			<th>example</th>
			<th>ags_version</th>
			<th>ags_name</th>
			<th>agsml_name</th>
			<th>gint_name</th>
			<xsl:apply-templates select="/root/DictionaryAGSML"/>
           
      </Table>
      <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
       <PageSetup>
        <Header x:Margin="0.3"/>
        <Footer x:Margin="0.3"/>
        <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
       </PageSetup>
       <Selected/>
       <Panes>
        <Pane>
         <Number>3</Number>
         <ActiveRow>1</ActiveRow>
        </Pane>
       </Panes>
       <ProtectObjects>False</ProtectObjects>
       <ProtectScenarios>False</ProtectScenarios>
      </WorksheetOptions>
     </Worksheet>
    </Workbook>

</xsl:template>

<xsl:template match="DictionaryAGSML">
 <xsl:apply-templates select="table">
 <xsl:with-param name="ags_version" select="@agsversion" />
 </xsl:apply-templates>
 </xsl:template>
 
<xsl:template match="table">
 <xsl:param name="ags_version" />
 <xsl:apply-templates select="field">
 <xsl:with-param name="table_name" select="@name" />
 <xsl:with-param name="ags_version" select="$ags_version" />
 </xsl:apply-templates>
</xsl:template>

<xsl:template match="field">
<xsl:param name="table_name" />
<xsl:param name="ags_version" />
	<tr>
		<td><xsl:value-of select="$table_name"/></td>
		<td><xsl:value-of select="@name"/></td>
		<td><xsl:value-of select="description"/></td>  
		<td><xsl:value-of select="unit"/></td> 
		<td><xsl:value-of select="example"/></td>
		<td><xsl:value-of select="$ags_version"/></td>
		<td><xsl:value-of select="ags/name"/></td>
		<td><xsl:value-of select="agsml/name"/></td>
		<td><xsl:value-of select="gint/name"/></td>
	</tr>
</xsl:template>
</xsl:stylesheet>