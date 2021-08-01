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

import cypis.modelAPI.ADTool.Node;
import cypis.modelAPI.ADTool.Operator;
import cypis.modelAPI.ADTool.NodeType;
import cypis.modelAPI.Strategy.ActionParser;
import cypis.modelAPI.Strategy.TemplateReductor;
import cypis.modelAPI.UPPAAL.Edge;
import cypis.modelAPI.UPPAAL.Label;
import cypis.modelAPI.UPPAAL.Model;
import cypis.modelAPI.UPPAAL.State;
import cypis.modelAPI.UPPAAL.Template;
import cypis.modelAPI.fileIO.UPPAALWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class Cypis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createTestTree();
        Node t = createTestTree2();
        Model m = createTestModel();
        
        ArrayList<Template> templates = new ArrayList<>();
        TemplateReductor tr = new TemplateReductor();
        templates.add(tr.reduce(m.getTemplates().get(0), t));
        
        Model m2 = new Model(m);
        m2.setTemplates(templates);
        
        UPPAALWriter w = new UPPAALWriter();
        File f = new File("test.xml");
        try {
            w.writeModel(m, f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void createTestTree(){//creates a test Attack-Defense Tree
        Node tree = new Node(Operator.OR, NodeType.NODE, "A<> NOT punished");//create root node
        
        tree.addChild(new Node(Operator.OR, NodeType.NODE, "when in received_ballot_coerced do notify_authority"));//create and register first child node
        
        tree.addChild(new Node(Operator.OR, NodeType.NODE, "when in received_fake_tracker do say_lie"));//create and register second child node
    }
    
    public static Node createTestTree2(){//creates a test Attack-Defense Tree related to the test UPPAAL model
        Node tree = new Node(Operator.OR, NodeType.NODE, "A<> a");//create root node
        
        tree.addChild(new Node(Operator.OR, NodeType.NODE, "when in start do go_a"));//create and register child node
        
        return tree;
    }
    
    public static Model createTestModel(){//creates a test UPPAAL model
        Model m = new Model();//create main model object
        m.setDeclaration("// Place global declarations here.");
        m.setSystemDeclaration(
                "// Place template instantiations here.\n" +
                "Process = Template();\n" +
                "\n" +
                "// List one or more processes to be composed into a system.\n" +
                "system Process;");
        
        Template t = new Template();
        t.setName("Template");
        t.setDeclaration(
                "//Actions START\n" +
                "const bool go_a = true;\n" +
                "const bool go_b = true;\n" +
                "//Actions STOP");
        
        t.addState(new State( //add state "b"
                new Label(88, 56, "b"), //name
                null, //invariant
                "", //comment
                "id0", //id
                false, //initial
                false, //urgent
                false, //committed
                80, //x
                80 //y
        ));
        
        t.addState(new State( //add state "a"
                new Label(-72, 56, "a"), //name
                null, //invariant
                "", //comment
                "id1", //id
                false, //initial
                false, //urgent
                false, //committed
                -48, //x
                80 //y
        ));
        
        t.addState(new State( //add state "start"
                new Label(0, -72, "start"), //name
                null, //invariant
                "", //comment
                "id2", //id
                true, //initial
                false, //urgent
                false, //committed
                16, //x
                -40 //y
        ));
        
        t.addEdge(new Edge(
                "id2", //source "start
                "id0", //target "b"
                null, //select
                new Label(48, 0, "go_b"), //guard
                null, //sync
                null //update
        ));
        
        t.addEdge(new Edge(
                "id2", //source "start
                "id1", //target "a"
                null, //select
                new Label(-48, 0, "go_a"), //guard
                null, //sync
                null //update
        ));
        
        m.addTemplate(t);
        
        return m;
    }
}
