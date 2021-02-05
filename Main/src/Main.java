import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        File input_file = new File("Main/src/input.txt");
        FileReader fr = new FileReader(input_file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        FileWriter fw = new FileWriter("Main/src/output.txt");

        

        fr.close();
        fw.close();
    }
}
