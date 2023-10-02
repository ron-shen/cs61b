package proj1b;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void TestOffByOne(){
        assertTrue(offByOne.equalChars('a','b'));
        assertTrue(offByOne.equalChars('r','q'));
        assertFalse(offByOne.equalChars('a','e'));
    }
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
}
