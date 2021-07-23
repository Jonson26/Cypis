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
package cypis.modelAPI.ADTrees;

import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga <filip.jamroga.001 at student.uni.lu>
 * 
 * This class can be used to represent ADTool-style Attack-Defense Trees in-code.
 * Note however, that loading and saving of ADTool files is not in the feature set of this class.
 */
public class TreeNode {
    private TreeNodeType type;
    private TreeOperator operator;
    private String label;
    private ArrayList<TreeNode> children;
    private TreeNode countermeasure;
    
    //constructors
    public TreeNode(TreeOperator o, TreeNodeType t, String l){
        type = t;
        operator = o;
        label = l;
        children = new ArrayList<>();
    }
    
    public TreeNode(){
        this(TreeOperator.OR, TreeNodeType.NODE, "default node");
    }
    
    public void addChild(TreeNode c){
        children.add(c);
    }
    
    //below are all the getsetters
    public TreeNodeType getType() {
        return type;
    }

    public void setType(TreeNodeType type) {
        this.type = type;
    }

    public TreeOperator getOperator() {
        return operator;
    }

    public void setOperator(TreeOperator operator) {
        this.operator = operator;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public TreeNode getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(TreeNode countermeasure) {
        this.countermeasure = countermeasure;
    }
}
