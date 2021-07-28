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

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class ActionParser {
    public ArrayList<Action> parse(String declaration){
        ArrayList<Action> actions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(declaration));
        
        try{
            String s = "";;
            while(!s.equals("//Actions START")){
                s = reader.readLine();
            }
            
            while(!s.equals("//Actions STOP")){
                s = reader.readLine();
                actions.add(parseLine(s));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        actions.removeAll(Collections.singleton(null));
        
        return actions;
    }
    
    private Action parseLine(String line){
        Pattern pattern = Pattern.compile("const bool(.*?)= true");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            String s = matcher.group(1).replaceAll("\\s+","");
            return new Action(s);
        }
        return null;
    }
}
