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

/**
 *
 * @author Filip Jamroga <filip.jamroga.001 at student.uni.lu>
 */
public class Template {
    private String declaration;
    private ArrayList<State> states;
    private ArrayList<Edge> edges;

    public Template(String declaration, ArrayList<State> states, ArrayList<Edge> edges) {
        this.declaration = declaration;
        this.states = states;
        this.edges = edges;
    }

    public Template() {
        this("", new ArrayList<>(), new ArrayList<>());
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
    
    public void addState(State s){
        states.add(s);
    }
    
    public State getState(int i){
        return states.get(i);
    }
    
    public State getState(String id){
        return states.get(findStateById(id));
    }
    
    public Edge getEdge(int i){
        return edges.get(i);
    }
    
    public void addEdge(Edge e){
        edges.add(e);
    }
    
    public int findStateById(String id){//returns -1 if element cannot be found
        int i;
        for(i=0; !states.get(i).getId().equals(id) && states.size()!=i; i++){
        }
        if(states.size()==i){
            return -1;
        }
        return i;
    }
    
    public int findStateByName(String name){//returns -1 if element cannot be found; only returns first instance found
        int i;
        for(i=0; !states.get(i).getName().equals(name) && states.size()!=i; i++){
        }
        if(states.size()==i){
            return -1;
        }
        return i;
    }
}
