/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypis.modelAPI.ADTrees;

import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga
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
