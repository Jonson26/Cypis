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
import cypis.modelAPI.ADTool.NodeType;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class StrategyParser {
    
    public Strategy parseStrategies(ArrayList<String> list, String name){
        ArrayList<PartialStrategy> strategy = new ArrayList<>();
        HashMap<String, String[]> locations = new HashMap<>();
        
        for(String s: list){
            TemporaryPartialStrategy t = parsePartialStrategyDefinitionString(s);
            if(t.valid){
                String[] locs = t.action.split(",");
                String[] act = t.state.split(",");

                for(String l: locs){
                    if(locations.containsKey(l)){
                        String[] actt = locations.get(l);
                        String[] actt2 = {};
                        for(String st: act){
                            if(contains(st, actt)){
                                int i = actt2.length;
                                actt2[i] = st;
                            }
                        }
                        locations.put(l, act);
                    }else{
                        locations.put(l, act);
                    }
                }
            }
        }
        
        for(String key: locations.keySet()){
            String[] l = locations.get(key);
            if(l.length==0){
                strategy.add(new PartialStrategy(key, null, true));// null action partial strategy found
                System.out.println("Warning, null action partial strategy found. Please check if your attack-defense tree does not contain an internally contradictive set of partial strategies for location "+key+"!");
            }
            for(String e: l){
                strategy.add(new PartialStrategy(key, e, true));
            }
        }
        
        return new Strategy(name, strategy);
    }
    
    public ArrayList<Strategy> parseStrategies(Node adt){
        String name1 = adt.getLabel();
        String name2 = null;
        if(adt.getChildren().size()>1){
            name2 = adt.getChildren().get(1).getLabel();
        }
        
        ArrayList<Node> leaves = getLeaves(adt);
        
        ArrayList<PartialStrategy> strategy1 = new ArrayList<>();
        ArrayList<PartialStrategy> strategy2 = new ArrayList<>();
        
        for(int i=0; leaves.size()>i; i++){
            Node leaf = leaves.get(i);
            if(leaf.getType() == NodeType.COUNTERMEASURE){
                strategy2.add(new PartialStrategy(leaf.getLabel()));
            }else{
                strategy1.add(new PartialStrategy(leaf.getLabel()));
            }
        }
        
        ArrayList<Strategy> startegies = new ArrayList<>();
        startegies.add(new Strategy(name1, strategy1));
        if(name2 != null){
            startegies.add(new Strategy(name2, strategy2));
        }
        
        return startegies;
    }
    
    private ArrayList<Node> getLeaves(Node tree){
        ArrayList<Node> leaves = new ArrayList<>();
        
        ArrayList<Node> branches = tree.getChildren();
        
        for(int i=0; branches.size()>i; i++){
            Node c = branches.get(i);
            if(c.getChildren().isEmpty()){
                leaves.add(c);
            }else{
                leaves.addAll(getLeaves(c));
            }
        }
        
        return leaves;
    }
    
    private TemporaryPartialStrategy parsePartialStrategyDefinitionString(String s){
        if(s.length()<7){
            return new TemporaryPartialStrategy(null, null, false);
        }
        String t = s.substring(0, 7);
        if (t.equals("when in")){
            s = s.substring(7);
        }else{
            return new TemporaryPartialStrategy(null, null, false);
        }
        String[] parts = s.split(" do ");
        return new TemporaryPartialStrategy(parts[0].replaceAll("\\s+",""),
                                            parts[1].replaceAll("\\s+",""),
                                            true);
    }
    
    private Boolean contains(String s, String[] t){
        for(String st: t){
            if(s.equals(st)) return true;
        }
        return false;
    }
    
    private class TemporaryPartialStrategy{
        String action, state;
        Boolean valid;

        public TemporaryPartialStrategy(String action, String state, Boolean valid) {
            this.action = action;
            this.state = state;
            this.valid = valid;
        }
        
        public PartialStrategy ConvertToFullPartialStrategy(){
            return new PartialStrategy(action, state, valid);
        }
    }
}
