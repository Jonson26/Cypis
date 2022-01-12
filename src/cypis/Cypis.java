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
import cypis.modelAPI.ADTool.NodeType;
import cypis.modelAPI.Strategy.Expression;
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
    private static final String VERSION = "2.1.0";//bump manually
    
    private String settingsFile = "";
    private Settings setting;
    
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
                settingsFile = args[0];
                reduceModel();
                break;
        }
    }
    
    public void reduceModel(){
        EasyFileLoader fl = new EasyFileLoader();//create loader for designated model files
        
        System.out.println("Loading Project Settings");
        setting = fl.loadSettings(settingsFile);//load settings file
        
        System.out.println("Loading Model");
        Model m = fl.loadModel(setting.getInputModelFileName());//load UPPAAL model file
        
        System.out.println("Loading Attack Tree");
        Node t = fl.loadTree(setting.getInputTreeFileName());//load ADTool XML export file
        
        if(setting.getDefenderAgentExists()){
            System.out.println("Two-Agent Strategy Definition Found");
            System.out.println("Separating Out Attack And Defense Strategy Definitions");
            
            //separate the attacker tree and the defender tree into two different entities
            Node attacker = t.clone();
            int i = 0;
            while(i>0){
                i = attacker.prune(NodeType.COUNTERMEASURE);
            }
            
            Node defender = t.clone();
            i = 0;
            while(i>0){
                i = defender.prune(NodeType.NODE);
            }
            
            System.out.println("Converting Strategy Definitions To Logical Formulas");
            Expression attExp = new Expression(attacker);
            Expression defExp = new Expression(defender);
            
            System.out.println("Creating EDNF Representation");
            ArrayList<ArrayList<String>> attEDNF = attExp.bulidEDNF();
            ArrayList<ArrayList<String>> defEDNF = defExp.bulidEDNF();
            
            System.out.println("Converting EDNF Representations Into Strategies");
            ArrayList<Strategy> attStrat = new ArrayList<>();
            ArrayList<Strategy> defStrat = new ArrayList<>();
            
            
            //parse each element of generated EDNF into a Strategy object
            StrategyParser p = new StrategyParser();
            for(ArrayList<String> l: attEDNF){
                attStrat.add(p.parseStrategies(l, setting.getAttackerAgentName()));
            }
            for(ArrayList<String> l: defEDNF){
                defStrat.add(p.parseStrategies(l, setting.getDefenderAgentName()));
            }
            
            //If no defender strategy is defined in the tree, despite the defender being required by the settings file, add an empty Strategy into the defender strategy table.
            if(defStrat.isEmpty()){
                System.out.println("No defender strategies defined. Adding empty strategy.");
                defStrat.add(new Strategy(setting.getDefenderAgentName(), new ArrayList<>()));
            }
            
            System.out.println("Generating Cross Product Of The Two Strategy Sets");
            ArrayList<ArrayList<Strategy>> strats = new ArrayList<>();
            for(Strategy as: attStrat){
                for(Strategy ds: defStrat){
                    ArrayList<Strategy> element = new ArrayList<>();
                    element.add(as);
                    element.add(ds);
                    strats.add(element);
                }
            }
            
            for(i=0; i<strats.size(); i++){
                System.out.println("Processing Possible Strategy Tuple #"+i);
                ArrayList<Strategy> s = strats.get(i);

                System.out.println("Selecting "+s.get(0).getRelevantAgentName());
                ArrayList<Template> templates = (ArrayList<Template>) m.getTemplates().clone();//find required template
                int templateIndex = templates.indexOf(new Template(s.get(0).getRelevantAgentName(), null, null, null, null));
                TemplateReductor tr = new TemplateReductor();

                System.out.println("Reducing "+s.get(0).getRelevantAgentName());
                Template template = tr.reduce(m.getTemplates().get(templateIndex), s.get(0));
                templates.set(templateIndex, template);//reduce template, and replace the original

                if(s.size()>1){
                    tr = new TemplateReductor();
                    System.out.println("Selecting "+s.get(1).getRelevantAgentName());
                    templateIndex = templates.indexOf(new Template(s.get(1).getRelevantAgentName(), null, null, null, null));

                    System.out.println("Reducing "+s.get(1).getRelevantAgentName());
                    templates.set(templateIndex, tr.reduce(m.getTemplates().get(templateIndex), s.get(1)));//reduce template, and replace the original
                }

                Model m2 = new Model(m);
                m2.setTemplates(templates);

                System.out.println("Writing Output to "+setting.getSpecificOutputFileName(i));
                UPPAALWriter w = new UPPAALWriter();//write resulting model to file
                File f = new File(setting.getSpecificOutputFileName(i));
                try {
                    w.writeModel(m2, f);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }else{
            System.out.println("Single-Agent Strategy Definition Found");
            Node attacker = t.clone();
            
            System.out.println("Converting Strategy Definition To Logical Formulas");
            Expression attExp = new Expression(attacker);
            
            System.out.println("Creating EDNF Representation");
            ArrayList<ArrayList<String>> attEDNF = attExp.bulidEDNF();
            
            System.out.println("Converting EDNF Representations Into Strategies");
            ArrayList<Strategy> attStrat = new ArrayList<>();
            
            StrategyParser p = new StrategyParser();
            
            for(ArrayList<String> l: attEDNF){
                attStrat.add(p.parseStrategies(l, setting.getAttackerAgentName()));
            }
            
            for(int i=0; i<attStrat.size(); i++){
                System.out.println("Processing Possible Strategy Tuple #"+i);
                Strategy s = attStrat.get(i);

                System.out.println("Selecting "+s.getRelevantAgentName());
                ArrayList<Template> templates = (ArrayList<Template>) m.getTemplates().clone();//find required template
                int templateIndex = templates.indexOf(new Template(s.getRelevantAgentName(), null, null, null, null));
                TemplateReductor tr = new TemplateReductor();

                System.out.println("Reducing "+s.getRelevantAgentName());
                templates.set(templateIndex, tr.reduce(m.getTemplates().get(templateIndex), s));//reduce template, and replace the original

                Model m2 = new Model(m);
                m2.setTemplates(templates);

                System.out.println("Writing Output to "+setting.getSpecificOutputFileName(i));
                UPPAALWriter w = new UPPAALWriter();//write resulting model to file
                File f = new File(setting.getSpecificOutputFileName(i));
                try {
                    w.writeModel(m2, f);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        System.out.println("Finished!");
    }
    
    public void printHelp(){
        String cmd = System.getProperties().getProperty("sun.java.command");
        System.out.println("usage: java -jar cypis.jar (-h | --help | settingsfile)");
    }
}
