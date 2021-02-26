<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" type="text/xsl" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"> 
<xsl:template match="/">

<html>
  <head>
  <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
  <title><xsl:value-of select="DataStructureAGSML/Hole/HoleId"/></title>
  <link rel="stylesheet" type="text/css" href="ags_format.css" />
  <script type="text/javascript" src="ags_java.js"></script>
  </head>
  <body onload="loadSVGL()">
  <h2>Silvertown Tunnel Tender Design </h2>
  <h2>Ground Investigation Data for Hole  
				<xsl:element name="a">
					<xsl:attribute name="href">
						http://www.google.com/maps/place/
						<xsl:value-of select="DataStructureAGSML/Hole/GintCoordinateLatitude"/>,
						<xsl:value-of select="DataStructureAGSML/Hole/GintCoordinateLongitude"/>
					</xsl:attribute>
						<xsl:value-of select="DataStructureAGSML/Hole/HoleId"/>
				</xsl:element>
  <div style="position: absolute; top:0px; left:1100px; width:200px; height:25px">
<img width="140" height="115" alt="logo" src="TTT-FLO logo.png"/>
</div>
  </h2>
  <table>
  <th><h2><a onclick="toggleVisible('holeInformation')">holeInformation</a></h2></th>
  <th><h2><a onclick="toggleVisible('laboratoryTesting')">laboratoryTesting</a></h2></th>
  <th><h2><a onclick="toggleVisible('monitoring')">monitoring</a></h2></th> 
  <th><h2><a onclick="toggleVisible('fieldTesting')">fieldTesting</a></h2></th>
  <th><h2><a onclick="toggleVisible('groundInformation')">groundInformation</a></h2></th>
  </table>
  <xsl:apply-templates/> 
  </body>
  </html>
</xsl:template>

<xsl:template match="DataStructureAGSML">
				
		<div id="holeInformation">
		<h2>holeInformation</h2>
		<xsl:apply-templates select="Hole"/>
		<xsl:apply-templates select="Hole/holeInformation"/>
		</div>
 
		<div id="laboratoryTesting">
		<xsl:apply-templates select="Hole/holeInformation/laboratoryTesting"/>
		</div>
		
		<div id="monitoring">
		<xsl:apply-templates select="Hole/monitoring"/>
		</div>
		
		<div id="fieldTesting"> 
		<xsl:apply-templates select="Hole/fieldTesting"/>
		</div>
		
		<div id="groundInformation">
		<xsl:apply-templates select="Hole/groundInformation"/>
		</div>
</xsl:template>

<xsl:template match="Hole">
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
		
		<tr>
		<td><xsl:value-of select="HoleId"/></td>
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
		
		<xsl:if test="string-length(HoleRem) &gt; 12">
		<tr>
		<td>*Remarks(cont)</td><td colspan="12" ><xsl:value-of select="HoleRem"/></td>
		</tr>
		</xsl:if>
</table>

</xsl:template>



<xsl:template match="holeInformation">

<h3>Project Information (Proj)</h3>
	<xsl:if test="count(Proj)>0">
	<table id="Proj">
		<th>project identifier</th>
		<th></th>
		<th>project identifier</th>
		<th>project title</th>
		<th>Client name</th>
		<th>Contractor name</th>
		<th>Project Engineer</th>
		<th>Date of production of data</th>
		<th>AGS Edition number</th>
		<th>General project comments</th>
		<th>Monitoring contractor identifier</th>
		<th>Data file producer</th>
		<th>Project Datum</th>
		<th>Project Datum</th>
		<th></th>
		<th>Data file recipient</th>
		<th>Issue sequence number</th>
		<th>Status of data within submission</th>
		<th>File Set</th>
		<th>Location of project</th>
		<xsl:apply-templates  select="Proj" />
	</table>
	</xsl:if>

<h3>Backfill details (Bkfl)</h3>
	<xsl:if test="count(Bkfl)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the top of section (m)</th>
	<th>Depth to the base of section ()</th>
	<th>Backfill Legend code ()</th>
	<th>Date of backfill ()</th>
	<th>Backfill Remarks (m)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Bkfl" />
	</table>
	</xsl:if>

<h3>Casing diameter by depth (Cdia)</h3>
	<xsl:if test="count(Cdia)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth achieved at CDIA_HOLE (m)</th>
	<th>Casing diameter (mm)</th>
	<th>Remarks ()</th>
	<xsl:apply-templates select="Cdia" />
	</table>
	</xsl:if>
	
<h3>Chiselling details (Chis)</h3>
	<xsl:if test="count(Chis)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>depth at start of chiselling (m)</th>
	<th>depth at end of chiselling (m)</th>
	<th>Time taken (hhmm)</th>
	<th>Chiselling tool used ()</th>
	<th>Remarks ()</th>
	<xsl:apply-templates select="Chis" />
	</table>
	</xsl:if>		

<h3>Drilling Information (Drem) </h3>
	<xsl:if test="count(Drem)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of DREM_REM (m)</th>
	<th>Base depth (m)</th>
	<th>General remarks ()</th>
	<xsl:apply-templates select="Drem" />
	</table>
	</xsl:if>		

<h3>Rotary Core Flush Details (Flsh) </h3>
	<xsl:if test="count(Flsh)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to top of flush zone (m)</th>
	<th>Depth to bottom of flush zone (m)</th>
	<th>Type of flush ()</th>
	<th>Flush Return (%)</th>
	<th>Colour of flush return ()</th>
	<xsl:apply-templates select="Flsh" />
	</table>
	</xsl:if>	

<h3>Hole diameter by depth (Hdia)</h3>
	<xsl:if test="count(Hdia)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth achieved at HDIA_HOLE (m)</th>
	<th>Casing diameter (mm)</th>
	<th>Remarks ()</th>
	<xsl:apply-templates select="Hdia" />
	</table>
	</xsl:if>	

<h3>Depth related hole information (Hdph)</h3>
	<xsl:if test="count(Hdph)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to TOP of section (m)</th>
	<th>Depth to BASE of section (m)</th>
	<th>Type of exploratory hole ()</th>
	<th>Date of start of section (dd/mm/yyyy)</th>
	<th>Time of start of section (hh:mm)</th>
	<th>Date of end of section (dd/mm/yyyy)</th>
	<th>Time of end of section (hh:mm)</th>
	<th>Name of crew ()</th>
	<th>The definitive person responsible for logging the section ()</th>
	<th>Plant used ()</th>
	<th>Shoring/Support used ()</th>
	<th>Rotary Remarks ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Hdph" />
	</table>
	</xsl:if>
	
<h3>Hole progress by time (Ptim)</h3>
	<xsl:if test="count(Ptim)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Date of reading (dd/mm/yyyy)</th>
	<th>Time of reading (hr:mm:ss)</th>
	<th>Hole depth at PTIM_TIME (m)</th>
	<th>Depth of casing at PTIM_TIME (m)</th>
	<th>Depth to water at PTIM_TIME (m)</th>
	<th>Remarks at PTIM_TIME ()</th>
	<xsl:apply-templates select="Ptim" />
	</table>
	</xsl:if>
</xsl:template>

<xsl:template match="laboratoryTesting">
<h2>laboratoryTesting</h2>
<h3>California Bearing Test General (Cbrg)</h3>
	<xsl:if test="count(Cbrg)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Sample Condition ()</th>
	<th>Method of remoulding ()</th>
	<th>Notes on CBR Test ()</th>
	<th>Natural moisture content (%)</th>
	<th>Initial moisture content (%)</th>
	<th>Weight percent retained on 20mm sieve (%)</th>
	<th>Amount of total swell recorded (mm)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Cbrg" />
	</table>
	</xsl:if>
<h3>California Bearing Test (Cbrt)</h3>
	<xsl:if test="count(Cbrt)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>CBR test number ()</th>
	<th>CBR at top (%)</th>
	<th>CBR at bottom (%)</th>
	<th>Moisture content at top (%)</th>
	<th>Moisture content at bottom (%)</th>
	<th>Bulk Density (Mg/m3)</th>
	<th>Dry density (%)</th>
	<th>Amount of swell recorded (mm)</th>
	<th>Test specific remarks ()</th>
	<xsl:apply-templates select="Cbrg/cbrResults/Cbrt" />
	</table>
	</xsl:if>
	
<h3>Chalk Tests (Chlk)</h3>
	<xsl:if test="count(Chlkt)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Chalk crushing test number ()</th>
	<th>Chalk crusiing value as BS 1377 Part 4 Cl 6 ()</th>
	<th>Chalk natural moisture content (%)</th>
	<th>Chalk saturated moisture content (%)</th>
	<th>Weight percent of material retained on 10mm sieve (%)</th>
	<th>Remarks ()</th>
	<th>Chalk calcium carbonate conetent (%)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Chlk" />
	</table>
	</xsl:if>	
	
<h3>Classification Tests (Clss)</h3>
	<xsl:if test="count(Clss)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Natural moisture content (%)</th>
	<th>Liquid Limit ()</th>
	<th>Plastic limit ()</th>
	<th>Bulk Density (Mg/m3)</th>
	<th>Dry density (%)</th>
	<th>Particle density (Mg/m3)</th>
	<th>Percentage passing 425 mu m sieve (%)</th>
	<th>Method of preperation ()</th>
	<th>Shrinkage limit (%)</th>
	<th>Linear shrinkage (%)</th>
	<th>Hand vane undrained shear strength (peak) (kN/m2)</th>
	<th>Hand vane undrained shear strength (remoulded) (kN/m2)</th>
	<th>Pocket penetrometer undrained shear strength (kN/m2)</th>
	<th>Laboratory vane undrained shear strength (peak) (kN/m2)</th>
	<th>Laboratory hand vane undrained shear strength (remoulded) (kN/m2)</th>
	<th>Notes on classification testing ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Clss" />
	</table>
	</xsl:if>
	
<h3>Compactions Tests General (Cmpg)</h3>
	<xsl:if test="count(Cmpg)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Compaction test type ()</th>
	<th>Compaction mould type ()</th>
	<th>Weight percent of material on 37.5mm sieve (%staxtz)</th>
	<th>Weight percent of material on 20mm sieve (%)</th>
	<th>Particle density measured or assumed (Mg/m3)</th>
	<th>Maximum dry density (Mg/m3)</th>
	<th>Moisture content at maximum dry density (%)</th>
	<th>Notes on compaction test required under BS 1377:1980 ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Cmpg" />
	</table>
	</xsl:if>	

<h3>Compactions Tests (Cmpt)</h3>
	<xsl:if test="count(Cmpt)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Compaction point number ()</th>
	<th>Moisture content (%)</th>
	<th>Maximum dry density (Mg/m3)</th>
	<xsl:apply-templates select="Cmpg/comResults/Cmpt" />
	</table>
	</xsl:if>	

