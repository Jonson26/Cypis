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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class Template {
    private String name, parameter, declaration;
    private ArrayList<Location> locations;
    private ArrayList<Edge> edges;

    public Template(String name, String parameter, String declaration, ArrayList<Location> locations, ArrayList<Edge> edges) {
        this.name = name;
        this.parameter = parameter;
        this.declaration = declaration;
        this.locations = locations;
        this.edges = edges;
    }
    
    public Template(Template t){
        this(t.getName(), t.getParameter(), t.getDeclaration(), t.getLocations(), t.getEdges());
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

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
    
    public void addLocation(Location s){
        locations.add(s);
    }
    
    public Location getLocation(int i){
        return locations.get(i);
    }
    
    public Location getLocation(String id){
        return locations.get(findLocationById(id));
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
    
    public int findLocationById(String id){//returns -1 if element cannot be found
        return locations.indexOf(new Location(null, id, 0, 0));
    }
    
    public int findLocationByName(String name){//returns -1 if element cannot be found; only returns first instance found
        for(int i=0; i<locations.size(); i++){
            try {
                if(locations.get(i).getName().getContent().equals(name)){
                    return i;
                }
            } catch (Exception ex) {
                return -1;
            }
        }
        return -1;
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
