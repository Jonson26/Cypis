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
package cypis.modelAPI.fileIO;

import cypis.modelAPI.ADTool.Node;
import cypis.modelAPI.ADTool.NodeType;
import cypis.modelAPI.ADTool.Operator;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class ADToolHandler  extends DefaultHandler{
    private static final String NODE = "node";
    private static final String LABEL = "label";

    private String mode;
    
    private Stack<Node> tree;
    private StringBuilder elementValue;
    private Operator Operator;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        mode = "node";
        tree = new Stack<>();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case NODE:
                Node n = new Node();
                if(tree.isEmpty()){
                    tree.push(n);
                }else{
                    tree.peek().addChild(n);
                    tree.push(n);
                }
                if(attr.getLength()>1){
                    if(attr.getValue("switchRole").equals("yes")){
                        n.switcher = true;
                        if(mode.equals("node")){
                            mode = "countermeasure";
                        }else if(mode.equals("countermeasure")){
                            mode = "node";
                        }
                    }
                }
                if(mode.equals("node")){
                    n.setType(NodeType.NODE);
                }else if(mode.equals("countermeasure")){
                    n.setType(NodeType.COUNTERMEASURE);
                }
                if(attr.getValue("refinement").equals("disjunctive")){
                    n.setOperator(Operator.OR);
                }else if(attr.getValue("refinement").equals("conjunctive")){
                    n.setOperator(Operator.AND);
                }
                break;
            case LABEL:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case NODE:
                if(tree.size()>1){
                    Node n;
                    n = tree.pop();
                    if(n.switcher){
                        if(mode.equals("node")){
                            mode = "countermeasure";
                        }else if(mode.equals("countermeasure")){
                            mode = "node";
                        }
                    }
                }
                break;
            case LABEL:
                tree.peek().setLabel(elementValue.toString());
                break;
        }
    }

//    private BaeldungArticle latestArticle() {
//        List<BaeldungArticle> articleList = website.articleList;
//        int latestArticleIndex = articleList.size() - 1;
//        return articleList.get(latestArticleIndex);
//    }

    public Node getTree() {
        return tree.pop();
    }
}