<h3>Contaminant and Chemical Testing (Cnmt)</h3>
	<xsl:if test="count(Cnmt)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Determinand ()</th>
	<th>Test type ()</th>
	<th>Test result ()</th>
	<th>Test result units ()</th>
	<th>Chemical Abstraction Service registry number (where appropriate) ()</th>
	<th>Test method ()</th>
	<th>Sample Preparation ()</th>
	<th>Comments on test ()</th>
	<th>Method lower detection limit ()</th>
	<th>Client/laboratory preferred name of determinand ()</th>
	<th>Client/laboratory preferred name of determinand ()</th>
	<xsl:apply-templates select="Cnmt" />
	</table>
	</xsl:if>
	
<h3>Consolidation Test - General (Cong)</h3>
	<xsl:if test="count(Cong)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Oedometer or Rowe, primary or secondary consolidation ()</th>
	<th>Sample condition ()</th>
	<th>Test details including method statement ()</th>
	<th>Coefficient of volume compressibility ovre CONG_INCD (m2/MN)</th>
	<th>Defined stress range ()</th>
	<th>Test specimen diameter (mm)</th>
	<th>Test specimen height (mm)</th>
	<th>Initial moisture content (%)</th>
	<th>Final moisture content (%)</th>
	<th>Initial bulk density (Mg/m3)</th>
	<th>Initial Dry Density (Mg/m3)</th>
	<th>Particle density (BS 1377) with # if assumed (Mg/m3)</th>
	<th>Initial degree of saturation (%)</th>
	<th>Swelling pressure (kN/m2)</th>
	<th>Height change of specimen on saturation as percentage of original height (%)</th>
	<th>Associated file reference ()</th>
	<th>Initial voids ratio ()</th>
	<xsl:apply-templates select="Cong" />
	</table>
	</xsl:if>
	
<h3>Consolidation Test (Cont)</h3>
	<xsl:if test="count(Cont)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Oedometer stress increment number ()</th>
	<th>Initial voids ratio ()</th>
	<th>Stress at end of stress incremenet/decrement (kN/m2)</th>
	<th>Voids ratio at end of stress increment ()</th>
	<th>Reported coefficient of volume compressibility over stress increment (m2/MN)</th>
	<th>Reported coefficient of consolidation over stress increment ()</th>
	<th>Coefficient of secondary compression over stress increment ()</th>
	<th>Coefficient of consolidation determined by the root time method ()</th>
	<th>Coefficient of consolidation determined by the log time method ()</th>
	<th>Remarks including method used to determine coefficients reported under CONS_INMV and selected CONS_INCV (from either of CONS_CVRT or CONS_CVLG) ()</th>
	<th>Compression Index ()</th>
	<th>Swelling Index ()</th>
	<th>Past overconsolidation pressure ()</th>
	<xsl:apply-templates select="Cong/conResult/Cont" />
	</table>
	</xsl:if>

<h3>Frost Susceptibility (Frst)</h3>
	<xsl:if test="count(Frst)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Sample condition ()</th>
	<th>Notes on frost susceptibility ()</th>
	<th>Maximum dry density (Mg/m3)</th>
	<th>Moisture content (%)</th>
	<th>Frost heave, first specimen (%)</th>
	<th>Frost heave, second specimen (%)</th>
	<th>Frost heave, third specimen (%)</th>
	<th>Mean heave of 3 specimens (%)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Frst" />
	</table>
	</xsl:if>

<h3>Particle Size Distribution Analysis Data (Grad)</h3>
	<xsl:if test="count(Grad)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Sieve or particle size (mm)</th>
	<th>Percentage passing finer (%)</th>
	<th>Grade analysis test type ()</th>
	<xsl:apply-templates select="Grad" />
	</table>
	</xsl:if>	

<h3>Moisture Condition Value (MCV)  Test General (Mcvg)</h3>
	<xsl:if test="count(Mcvg)>0">
	<table Id="Mcvg">
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Notes on MCV test as BS 1377 Part 4 Cl 5.4, and 5.5 Test report items a) and c) ()</th>
	<th>Weight percent of material retained on 20mm sieve (%)</th>
	<th>Natural moisture content (%)</th>
	<th>MCV precalibrated value as BS 1377 Part 4 and whether highre or lower ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Mcvg" />
	</table>
	</xsl:if>
	
<h3>Moisture Condition Value (MCV) Test (Mcvt)</h3>
	<xsl:if test="count(Mcvt)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Moisture Condition Value (MCV) test number ()</th>
	<th>Moisture content (%)</th>
	<th>Moisture Condition Value (MCV) at MCVT_MC ()</th>
	<th>Bulk density related to the MCVT_RELK MCV (Mg/m3)</th>
	<xsl:apply-templates select="Mcvg/mcvResults/Mcvt" />
	</table>
	</xsl:if>

<h3>Laboratory Permeability Tests (Ptst)</h3>
	<xsl:if test="count(Ptst)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Permeability test number ()</th>
	<th>Permeability test method ()</th>
	<th>Sample condition ()</th>
	<th>Size cut off of material too coarse for testing (mm)</th>
	<th>Diameter of test sample (mm)</th>
	<th>Length of test sample (mm)</th>
	<th>Initial moisture content (%)</th>
	<th>Initial bulk density of test sample (Mg/m3)</th>
	<th>Dry density of test sample (Mg/m3)</th>
	<th>Void ratio of test sample ()</th>
	<th>Coefficient of permability (m/s)</th>
	<th>Mean effective stress at which permeability measured (when measured in triaxila cell) (kN/m2)</th>
	<th>Initial degree of saturation (%)</th>
	<th>Final degree of saturation (%)</th>
	<th>Particle density measured or assumed (Mg/m3)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Ptst" />
	</table>
	</xsl:if>

<h3>Relative Density Test (Reld)</h3>
	<xsl:if test="count(Reld)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Method of test ()</th>
	<th>Maximum dry density as BS 1377 part 4 cl 4 (Mg/m3)</th>
	<th>Weight percent of sample retained on 37.5mm sieve (%)</th>
	<th>Weight percent of sample retained on 6.3mm sieve (%)</th>
	<th>Weight percent of sample retained on 2mm sieve (%)</th>
	<th>Minimum dry density as BS 1377 part 4 cl 4 (Mg/m3)</th>
	<xsl:apply-templates select="Reld" />
	</table>
	</xsl:if>
	
<h3>Rock Sample Tests (Rock)</h3>
	<xsl:if test="count(Rock)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Uncorrected point load (Is5) (MN/m2)</th>
	<th>Size corrected point load index (Is50) (MN/m2)</th>
	<th>Point load test type (A,D,L or P) ()</th>
	<th>Uniaxial compressive strength (size corrected) (%)</th>
	<th>Remarks ()</th>
	<th>Details in addition to ROCK_PLTF ()</th>
	<th>Notes on uniaxial compressive strength test, including sample dimensions ()</th>
	<th>Elastic modulus (MN/m2)</th>
	<th>Poisson's ratio ()</th>
	<th>Tensile strength test by the Brazilian method (MN/m2)</th>
	<th>Rock Porosity (%)</th>
	<th>Notes on type of porosity test ()</th>
	<th>Natural moisture content (%)</th>
	<th>Bulk density (Mg/m3)</th>
	<th>Dry density (Mg/m3)</th>
	<th>Aggregate particle density (Mg/m3)</th>
	<th>Aggregate particle density test method and notes ()</th>
	<th>Aggregate water absorption (%)</th>
	<th>Aggregate water absorption test method and notes ()</th>
	<th>Slake durability Index (%)</th>
	<th>Slake durability test method and notes ()</th>
	<th>Aggregate Soundness Test (%)</th>
	<th>Aggregate soundness test method and notes ()</th>
	<th>Aggregate Crushing Value (%)</th>
	<th>Aggregate crushing value test method and notes ()</th>
	<th>Aggregate Impcat Value (%)</th>
	<th>Aggregate impact value test method and notes ()</th>
	<th>Aggregate Los Angeles abrasion (%)</th>
	<th>Aggregate Los Angeles abrasion test method and notes ()</th>
	<th>Aggregate Abrasion Value ()</th>
	<th>Aggregate Polished Stone Value (%)</th>
	<th>Aggregate Flakiness Index (%)</th>
	<th>Aggregate Elongation Index (%)</th>
	<th>Specimen description (%)</th>
	<th>Shore hardness ()</th>
	<th>P-wave velocity (m/s)</th>
	<th>S-wave velocity (m/s)</th>
	<th>Dynamic Elastic Modulus (GPa)</th>
	<th>Shear modulus derived from ROCK_SWAV (GPa)</th>
	<th>Rock swelling index (kN/m2)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Rock" />
	</table>
	</xsl:if>	
	
<h3>Shear Box Testing - General (Shbg)</h3>
	<xsl:if test="count(Shbg)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Test type eg small shear box, large shear box, ring shear ()</th>
	<th>Test notes eg undisturbed, pre-exsiting shear, recompacted, rock joint, cut plane ()</th>
	<th>Peak cohesion intercept (kN/m2)</th>
	<th>Peak angle of friction (deg)</th>
	<th>Residual ohesion intercept (kN/m2)</th>
	<th>Residual angle of friction (deg)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Shbg" />
	</table>
	</xsl:if>
	
<h3>Shear Box Testing (Shbt)</h3>
	<xsl:if test="count(Shbt)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen depth (m)</th>
	<th>Shear box stage number ()</th>
	<th>Bulk density (Mg/m3)</th>
	<th>Dry density (Mg/m3)</th>
	<th>Shear box normal stress (kN/m2)</th>
	<th>Displacement rate (mm/min)</th>
	<th>Shear box peak shear stress (kN/m2)</th>
	<th>Shear box residual shear stress (kN/m2)</th>
	<th>Displacement at peak shear strength (mm)</th>
	<th>Displacement at residual shear strength (mm)</th>
	<th>Particle density, measured or assumed (Mg/m3)</th>
	<th>Initial voids ratio ()</th>
	<th>Initial moisture content (%)</th>
	<th>Final moisture content (%)</th>
	<th>Remarks on test stage ()</th>
	<xsl:apply-templates select="Shbt" />
	</table>
	</xsl:if>	

<h3>Suction Testing (Suct)</h3>
	<xsl:if test="count(Suct)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Test method ()</th>
	<th>Suction value (kN/m2)</th>
	<xsl:apply-templates select="Suct" />
	</table>
	</xsl:if>
	
<h3>Ten Per Cent Fines (Tnpc)</h3>
	<xsl:if test="count(Tnpc)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Ten per cent fine test number ()</th>
	<th>Notes on testing as per BS 812 ()</th>
	<th>10% fines values on dry aggregate (kN)</th>
	<th>10% fines values on wet aggregate (kN)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Tnpc" />
	</table>
	</xsl:if>
	
<h3>Triaxial Test General (Trig)</h3>
	<xsl:if test="count(Trig)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Test type ()</th>
	<th>Sample condition ()</th>
	<th>Value of undrained shear strength (kN/m2)</th>
	<th>Cohesion intercept associated with TRIG_PHI (kN/m2)</th>
	<th>Angle of friction for effective shear strength triaxial test (kN/m2)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Trig" />
	</table>
	</xsl:if>

