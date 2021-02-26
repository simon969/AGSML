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
            <Row><Cell><Data ss:Type="String">Transaction Code Descriptions (MAGIC) </Data></Cell></Row>
            <xsl:for-each select="ResponseBody/ActivityDetails/ActivityDetail">     
                <Row> 
                    <Cell>
                        <Data ss:Type="String">
                            <xsl:value-of select="TranCodeDesc" />
                        </Data>
                    </Cell>

                </Row>
            </xsl:for-each>

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
</xsl:stylesheet>