package undo.impl;

import undo.Change;
import undo.Document;

public class RichTextChange implements Change {
    
    public enum TYPES{insert, delete}
    private TYPES type;
    private int pos;
    private String s;
    private int oldDot;
    private int newDot;

    public RichTextChange(TYPES type, int pos, int oldDot, int newDot, String s){
        this.type = type;
        this.pos = pos;
        this.oldDot = oldDot;
        this.newDot = newDot;
        this.s = s;
    }

    @Override
    public String getType() {
        return type.name();
    }

    @Override
    public void apply(Document doc) {
        if(type.equals(TYPES.insert)){
            doc.insert(pos, s);
        } else {
            doc.delete(pos, s);
        }
    }

    @Override
    public void revert(Document doc) {
        if(type.equals(TYPES.insert)){
            doc.delete(pos, s);
        } else {
            doc.insert(pos, s);
        }
    }


}