<h3>Triaxial Test (Trix)</h3>
	<xsl:if test="count(Trix)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to the TOP of sample (m)</th>
	<th>Sample Reference number ()</th>
	<th>Sample type ()</th>
	<th>Specimen reference number ()</th>
	<th>Specimen depth (m)</th>
	<th>Triaxial test/stage number ()</th>
	<th>Specimen diameter (mm)</th>
	<th>Speciment initial moisture content (%)</th>
	<th>Total cell pressure (kN/m2)</th>
	<th>Deviator stress at failure (kN/m2)</th>
	<th>Sample length (mm)</th>
	<th>Bulk Density (Mg/m3)</th>
	<th>Dry density (%)</th>
	<th>Porewater pressure at failure (kN/m2)</th>
	<th>Pore water pressure at start of shear stage (%)</th>
	<th>Value of undrained Shear Strength (%)</th>
	<th>Strain at failure (%)</th>
	<th>Mode of failure ()</th>
	<xsl:apply-templates select="Trig/triResults/Trix" />
	</table>
	</xsl:if>
</xsl:template>

<xsl:template match="monitoring">

<h2>monitoring</h2>

<h3>Single point instrument installation details (Inst)</h3>
	<xsl:if test="count(Inst)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to reference level of instrument from HOLE_GL or HOLE_LOCZ (m)</th>
	<th>Instrument reference number ()</th>
	<th>Instrument type ()</th>
	<th>Instrument installation date (dd/mm/yyyy)</th>
	<th>Inclination of instrument (measured positively down from horizontal) (deg)</th>
	<th>Direction of INST_DIP (degrees from north) (deg)</th>
	<th>Pressure reading at zero applied pressure (kN/m2)</th>
	<th>Details of insitu density test ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Inst" />
	</table>
	</xsl:if>
	
<h3>Single point instrument readings (Iobs)</h3>
	<xsl:if test="count(Iobs)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to reference level of instrument from HOLE_GL or HOLE_LOCZ (m)</th>
	<th>Instrument reference number ()</th>
	<th>Date of reading (dd/mm/yyyy)</th>
	<th>Time of reading (hr:mm:ss)</th>
	<th>Reading of pressure (kN/m2)</th>
	<th>Level of settlement point relative to datum (m)</th>
	<th>Remarks ()</th>
	<xsl:apply-templates select="Iobs" />
	</table>
	</xsl:if>
	
<h3>Monitoring Point (Monp)</h3>
	<xsl:if test="count(Monp)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Distance of monitoring point from HOLE_ID (m)</th>
	<th>Monitoring Point identifier ()</th>
	<th>Installation date (dd/mm/yyyy)</th>
	<th>Instrument type ()</th>
	<th>Distance to top of response zone from HOLE_ID datum (m)</th>
	<th>Distance to base of response zone from HOLE_ID datum (m)</th>
	<th>Bearing of monitoring axis A (compass bearing) (deg)</th>
	<th>Bearing of monitoring axis B (compass bearing) (deg)</th>
	<th>Bearing of monitoring axis C (compass bearing) (deg)</th>
	<th>Inclination of instrument axis A (measured positively down from horizontal) (deg)</th>
	<th>Inclination of instrument axis B (measured positively down from horizontal) (deg)</th>
	<th>Inclination of instrument axis C (measured positively down from horizontal) (deg)</th>
	<th>Reading sign convention in direction A ()</th>
	<th>Reading sign convention in direction B ()</th>
	<th>Reading sign convention in direction C ()</th>
	<th>Remarks ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Monp" />
	</table>
	</xsl:if>
	
<h3>In Situ Contaminant and Chemical Testing (Icct)</h3>
	<xsl:if test="count(monResults/Icct)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Distance from reference point ()</th>
	<th>Monitoring Point ID (optional) ()</th>
	<th>Date of reading (and time for SQL and AGSML) ()</th>
	<th>Time of Reading (hr:min:sec)</th>
	<th>Determinand ()</th>
	<th>Test type ()</th>
	<th>Test result ()</th>
	<th>Test result units ()</th>
	<th>Test method/instrument type ()</th>
	<th>Chemical Abstract Service registry number (where appropriate) ()</th>
	<th>Sample preparation ()</th>
	<th>Comments on Test ()</th>
	<th>Method/instrumentation lower detection limit ()</th>
	<th>Method/instrumentation upper detection limit ()</th>
	<th>Client/laboratory preferred name of determinand ()</th>
	<th>Name of testing laboratory/Organisation ()</th>
	<th>Accrediting body (when appropriate) ()</th>
	<th>Laboratory Internal Reference ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="monResults/Icct" />
	</table>
	</xsl:if>
	
<h3>Monitoring Reading (Monr)</h3>
	<xsl:if test="count(monResults/Monr)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Distance of monitoring point from HOLE_ID (m)</th>
	<th>Monitoring Point identifier ()</th>
	<th>Date of reading (dd/mm/yyyy)</th>
	<th>Time of reading (hr:mm:ss)</th>
	<th>Distance A from HOLE_ID (slip indicator top rod) (m)</th>
	<th>Distance B from HOLE_ID (slip indicator top rod) (m)</th>
	<th>Displacement in direction A (mm)</th>
	<th>Displacement in direction B (mm)</th>
	<th>Displacement in direction C (mm)</th>
	<th>Pressure (kN/m2)</th>
	<th>Rotataion/Tilt in direction A (deg)</th>
	<th>Rotation/Tilt in direction B (deg)</th>
	<th>Rotation in direction C (deg)</th>
	<th>Strain in direction A (%)</th>
	<th>Strain in direction B (%)</th>
	<th>Strain in direction C ()</th>
	<th>Force (kN)</th>
	<th>Temperature (DegC)</th>
	<th>Depth to water from HOLE_ID datum (m)</th>
	<th>Absolute position (Eastings) (m)</th>
	<th>Absolute position (Northings) (m)</th>
	<th>Absolute position (Level) (m)</th>
	<th>Head of water above tip (m)</th>
	<th>Guage length (m)</th>
	<th>Flow (l/s)</th>
	<th>Details for instrument reference, probe logger, serial numbersRemarks ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="monResults/Monr" />
	</table>
	</xsl:if>
	
<h3>Piezometer Installation Details (Pref)</h3>
	<xsl:if test="count(Pref)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to reference level of piezometer tip (m)</th>
	<th>Installation date (dd/mm/yyyy)</th>
	<th>Instrument type ()</th>
	<th>Depth to top of response zone (m)</th>
	<th>Depth to base or response zone (m)</th>
	<th>Remarks ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Pref" />
	</table>
	</xsl:if>
	
<h3>Piezometer Readings (Pobs)</h3>
	<xsl:if test="count(Pobs)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to reference level of piezometer tip (m)</th>
	<th>Date of piezometer reading (dd/mm/yyyy)</th>
	<th>Time of piezometer reading (hr:mm:ss)</th>
	<th>Depth to water below ground surface (m)</th>
	<th>Head of water above piezometer tip (m)</th>
	<th>Remarks ()</th>
	<xsl:apply-templates select="Pobs" />
	</table>
	</xsl:if>	
	
<h3>Profiling Instrument Installation Details (Prof)</h3>
	<xsl:if test="count(Prof)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Instrument reference number ()</th>
	<th>Date of reading (dd/mm/yyyy)</th>
	<th>Profiling instrument type ()</th>
	<th>Depth to top of response zone from HOLE_GFL or HOLE_LOCZ (m)</th>
	<th>Depth to base of response zone from HOLE_GL or HOLE_LOCZ (m)</th>
	<th>Orientation of primary keyword (degrees from north) (deg)</th>
	<th>Remarks ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Prof" />
	</table>
	</xsl:if>

<h3>Profiling Instrument Readings (Prob)</h3>
	<xsl:if test="count(Prob)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Date of reading (dd/mm/yyyy hr:mm:ss)</th>
	<th>Time of piezometer reading (hr:mm:ss)</th>
	<th>Depth to reference level of piezometer tip (m)</th>
	<th>Rod or inclinometer guage length (m)</th>
	<th>Depth to top of slip obstruction from HOLE_GL or HOLE_LOCZ to slip indicator (m)</th>
	<th>Depth to base of slip obstruction from HOLE_GL or HOLE_LOCZ to slip indicator (m)</th>
	<th>Primary keyway displacement direction A (mm)</th>
	<th>Displacement direction B (mm)</th>
	<th>Displacement direction C (mm)</th>
	<th>Displacement direction D (mm)</th>
	<th>Remarks ()</th>
	<xsl:apply-templates select="Prob" />
	</table>
	</xsl:if>
	
<h3>Time Related Remarks (Trem)</h3>
	<xsl:if test="count(Trem)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Date of remark (dd/mm/yyyy)</th>
	<th>Time of remark (hr:mm:ss)</th>
	<th>Time related remark ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates select="Trem" />
	</table>
	</xsl:if>
	
</xsl:template>

<xsl:template match="fieldTesting">
<h2>fieldTesting</h2>
<h3>Dynamic Probe Test - General (Dprg) </h3>
	<xsl:if test="count(Dprg)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Dynamic probe type ()</th>
		<th>Test method ()</th>
		<th>Hammer mass (kg)</th>
		<th>Standard drop height (mm)</th>
		<th>Cone base diameter (mm)</th>
		<th>Rod diameter (mm)</th>
		<th>Type of anvil damper ()</th>
		<th>Depth of cone if left in the ground (m)</th>
		<th>General remarks ()</th>
		<th>Cone angle (deg)</th>
		<th>Rod Mass (kg/m)</th>
		<th>Associated file reference ()</th>
		<xsl:apply-templates  select="Dprg" />
	</table>
	</xsl:if>
	
<h3>Dynamic Probe Test(Dprb) </h3>
	<xsl:if test="count(Dprb)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to start of dynamic propbe increment (m)</th>
	<th>Dynamic probe blows for increment DPRB_INC ()</th>
	<th>Maximum torque required to rotate rods (Nm)</th>
	<th>Duration of delay before increment started (hhmm)</th>
	<th>Dynamic probe increment (mm)</th>
	<th>Notes on events during increment ()</th>
	<xsl:apply-templates  select="Dprb" />
	</table>
	</xsl:if>
	
<h3>Insitu CBR Test (Icbr) </h3>
	<xsl:if test="count(Icbr)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of test (m)</th>
	<th>Test number ()</th>
	<th>Details of apparatus and kentledge ()</th>
	<th>CBR value (%)</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Seating force (N)</th>
	<th>Surcharge Pressure (kN/m2)</th>
	<th>Type of CBR ()</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
	<xsl:apply-templates  select="Icbr" />
	</table>
	</xsl:if>
	
<h3>Insitu Density Test (Iden) </h3>
	<xsl:if test="count(Iden)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of insitu density test ()</th>
	<th>Test number ()</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Details of insitu density test ()</th>
	<th>Insitu bulk density (Mg/m3)</th>
	<th>Moisture content relating to in situ test (%)</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
	<xsl:apply-templates  select="Iden" />
	</table>
	</xsl:if>
	
