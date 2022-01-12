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
package cypis;

import cypis.modelAPI.ADTool.Node;
import cypis.modelAPI.ADTool.NodeType;
import cypis.modelAPI.Strategy.Expression;
import cypis.modelAPI.fileIO.EasyFileLoader;
import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class CypisTest {//in order to run tests, use this file as main class
    public static void main(String[] args){
        new CypisTest().performTest(Test.ALL, "adt_test1.xml");
    }
    
    public void performTest(Test t, String testfilename){
        Boolean prune = false;
        Boolean expr = false;
        Boolean ednf = false;
        
        switch(t){
            case PRUNING:
                prune = true;
                break;
            case EXPRESSION:
                expr = true;
                break;
            case EDNF:
                ednf = true;
                break;
            case ALL:
                prune = true;
                expr = true;
                ednf = true;
                break;
        }
        
        ArrayList<Node> prunedTrees = pruningTest(testfilename, prune);
        
        Expression[] ex = {null, null};
        for(int i = 0; prunedTrees.size()>i; i++){
            ex[i] = expressionTest(prunedTrees.get(i), expr);
        }
        
        ArrayList<ArrayList<String>>[] ednfs = new ArrayList[2];
        for(int i = 0; ex.length>i; i++){
            ednfs[i] = EDNFTest(ex[i], ednf);
        }
    }
    
    public ArrayList<Node> pruningTest(String filename, Boolean debug){//load adtree xml file, perform pruning; if debug is true print out result to console
        ArrayList<Node> o = new ArrayList<>();
        EasyFileLoader f = new EasyFileLoader();
        Node n = f.loadTree(filename);
        
        Node n1 = n.clone();//prune all node type leaves
        for(int i=1; i!=0; ){
            i=n1.prune(NodeType.NODE);
        }
        Node n2 = n.clone();//prune all countermeasure type leaves
        for(int i=1; i!=0; ){
            i=n2.prune(NodeType.COUNTERMEASURE);
        }
        
        if(debug){//debug output
            System.out.println("---PRUNING DEBUG---");
            System.out.println(n1.generateStringRepresentation());
            System.out.println(n2.generateStringRepresentation());
        }
        
        o.add(n1);//package and return results
        o.add(n2);
        return o;
    }
    
    public Expression expressionTest(Node n, Boolean debug){//test tree-to-expression conversion; if debug is true print out result to console
        Expression e = new Expression(n);
        if(debug){
            System.out.println("---EXPRESSION DEBUG---");
            System.out.println(e.buildExpressionRepresentation());
        }
        return e;
    }
    
    public ArrayList<ArrayList<String>> EDNFTest(Expression e, Boolean debug){//test expression-to-ednf conversion; if debug is true print out result to console
        ArrayList<ArrayList<String>> o = e.bulidEDNF();
        if(debug){
            System.out.println("---EDNF DEBUG---");
            System.out.print("{");
            for(ArrayList<String> block: o){
                System.out.print("[");
                for(String element: block){
                    System.out.print(" "+element+" ");
                }
                System.out.print("]\n");
            }
            System.out.print("}");
        }
        return null;
    }
    
    public enum Test{
        PRUNING, EXPRESSION, EDNF, ALL;
    }
}
