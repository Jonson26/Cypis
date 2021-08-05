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

import cypis.modelAPI.UPPAAL.Edge;
import cypis.modelAPI.UPPAAL.Label;
import cypis.modelAPI.UPPAAL.Model;
import cypis.modelAPI.UPPAAL.Nail;
import cypis.modelAPI.UPPAAL.State;
import cypis.modelAPI.UPPAAL.Template;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class UPPAALHandler extends DefaultHandler{
    private static final String NTA = "nta";
    private static final String DECLARATION = "declaration";
    private static final String TEMPLATE = "template";
    private static final String NAME = "name";
    private static final String PARAMETER = "parameter";
    private static final String LOCATION = "location";
    private static final String INIT = "init";
    private static final String TRANSITION = "transition";
    private static final String SOURCE = "source";
    private static final String TARGET = "target";
    private static final String LABEL = "label";
    private static final String NAIL = "nail";
    private static final String SYSTEM = "system";
    private static final String COMMITTED = "committed";
    private static final String URGENT = "urgent";
    
    private String mode, lastkind;

    private Model model;
    private StringBuilder elementValue;
    
    private Template template;
    private State state;
    private Edge edge;
    private Label name, label;

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
        model = new Model();
        mode = "model";
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case DECLARATION:
                elementValue = new StringBuilder();
                break;
            case TEMPLATE:
                mode = "template";
                template = new Template();
                break;
            case NAME:
                name = new Label();
                elementValue = new StringBuilder();
                if(attr.getLength()>0){
                    name.setX(Integer.parseInt(attr.getValue("x")));
                    name.setY(Integer.parseInt(attr.getValue("y")));
                }
                break;
            case PARAMETER:
                elementValue = new StringBuilder();
                break;
            case LOCATION:
                mode = "state";
                state = new State(null, attr.getValue("id"), Integer.parseInt(attr.getValue("x")), Integer.parseInt(attr.getValue("y")));
                break;
            case INIT:
                String initId = attr.getValue("ref");
                template.getState(initId).setInitial(true);
                break;
            case TRANSITION:
                edge = new Edge();
                break;
            case SOURCE:
                edge.setSource(attr.getValue("ref"));
                break;
            case TARGET:
                edge.setTarget(attr.getValue("ref"));
                break;
            case LABEL:
                label = new Label();
                elementValue = new StringBuilder();
                if(attr.getLength()>0){
                    lastkind = attr.getValue("kind");
                    label.setX(Integer.parseInt(attr.getValue("x")));
                    label.setY(Integer.parseInt(attr.getValue("y")));
                }
                break;
            case NAIL:
                edge.addNail(new Nail(Integer.parseInt(attr.getValue("x")), Integer.parseInt(attr.getValue("y"))));
                break;
            case SYSTEM:
                elementValue = new StringBuilder();
                break;
            case COMMITTED:
                state.setCommitted(true);
                break;
            case URGENT:
                state.setUrgent(true);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case DECLARATION:
                if("model".equals(mode)){
                    model.setDeclaration(elementValue.toString());
                }else if("template".equals(mode)){
                    template.setDeclaration(elementValue.toString());
                }
                break;
            case TEMPLATE:
                mode = "model";
                model.addTemplate(template);
                break;
            case NAME:
                name.setContent(elementValue.toString());
                if("template".equals(mode)){
                    template.setName(elementValue.toString());
                }else if("state".equals(mode)){
                    name.setContent(elementValue.toString());
                    state.setName(name);
                }
                break;
            case PARAMETER:
                template.setParameter(elementValue.toString());
                break;
            case LOCATION:
                mode = "template";
                template.addState(state);
                break;
            case TRANSITION:
                template.addEdge(edge);
                break;
            case LABEL:
                label.setContent(elementValue.toString());
                switch(lastkind){
                    case "select":
                        edge.setSelect(label);
                        break;
                    case "guard":
                        edge.setGuard(label);
                        break;
                    case "synchronisation":
                        edge.setSync(label);
                        break;
                    case "assignment":
                        edge.setUpdate(label);
                        break;
                }
                break;
            case SYSTEM:
                model.setSystemDeclaration(elementValue.toString());
                break;
        }
    }

//    private BaeldungArticle latestArticle() {
//        List<BaeldungArticle> articleList = website.articleList;
//        int latestArticleIndex = articleList.size() - 1;
//        return articleList.get(latestArticleIndex);
//    }

    public Model getModel() {
        return model;
    }
}
