

import org.junit.Test;
import static org.junit.Assert.*;


public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void TestOffByOne(){
        assertTrue(obo.equalChars('a','b'));
        assertTrue(obo.equalChars('r','q'));
        assertFalse(obo.equalChars('a','e'));
    }
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
}
