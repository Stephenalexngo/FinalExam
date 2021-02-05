import java.util.ArrayList;
import java.util.HashMap;

public class GenerateLLTable {
    String[] nonTerminals = {"expression","plus_expression","paren","multi_expression","final_value"};
    String[] terminals = {"+","*","(",")","id","$"};

    String[] expression_table = {"empty", "empty", "expression plus_expression", "empty", "expression plus_expression", "empty"};
    String[] plus_expression_table = {"+ expression plus_expression", "empty", "empty", "epsilon", "empty", "epsilon    "};
    String[] paren_table = {"empty", "empty", "paren multi_expression", "empty", "paren multi_expression", "empty"};
    String[] multi_expression_table = {"epsilon", "* paren multi_expression", "empty", "epsilon", "empty", "epsilon"};
    String[] final_value_table = {"empty", "empty", "( expression )", "empty", "id", "empty"};
    
    // ASCII (a-z = 97-122) (0-9 = 48-57)

    HashMap<String, HashMap<String, TerminalPair> > LLParsingTable = new HashMap<String, HashMap<String, TerminalPair> >();
    
    public HashMap<String, HashMap<String, TerminalPair> > generateTable(){
        for(int x=0; x<nonTerminals.length; x++){
            HashMap<String, TerminalPair> terminalMap = new HashMap<String, TerminalPair>();
            for(int y=0; y<terminals.length; y++){
                String[] values = {};
                
                if(x==0){
                    values = expression_table[y].split(" ");
                }
                else if(x==1){
                    values = plus_expression_table[y].split(" ");
                }
                else if(x==2){
                    values = paren_table[y].split(" ");
                }
                else if(x==3){
                    values = multi_expression_table[y].split(" ");
                }
                else if(x==4){
                    values = final_value_table[y].split(" ");
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