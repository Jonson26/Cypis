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

import cypis.Cypis;
import cypis.modelAPI.ADTool.Node;
import cypis.modelAPI.UPPAAL.Model;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class EasyFileLoader {
    public Model loadModel(String filename){
        Model m = null;
        try {
            new TempXMLFilemaker().adaptUPPAALFile(filename);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UPPAALHandler handler = new UPPAALHandler();
            saxParser.parse("cypistemp.xml", handler);
            m = handler.getModel();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Cypis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    
    public Node loadTree(String filename){
        Node n = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ADToolHandler handler = new ADToolHandler();
            saxParser.parse(filename, handler);
            n = handler.getTree();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Cypis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
}
