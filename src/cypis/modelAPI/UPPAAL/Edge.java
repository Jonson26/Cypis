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
public class Edge {
    private String source, target;
    private Label select, guard;
    private ArrayList<Nail> nails;

    public Edge(String source, String target, Label select, Label guard, ArrayList<Nail> nails) {
        this.source = source;
        this.target = target;
        this.select = select;
        this.guard = guard;
        this.nails = nails;
    }
    
    public Edge(State source, State target, Label select, Label guard, ArrayList<Nail> nails) {
        this(source.getName().getContent(), target.getName().getContent(), select, guard, nails);
    }
    
    public Edge(String source, String target, Label select, Label guard) {
        this(source, target, select, guard, new ArrayList<>());
    }
    
    public Edge(State source, State target, Label select, Label guard) {
        this(source.getName().getContent(), target.getName().getContent(), select, guard, new ArrayList<>());
    }

    public Label getSelect() {
        return select;
    }

    public void setSelect(Label select) {
        this.select = select;
    }

    public Label getGuard() {
        return guard;
    }

    public void setGuard(Label guard) {
        this.guard = guard;
    }

    public ArrayList<Nail> getNails() {
        return nails;
    }

    public void setNails(ArrayList<Nail> nails) {
        this.nails = nails;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
    
    public void addNail(Nail nail){
        nails.add(nail);
    }
}
