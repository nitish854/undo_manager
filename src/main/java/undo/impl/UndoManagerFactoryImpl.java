package undo.impl;

import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;

public class UndoManagerFactoryImpl implements UndoManagerFactory {
    @Override
    public UndoManager createUndoManager(Document doc, int bufferSize) {
        return new RichTextUndoManager(doc, bufferSize);
    }
}
