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
package cypis.modelAPI.Strategy;

import cypis.modelAPI.UPPAAL.Edge;
import cypis.modelAPI.UPPAAL.Location;
import cypis.modelAPI.UPPAAL.Template;
import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class TemplateReductor {
    private Template template;
//    private Node attackTree;
    
    private ArrayList<PartialStrategy> strategy;
    private ArrayList<Action> actions;
    private ArrayList<EdgeDataContainer> actionEdgeIndex;
    private ArrayList<String> relevantLocationNames;
    private ArrayList<Integer> redundantEdgeIndex;

    public TemplateReductor() {
//        strategy = new ArrayList<>();
        actions = new ArrayList<>();
        actionEdgeIndex = new ArrayList<>();
        redundantEdgeIndex = new ArrayList<>();
    }
    
    public Template reduce(Template t, Strategy s) throws Exception{
        template = new Template(t);
//        attackTree = n;
        
//        parsePartialStrategies(attackTree);//create a list of partial strategies
        strategy = s.getStrat();
        
        actions = new ActionParser().parse(template.getDeclaration());//create a list of all actions defined in template
        
        checkEdges();//create a list of all action edges
        checkLocationRelevance();
        checkEdgeRedundancy();//create a list of redundant edges
        removeRedundantEdges();
        
        boolean run = true;
        while(run){
            run = removeOrphanedLocations();
            boolean temp = removeOrphanedEdges();
            run = (run || temp);
        }
        
        return template;
    }
    
//    private void parsePartialStrategies(Node n){
//        PartialStrategy p = new PartialStrategy(n.getLabel());//get the node n label and parse it
//        if(p.isValid()){//if the resulting partial strategy is valid, add it to the rest
//            strategy.add(p);
//        }
//        
//        ArrayList<Node> children = n.getChildren();
//        for(int i=0; i<children.size(); i++){//do the same for all the children of n
//            parsePartialStrategies(children.get(i));
//        }
//    }
    
    private void checkEdges(){//check all edges if any of them have actions
        ArrayList<Edge> edges = template.getEdges();
        for(int i=0; i<edges.size(); i++){
            for(int j=0; j<actions.size(); j++){
                if(actions.get(j).checkEdge(edges.get(i))){
                    actionEdgeIndex.add(new EdgeDataContainer(i, actions.get(j)));
                    j = actions.size();
                }
            }
        }
    }
    
    private void checkLocationRelevance() throws Exception{
        relevantLocationNames = new ArrayList<>();
        
        for (PartialStrategy s : strategy) {
            String name = s.getLocation();
            int i = template.findLocationByName(name);
            if(i>=0){
                relevantLocationNames.add(template.getLocation(i).getName().getContent());
            }
        }
    }
    
    private void checkEdgeRedundancy() throws Exception{
        ArrayList<Boolean> rt = new ArrayList();
        actionEdgeIndex.forEach(_item -> {
            rt.add(Boolean.FALSE);
        });
        
        for(int i=0; i<actionEdgeIndex.size(); i++){
            String s0 = template.getLocation(template.getEdge(actionEdgeIndex.get(i).index).getSource()).getName().getContent();
            if(relevantLocationNames.contains(s0)){
                for(int j=0; j<strategy.size(); j++){
                    if(strategy.get(j).isValid()){
                        String s1 = actionEdgeIndex.get(i).action.getName();
                        String s2 = strategy.get(j).getAction();
                        String s3 = template.getEdge(actionEdgeIndex.get(i).index).getSource();
                        String s4 = strategy.get(j).getLocation();
                        String s5 = template.getLocation(template.findLocationByName(s4)).getId();
                        if(s1.equals(s2) && s3.equals(s5)){
                            rt.set(i, Boolean.TRUE);
                        }
                    }
                }
            }else{
                rt.set(i, Boolean.TRUE);
            }
        }
        
        
        for(int i=0; i<rt.size(); i++){
            if(rt.get(i).equals(Boolean.FALSE)){
                redundantEdgeIndex.add(actionEdgeIndex.get(i).index);
            }
        }
    }
    
    private void removeRedundantEdges(){
        ArrayList<Edge> e = new ArrayList<>();
        
        for(int i=0; i<template.getEdges().size(); i++){
            if(!redundantEdgeIndex.contains(i)){
                e.add(template.getEdge(i));
            }
        }
        
        template.setEdges(e);
    }
    
    private boolean removeOrphanedLocations(){
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Edge> edges = template.getEdges();
        boolean changed = false;
        
        for(Location location : template.getLocations()){
            if(location.isInitial()){//if location is initial, don't remove
                locations.add(location);
                continue;
            }
            for(Edge edge : edges){
                if(edge.getTarget().equals(location.getId())){//if an edge leading to the location in question has been found, don't remove
                    locations.add(location);
                    break;
                }
            }
        }
        
        changed = !(locations.size() == template.getLocations().size());
        
        template.setLocations(locations);
        
        return changed;
    }
    
    private boolean removeOrphanedEdges(){
        ArrayList<Edge> edges = new ArrayList<>();
        boolean changed = false;
        
        for(int i=0; i<template.getEdges().size(); i++){
            Edge s = template.getEdge(i);
            if(template.findLocationById(s.getSource())<0){//check if the current edge's source location exists
                changed = true;//if not, drop
            }else{
                edges.add(s);//if present, keep
            }
        }
        
        template.setEdges(edges);
        
        return changed;
    }
    
    class EdgeDataContainer{
        int index;
        Action action;

        public EdgeDataContainer(int index, Action action) {
            this.index = index;
            this.action = action;
        }
    }
}
