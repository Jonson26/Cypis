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

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class StrategyParser {
    public ArrayList<Strategy> parseStrategies(Node adt){
        String name1 = adt.getLabel();
        String name2 = null;
        if(adt.getChildren().size()>1){
            name2 = adt.getCountermeasure().getLabel();
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
}
