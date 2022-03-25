import junit.framework.TestCase;

public class ParseTest extends TestCase {
    
    public ParseTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of verPrint method, of class Parse.
     */
    public void testVerPrint() {
        System.out.println("verPrint");
        Parse instance = new Parse();
        Lista l = instance.toLista("+ 1 2");
        boolean expResult = false;
        boolean result = instance.verPrint(l);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verDefun method, of class Parse.
     */
    public void testVerDefun() {
        System.out.println("verDefun");
        Parse instance = new Parse();
        Lista l = instance.toLista("+ 1 2");
        boolean expResult = false;
        boolean result = instance.verDefun(l);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluarLista method, of class Parse.
     */
    public void testEvaluarLista() {
        System.out.println("evaluarLista");
        Parse instance = new Parse();
        Lista l = instance.toLista("+ 1 2");
        boolean expResult = false;
        boolean result = instance.evaluarLista(l);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
