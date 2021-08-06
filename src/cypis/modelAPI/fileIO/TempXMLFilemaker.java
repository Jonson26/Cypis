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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Filip Jamroga (filip.jamroga.001 at student.uni.lu)
 */
public class TempXMLFilemaker {
    
    public void adaptUPPAALFile(String filename) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        
        FileWriter fw = new FileWriter("./cypistemp/temp.xml");
        BufferedWriter bw = new BufferedWriter(fw);
        
        String s = br.readLine();
        bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        bw.newLine();
        if(s.length()>38){
            s = s.substring(38);
        }else{
            s = br.readLine();
        }
        if(s.length()>126){
            s = s.substring(126);
            bw.write(s);
            bw.newLine();
        }
        
        s = br.readLine();
        while(s!=null){
            bw.write(s);
            bw.newLine();
            s = br.readLine();
        }
        
        bw.close();
        fw.close();
        br.close();
        fr.close();
    }
}
