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
        TreeNode tree = new TreeNode(TreeOperator.OR, TreeNodeType.NODE, "A<> NOT punished");//create root node
        
        tree.addChild(new TreeNode(TreeOperator.OR, TreeNodeType.NODE, "when in received_ballot_coerced do notify_authority"));//create and register first child node
        
        tree.addChild(new TreeNode(TreeOperator.OR, TreeNodeType.NODE, "when in received_fake_tracker do say_lie"));//create and register second child node
    }
}
