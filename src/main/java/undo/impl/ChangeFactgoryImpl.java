package undo.impl;

import undo.Change;
import undo.ChangeFactory;

public class ChangeFactgoryImpl implements ChangeFactory {
    @Override
    public Change createDeletion(int pos, String s, int oldDot, int newDot) {
       return new RichTextChange(RichTextChange.TYPES.delete, pos, oldDot, newDot, s);
    }

    @Override
    public Change createInsertion(int pos, String s, int oldDot, int newDot) {
        return new RichTextChange(RichTextChange.TYPES.insert, pos, oldDot, newDot, s);
    }
}
