package undo.impl;

import undo.Change;
import undo.Document;
import undo.UndoManager;

import java.util.ArrayList;
import java.util.List;

public class RichTextUndoManager implements UndoManager {

    private List<Change> changes;
    private int bufferSize;
    private Document document;
    private int currentChangeIndex;

    public RichTextUndoManager(Document document, int bufferSize){
        this.document = document;
        this.bufferSize = bufferSize;
        changes = new ArrayList<>(bufferSize);
    }

    @Override
    public void registerChange(Change change) {

        if(currentChangeIndex != changes.size() -1 && changes.size() > 0){
            changes = changes.subList(0, currentChangeIndex+1);
        }

        if(changes.size() == bufferSize){
            changes.remove(0);
        }

        changes.add(change);
        change.apply(document);
        currentChangeIndex = changes.size() -1;
    }

    @Override
    public boolean canUndo() {
        return changes.size() > 0 && currentChangeIndex > -1 ? true : false;
    }

    @Override
    public void undo() {
        if(canUndo()) {
            changes.get(currentChangeIndex).revert(document);
            currentChangeIndex--;
        }else {
            throw new IllegalStateException("Undo operation not allowed");
        }
    }

    @Override
    public boolean canRedo() {
        return changes.size() > 0 && currentChangeIndex > -2 && currentChangeIndex < changes.size() - 1 ? true : false;
    }

    @Override
    public void redo() {
        if(canRedo()) {
            changes.get(currentChangeIndex + 1).apply(document);
            currentChangeIndex++;
        } else {
            throw new IllegalStateException("Redo operation not allowed");
        }
    }
}
