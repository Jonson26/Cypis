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

import cypis.modelAPI.ADTool.Node;
import cypis.modelAPI.UPPAAL.Edge;
import cypis.modelAPI.UPPAAL.State;
import cypis.modelAPI.UPPAAL.Template;
import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class TemplateReductor {
    private Template template;
    private Node attackTree;
    
    private ArrayList<PartialStrategy> strategy;
    private ArrayList<Action> actions;
    private ArrayList<EdgeDataContainer> actionEdgeIndex;
    private ArrayList<Integer> redundantEdgeIndex;

    public TemplateReductor() {
        strategy = new ArrayList<>();
        actions = new ArrayList<>();
        actionEdgeIndex = new ArrayList<>();
        redundantEdgeIndex = new ArrayList<>();
    }
    
    public Template reduce(Template t, Node n){
        template = new Template(t);
        attackTree = n;
        
        parsePartialStrategies(attackTree);//create a list of partial strategies
        
        actions = new ActionParser().parse(template.getDeclaration());//create a list of all actions defined in template
        
        checkEdges();//create a list of all action edges
        checkEdgeRedundancy();//create a list of redundant edges
        removeRedundantEdges();
        
        boolean run = true;
        while(run){
            run = removeOrphanedStates() || removeOrphanedEdges();
        }
        
        return template;
    }
    
    private void parsePartialStrategies(Node n){
        PartialStrategy p = new PartialStrategy(n.getLabel());//get the node n label and parse it
        if(p.isValid()){//if the resulting partial strategy is valid, add it to the rest
            strategy.add(p);
        }
        
        ArrayList<Node> children = n.getChildren();
        for(int i=0; i<children.size(); i++){//do the same for all the children of n
            parsePartialStrategies(children.get(i));
        }
    }
    
    private void checkEdges(){//check all edges if any of them have actions
        ArrayList<Edge> edges = template.getEdges();
        for(int i=0; i<edges.size(); i++){
            for(int j=0; j<actions.size(); j++){
                if(actions.get(j).checkEdge(edges.get(i))){
                    actionEdgeIndex.add(new EdgeDataContainer(i, actions.get(j)));
                }
            }
        }
    }
    
    private void checkEdgeRedundancy(){
        ArrayList<Boolean> rt = new ArrayList();
        for(int i=0; i<actionEdgeIndex.size(); i++){
            rt.add(Boolean.FALSE);
        }
        
        for(int i=0; i<actionEdgeIndex.size(); i++){
            for(int j=0; j<strategy.size(); j++){
                if(actionEdgeIndex.get(i).action.getName().equals(strategy.get(j).getAction()) &&
                        template.getEdge(actionEdgeIndex.get(i).index).getSource().equals(strategy.get(j).getState())){
                    rt.set(i, Boolean.TRUE);
                }
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
    
    private boolean removeOrphanedStates(){
        ArrayList<State> states = new ArrayList<>();
        ArrayList<Edge> edges = template.getEdges();
        boolean changed = false;
        
        for(int i=0; i<template.getStates().size(); i++){
            State s = template.getStates().get(i);
            if(s.isInitial()){//if state is initial, don't remove
                    states.add(s);
                    continue;
            }
            for(int j=0; j<edges.size(); j++){
                if(template.findStateById(edges.get(j).getSource())==i){//if an edge leading to the state in question has been found, don't remove
                    states.add(s);
                    break;//we only need to find one targeting edge to be sure that the state isn't orphaned
                }else if(i+1<template.getStates().size()){//if we're at the end of the list of edges, and we still haven't found a targeting edge, drop state
                    changed = true;
                }
            }
        }
        
        template.setStates(states);
        
        return changed;
    }
    
    private boolean removeOrphanedEdges(){
        ArrayList<Edge> edges = new ArrayList<>();
        boolean changed = false;
        
        for(int i=0; i<template.getEdges().size(); i++){
            Edge s = template.getEdge(i);
            if(template.findStateById(s.getSource())<0){//check if the current edge's source state exists
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