<h3>On Site Volatile Headspace Tetsing Using Flame Ionisation Detector (Ifid) </h3>
	<xsl:if test="count(Ifid)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of headspace test sample (m)</th>
	<th>Test number ()</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Details of FID used amd method description ()</th>
	<th>Result of FID analysis (ppmv)</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
		<xsl:apply-templates  select="Ifid" />
	</table>
	</xsl:if>
	
<h3>On Site Volatile Headspace Testing by Photo Ionisation Detector (Ipid) </h3>
	<xsl:if test="count(Ipid)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of headspace test sample (m)</th>
	<th>Test number ()</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Details of FID used amd method description ()</th>
	<th>Result of PID analysis (ppmv)</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
	<xsl:apply-templates  select="Ipid" />
	</table>
	</xsl:if>
	
<h3>Insitu Permeability Test (Iprm) </h3>
	<xsl:if test="count(Dprg)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to TOP of test zone (m)</th>
	<th>Depth to BASE of test zone (m)</th>
	<th>Stage number of multistage packer test ()</th>
	<th>Test number ()</th>
	<th>Type of test ()</th>
	<th>depth to water in borehole or piezometer immediately prior to test (m)</th>
	<th>Depth to water at start of test (m)</th>
	<th>Diameter of test zone (m)</th>
	<th>Diameters of standpipe or casing (m)</th>
	<th>Permeability (m/s)</th>
	<th>Test Remarks ()</th>
	<th>Average flow during packer test stage (l/s)</th>
	<th>Depth to assumed standing water level (m)</th>
	<th>Applied total head of water during test stage at centre of packer test zone (m)</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates  select="Dprg" />
	</table>
	</xsl:if>

<h3>Insitu Redox Test (Irdx) </h3>
	<xsl:if test="count(Irdx)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of redox test (m)</th>
	<th>Test number ()</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Details of redox test and probe type ()</th>
	<th>pH ()</th>
	<th>Redox potential (mV)</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
	<xsl:apply-templates  select="Irdx" />
	</table>
	</xsl:if>

<h3>Insitu Resestivity Test (Ires) </h3>
	<xsl:if test="count(Ires)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth range to which in situ resistivity test relates (m)</th>
	<th>Test number ()</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Result (ohm cm)</th>
	<th>Details of test eg electrode spacing and configuration ()</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
	<xsl:apply-templates  select="Ires" />
	</table>
	</xsl:if>	
	
<h3>Insitu Vane Test (Ivan) </h3>
	<xsl:if test="count(Ivan)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of vane test (m)</th>
	<th>Test number ()</th>
	<th>Details of vane test, vane size, vane type ()</th>
	<th>Vane test result (kN/m2)</th>
	<th>Vane test remoulded result (kN/m2)</th>
	<th>Hand penetrometer result (kN/m2)</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
	<xsl:apply-templates  select="Ivan" />
	</table>
	</xsl:if>		
	
<h3>Insitu Resestivity Test (Ires) </h3>
	<xsl:if test="count(Ires)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth range to which in situ resistivity test relates (m)</th>
	<th>Test number ()</th>
	<th>Test date (dd/mm/yyyy)</th>
	<th>Result (ohm cm)</th>
	<th>Details of test eg electrode spacing and configuration ()</th>
	<th>Stratum reference shown on trial pit or traverse sketch ()</th>
	<xsl:apply-templates  select="Ires" />
	</table>
	</xsl:if>
	
<h3>Standard Penetration Test Results (Ispt) </h3>
	<xsl:if test="count(Ispt)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth to top of test (m)</th>
	<th>Number of blows for seating drive ()</th>
	<th>Number of blows for main test drive ()</th>
	<th>Total pentration of seating drive and test drive (mm)</th>
	<th>SPT'N' value ()</th>
	<th>SPT reported result ()</th>
	<th>Casing depth at time of test (m)</th>
	<th>Depth to water at time of test (m)</th>
	<th>Type of SPT Test ()</th>
	<th>Self-weight penetration (mm)</th>
	<th>Number of blows for 1st Increment (Seating) ()</th>
	<th>Number of blows for 2nd Increment (Seating) ()</th>
	<th>Number of blows for 3rd Increment (Test) ()</th>
	<th>Number of blows for 4th Increment (Test) ()</th>
	<th>Number of blows for 5th Increment (Test) ()</th>
	<th>Number of blows for 6th Increment (Test) ()</th>
	<th>Penetration for 1st Increment (Seating Drive) (mm)</th>
	<th>Penetration for 2nd Increment (Seating Drive) (mm)</th>
	<th>Penetration for 3rd Increment (Test) (mm)</th>
	<th>Penetration for 4th Increment (Test) (mm)</th>
	<th>Penetration for 5th Increment (Test) (mm)</th>
	<th>Penetration for 6th Increment (Test) (mm)</th>
	<xsl:apply-templates  select="Ispt" />
	</table>
	</xsl:if>	

<h3>Pressuremeter Test Results, General (Prtg) </h3>
	<xsl:if test="count(Prtg)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Reference no of test ()</th>
	<th>Depth of test (m)</th>
	<th>Date of test (dd/mm/yyyy)</th>
	<th>Pressuremeter type ()</th>
	<th>Uninflated diameter of pressuremeter (mm)</th>
	<th>Estimated horizontal stress arm (pair) 1 (kN/m2)</th>
	<th>Estimated horizontal stress arm (pair) 2 (kN/m2)</th>
	<th>Estimated horizontal stress arm (pair) 3 (kN/m2)</th>
	<th>Estimated horizontal stress average (kN/m2)</th>
	<th>Initial shear modulus arm (pair) 1 (MN/m2)</th>
	<th>Initial shear modulus arm (pair) 2 (MN/m2)</th>
	<th>Initial shear modulus arm (pair) 3 (MN/m2)</th>
	<th>Initial shear modulus average (MN/m2)</th>
	<th>Undrained shear strength arm (pair) 1 (kN/m2)</th>
	<th>Undrained shear strength arm (pair) 2 (kN/m2)</th>
	<th>Undrained shear strength arm (pair) 3 (kN/m2)</th>
	<th>Undrained shear strength average (kN/m2)</th>
	<th>Limit pressure arm (pair) 1 (kN/m2)</th>
	<th>Limit pressure arm (pair) 2 (kN/m2)</th>
	<th>Limit pressure arm (pair) 3 (kN/m2)</th>
	<th>Limit pressure average (kN/m2)</th>
	<th>Angle of friction arm (pair) 1 (deg)</th>
	<th>Angle of friction arm (pair) 2 (deg)</th>
	<th>Angle of friction arm (pair) 3 (deg)</th>
	<th>Angle of friction average (deg)</th>
	<th>Angle of dilation arm (pair) 1 (deg)</th>
	<th>Angle of dilation arm (pair) 2 (deg)</th>
	<th>Angle of dilation arm (pair) 3 (deg)</th>
	<th>Angle of dilation average (deg)</th>
	<th>Angle of friction at constant volume (fcrit) used (deg)</th>
	<th>Remarks ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates  select="Prtg" />
	</table>
	</xsl:if>
	
<h3>Pressuremeter Test Data (Prtd) </h3>
	<xsl:if test="count(Prtd)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Reference no of test ()</th>
	<th>Depth of test (m)</th>
	<th>Sequence No ()</th>
	<th>Date of test (dd/mm/yyyy)</th>
	<th>Pressuremeter type ()</th>
	<th>Uninflated diameter of pressuremeter (mm)</th>
	<th>Arm (pair) 1 displacement (mm)</th>
	<th>Arm (pair) 2 displacement (mm)</th>
	<th>Arm (pair) 3 displacement (mm)</th>
	<th>Total Pressure (pair) 1 (kN/m2)</th>
	<th>Total Pressure (pair) 2 (kN/m2)</th>
	<th>Total Pressure (pair) 3 (kN/m2)</th>
	<th>Pore Pressure cell A (kN/m2)</th>
	<th>Pore Pressure cell B (kN/m2)</th>
	<th>Remarks ()</th>
	<th>Total pressure in test cell (kN/m2)</th>
	<th>Volume change in test cell (cm3)</th>
	<xsl:apply-templates  select="Prtd" />
	</table>
	</xsl:if>	
	
<h3>Pressuremeter Test Results, Individual Loops (Prtl) </h3>
	<xsl:if test="count(Prtl)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Reference no of test ()</th>
	<th>Depth of test (m)</th>
	<th>Date of test (dd/mm/yyyy)</th>
	<th>Unload Reload loop number ()</th>
	<th>Pressuremeter type ()</th>
	<th>Uninflated diameter of pressuremeter (mm)</th>
	<th>Unload reload shear modulus arm (pair) 1 (MN/m2)</th>
	<th>Unload reload shear modulus arm (pair) 2 (MN/m2)</th>
	<th>Unload reload shear modulus arm (pair) 3 (MN/m2)</th>
	<th>Unload reload shear modulus average (MN/m2)</th>
	<xsl:apply-templates  select="Prtl" />
	</table>
	</xsl:if>		
	
<h3>Pumping Test (Pump) </h3>
	<xsl:if test="count(Pump)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Date of reading (dd/mm/yyyy)</th>
	<th>Time of reading (hh:mm:ss)</th>
	<th>Depth to water below ground (m)</th>
	<th>Pumping rate from hole (l/s)</th>
	<th>Remark ()</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates  select="Pump" />
	</table>
	</xsl:if>
	
<h3>Static Cone Penetration Test (Stcn) </h3>
	<xsl:if test="count(Stcn)>0">
	<table>
	<th>Exploratory hole or location equivalent ()</th>
	<th>Depth of results for static cone test (m)</th>
	<th>Static cone test type ()</th>
	<th>Static cone identification reference ()</th>
	<th>Static cone resistance (MN/m2)</th>
	<th>Local unit side friction resistance (kN/m2)</th>
	<th>Porewater pressure (kN/m2)</th>
	<th>Second porewater pressure (kN/m2)</th>
	<th>Porewater pressure (kN/m2)</th>
	<th>Conductivity (uS/cm)</th>
	<th>Temperature (DegC)</th>
	<th>pH reading ()</th>
	<th>Slope indicator no. 1 (deg)</th>
	<th>Slope indicator no. 2 (deg)</th>
	<th>Redox potential reading (mV)</th>
	<th>Fluorescence intensity (%)</th>
	<th>Photo-multiplier tube reading (counts/s)</th>
	<th>Photo ionization detector reading (uV)</th>
	<th>Flame ionization detector reading (uV)</th>
	<th>Associated file reference ()</th>
	<xsl:apply-templates  select="Stcn" />
	</table>
	</xsl:if>
	
</xsl:template>

