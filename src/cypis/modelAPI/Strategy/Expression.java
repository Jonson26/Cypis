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
import cypis.modelAPI.ADTool.Operator;
import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class Expression {
    public ExpressionType operator;
    public Expression a, b;
    public String varName;
    
    public Expression(ExpressionType operator, Expression a, Expression b) {
        this.operator = operator;
        if(operator != ExpressionType.VARIABLE){
            this.a = a;
            this.b = b;
        }else{
            this.varName = "?";
        }
    }

    public Expression(ExpressionType operator, String varName) {
        this.operator = operator;
        if(operator != ExpressionType.VARIABLE){
            this.a = new Expression(ExpressionType.VARIABLE, "?");
            this.b = new Expression(ExpressionType.VARIABLE, "?");
        }else{
            this.varName = varName;
        }
    }
    
    public Expression(Node n){
        if(n.getChildren().isEmpty()){//Node is a leaf, therefore it converts to a variable.
            operator = ExpressionType.VARIABLE;
            varName = n.getLabel();
        }else if(n.getChildren().size() == 1){//Node has only one child, thereofre it is equal to a one-element expression, with the element being the expression it's single child converts to.
            Expression e = new Expression(n.getChildren().get(0));
            this.a = e.a;
            this.b = e.b;
            this.operator = e.operator;
            this.varName = e.varName;
        }else{
            if(n.getOperator() == Operator.AND){//Expression is of type AND
                this.operator = ExpressionType.AND;
            }else{//Expression is of type OR
                this.operator = ExpressionType.OR;
            }
            this.a = new Expression(n.getChildren().get(0));
            Node temp = new Node(n.getOperator(), n.getType(), n.getLabel());//In case there's more than 2 children in n
            temp.removeChild(0);
            this.b = new Expression(temp);
        }
    }
    
    public String buildExpressionRepresentation(){
        switch(operator){
            case VARIABLE:
                return varName;
            case OR:
                return "("+a.buildExpressionRepresentation()+" OR "+b.buildExpressionRepresentation()+")";
            case AND:
                return "("+a.buildExpressionRepresentation()+" AND "+b.buildExpressionRepresentation()+")";
        }
        return "Err";
    }
    
    public ArrayList<ArrayList<String>> bulidEDNF(){
        ArrayList<ArrayList<String>> o = new ArrayList<>();
        ArrayList<ArrayList<String>> aL, bL, cL;
        switch(operator){
            case VARIABLE:
                ArrayList<String> t =  new ArrayList<>();
                t.add(varName);
                o.add(t);
                return o;
            case OR:
                aL = a.bulidEDNF();
                bL = b.bulidEDNF();
                cL = new Expression(ExpressionType.AND, a, b).bulidEDNF();
                o.addAll(aL);
                o.addAll(bL);
                o.addAll(cL);
                return o;
            case AND:
                aL = a.bulidEDNF();
                bL = b.bulidEDNF();
                aL.forEach(ae -> {
                    bL.forEach(be -> {
                        ae.addAll(be);
                        o.add(ae);
                    });
                });
                return o;


        }
        return o;
    }
}
