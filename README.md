# Cypis
Automated UPPAAL Model Reduction Tool in Java

This program takes an xml-based settings file as an argument and produces a valid reduced UPPAAL model. The settings file should contain a reference to an UPPAAL Model file along with a relevant strategy encoded in an ADTool XML file as inputs. Proper application of this tool can greatly increase UPPAAL's verification performance.

Command line usage:

<pre>
java -jar Cypis.jar (-h | --help | settingsfile)
</pre>

Example settings file:

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;settings&gt;
	&lt;modelfile&gt;Castle_Siege.xml&lt;/modelfile&gt;
	&lt;treefile&gt;Win_The_Battle.xml&lt;/treefile&gt;

	&lt;attackeragent&gt;King Arthur&lt;/attackeragent&gt;
	&lt;defenderagent exists="true"&gt;Morgana&lt;/defenderagent&gt;

	&lt;wildcard&gt;#&lt;/wildcard>
	&lt;outfile&gt;Castle_Siege_Reduced_#.xml&lt;/outfile&gt;
&lt;/settings&gt;
</pre>
