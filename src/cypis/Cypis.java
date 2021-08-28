/*
 * Copyright (C) 2021 Filip Jamroga <filip.jamroga.001 at student.uni.lu>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cypis;

import cypis.modelAPI.ADTool.Node;
import cypis.modelAPI.Strategy.Strategy;
import cypis.modelAPI.Strategy.StrategyParser;
import cypis.modelAPI.Strategy.TemplateReductor;
import cypis.modelAPI.UPPAAL.Model;
import cypis.modelAPI.UPPAAL.Template;
import cypis.modelAPI.fileIO.EasyFileLoader;
import cypis.modelAPI.fileIO.UPPAALWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class Cypis {
    private static final String VERSION = "1.1.0";//bump manually
    
    private String modelFile = "";
    private String treeFile = "";
    private String outFile = "cypisoutput.xml";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Cypis Model Reductor version " + VERSION);
        System.out.println("(c) 2021 Filip Jamroga");
        Cypis c = new Cypis();
        c.processArgs(args);
    }
    
    public void processArgs(String[] args){
        if(args.length == 0){
            System.out.println("Not enough arguments!");
            printHelp();
            return;
        }
        switch(args[0]){
            case "-h":
            case "--help":
                printHelp();
                break;
            default:
                if(args.length < 2){
                    System.out.println("Not enough arguments!");
                    printHelp();
                    return;
                }
                modelFile = args[0];
                treeFile = args[1];
                
                if(args.length > 2 && ("-o".equals(args[2]) || "--output".equals(args[2]))){
                    if(args.length < 4){
                        System.out.println("Not enough arguments!");
                        printHelp();
                        return;
                    }
                    outFile = args[4];
                }
                reduceModel();
                break;
        }
    }
    
    public void reduceModel(){
        EasyFileLoader fl = new EasyFileLoader();//load designated model files
        System.out.println("Loading Model");
        Model m = fl.loadModel(modelFile);
        System.out.println("Loading Attack Tree");
        Node t = fl.loadTree(treeFile);
        
        System.out.println("Processing Attack tree");
        ArrayList<Strategy> s = new StrategyParser().parseStrategies(t);
        
        System.out.println("Number of Agents: "+s.size());
        
        System.out.println("Selecting "+s.get(0).getRelevantAgentName());
        ArrayList<Template> templates = (ArrayList<Template>) m.getTemplates().clone();//find required template
        int templateIndex = templates.indexOf(new Template(s.get(0).getRelevantAgentName(), null, null, null, null));
        TemplateReductor tr = new TemplateReductor();
        
        System.out.println("Reducing "+s.get(0).getRelevantAgentName());
        templates.set(templateIndex, tr.reduce(m.getTemplates().get(templateIndex), s.get(0)));//reduce template, and replace the original
        
        if(s.size()>1){
            tr = new TemplateReductor();
            System.out.println("Selecting "+s.get(1).getRelevantAgentName());
            templateIndex = templates.indexOf(new Template(s.get(1).getRelevantAgentName(), null, null, null, null));

            System.out.println("Reducing "+s.get(1).getRelevantAgentName());
            templates.set(templateIndex, tr.reduce(m.getTemplates().get(templateIndex), s.get(1)));//reduce template, and replace the original
        }
        
        Model m2 = new Model(m);
        m2.setTemplates(templates);
        
        System.out.println("Writing Output to "+outFile);
        UPPAALWriter w = new UPPAALWriter();//write resulting model to file
        File f = new File(outFile);
        try {
            w.writeModel(m2, f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished!");
    }
    
    public void printHelp(){
        String cmd = System.getProperties().getProperty("sun.java.command");
        System.out.println(
                "usage: java -jar cypis.jar (-h | --help | modelfile treefile [options])\n" +
                "	options:\n" +
                "		-h, --help              prints this message\n" +
                "		-o, --output outfile	changes the output file's name");
    }
}