<xsl:template match="groundInformation">
<h2>groundInformation</h2>
<h3> Geology Information </h3>
	<xsl:if test="count(Geol)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to the TOP of stratum (m)</th>
		<th>Depth to the BASE of stratum ()</th>
		<th>General Description of stratum ()</th>
		<th>Legend Code ()</th>
		<th>Geology Code ()</th>
		<th>Secondary Geology Code ()</th>
		<th>Stratum reference shown on trial pit or traverse sketch ()</th>
		<th>Associated file reference ()</th>
		<xsl:apply-templates  select="Geol" />
	</table>
	</xsl:if>

<h3>Core Information </h3>
	<xsl:if test="count(Core)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to TOP of core run (m)</th>
		<th>Depth to BOT of core run (m)</th>
		<th>Percentage of core recovered in core run (TCR) (%)</th>
		<th>Percentage of solid core recovered in core run (SCR) (%)</th>
		<th>Rock Quality Designation for core run (RQD) (%)</th>
		<th>Rotary Remarks ()</th>
		<th>Core diameter (mm)</th>
		<th>Associated file reference ()</th>
		<xsl:apply-templates select="Core" />
	</table>
	</xsl:if>

<h3> Detail Information </h3>
	<xsl:if test="count(Detl)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to TOP of detail description (m)</th>
		<th>Depth to BASE of detail description (m)</th>
		<th>Detail description (m)</th>
		<xsl:apply-templates select="Detl" />
	</table>
</xsl:if>

<h3> Discontinuity Information </h3>
	<xsl:if test="count(Disc)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to TOP in hole or distance to start traverse, pf discontinuity zone, or discontinuity (m)</th>
		<th>Depth to BASE in hole or distance to end of traverse, of discontinuity zone. (m)</th>
		<th>Discontinuity set reference number ()</th>
		<th>Discontinuity number ()</th>
		<th>Type of discontinuity ()</th>
		<th>Dip of discontinuity (deg)</th>
		<th>Dip direction of discontinuity (deg)</th>
		<th>Small scale roughness (ISRM 1978) ()</th>
		<th>Intermediate scale planarity (ISRM 1978) ()</th>
		<th>Large scale waviness, wavelength (ISRM 1978) (m)</th>
		<th>Large scale waviness, amplitude (ISRM 1978) (m)</th>
		<th>Joint Roughness Coefficient ()</th>
		<th>Surface Appearance ()</th>
		<th>Discontinuity aperture measurement ()</th>
		<th>Discontinuity aperture observation ()</th>
		<th>Infilling material ()</th>
		<th>Discontinuity termination (lower, upper) (ISRM 1978) ()</th>
		<th>Persistance measurement (m)</th>
		<th>Discontinuity wall strength (MPa)</th>
		<th>Discontinuity wall weathering ()</th>
		<th>Seepage rating (ISRM 1978) ()</th>
		<th>Water flow estimates (l/min)</th>
		<th>Remarks ()</th>
		<th>Associated file reference ()</th>
		<xsl:apply-templates select="Disc" />
	</table>
</xsl:if>
<h3> Fracture Information </h3>
	<xsl:if test="count(Frac)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to top in hole or distance to start on traverse, of the zone (m)</th>
		<th>Depth to base in hole or distance to end on traverse, of the zone (m)</th>
		<th>Fracture set reference number ()</th>
		<th>Frcature index over zone (frcatures per meter) ()</th>
		<th>Maximum fracture spacing over zone (mm)</th>
		<th>Average fracture spacing over zone (mm)</th>
		<th>Minimum fracture spacing over zone (mm)</th>
		<th>Associated file reference ()</th>
		<xsl:apply-templates select="Frac" />
	</table>
	</xsl:if>
<h3> Water Strike Information </h3>
	<xsl:if test="count(Wstk)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to water strike (m)</th>
		<th>Minutes after strike (min)</th>
		<th>Casing depth at time of water strike (m)</th>
		<th>Date of water strike (dd/mm/yyyy)</th>
		<th>Time of water strike (hhmm)</th>
		<th>Depth to water strike after WSTK_NMIN minutes (m)</th>
		<th>Flow rate remarks ()</th>
		<th>Depth at which water strike sealed by casing (m)</th>
		<xsl:apply-templates select="Wstk" />
	</table>
	</xsl:if>
<h3> Weathering Information </h3>
	<xsl:if test="count(Weth)>0">
	<table>
		<th>Exploratory hole or location equivalent ()</th>
		<th>Depth to TOP of weathering subdivision (m)</th>
		<th>Depth to BASE of weathering subdivision (m)</th>
		<th>Material weathering grade ()</th>
		<th>Renmarks, weathering system used ()</th>
		<xsl:apply-templates select="Weth" />
	</table>
	</xsl:if>	
</xsl:template>

<xsl:template match="Bkfl">
	<tr>
		<td><xsl:value-of select="PointID"/></td>
		<td><xsl:value-of select="BkflTop"/></td>
		<td><xsl:value-of select="BkflBase"/></td>
		<td><xsl:value-of select="BkflLeg"/></td>
		<td><xsl:value-of select="BkflDate"/></td>
		<td><xsl:value-of select="BkflRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cbrg">
	<tr>
		<td><xsl:value-of select="PointID"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="CbrgCond"/></td>
		<td><xsl:value-of select="CbrgMeth"/></td>
		<td><xsl:value-of select="CbrgRem"/></td>
		<td><xsl:value-of select="CbrgNMC"/></td>
		<td><xsl:value-of select="CbrgIMC"/></td>
		<td><xsl:value-of select="Cbrg200"/></td>
		<td><xsl:value-of select="CbrgSwel"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cbrt">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="CbrtTesn"/></td>
		<td><xsl:value-of select="CbrtTop"/></td>
		<td><xsl:value-of select="CbrtBot"/></td>
		<td><xsl:value-of select="CbrtMct"/></td>
		<td><xsl:value-of select="CbrtMcbt"/></td>
		<td><xsl:value-of select="CbrtBDen"/></td>
		<td><xsl:value-of select="CbrtDDen"/></td>
		<td><xsl:value-of select="CbrtSwel"/></td>
		<td><xsl:value-of select="CbrtRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cdia">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="CdiaCdep"/></td>
		<td><xsl:value-of select="CdiaHole"/></td>
		<td><xsl:value-of select="CdiaRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Chis">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="ChisFrom"/></td>
		<td><xsl:value-of select="ChisTo"/></td>
		<td><xsl:value-of select="ChisTime"/></td>
		<td><xsl:value-of select="ChisTool"/></td>
		<td><xsl:value-of select="ChisRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Chlk">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="ChlkTesn"/></td>
		<td><xsl:value-of select="ChlkCcv"/></td>
		<td><xsl:value-of select="ChlkMc"/></td>
		<td><xsl:value-of select="ChlkSmc"/></td>
		<td><xsl:value-of select="Chlk010"/></td>
		<td><xsl:value-of select="ChlkRem"/></td>
		<td><xsl:value-of select="ChlkCarb"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Clss">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="ClssNMC"/></td>
		<td><xsl:value-of select="ClssLL"/></td>
		<td><xsl:value-of select="ClssPl"/></td>
		<td><xsl:value-of select="ClssBDen"/></td>
		<td><xsl:value-of select="ClssDDen"/></td>
		<td><xsl:value-of select="ClssPD"/></td>
		<td><xsl:value-of select="Clss425"/></td>
		<td><xsl:value-of select="ClssPrep"/></td>
		<td><xsl:value-of select="ClssSLim"/></td>
		<td><xsl:value-of select="ClssLS"/></td>
		<td><xsl:value-of select="ClssHVP"/></td>
		<td><xsl:value-of select="ClssHVR"/></td>
		<td><xsl:value-of select="ClssPPen"/></td>
		<td><xsl:value-of select="ClssVnPk"/></td>
		<td><xsl:value-of select="ClssVnRm"/></td>
		<td><xsl:value-of select="ClssRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cmpg">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="CmpgType"/></td>
		<td><xsl:value-of select="CmpgMold"/></td>
		<td><xsl:value-of select="CMPG375"/></td>
		<td><xsl:value-of select="Cmpg200"/></td>
		<td><xsl:value-of select="CmpgPden"/></td>
		<td><xsl:value-of select="CmpgMaxD"/></td>
		<td><xsl:value-of select="CmpgMCop"/></td>
		<td><xsl:value-of select="CmpgRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cmpt">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="CmpgTesn"/></td>
		<td><xsl:value-of select="CmptMC"/></td>
		<td><xsl:value-of select="CmptDDen"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cnmt">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="CnmtType"/></td>
		<td><xsl:value-of select="Cnmt_Ttyp"/></td>
		<td><xsl:value-of select="Cnmt_Resl"/></td>
		<td><xsl:value-of select="CnmtUnit"/></td>
		<td><xsl:value-of select="CnmtCAS"/></td>
		<td><xsl:value-of select="CnmtMeth"/></td>
		<td><xsl:value-of select="CnmtPrep"/></td>
		<td><xsl:value-of select="CnmtRem"/></td>
		<td><xsl:value-of select="CnmtLim"/></td>
		<td><xsl:value-of select="CnmtName"/></td>
		<td><xsl:value-of select="CnmtLab"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cong">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="CongType"/></td>
		<td><xsl:value-of select="CongCond"/></td>
		<td><xsl:value-of select="CongRem"/></td>
		<td><xsl:value-of select="CongIncM"/></td>
		<td><xsl:value-of select="CongIncD"/></td>
		<td><xsl:value-of select="CongDia"/></td>
		<td><xsl:value-of select="CongHigt"/></td>
		<td><xsl:value-of select="CongMCI"/></td>
		<td><xsl:value-of select="CongMCF"/></td>
		<td><xsl:value-of select="CongBDen"/></td>
		<td><xsl:value-of select="CongDDen"/></td>
		<td><xsl:value-of select="CongPDen"/></td>
		<td><xsl:value-of select="CongSatr"/></td>
		<td><xsl:value-of select="CongSprs"/></td>
		<td><xsl:value-of select="CongSath"/></td>
		<td><xsl:value-of select="FileFset"/></td>
		<td><xsl:value-of select="CongIvr"/></td>
	</tr>
</xsl:template>

<xsl:template match="Cons">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="ConsIncn"/></td>
		<td><xsl:value-of select="ConsIvr"/></td>
		<td><xsl:value-of select="ConsIncf"/></td>
		<td><xsl:value-of select="ConsInce"/></td>
		<td><xsl:value-of select="ConsInMv"/></td>
		<td><xsl:value-of select="ConsInCv"/></td>
		<td><xsl:value-of select="ConsInSc"/></td>
		<td><xsl:value-of select="ConsCvRt"/></td>
		<td><xsl:value-of select="CongCvLg"/></td>
		<td><xsl:value-of select="ConsRem"/></td>
		<td><xsl:value-of select="ConsCC"/></td>
		<td><xsl:value-of select="ConsCS"/></td>
		<td><xsl:value-of select="ConsPOP"/></td>
	</tr>
</xsl:template>

