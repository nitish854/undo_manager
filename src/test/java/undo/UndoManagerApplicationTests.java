package undo;

import org.junit.jupiter.api.*;
import undo.impl.ChangeFactgoryImpl;
import undo.impl.RichTextChange;
import undo.impl.RichTextDocument;
import undo.impl.UndoManagerFactoryImpl;

class UndoManagerApplicationTests {

    private  Document d;
    private UndoManager um;
    private ChangeFactory cf;

    @BeforeEach
    public  void commonAction(){
        d = new RichTextDocument();
        um = new UndoManagerFactoryImpl().createUndoManager(d, 3);
        cf = new ChangeFactgoryImpl();
    }

	@Test
	void testInsertionOfString() {
		Change c = cf.createInsertion(0, "first",0, "first".length()  );
		um.registerChange(c);

		Assertions.assertEquals("first",  d.getContent());

		Change c1 = cf.createInsertion( 0, "second", 0, "second".length()  );
		um.registerChange(c1);

		Assertions.assertEquals("secondfirst",  d.getContent());

	}

	@Test
    void testDeletion() {
        Change c = cf.createInsertion( 0, "first", 0, "first".length() );
        um.registerChange(c);

        Assertions.assertEquals("first",  d.getContent());

        Change c1 = cf.createInsertion(  0, "second", 0, "second".length() );
        um.registerChange(c1);

        Assertions.assertEquals("secondfirst",  d.getContent());


        Change c2 = cf.createDeletion( 0, "second", 0, "second".length() );
        um.registerChange(c2);

        Assertions.assertEquals("first",  d.getContent());
    }


    @Test
    void testUndo() {
        Change c = cf.createInsertion( 0, "first", 0, "first".length() );
        um.registerChange(c);

        Assertions.assertEquals("first",  d.getContent());

        Change c1 = cf.createInsertion( 0, "second", 0, "second".length() );
        um.registerChange(c1);

        Assertions.assertEquals("secondfirst",  d.getContent());


        Change c2 = cf.createDeletion( 0, "second", 0, "second".length() );
        um.registerChange(c2);

        Assertions.assertEquals("first",  d.getContent());


        Assertions.assertTrue(um.canUndo());
        um.undo();
        Assertions.assertEquals("secondfirst",  d.getContent());
    }

    @Test
    void testRedo() {
        Change c = cf.createInsertion( 0, "first", 0, "first".length() );
        um.registerChange(c);

        Assertions.assertEquals("first",  d.getContent());

        Change c1 = cf.createInsertion( 0, "second" ,0, "second".length() );
        um.registerChange(c1);

        Assertions.assertEquals("secondfirst",  d.getContent());


        Change c2 = cf.createDeletion( 0, "second", 0, "second".length() );
        um.registerChange(c2);

        Assertions.assertEquals("first",  d.getContent());


        Assertions.assertTrue(um.canUndo());
        um.undo();
        Assertions.assertEquals("secondfirst",  d.getContent());


        Assertions.assertTrue(um.canRedo());
        um.redo();
        Assertions.assertEquals("first",  d.getContent());
    }

    @Test
    void testRedoAfterInsertionFails() {
        Change c = cf.createInsertion( 0, "first", 0, "first".length() );
        um.registerChange(c);

        Assertions.assertEquals("first",  d.getContent());

        Change c1 = cf.createInsertion( 0, "second", 0, "second".length() );
        um.registerChange(c1);

        Assertions.assertEquals("secondfirst",  d.getContent());


        Change c2 = cf.createDeletion( 0, "second", 0, "second".length() );
        um.registerChange(c2);

        Assertions.assertEquals("first",  d.getContent());


        Assertions.assertTrue(um.canUndo());
        um.undo();
        Assertions.assertEquals("secondfirst",  d.getContent());


        Assertions.assertTrue(um.canRedo());
        um.redo();
        Assertions.assertEquals("first",  d.getContent());


        Change c3 = cf.createInsertion( 0, "third", 0, "third".length() );
        um.registerChange(c3);
        Assertions.assertFalse(um.canRedo());
        Assertions.assertThrows(IllegalStateException.class, ()->um.redo());

    }

    @Test
    void testUndoRespectsBufferLimit(){
        Change c = cf.createInsertion( 0, "first", 0, "first".length() );
        um.registerChange(c);

        Assertions.assertEquals("first",  d.getContent());

        Change c1 = cf.createInsertion( 0, "second", 0, "second".length() );
        um.registerChange(c1);

        Assertions.assertEquals("secondfirst",  d.getContent());


        Change c2 = cf.createDeletion( 0, "second", 0, "second".length() );
        um.registerChange(c2);

        Assertions.assertEquals("first",  d.getContent());

        Change c3 = cf.createInsertion( 0, "third", 0, "third".length() );
        um.registerChange(c3);

       um.undo();
       um.undo();
       um.undo();
       Assertions.assertEquals("first", d.getContent());
       Assertions.assertThrows(IllegalStateException.class, ()->um.undo());

    }

    @Test
    void testRedoWithoutUndoFails() {
        Change c = new RichTextChange(RichTextChange.TYPES.insert, 0, 0, "first".length(), "first");
        um.registerChange(c);

        Assertions.assertThrows(IllegalStateException.class, () -> um.redo());
    }


    }
