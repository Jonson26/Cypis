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
package cypis.modelAPI.UPPAALModels;

import java.util.ArrayList;

/**
 *
 * @author Filip Jamroga <filip.jamroga.001 at student.uni.lu>
 */
public class UPPAALEdge {
    private String source, target;
    private UPPAALLabel select, guard;
    private ArrayList<UPPAALNail> nails;

    public UPPAALEdge(String source, String target, UPPAALLabel select, UPPAALLabel guard, ArrayList<UPPAALNail> nails) {
        this.source = source;
        this.target = target;
        this.select = select;
        this.guard = guard;
        this.nails = nails;
    }
    
    public UPPAALEdge(UPPAALState source, UPPAALState target, UPPAALLabel select, UPPAALLabel guard, ArrayList<UPPAALNail> nails) {
        this(source.getName().getContent(), target.getName().getContent(), select, guard, nails);
    }
    
    public UPPAALEdge(String source, String target, UPPAALLabel select, UPPAALLabel guard) {
        this(source, target, select, guard, new ArrayList<>());
    }
    
    public UPPAALEdge(UPPAALState source, UPPAALState target, UPPAALLabel select, UPPAALLabel guard) {
        this(source.getName().getContent(), target.getName().getContent(), select, guard, new ArrayList<>());
    }

    public UPPAALLabel getSelect() {
        return select;
    }

    public void setSelect(UPPAALLabel select) {
        this.select = select;
    }

    public UPPAALLabel getGuard() {
        return guard;
    }

    public void setGuard(UPPAALLabel guard) {
        this.guard = guard;
    }

    public ArrayList<UPPAALNail> getNails() {
        return nails;
    }

    public void setNails(ArrayList<UPPAALNail> nails) {
        this.nails = nails;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
    
    public void addNail(UPPAALNail nail){
        nails.add(nail);
    }
}
