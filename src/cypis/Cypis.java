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
package cypis;

import cypis.modelAPI.ADTrees.TreeNode;
import cypis.modelAPI.ADTrees.TreeOperator;
import cypis.modelAPI.ADTrees.TreeNodeType;

/**
 *
 * @author Filip Jamroga <filip.jamroga.001 at student.uni.lu>
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
