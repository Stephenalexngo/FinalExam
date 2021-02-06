import java.util.ArrayList;
import java.util.HashMap;

public class GenerateLLTable {
    String[] nonTerminals = {"boy","car","door","apple","feather","good"};
    String[] terminals = {"(",")","alphanum","symbol","U","E","$"};

    String[] boy_table = {"( boy ) door", "empty", "car", "empty", "empty", "empty", "empty"};
    String[] car_table = {"empty", "empty", "alphanum door", "empty", "empty", "empty", "empty"};
    String[] door_table = {"empty", "epsilon", "empty", "symbol good", "apple", "empty", "epsilon"};
    String[] apple_table = {"empty", "empty", "empty", "empty", "U feather", "empty", "empty"};
    String[] feather_table = {"boy", "empty", "boy", "empty", "empty", "E", "empty"};
    String[] good_table = {"boy", "epsilon", "boy", "empty", "apple", "empty", "epsilon"};

    HashMap<String, HashMap<String, TerminalPair> > LLParsingTable = new HashMap<String, HashMap<String, TerminalPair> >();
    
    public HashMap<String, HashMap<String, TerminalPair> > generateTable(){
        for(int x=0; x<nonTerminals.length; x++){
            HashMap<String, TerminalPair> terminalMap = new HashMap<String, TerminalPair>();
            for(int y=0; y<terminals.length; y++){
                String[] values = {};
                
                if(x==0){
                    values = boy_table[y].split(" ");
                }
                else if(x==1){
                    values = car_table[y].split(" ");
                }
                else if(x==2){
                    values = door_table[y].split(" ");
                }
                else if(x==3){
                    values = apple_table[y].split(" ");
                }
                else if(x==4){
                    values = feather_table[y].split(" ");
                }
                else if(x==5){
                    values = good_table[y].split(" ");
                }

                ArrayList<String> arr = new ArrayList<String>();
                    for(String str: values){
                        arr.add(str);
                    }

                    terminalMap.put(terminals[y], new TerminalPair(terminals[y], arr));
            }

            LLParsingTable.put(nonTerminals[x], terminalMap);
        }

        return LLParsingTable;
    }
}