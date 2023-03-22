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
package cypis.modelAPI.UPPAAL;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class Template {
    private String name, parameter, declaration;
    private ArrayList<Location> states;
    private ArrayList<Edge> edges;

    public Template(String name, String parameter, String declaration, ArrayList<Location> states, ArrayList<Edge> edges) {
        this.name = name;
        this.parameter = parameter;
        this.declaration = declaration;
        this.states = states;
        this.edges = edges;
    }
    
    public Template(Template t){
        this(t.getName(), t.getParameter(), t.getDeclaration(), t.getStates(), t.getEdges());
    }

    public Template() {
        this("default name", "", "", new ArrayList<>(), new ArrayList<>());
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public ArrayList<Location> getStates() {
        return states;
    }

    public void setStates(ArrayList<Location> states) {
        this.states = states;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
    
    public void addState(Location s){
        states.add(s);
    }
    
    public Location getState(int i){
        return states.get(i);
    }
    
    public Location getState(String id){
        return states.get(findStateById(id));
    }
    
    public Edge getEdge(int i){
        return edges.get(i);
    }
    
    public void addEdge(Edge e){
        edges.add(e);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    
    public int findStateById(String id){//returns -1 if element cannot be found
//        int i;
//        for(i=0; !states.get(i).getId().equals(id) && states.size()!=i; i++){
//        }
//        if(states.size()==i){
//            return -1;
//        }
//        return i;
        return states.indexOf(new Location(null, id, 0, 0));
    }
    
    public int findStateByName(String name){//returns -1 if element cannot be found; only returns first instance found
        for(int i=0; i<states.size(); i++){//State s : states) {
            if(states.get(i).getName().getContent().equals(name)){//carnet.getCodeIsIn().equals(codeIsIn)) {
                    return i;
                }
            }
        return -1;
//        int i;
//        for(i=0; !states.get(i).getName().getContent().equals(name) && states.size()!=i; i++){
//        }
//        if(states.size()==i){
//            return -1;
//        }
//        return i;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Template other = (Template) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}
