import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.*;

public class Main {
    static HashMap<String, HashMap<String, TerminalPair> > LLTable = new HashMap<String, HashMap<String, TerminalPair> >();
    static HashMap<String, String> terminalMap = new HashMap<String, String>();

    static Stack<Character> input_stack = new Stack<Character>();
    static Stack<String> grammar_stack = new Stack<String>();

    static boolean isAlphaNum = false;

    // States
    private static final int q0 = 0;
    private static final int q1 = 1;
    private static final int q2 = 2;
    private static final int q3 = 3;
    private static final int q4 = 4;
    private static final int q5 = 5;
    private static final int q6 = 6;
    private static final int q7 = 7;
    private static final int q8 = 8;
    private static final int q9 = 9;
    private static final int garb = -1;

    public static void startup(String line){
        // start node
        grammar_stack.push("expression");

        // generate terminals
        terminalMap.put("+", "+");
        terminalMap.put("*", "*");
        terminalMap.put("(", "(");
        terminalMap.put(")", ")");

        // put all input in stack
        for(int x=line.length()-1; x > -1; x--){
            input_stack.push(line.charAt(x));
        }
    }

    static private int checker(int currstate, char character) {
        switch (currstate) {
            case q0: 
                switch (character) {
                    case '$': return q1;
                    case 'F': return q2;
                    case 'R': return q2;
                    case 'D': return q7;
                    default: return garb;
            }
            default: 
                return garb;
        }
    }
    // ASCII (a-z = 97-122) (0-9 = 48-57)
    public static boolean DoLLOneParsing(){
        int state = q0;
        isAlphaNum = false;

        while(!input_stack.isEmpty() && (state != garb)){
            // id = integers
            if(grammar_stack.peek().equals("id")){
                if(input_stack.peek() > 47 && input_stack.peek() < 58){
                    input_stack.pop();
                    isAlphaNum = true;
                    // state = checker(state, grammar_stack.peek().charAt(0));
                }
                else if(isAlphaNum){
                    grammar_stack.pop();
                    isAlphaNum = false;
                }
                else
                    state = garb;
            }
            else if(terminalMap.containsKey(grammar_stack.peek())){
                state = checker(state, grammar_stack.peek().charAt(0));
                grammar_stack.pop();
                input_stack.pop();
            }
            else if(grammar_stack.peek().equals("epsilon")){
                grammar_stack.pop();
            }
            else if(grammar_stack.peek().equals("empty")){
                state = checker(state, grammar_stack.peek().charAt(0));
            }
            else if(Character.isLowerCase(grammar_stack.peek().charAt(0))){
                String currtop = grammar_stack.peek();
                grammar_stack.pop();

                ArrayList<String> values = new ArrayList<String>();

                if(input_stack.peek() > 47 && input_stack.peek() < 58)
                    values = LLTable.get(currtop).get("id").getValue();
                else
                    values = LLTable.get(currtop).get(input_stack.peek().toString()).getValue();

                for(int x=values.size()-1; x>-1; x--){
                    grammar_stack.push(values.get(x));
                }
            }
        }

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

        // while( (line = br.readLine()) != null){
        //     if(!line.equals("")){
        //         fw.append(line + " - ");
        //         line = line.replace(" ", "");

        //         startup(line);
        //         if(DoLLOneParsing()){
        //             fw.append("ACCEPT");
        //         }
        //         else{
        //             fw.append("REJECT");
        //         }
                
        //         fw.append("\n");
        //     }
        // }

        LLTable.entrySet().forEach(nonTerminal->{
            System.out.println("NonTerminal = " + nonTerminal.getKey().toString());
            
            nonTerminal.getValue().entrySet().forEach(terminal->{
                System.out.print("Ternimal = " + terminal.getKey().toString() + " === ");

                for(int x=0; x<terminal.getValue().getValue().size(); x++){
                    System.out.print(terminal.getValue().getValue().get(x) + "  ");
                }

                System.out.println("");
            });

            System.out.println("");
        });

        System.out.println("Parsing Complete!");

        fr.close();
        fw.close();
    }
}