<xsl:template match="Core">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="CoreTop"/></td>
		<td><xsl:value-of select="CoreBot"/></td>
		<td><xsl:value-of select="CorePrec"/></td>
		<td><xsl:value-of select="CoreSrec"/></td>
		<td><xsl:value-of select="CoreRqd"/></td>
		<td><xsl:value-of select="CoreRem"/></td>
		<td><xsl:value-of select="CoreDiam"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Detl">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="DetlTop"/></td>
		<td><xsl:value-of select="DetlBase"/></td>
		<td><xsl:value-of select="DetlDesc"/></td>
	</tr>
</xsl:template>

<xsl:template match="Disc">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="DiscTop"/></td>
		<td><xsl:value-of select="DiscBase"/></td>
		<td><xsl:value-of select="FracSet"/></td>
		<td><xsl:value-of select="DiscNumb"/></td>
		<td><xsl:value-of select="DiscType"/></td>
		<td><xsl:value-of select="DiscDip"/></td>
		<td><xsl:value-of select="DiscDir"/></td>
		<td><xsl:value-of select="DiscRgh"/></td>
		<td><xsl:value-of select="DiscPlan"/></td>
		<td><xsl:value-of select="DiscWave"/></td>
		<td><xsl:value-of select="DiscAmp"/></td>
		<td><xsl:value-of select="DiscJRC"/></td>
		<td><xsl:value-of select="DiscApp"/></td>
		<td><xsl:value-of select="DiscApt"/></td>
		<td><xsl:value-of select="DiscApOb"/></td>
		<td><xsl:value-of select="DiscInfM"/></td>
		<td><xsl:value-of select="DiscTerm"/></td>
		<td><xsl:value-of select="DiscPers"/></td>
		<td><xsl:value-of select="DiscStr"/></td>
		<td><xsl:value-of select="DiscWeth"/></td>
		<td><xsl:value-of select="DiscSeep"/></td>
		<td><xsl:value-of select="DiscFlow"/></td>
		<td><xsl:value-of select="DiscRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Drem">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="DremDpth"/></td>
		<td><xsl:value-of select="DremBDep"/></td>
		<td><xsl:value-of select="DremRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Dprb">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="DprbDpth"/></td>
		<td><xsl:value-of select="DprbBlow"/></td>
		<td><xsl:value-of select="DprbTorq"/></td>
		<td><xsl:value-of select="DprbDel"/></td>
		<td><xsl:value-of select="DprbInc"/></td>
		<td><xsl:value-of select="DprbRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Dprg">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="DprgType"/></td>
		<td><xsl:value-of select="DprgTest"/></td>
		<td><xsl:value-of select="DprgMass"/></td>
		<td><xsl:value-of select="DprgDrop"/></td>
		<td><xsl:value-of select="DprgCone"/></td>
		<td><xsl:value-of select="DprgRod"/></td>
		<td><xsl:value-of select="DprgDamp"/></td>
		<td><xsl:value-of select="DprgTip"/></td>
		<td><xsl:value-of select="DprgRem"/></td>
		<td><xsl:value-of select="DprgAng"/></td>
		<td><xsl:value-of select="DprgRMss"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Flsh">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="FlshFrom"/></td>
		<td><xsl:value-of select="FlshTo"/></td>
		<td><xsl:value-of select="FlshType"/></td>
		<td><xsl:value-of select="FlshRetn"/></td>
		<td><xsl:value-of select="FlshCol"/></td>
	</tr>
</xsl:template>

<xsl:template match="Frac">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="FracTop"/></td>
		<td><xsl:value-of select="FracBase"/></td>
		<td><xsl:value-of select="FracSet"/></td>
		<td><xsl:value-of select="FracFi"/></td>
		<td><xsl:value-of select="FracIMax"/></td>
		<td><xsl:value-of select="FracIAve"/></td>
		<td><xsl:value-of select="FracIMin"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Frst">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="FrstCond"/></td>
		<td><xsl:value-of select="FrstRem"/></td>
		<td><xsl:value-of select="FrstDDen"/></td>
		<td><xsl:value-of select="FrstMC"/></td>
		<td><xsl:value-of select="FrstHve1"/></td>
		<td><xsl:value-of select="FrstHve2"/></td>
		<td><xsl:value-of select="FrstHve3"/></td>
		<td><xsl:value-of select="FrstHve"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Geol">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="GeolTop"/></td>
		<td><xsl:value-of select="GeolBase"/></td>
		<td><xsl:value-of select="GeolDesc"/></td>
		<td><xsl:value-of select="GeolLeg"/></td>
		<td><xsl:value-of select="GeolGeol"/></td>
		<td><xsl:value-of select="GeolGeo2"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
	</tr>
</xsl:template>

<xsl:template match="Grad">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="GradSize"/></td>
		<td><xsl:value-of select="GradPerP"/></td>
		<td><xsl:value-of select="GradType"/></td>
	</tr>
</xsl:template>

<xsl:template match="Hdia">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="HDiaHDep"/></td>
		<td><xsl:value-of select="HDiaHole"/></td>
		<td><xsl:value-of select="HDiaRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Hdph">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="HDphTop"/></td>
		<td><xsl:value-of select="HDphBase"/></td>
		<td><xsl:value-of select="HoleType"/></td>
		<td><xsl:value-of select="HDphStar"/></td>
		<td><xsl:value-of select="HDphStat"/></td>
		<td><xsl:value-of select="HDphEndD"/></td>
		<td><xsl:value-of select="HDphEndT"/></td>
		<td><xsl:value-of select="HDphCrew"/></td>
		<td><xsl:value-of select="HDphLog"/></td>
		<td><xsl:value-of select="HDphExc"/></td>
		<td><xsl:value-of select="HDphShor"/></td>
		<td><xsl:value-of select="HDphRem"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
	</tr>
</xsl:template>

<xsl:template match="Icbr">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IcbrDpth"/></td>
		<td><xsl:value-of select="IcbrTesN"/></td>
		<td><xsl:value-of select="IcbrRem"/></td>
		<td><xsl:value-of select="IcbrIcbr"/></td>
		<td><xsl:value-of select="IcbrDate"/></td>
		<td><xsl:value-of select="IcbrSeat"/></td>
		<td><xsl:value-of select="IcbrSurc"/></td>
		<td><xsl:value-of select="IcbrType"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
	</tr>
</xsl:template>

<xsl:template match="Icct">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="MonpDis"/></td>
		<td><xsl:value-of select="MonpId"/></td>
		<td><xsl:value-of select="IcctDateTime"/></td>
		<td></td>
		<td><xsl:value-of select="CnmtType"/></td>
		<td><xsl:value-of select="CnmtTType"/></td>
		<td><xsl:value-of select="IcctResl"/></td>
		<td><xsl:value-of select="IcctUnit"/></td>
		<td><xsl:value-of select="IcctMeth"/></td>
		<td><xsl:value-of select="IcctCas"/></td>
		<td><xsl:value-of select="IcctPrep"/></td>
		<td><xsl:value-of select="IcctRem"/></td>
		<td><xsl:value-of select="IcctLim"/></td>
		<td><xsl:value-of select="IcctULim"/></td>
		<td><xsl:value-of select="IcctName"/></td>
		<td><xsl:value-of select="IcctLab"/></td>
		<td><xsl:value-of select="IcctCred"/></td>
		<td><xsl:value-of select="IcctLbId"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Iden">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IdenDpth"/></td>
		<td><xsl:value-of select="IdenTesN"/></td>
		<td><xsl:value-of select="IdenDate"/></td>
		<td><xsl:value-of select="IdenRem"/></td>
		<td><xsl:value-of select="IdenIDen"/></td>
		<td><xsl:value-of select="IdenMC"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
	</tr>
</xsl:template>

<xsl:template match="Ifid">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IfidDpth"/></td>
		<td><xsl:value-of select="IfIdTesN"/></td>
		<td><xsl:value-of select="IfIdDate"/></td>
		<td><xsl:value-of select="IfIdRem"/></td>
		<td><xsl:value-of select="IfIdRes"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
	</tr>
</xsl:template>

<xsl:template match="Inst">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="InstTDep"/></td>
		<td><xsl:value-of select="InstId"/></td>
		<td><xsl:value-of select="InstType"/></td>
		<td><xsl:value-of select="InstDate"/></td>
		<td><xsl:value-of select="InstDip"/></td>
		<td><xsl:value-of select="InstDir"/></td>
		<td><xsl:value-of select="InstIntz"/></td>
		<td><xsl:value-of select="IdenRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Iobs">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="InstTDep"/></td>
		<td><xsl:value-of select="InstId"/></td>
		<td><xsl:value-of select="IObsDate"/></td>
		<td></td>
		<td><xsl:value-of select="IObsPres"/></td>
		<td><xsl:value-of select="IObsLevl"/></td>
		<td><xsl:value-of select="IObsRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Ipid">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IpidDpth"/></td>
		<td><xsl:value-of select="IpIdTesN"/></td>
		<td><xsl:value-of select="IpIdDate"/></td>
		<td><xsl:value-of select="IpIdRem"/></td>
		<td><xsl:value-of select="IpIdRes"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
	</tr>
</xsl:template>

<xsl:template match="Iprm">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IprmTop"/></td>
		<td><xsl:value-of select="IprmBase"/></td>
		<td><xsl:value-of select="IprmStg"/></td>
		<td><xsl:value-of select="IprmTesN"/></td>
		<td><xsl:value-of select="IprmType"/></td>
		<td><xsl:value-of select="IprmPrwl"/></td>
		<td><xsl:value-of select="IprmSwal"/></td>
		<td><xsl:value-of select="IprmTDia"/></td>
		<td><xsl:value-of select="IprmSDia"/></td>
		<td><xsl:value-of select="IprmIprm"/></td>
		<td><xsl:value-of select="IprmRem"/></td>
		<td><xsl:value-of select="IprmFlow"/></td>
		<td><xsl:value-of select="IprmAwl"/></td>
		<td><xsl:value-of select="IprmHead"/></td>
		<td><xsl:value-of select="IprmDate"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Irdx">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IrdxDpth"/></td>
		<td><xsl:value-of select="IrdxTesN"/></td>
		<td><xsl:value-of select="IrdxDate"/></td>
		<td><xsl:value-of select="IrdxRem"/></td>
		<td><xsl:value-of select="IrdxPh"/></td>
		<td><xsl:value-of select="IrdxIrdx"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
	</tr>
</xsl:template>

<xsl:template match="Ires">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IresDpth"/></td>
		<td><xsl:value-of select="IresTesN"/></td>
		<td><xsl:value-of select="IresDate"/></td>
		<td><xsl:value-of select="IresIres"/></td>
		<td><xsl:value-of select="IresRem"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
	</tr>
</xsl:template>

