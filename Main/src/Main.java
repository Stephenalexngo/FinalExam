import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.*;

public class Main {
    static HashMap<String, HashMap<String, TerminalPair> > LLTable = new HashMap<String, HashMap<String, TerminalPair> >();
    static HashMap<String, String> terminalMap = new HashMap<String, String>();

    static Stack<String> grammar_stack = new Stack<String>();

    static boolean isAlphaNum = false;

    public static String startup(String line){
        // start node
        grammar_stack.push("$");
        grammar_stack.push("boy");

        // generate terminals
        terminalMap.put("U", "U");
        terminalMap.put("E", "E");
        terminalMap.put("(", "(");
        terminalMap.put(")", ")");

        return line+"$";
    }

    static private ArrayList<String> checker(String currstate, char character) {
        switch (currstate) {
            case "boy": 
                switch (character) {
                    case '(': return LLTable.get("boy").get("(").getValue();
                    case ')': return LLTable.get("boy").get(")").getValue();
                    case 'A': return LLTable.get("boy").get("alphanum").getValue();
                    case 'S': return LLTable.get("boy").get("symbol").getValue();
                    case 'U': return LLTable.get("boy").get("U").getValue();
                    case 'E': return LLTable.get("boy").get("E").getValue();
                    case '$': return LLTable.get("boy").get("$").getValue();
                    default: return null;
            }
            case "car": 
                switch (character) {
                    case '(': return LLTable.get("car").get("(").getValue();
                    case ')': return LLTable.get("car").get(")").getValue();
                    case 'A': return LLTable.get("car").get("alphanum").getValue();
                    case 'S': return LLTable.get("car").get("symbol").getValue();
                    case 'U': return LLTable.get("car").get("U").getValue();
                    case 'E': return LLTable.get("car").get("E").getValue();
                    case '$': return LLTable.get("car").get("$").getValue();
                    default: return null;
            }
            case "door": 
                switch (character) {
                    case '(': return LLTable.get("door").get("(").getValue();
                    case ')': return LLTable.get("door").get(")").getValue();
                    case 'A': return LLTable.get("door").get("alphanum").getValue();
                    case 'S': return LLTable.get("door").get("symbol").getValue();
                    case 'U': return LLTable.get("door").get("U").getValue();
                    case 'E': return LLTable.get("door").get("E").getValue();
                    case '$': return LLTable.get("door").get("$").getValue();
                    default: return null;
            }
            case "apple": 
                switch (character) {
                    case '(': return LLTable.get("apple").get("(").getValue();
                    case ')': return LLTable.get("apple").get(")").getValue();
                    case 'A': return LLTable.get("apple").get("alphanum").getValue();
                    case 'S': return LLTable.get("apple").get("symbol").getValue();
                    case 'U': return LLTable.get("apple").get("U").getValue();
                    case 'E': return LLTable.get("apple").get("E").getValue();
                    case '$': return LLTable.get("apple").get("$").getValue();
                    default: return null;
            }
            case "feather": 
                switch (character) {
                    case '(': return LLTable.get("feather").get("(").getValue();
                    case ')': return LLTable.get("feather").get(")").getValue();
                    case 'A': return LLTable.get("feather").get("alphanum").getValue();
                    case 'S': return LLTable.get("feather").get("symbol").getValue();
                    case 'U': return LLTable.get("feather").get("U").getValue();
                    case 'E': return LLTable.get("feather").get("E").getValue();
                    case '$': return LLTable.get("feather").get("$").getValue();
                    default: return null;
            }
            case "good": 
                switch (character) {
                    case '(': return LLTable.get("good").get("(").getValue();
                    case ')': return LLTable.get("good").get(")").getValue();
                    case 'A': return LLTable.get("good").get("alphanum").getValue();
                    case 'S': return LLTable.get("good").get("symbol").getValue();
                    case 'U': return LLTable.get("good").get("U").getValue();
                    case 'E': return LLTable.get("good").get("E").getValue();
                    case '$': return LLTable.get("good").get("$").getValue();
                    default: return null;
            }
            default: 
                return null;
        }
    }
    // ASCII (a-z = 97-122) (0-9 = 48-57)
    public static boolean DoLLOneParsing(String line){
        isAlphaNum = false;
        int cnt=0;
        boolean invalid = false;

        while(line.charAt(cnt) != '$' && !invalid && !grammar_stack.peek().equals("$")){
            if(line.charAt(cnt) == 'E' && line.charAt(cnt+1) == 'U')
                cnt+=2;
            else if(grammar_stack.peek().equals("alphanum")){
                // System.out.println("PASOK ALPHANUM");
                if((line.charAt(cnt) > 47 && line.charAt(cnt) < 58) || (line.charAt(cnt) > 96 && line.charAt(cnt) < 123)){
                    cnt++;
                    isAlphaNum = true;
                }
                else if(isAlphaNum){
                    grammar_stack.pop();
                    isAlphaNum = false;
                }
                else
                    invalid = true;
            }
            else if(grammar_stack.peek().equals("symbol")){
                // System.out.println("PASOK SYMBOL");
                if(line.charAt(cnt) == '?' || line.charAt(cnt) == '+' || line.charAt(cnt) == '*'){
                    cnt++;
                    grammar_stack.pop();
                }
                else
                    invalid = true;
            }
            else if(grammar_stack.peek().equals("epsilon")){
                // System.out.println("PASOK EPSILON");
                grammar_stack.pop();
            }
            else if(grammar_stack.peek().equals("empty")){
                // System.out.println("PASOK EMPTY");
                invalid = true;
            }
            else if(Character.isLowerCase(grammar_stack.peek().charAt(0))){
                // System.out.println("PASOK LOWER");
                String currtop = grammar_stack.peek();
                grammar_stack.pop();

                ArrayList<String> values = new ArrayList<String>();

                if((line.charAt(cnt) > 47 && line.charAt(cnt) < 58) || (line.charAt(cnt) > 96 && line.charAt(cnt) < 123))
                    values = checker(currtop, 'A');
                else if(line.charAt(cnt) == '?' || line.charAt(cnt) == '+' || line.charAt(cnt) == '*'){
                    values = checker(currtop, 'S');
                }
                else
                    values = checker(currtop, line.charAt(cnt));

                if(values != null){
                    for(int x=values.size()-1; x>-1; x--){
                        grammar_stack.push(values.get(x));
                    }
                }
                else
                    invalid = true;
            }
            else{
                // System.out.println("PASOK TERMINAL");
                if(terminalMap.containsKey(line.charAt(cnt)+"")){
                    if(terminalMap.get(line.charAt(cnt)+"").equals(grammar_stack.peek())){
                        cnt++;
                        grammar_stack.pop();
                    }
                    else
                        invalid = true;
                }
                else
                    invalid = true;
            }
        }

        if(isAlphaNum){
            grammar_stack.pop();
        }

        if(invalid){
            return false;
        }
        else{
            if(line.charAt(cnt) == '$'){
                if(grammar_stack.peek().equals("$"))
                    return true;
                else{
                    while(!grammar_stack.peek().equals("$")){
                        if(LLTable.containsKey(grammar_stack.peek())){
                            if(LLTable.get(grammar_stack.peek()).get("$").getValue().get(0).equals("epsilon")){
                                grammar_stack.pop();
                            }
                            else
                                return false;
                        }
                        else
                            return false;
                    }

                    return true;
                }
            } 
            else
                return false;
        }
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

                String newline = startup(line);
                // System.out.println("NEWLINE === " + newline);
                if(newline.charAt(0) == 'E'){
                    if(newline.charAt(1) == '$')
                        fw.append("ACCEPT");
                    else if(newline.charAt(1) == 'U'){
                        if(DoLLOneParsing(newline.substring(2, newline.length())))
                            fw.append("ACCEPT");
                        else
                            fw.append("REJECT");
                    }
                    else
                        fw.append("REJECT");
                }
                else if(DoLLOneParsing(newline)){
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
