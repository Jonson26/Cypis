/*
 * Copyright (C) 2021 Filip Jamroga (filip.jamroga.001 at student.uni.lu)
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
package cypis.modelAPI.fileIO;

import cypis.modelAPI.UPPAAL.Edge;
import cypis.modelAPI.UPPAAL.Label;
import cypis.modelAPI.UPPAAL.Model;
import cypis.modelAPI.UPPAAL.State;
import cypis.modelAPI.UPPAAL.Template;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class UPPAALWriter {
    private String urgentID;
    
    public void writeModel(Model m, File f) throws IOException{//Writes a (hopefully) UPPAAL-compatible file containing the data in the Model object provided.
        String buffer = "";
        
        //paste opening blurb
        buffer += "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
                  "<!DOCTYPE nta PUBLIC '-//Uppaal Team//DTD Flat System 1.1//EN' 'http://www.it.uu.se/research/group/darts/uppaal/flat-1_1.dtd'>";
        //open nta tag
        buffer += "\n\n<nta>\n";
        
        //write the declaration
        buffer += "<declaration>\n"+m.getDeclaration()+"\n</declaration>\n";
        
        //write all the templates
        for(int i=0; i<m.getTemplates().size(); i++){
            buffer += composeTemplate(m.getTemplates().get(i));
        }
        
        //write system declaration
        buffer += "<system>\n"+m.getSystemDeclaration()+"\n</system>\n";
        //close nta tag
        buffer += "</nta>";
        
        //write buffer to file
        FileWriter fr = null;
        BufferedWriter br = null;
        fr = new FileWriter(f);
        br = new BufferedWriter(fr);
        br.write(buffer);
        br.close();
        fr.close();
    }
    
    private String composeTemplate(Template t){
        String buffer = "";
        
        buffer += "<template>\n";
        
        buffer += "<name>"+t.getName()+"</name>\n";
        buffer += "<parameter>"+t.getParameter()+"</parameter>\n";
        buffer += "<declaration>\n"+t.getDeclaration()+"</declaration>\n";
        
        for(int i=0; i<t.getStates().size(); i++){
            buffer += composeState(t.getStates().get(i));
        }
        
        buffer += "<init ref=\""+urgentID+"\"/>\n";
        
        for(int i=0; i<t.getEdges().size(); i++){
            buffer += composeEdge(t.getEdges().get(i));
        }
       
        buffer += "</template>\n";
        
        return buffer;
    }
    
    private String composeState(State s){
        String buffer = "";
        
        if(s.isInitial()){
            urgentID = s.getId();
        }
        
        buffer += "<location id=\""+s.getId()+"\" x=\""+s.getX()+"\" y=\""+s.getY()+"\">\n";
        buffer += "<name x=\""+s.getName().getX()+"\" y=\""+s.getName().getY()+"\">"+s.getName().getContent()+"</name>\n";
        
        if(s.isCommitted()){
            buffer += "<committed/>\n";
        }else if(s.isUrgent()){
            buffer += "<urgent/>\n";
        }
        
        buffer += "</location>\n";
        
        return buffer;
    }
    
    private String composeEdge(Edge e){
        String buffer = "";
        
        buffer += "<transition>\n";
        
        buffer += "<source ref=\""+e.getSource()+"\"/>\n";
        buffer += "<target ref=\""+e.getTarget()+"\"/>\n";
        
        if(e.getSelect()!=null){
            Label l = e.getSelect();
            buffer += "<label kind=\"select\" x=\""+l.getX()+"\" y=\""+l.getY()+"\">"+l.getContent()+"</label>\n";
        }
        if(e.getGuard()!=null){
            Label l = e.getGuard();
            buffer += "<label kind=\"guard\" x=\""+l.getX()+"\" y=\""+l.getY()+"\">"+l.getContent()+"</label>\n";
        }if(e.getSync()!=null){
            Label l = e.getSync();
            buffer += "<label kind=\"synchronisation\" x=\""+l.getX()+"\" y=\""+l.getY()+"\">"+l.getContent()+"</label>\n";
        }
        if(e.getUpdate()!=null){
            Label l = e.getUpdate();
            buffer += "<label kind=\"assignment\" x=\""+l.getX()+"\" y=\""+l.getY()+"\">"+l.getContent()+"</label>\n";
        }
        
        for(int i=0; i<e.getNails().size(); i++){
            buffer += "<nail x=\""+e.getNails().get(i).getX()+"\" y=\""+e.getNails().get(i).getX()+"\"/>\n";
        }
        
        buffer += "</transition>\n";
        
        return buffer;
    }
}