<xsl:template match="Ispt">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IsptTop"/></td>
		<td><xsl:value-of select="IsptSeat"/></td>
		<td><xsl:value-of select="IsptMain"/></td>
		<td><xsl:value-of select="IsptNpen"/></td>
		<td><xsl:value-of select="IsptNval"/></td>
		<td><xsl:value-of select="IsptRep"/></td>
		<td><xsl:value-of select="IsptCas"/></td>
		<td><xsl:value-of select="IsptWat"/></td>
		<td><xsl:value-of select="IsptType"/></td>
		<td><xsl:value-of select="IsptSwp"/></td>
		<td><xsl:value-of select="IsptInc1"/></td>
		<td><xsl:value-of select="IsptInc2"/></td>
		<td><xsl:value-of select="IsptInc3"/></td>
		<td><xsl:value-of select="IsptInc4"/></td>
		<td><xsl:value-of select="IsptInc5"/></td>
		<td><xsl:value-of select="IsptInc6"/></td>
		<td><xsl:value-of select="IsptPen1"/></td>
		<td><xsl:value-of select="IsptPen2"/></td>
		<td><xsl:value-of select="IsptPen3"/></td>
		<td><xsl:value-of select="IsptPen4"/></td>
		<td><xsl:value-of select="IsptPen5"/></td>
		<td><xsl:value-of select="IsptPen6"/></td>
	</tr>
</xsl:template>

<xsl:template match="Ivan">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="IvanDpth"/></td>
		<td><xsl:value-of select="IvanTesN"/></td>
		<td><xsl:value-of select="IvanRem"/></td>
		<td><xsl:value-of select="IvanIvan"/></td>
		<td><xsl:value-of select="IvanIvar"/></td>
		<td><xsl:value-of select="IvanIPen"/></td>
		<td><xsl:value-of select="IvanDate"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
	</tr>
</xsl:template>

<xsl:template match="Mcvg">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="McvgRem"/></td>
		<td><xsl:value-of select="Mcvg200"/></td>
		<td><xsl:value-of select="McvgNMC"/></td>
		<td><xsl:value-of select="McvgPrcl"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Mcvt">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="McvtTesN"/></td>
		<td><xsl:value-of select="McvtMC"/></td>
		<td><xsl:value-of select="McvtRelK"/></td>
		<td><xsl:value-of select="McvtBDen"/></td>
	</tr>
</xsl:template>

<xsl:template match="Monp">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="MonpDis"/></td>
		<td><xsl:value-of select="MonpID"/></td>
		<td><xsl:value-of select="MonpDate"/></td>
		<td><xsl:value-of select="MonpType"/></td>
		<td><xsl:value-of select="MonpTrz"/></td>
		<td><xsl:value-of select="MonpBrz"/></td>
		<td><xsl:value-of select="MonpBrgA"/></td>
		<td><xsl:value-of select="MonpBrgB"/></td>
		<td><xsl:value-of select="MonpBrgC"/></td>
		<td><xsl:value-of select="MonpIncA"/></td>
		<td><xsl:value-of select="MonpIncB"/></td>
		<td><xsl:value-of select="MonpIncC"/></td>
		<td><xsl:comment> <xsl:value-of select="MonpRSCA"/></xsl:comment></td>
		<td><xsl:value-of select="MonpRSCB"/></td>
		<td><xsl:value-of select="MonpRSCC"/></td>
		<td><xsl:value-of select="MonpRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Monr">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="MonrDis"/></td>
		<td><xsl:value-of select="MonpID"/></td>
		
		<td>
		<xsl:if test="MonrDateTime">
		<xsl:value-of select="MonrDateTime"/>
		</xsl:if>
		<xsl:if test="DateTime">
		<xsl:value-of select="DateTime"/>
		</xsl:if>
		</td>
		
		<td></td>
		<td><xsl:value-of select="MonrDstA"/></td>
		<td><xsl:value-of select="MonrDstB"/></td>
		<td><xsl:value-of select="MonrDspA"/></td>
		<td><xsl:value-of select="MonrDspB"/></td>
		<td><xsl:value-of select="MonrDspC"/></td>
		<td><xsl:value-of select="MonrPres"/></td>
		<td><xsl:value-of select="MonrAngA"/></td>
		<td><xsl:value-of select="MonrAngB"/></td>
		<td><xsl:value-of select="MonrAngC"/></td>
		<td><xsl:value-of select="MonrSTRA"/></td>
		<td><xsl:value-of select="MonrStrB"/></td>
		<td><xsl:value-of select="MonrStrC"/></td>
		<td><xsl:value-of select="MonrForc"/></td>
		<td><xsl:value-of select="MonrTemp"/></td>
		
		<td>
		<xsl:if test="MonrWDep">
		<xsl:value-of select="MonrWDep"/>
		</xsl:if>
		<xsl:if test="MONR_WDEP">
		<xsl:value-of select="MONR_WDEP"/>
		</xsl:if>
		</td>
		
		<td><xsl:value-of select="MonpEast"/></td>
		<td><xsl:value-of select="MonpNrth"/></td>
		<td><xsl:value-of select="MonpLev"/></td>
		<td><xsl:value-of select="MonpWhd"/></td>
		<td><xsl:value-of select="MonpGuag"/></td>
		<td><xsl:value-of select="MonpFlow"/></td>
		<td><xsl:value-of select="MonrRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Pobs">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="PrefTDep"/></td>
		<td><xsl:value-of select="PObsDate"/></td>
		<td></td>
		<td><xsl:value-of select="PObsDep"/></td>
		<td><xsl:value-of select="PObsHead"/></td>
		<td><xsl:value-of select="PObsRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Pref">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="PrefTDep"/></td>
		<td><xsl:value-of select="PrefDate"/></td>
		<td><xsl:value-of select="PrefType"/></td>
		<td><xsl:value-of select="PrefTrps"/></td>
		<td><xsl:value-of select="PrefBrps"/></td>
		<td><xsl:value-of select="PrefRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Prob">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="ProbDate"/></td>
		<td></td>
		<td><xsl:value-of select="ProbDep"/></td>
		<td><xsl:value-of select="PrObGuag"/></td>
		<td><xsl:value-of select="PrObTDep"/></td>
		<td><xsl:value-of select="PrObBDep"/></td>
		<td><xsl:value-of select="ProbA"/></td>
		<td><xsl:value-of select="ProbB"/></td>
		<td><xsl:value-of select="ProbC"/></td>
		<td><xsl:value-of select="ProbD"/></td>
		<td><xsl:value-of select="ProbRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Prof">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="ProfId"/></td>
		<td><xsl:value-of select="ProfDate"/></td>
		<td><xsl:value-of select="ProfType"/></td>
		<td><xsl:value-of select="ProfTrps"/></td>
		<td><xsl:value-of select="ProfBrps"/></td>
		<td><xsl:value-of select="ProfDirA"/></td>
		<td><xsl:value-of select="ProfRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Proj">
	<tr>
	<td><xsl:value-of select="ProjId"/></td>
	<td><xsl:value-of select="GintRecID"/></td>
	<td><xsl:value-of select="gINTProjId"/></td>
	<td><xsl:value-of select="ProjName"/></td>
	<td><xsl:value-of select="ProjClnt"/></td>
	<td><xsl:value-of select="ProjCont"/></td>
	<td><xsl:value-of select="projEng"/></td>
	<td><xsl:value-of select="projDate"/></td>
	<td><xsl:value-of select="projAGS"/></td>
	<td><xsl:value-of select="projMemo"/></td>
	<td><xsl:value-of select="projCid"/></td>
	<td><xsl:value-of select="projProd"/></td>
	<td><xsl:value-of select="projDatm"/></td>
	<td><xsl:value-of select="projGrid"/></td>
	<td><xsl:value-of select="projPage"/></td>
	<td><xsl:value-of select="projRecv"/></td>
	<td><xsl:value-of select="projIsno"/></td>
	<td><xsl:value-of select="projStat"/></td>
	<td><xsl:value-of select="FileFset"/></td>
	<td><xsl:value-of select="projLoc"/></td>
	</tr>
</xsl:template>

<xsl:template match="Prtd">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="PrtdTRef"/></td>
		<td><xsl:value-of select="PrtdDpth"/></td>
		<td><xsl:value-of select="PrtdSeq"/></td>
		<td><xsl:value-of select="PrtdDate"/></td>
		<td><xsl:value-of select="PrtdType"/></td>
		<td><xsl:value-of select="PrtdDia"/></td>
		<td><xsl:value-of select="PrtdArm1"/></td>
		<td><xsl:value-of select="PrtdArm2"/></td>
		<td><xsl:value-of select="PrtdArm3"/></td>
		<td><xsl:value-of select="PrtdTpc1"/></td>
		<td><xsl:value-of select="PrtdTpc2"/></td>
		<td><xsl:value-of select="PrtdTpc3"/></td>
		<td><xsl:value-of select="PrtdPpA"/></td>
		<td><xsl:value-of select="PrtdPpB"/></td>
		<td><xsl:value-of select="PrtdRem"/></td>
		<td><xsl:value-of select="PrtdPres"/></td>
		<td><xsl:value-of select="PrtdVol"/></td>
	</tr>
</xsl:template>

<xsl:template match="Prtg">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="PrtgTRef"/></td>
		<td><xsl:value-of select="PrtgDpth"/></td>
		<td><xsl:value-of select="PrtgDate"/></td>
		<td><xsl:value-of select="PrtgType"/></td>
		<td><xsl:value-of select="PrtgDia"/></td>
		<td><xsl:value-of select="PrtgHA1"/></td>
		<td><xsl:value-of select="PrtgHA2"/></td>
		<td><xsl:value-of select="PrtgHA3"/></td>
		<td><xsl:value-of select="PrtgHAA"/></td>
		<td><xsl:value-of select="PrtgGIA1"/></td>
		<td><xsl:value-of select="PrtgGIA2"/></td>
		<td><xsl:value-of select="PrtgGIA3"/></td>
		<td><xsl:value-of select="PrtgGIAA"/></td>
		<td><xsl:value-of select="PrtgCUA1"/></td>
		<td><xsl:value-of select="PrtgCUA2"/></td>
		<td><xsl:value-of select="PrtgCUA3"/></td>
		<td><xsl:value-of select="PrtgCUAA"/></td>
		<td><xsl:value-of select="PrtgPLA1"/></td>
		<td><xsl:value-of select="PrtgPLA2"/></td>
		<td><xsl:value-of select="PrtgPLA3"/></td>
		<td><xsl:value-of select="PrtgPLAA"/></td>
		<td><xsl:value-of select="PrtgAFA1"/></td>
		<td><xsl:value-of select="PrtgAFA2"/></td>
		<td><xsl:value-of select="PrtgAFA3"/></td>
		<td><xsl:value-of select="PrtgAFAA"/></td>
		<td><xsl:value-of select="PrtgADA1"/></td>
		<td><xsl:value-of select="PrtgADA2"/></td>
		<td><xsl:value-of select="PrtgADA3"/></td>
		<td><xsl:value-of select="PrtgADAA"/></td>
		<td><xsl:value-of select="PrtgAFCV"/></td>
		<td><xsl:value-of select="PrtgRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Prtl">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="PrtlTRef"/></td>
		<td><xsl:value-of select="PrtlDpth"/></td>
		<td><xsl:value-of select="PrtlDate"/></td>
		<td><xsl:value-of select="PrtlLNo"/></td>
		<td><xsl:value-of select="PrtlType"/></td>
		<td><xsl:value-of select="PrtlDia"/></td>
		<td><xsl:value-of select="PrtlGA1"/></td>
		<td><xsl:value-of select="PrtlGA2"/></td>
		<td><xsl:value-of select="PrtlGA3"/></td>
		<td><xsl:value-of select="PrtlGAA"/></td>
	</tr>
