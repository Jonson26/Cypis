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
package cypis.modelAPI.ADTool;

import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 * 
 * This class can be used to represent ADTool-style Attack-Defense Trees in-code.
 * Note however, that loading and saving of ADTool files is not in the feature set of this class.
 */
public class Node {
    private NodeType type;
    private Operator operator;
    private String label;
    private ArrayList<Node> children;
    private Node countermeasure;
    public boolean switcher;
    
    //constructors
    public Node(Operator o, NodeType t, String l){
        type = t;
        operator = o;
        label = l;
        children = new ArrayList<>();
        switcher = false;
    }
    
    public Node(){
        this(Operator.OR, NodeType.NODE, "default node");
    }
    
    public void addChild(Node c){
        children.add(c);
    }
    
    //below are all the getsetters
    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public Node getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(Node countermeasure) {
        this.countermeasure = countermeasure;
    }
}
