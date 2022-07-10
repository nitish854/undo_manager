package undo.impl;

import undo.Document;

public class RichTextDocument implements Document {

    private StringBuilder content = new StringBuilder();
    private int currentPos = 0;

    @Override
    public void delete(int pos, String s) {

        if ((pos < 0) || pos >= content.length() || pos + s.length() > content.length()){
            throw new IllegalStateException("position out of string content");
        }

        if (content.subSequence(pos, pos + s.length()).toString().equalsIgnoreCase(s)){
            content.replace(pos, pos + s.length(), "");
        }
    }

    @Override
    public void insert(int pos, String s) {
        if (pos < 0 || pos > content.length() ){
            throw new IllegalStateException("position out of string content");
        }

        content.insert(pos, s);
        currentPos = pos + s.length();
    }

    @Override
    public void setDot(int pos) {
        currentPos = pos;
    }

    public String getContent(){
        return content.toString();
    }
}
