/**
 Copyright [2020] [Javier Linares Castrillón]
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package domain;

/**
 * @author Javier
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class will read the data from a txt and write the Cipher's output into another txt.
 */
public class WriterAndReader {

    private String fileName = "ciphertext.txt";
    private String plaintext = "empty";


    public String toString(){
        return plaintext;
    }

    public WriterAndReader(String name){
        try {
            File f  = new File(name);
            f.createNewFile();
            Scanner sc = new Scanner(f);
            StringBuilder sb = new StringBuilder();
            while(sc.hasNext()) {
                String s = sc.nextLine();
                sb.append(s);
            }
            plaintext = sb.toString();

        }catch(IOException ex)	{
            System.err.println(ex);
        }
    }

    public void write(String s) {
        try {
            File f = new File(fileName);
            f.createNewFile();
            FileWriter fw = new FileWriter(fileName);

            fw.write(s);
            fw.close();
        }catch(IOException ex) {
            System.err.println(ex);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
