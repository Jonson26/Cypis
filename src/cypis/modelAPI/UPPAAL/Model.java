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
package cypis.modelAPI.UPPAAL;

import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class Model {
    private String declaration, systemDeclaration;
    private ArrayList<Template> templates;

    public Model(String declaration, String systemDeclaration, ArrayList<Template> templates) {
        this.declaration = declaration;
        this.systemDeclaration = systemDeclaration;
        this.templates = templates;
    }

    public Model() {
        this("", "", new ArrayList<>());
    }
    
    public Model(Model m) {
        this.declaration = m.getDeclaration()+"";
        this.systemDeclaration = m.getSystemDeclaration()+"";
        this.templates = (ArrayList<Template>) m.getTemplates().clone();
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getSystemDeclaration() {
        return systemDeclaration;
    }

    public void setSystemDeclaration(String systemDeclaration) {
        this.systemDeclaration = systemDeclaration;
    }

    public ArrayList<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(ArrayList<Template> templates) {
        this.templates = templates;
    }
    
    public void addTemplate(Template t){
        templates.add(t);
    }
    
    public Template getTemplate(String name){//returns first instance found; avoid duplicate names!
        int i;
        for(i=0; !templates.get(i).getName().equals(name) && templates.size()!=i; i++){
        }
        if(templates.size()==i){
            return null;
        }
        return templates.get(i);
    }
}
