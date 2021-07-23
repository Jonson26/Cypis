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

/**
 *
 * @author Filip Jamroga <filip.jamroga.001 at student.uni.lu>
 */
public class UPPAALState {
    private UPPAALLabel name, invariant;
    String comment, id;
    boolean initial, urgent, committed;
    int x, y;

    public UPPAALState(UPPAALLabel name, UPPAALLabel invariant, String comment, String id, boolean initial, boolean urgent, boolean committed, int x, int y) {
        this.name = name;
        this.invariant = invariant;
        this.comment = comment;
        this.id = id;
        this.initial = initial;
        this.urgent = urgent;
        this.committed = committed;
        this.x = x;
        this.y = y;
    }

    public UPPAALState(UPPAALLabel name, String id, int x, int y) {
        this(name, null, null, id, false, false, false, x, y);
    }

    public UPPAALLabel getName() {
        return name;
    }

    public void setName(UPPAALLabel name) {
        this.name = name;
    }

    public UPPAALLabel getInvariant() {
        return invariant;
    }

    public void setInvariant(UPPAALLabel invariant) {
        this.invariant = invariant;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getId() {// only getter for id - this value should not be changed under _any_ circumstances
        return id;
    }
}
