import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.*;

public class Main {
    public static HashMap<String, HashMap<String, TerminalPair> > LLTable = new HashMap<String, HashMap<String, TerminalPair> >();

    public static boolean DoLLOneParsing(String line){


        return true;
    }

    public static void main(String[] args) throws Exception {
        File input_file = new File("Main/src/input.txt");
        FileReader fr = new FileReader(input_file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        FileWriter fw = new FileWriter("Main/src/output.txt");

        GenerateLLTable gentable = new GenerateLLTable();
        LLTable = gentable.generateTable();

        while( (line = br.readLine()) != null){
            if(!line.equals("")){
                fw.append(line + " - ");
                line = line.replace(" ", "");

                if(DoLLOneParsing(line)){
                    fw.append("ACCEPT");
                }
                else{
                    fw.append("REJECT");
                }
                
                fw.append("\n");
            }
        }

        // LLTable.entrySet().forEach(nonTerminal->{
        //     System.out.println("NonTerminal = " + nonTerminal.getKey().toString());
            
        //     nonTerminal.getValue().entrySet().forEach(terminal->{
        //         System.out.print("Ternimal = " + terminal.getKey().toString() + " === ");

        //         for(int x=0; x<terminal.getValue().getValue().size(); x++){
        //             System.out.print(terminal.getValue().getValue().get(x) + "  ");
        //         }

        //         System.out.println("");
        //     });

        //     System.out.println("");
        // });

        System.out.println("Parsing Complete!");

        fr.close();
        fw.close();
    }
}
