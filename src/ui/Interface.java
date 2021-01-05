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

package ui;
import app.Main;
import domain.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Javier Linares
 */

/**
 * This class defines the interface with which the user will interact.
 */
public class Interface {

   private static Scanner ss = new Scanner(System.in), si = new Scanner(System.in);


    public static void begin() {


        WriterAndReader wr;
        int x = 0;
        boolean run = true;
        String encryptedText = "", fileName = "", key = "", decryptedText = "";


        do {
            System.out.println();
            System.out.println("Choose an option:");
            System.out.println("1: Encrypt.");
            System.out.println("2: Decrypt.");
            System.out.println("3: Quit.");
            System.out.println();
            try {
                x = si.nextInt();
            } catch (InputMismatchException ie) {
                System.out.println("You must type a number between 1 and 3");
                si = new Scanner(System.in);
                x = 0;
            }

            switch (x) {

                case 1:
                    System.out.println();
                    System.out.println("Which file would you like to encrypt?: ");
                    fileName = ss.nextLine();
                    wr = new WriterAndReader(fileName);
                    try { encryptedText = CipherAES.encrypt(wr.toString());
                    } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Key: " + CipherAES.getKeyUsed());
                    wr.write(encryptedText);
                    System.out.println("File name: ciphertext.txt");
		    System.out.println("ciphertext: " + encryptedText);
                    break;

                case 2:
                    System.out.println();
                    System.out.println("Which file would you like to decrypt?: ");
                    fileName = ss.nextLine();
                    System.out.println("Enter a valid key: ");
                    key = ss.nextLine();
                    wr = new WriterAndReader(fileName);
                    try { decryptedText = CipherAES.decrypt(wr.toString(), key);
                    } catch (UnsupportedEncodingException | IllegalArgumentException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    wr.write(decryptedText);
                    break;


                case 3:
                    run = false;
                    break;
                default:
                    System.out.println("Wrong input.");

            }
        }
            while (run);
    }

}
