# Cypis
Automated UPPAAL Model Reduction Tool in Java

This program takes an UPPAAL Model file along with a relevant strategy encoded in an ADTool XML file as inputs, and produces a valid reduced UPPAAL model. Proper application of this tool can greatly increase UPPAAL's verification performance.

Command line usage:

<pre>
java -jar Cypis.jar (-h | --help | modelfile treefile agentname [options])

  options:
  
    -h, --help              prints this message
    
    -o, --output outfile    changes the output file's name
</pre>
