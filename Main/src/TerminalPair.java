import java.util.ArrayList;

public class TerminalPair {
    private String terminalName;
    private ArrayList<String> value;

    public TerminalPair(String name, ArrayList<String> value){
        this.terminalName = name;
        this.value = value;
    }

    public String getName(){
        return this.terminalName;
    }

    public ArrayList<String> getValue(){
        return this.value;
    }
}
