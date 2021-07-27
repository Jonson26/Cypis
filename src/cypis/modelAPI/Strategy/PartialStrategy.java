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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class PartialStrategy {
    private String state, action;
    private boolean valid;

    public PartialStrategy(String requirement) {
        parsePartialStrategyDefinitionString(requirement);
    }

    public String getState() {
        return state;
    }

    public String getAction() {
        return action;
    }
    
    public boolean isValid(){
        return valid;
    }
    
    private void parsePartialStrategyDefinitionString(String s){
        Pattern pattern = Pattern.compile("when in(.*?)");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()){
            s = matcher.group(1);
        }else{
            action = null;
            state = null;
            valid = false;
            return;
        }
        String[] parts = s.split(" do ");
        action = parts[0].replaceAll("\\s+","");
        state = parts[1].replaceAll("\\s+","");
        valid = true;
    }
}
