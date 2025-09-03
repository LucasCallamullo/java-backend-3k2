package utils;


public class MenuOption {
    private int code;
    private String text;
    private FuncAction action;

    public MenuOption(int code, String text, FuncAction action){
        this.code = code;
        this.text = text;
        this.action = action;
    }

    public FuncAction getAction(){
        return this.action;
    }

    public int getCode(){
        return this.code;
    }

    @Override
    public String toString() {
        return this.code + " - " + this.text;
    }
}
