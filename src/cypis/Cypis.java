/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypis;

import cypis.modelAPI.ADTrees.*;
/**
 *
 * @author User
 */
public class Cypis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createTestTree();
    }
    
    public static void createTestTree(){//creates a test Attack-Defense Tree
        TreeNode tree = new TreeNode();//create root node
        tree.setOperator(TreeOperator.OR);
        tree.setType(TreeNodeType.NODE);
        tree.setLabel("A<> NOT punished");
        
        TreeNode node1 = new TreeNode();//create first child node
        node1.setOperator(TreeOperator.OR);
        node1.setType(TreeNodeType.NODE);
        node1.setLabel("when in received_ballot_coerced do notify_authority");
        
        TreeNode node2 = new TreeNode();//create second child node
        node2.setOperator(TreeOperator.OR);
        node2.setType(TreeNodeType.NODE);
        node2.setLabel("when in received_fake_tracker do say_lie");
        
        tree.setChildren(new TreeNode[]{node1, node2});//register both child nodes as such in the root node
    }
}
