/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypis.modelAPI.ADTrees;

import cypis.TreeNodeType;
import cypis.TreeOperator;

/**
 *
 * @author User
 */
public class TreeNode {
    private TreeNodeType type;
    private TreeOperator operator;
    private String label;
    private TreeNode[] children;
    private TreeNode countermeasure;

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

    public TreeNode[] getChildren() {
        return children;
    }

    public void setChildren(TreeNode[] children) {
        this.children = children;
    }

    public TreeNode getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(TreeNode countermeasure) {
        this.countermeasure = countermeasure;
    }
}
