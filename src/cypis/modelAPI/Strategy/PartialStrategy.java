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

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class PartialStrategy {
    private String location, action;
    private boolean valid;
    
    /**
    * Functionality moved to StrategyParser due to need for more complex partial
    * strategy description, without altering the basic properties of how the 
    * PartialStrategy class operates.
    *
    * @deprecated use {@link #PartialStrategy(String location, String action, boolean valid)} instead.  
    */
    @Deprecated
    public PartialStrategy(String requirement) {
        parsePartialStrategyDefinitionString(requirement);
    }

    public PartialStrategy(String location, String action, boolean valid) {
        this.location = location;
        this.action = action;
        this.valid = valid;
    }

    public String getLocation() {
        return location;
    }

    public String getAction() {
        return action;
    }
    
    public boolean isValid(){
        return valid;
    }
    
    private void parsePartialStrategyDefinitionString(String s){
        if(s.length()<7){
            return;
        }
        String t = s.substring(0, 7);
        if (t.equals("when in")){
            s = s.substring(7);
        }else{
            action = null;
            location = null;
            valid = false;
            return;
        }
        String[] parts = s.split(" do ");
        action = parts[1].replaceAll("\\s+","");//delete all whitespace
        location = parts[0].replaceAll("\\s+","");
        valid = true;
    }
}
