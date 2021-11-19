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

import cypis.Settings;
import cypis.modelAPI.ADTool.Operator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class SettingsHandler  extends DefaultHandler{
    private static final String MODELFILE = "modelfile";
    private static final String TREEFILE = "treefile";
    private static final String ATTACKERAGENT = "attackeragent";
    private static final String DEFENDERAGENT = "defenderagent";
    private static final String WILDCARD = "wildcard";
    private static final String OUTFILE = "outfile";
    
    private String mf, tf, aa, da, wc, of;
    private Boolean dae;
    
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
        
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case MODELFILE:
                elementValue = new StringBuilder();
                break;
            case TREEFILE:
                elementValue = new StringBuilder();
                break;
            case ATTACKERAGENT:
                elementValue = new StringBuilder();
                break;
            case DEFENDERAGENT:
                dae = Boolean.getBoolean(attr.getValue("exists"));
                elementValue = new StringBuilder();
                break;
            case WILDCARD:
                elementValue = new StringBuilder();
                break;
            case OUTFILE:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case MODELFILE:
                mf = elementValue.toString();
                break;
            case TREEFILE:
                tf = elementValue.toString();
                break;
            case ATTACKERAGENT:
                aa = elementValue.toString();
                break;
            case DEFENDERAGENT:
                da = elementValue.toString();
                break;
            case WILDCARD:
                wc = elementValue.toString().replace(" ", "");
                break;
            case OUTFILE:
                of = elementValue.toString();
                break;
        }
    }

//    private BaeldungArticle latestArticle() {
//        List<BaeldungArticle> articleList = website.articleList;
//        int latestArticleIndex = articleList.size() - 1;
//        return articleList.get(latestArticleIndex);
//    }

    public Settings getSettings() {
        return new Settings(mf, tf, aa, dae, da, wc, of);
    }
}