</xsl:template>

<xsl:template match="Ptim">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="PtimDate"/></td>
		<td></td>
		<td><xsl:value-of select="PtimDep"/></td>
		<td><xsl:value-of select="PtimCas"/></td>
		<td><xsl:value-of select="PtimWat"/></td>
		<td><xsl:value-of select="PtimRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Ptst">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="PtstTesN"/></td>
		<td><xsl:value-of select="PtstRem"/></td>
		<td><xsl:value-of select="PtstCond"/></td>
		<td><xsl:value-of select="PtstSzun"/></td>
		<td><xsl:value-of select="PtstDia"/></td>
		<td><xsl:value-of select="PtstLen"/></td>
		<td><xsl:value-of select="PtstMC"/></td>
		<td><xsl:value-of select="PtstBDen"/></td>
		<td><xsl:value-of select="PtstDDen"/></td>
		<td><xsl:value-of select="PtstVoid"/></td>
		<td><xsl:value-of select="PtstK"/></td>
		<td><xsl:value-of select="PtstTstr"/></td>
		<td><xsl:value-of select="PtstIsat"/></td>
		<td><xsl:value-of select="PtstFsat"/></td>
		<td><xsl:value-of select="PtstPDen"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Pump">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="PumpDate"/></td>
		<td></td>
		<td><xsl:value-of select="PumpDpth"/></td>
		<td><xsl:value-of select="PumpQuat"/></td>
		<td><xsl:value-of select="PumpRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Reld">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="ReldRem"/></td>
		<td><xsl:value-of select="ReldDMax"/></td>
		<td><xsl:value-of select="Reld375"/></td>
		<td><xsl:value-of select="Reld063"/></td>
		<td><xsl:value-of select="Reld020"/></td>
		<td><xsl:value-of select="ReldDMin"/></td>
	</tr>
</xsl:template>

<xsl:template match="Rock">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="RockPls"/></td>
		<td><xsl:value-of select="RockPlsi"/></td>
		<td><xsl:value-of select="RockPltf"/></td>
		<td><xsl:value-of select="RockUcs"/></td>
		<td><xsl:value-of select="RockRem"/></td>
		<td><xsl:value-of select="RockPrem"/></td>
		<td><xsl:value-of select="RockUrem"/></td>
		<td><xsl:value-of select="RockE"/></td>
		<td><xsl:value-of select="RockMu"/></td>
		<td><xsl:value-of select="RockBraz"/></td>
		<td><xsl:value-of select="RockPoro"/></td>
		<td><xsl:value-of select="RockPore"/></td>
		<td><xsl:value-of select="RockMc"/></td>
		<td><xsl:value-of select="RockBden"/></td>
		<td><xsl:value-of select="RockDden"/></td>
		<td><xsl:value-of select="RockPden"/></td>
		<td><xsl:value-of select="RockDrem"/></td>
		<td><xsl:value-of select="RockWtab"/></td>
		<td><xsl:value-of select="RockWrem"/></td>
		<td><xsl:value-of select="RockSdi"/></td>
		<td><xsl:value-of select="RockSrem"/></td>
		<td><xsl:value-of select="RockSoun"/></td>
		<td><xsl:value-of select="RockMrem"/></td>
		<td><xsl:value-of select="RockAcv"/></td>
		<td><xsl:value-of select="RockCrem"/></td>
		<td><xsl:value-of select="RockAiv"/></td>
		<td><xsl:value-of select="RockIrem"/></td>
		<td><xsl:value-of select="RockLosa"/></td>
		<td><xsl:value-of select="RockLrem"/></td>
		<td><xsl:value-of select="RockAav"/></td>
		<td><xsl:value-of select="RockPsv"/></td>
		<td><xsl:value-of select="RockFi"/></td>
		<td><xsl:value-of select="RockEi"/></td>
		<td><xsl:value-of select="ROCK_DESC"/></td>
		<td><xsl:value-of select="RockShor"/></td>
		<td><xsl:value-of select="RockPwav"/></td>
		<td><xsl:value-of select="RockSwav"/></td>
		<td><xsl:value-of select="RockEmod"/></td>
		<td><xsl:value-of select="RockSg"/></td>
		<td><xsl:value-of select="RockSwel"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
	</tr>
</xsl:template>

<xsl:template match="Samp">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampBase"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SampDia"/></td>
		<td><xsl:value-of select="SampDesc"/></td>
		<td><xsl:value-of select="SampUblo"/></td>
		<td><xsl:value-of select="SampRem"/></td>
		<td><xsl:value-of select="SampDate"/></td>
		<td><xsl:value-of select="SampTime"/></td>
		<td><xsl:value-of select="SampBar"/></td>
		<td><xsl:value-of select="SampWDep"/></td>
		<td><xsl:value-of select="SampTemp"/></td>
		<td><xsl:value-of select="SampPres"/></td>
		<td><xsl:value-of select="SampFlow"/></td>
		<td><xsl:value-of select="SampPrep"/></td>
		<td><xsl:value-of select="GeolStat"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
	</tr>
</xsl:template>

<xsl:template match="Shbg">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="ShbgType"/></td>
		<td><xsl:value-of select="ShbgRem"/></td>
		<td><xsl:value-of select="ShpgPCoh"/></td>
		<td><xsl:value-of select="ShpgPhi"/></td>
		<td><xsl:value-of select="ShpgRCoh"/></td>
		<td><xsl:value-of select="ShpgRPhi"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
	</tr>
</xsl:template>

<xsl:template match="Shbt">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="SHBTTesN"/></td>
		<td><xsl:value-of select="ShbtBDen"/></td>
		<td><xsl:value-of select="ShbtDDen"/></td>
		<td><xsl:value-of select="ShbtNorm"/></td>
		<td><xsl:value-of select="ShbtDisp"/></td>
		<td><xsl:value-of select="ShbtPeak"/></td>
		<td><xsl:value-of select="ShbtRes"/></td>
		<td><xsl:value-of select="ShbtPDis"/></td>
		<td><xsl:value-of select="ShbtRDis"/></td>
		<td><xsl:value-of select="ShbtPDen"/></td>
		<td><xsl:value-of select="ShbtIvr"/></td>
		<td><xsl:value-of select="ShbtMCI"/></td>
		<td><xsl:value-of select="ShbtMCF"/></td>
		<td><xsl:value-of select="ShbtRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Stcn">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="StcnDpth"/></td>
		<td><xsl:value-of select="StcnType"/></td>
		<td><xsl:value-of select="StcnRef"/></td>
		<td><xsl:value-of select="StcnRes"/></td>
		<td><xsl:value-of select="StcnFres"/></td>
		<td><xsl:value-of select="StcnPwp1"/></td>
		<td><xsl:value-of select="StcnPwp2"/></td>
		<td><xsl:value-of select="StcnPwp3"/></td>
		<td><xsl:value-of select="StcnCon"/></td>
		<td><xsl:value-of select="StcnTemp"/></td>
		<td><xsl:value-of select="StcnPh"/></td>
		<td><xsl:value-of select="StcnSlp1"/></td>
		<td><xsl:value-of select="StcnSlp2"/></td>
		<td><xsl:value-of select="StcnRedx"/></td>
		<td><xsl:value-of select="StcnFfd"/></td>
		<td><xsl:value-of select="StcnPMT"/></td>
		<td><xsl:value-of select="StcnPid"/></td>
		<td><xsl:value-of select="StcnFid"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Suct">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="SuctMeth"/></td>
		<td><xsl:value-of select="SuctVal"/></td>
	</tr>
</xsl:template>

<xsl:template match="Tncp">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="TnpcTesN"/></td>
		<td><xsl:value-of select="TnpcRem"/></td>
		<td><xsl:value-of select="TnpcDry"/></td>
		<td><xsl:value-of select="TnpcWet"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Trem">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="TremDate"/></td>
		<td></td>
		<td><xsl:value-of select="TremRem"/></td>
		<td><xsl:value-of select="FileFset"/></td>
	</tr>
</xsl:template>

<xsl:template match="Trig">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="TrigType"/></td>
		<td><xsl:value-of select="TrigCond"/></td>
		<td><xsl:value-of select="TrigCu"/></td>
		<td><xsl:value-of select="TrigCoh"/></td>
		<td><xsl:value-of select="TrigPhi"/></td>
		<td><xsl:value-of select="FileFSet"/></td>
	</tr>
</xsl:template>

<xsl:template match="Trix">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="SampTop"/></td>
		<td><xsl:value-of select="SampRef"/></td>
		<td><xsl:value-of select="SampType"/></td>
		<td><xsl:value-of select="SpecRef"/></td>
		<td><xsl:value-of select="SpecDpth"/></td>
		<td><xsl:value-of select="TrixTesn"/></td>
		<td><xsl:value-of select="TrixSDia"/></td>
		<td><xsl:value-of select="TrixMC"/></td>
		<td><xsl:value-of select="TrixCell"/></td>
		<td><xsl:value-of select="TrixDevF"/></td>
		<td><xsl:value-of select="TrixSLen"/></td>
		<td><xsl:value-of select="TrixBDen"/></td>
		<td><xsl:value-of select="TrixDDen"/></td>
		<td><xsl:value-of select="TrixPwPF"/></td>
		<td><xsl:value-of select="TrixPwPI"/></td>
		<td><xsl:value-of select="TrixCu"/></td>
		<td><xsl:value-of select="TrixStrn"/></td>
		<td><xsl:value-of select="TrixMode"/></td>
	</tr>
</xsl:template>

<xsl:template match="Unit">
	<tr>
		<td><xsl:value-of select="UnitUnit"/></td>
		<td><xsl:value-of select="UnitDesc"/></td>
	</tr>
</xsl:template>

<xsl:template match="Weth">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="WethTop"/></td>
		<td><xsl:value-of select="WethBase"/></td>
		<td><xsl:value-of select="WethGrad"/></td>
		<td><xsl:value-of select="WethRem"/></td>
	</tr>
</xsl:template>

<xsl:template match="Wstk">
	<tr>
		<td><xsl:value-of select="HoleId"/></td>
		<td><xsl:value-of select="WStkDep"/></td>
		<td><xsl:value-of select="WStkNMin"/></td>
		<td><xsl:value-of select="WStkCas"/></td>
		<td><xsl:value-of select="WStkDate"/></td>
		<td><xsl:value-of select="WStkTime"/></td>
		<td><xsl:value-of select="WStk_Post"/></td>
		<td><xsl:value-of select="WStkFlow"/></td>
		<td><xsl:value-of select="WStkSeal"/></td>
	</tr>
</xsl:template>

</xsl:stylesheet>