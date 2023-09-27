

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {
    @Test
    public void TestOffByN(){
        OffByN ob3 = new OffByN(3);
        assertTrue(ob3.equalChars('a','d'));
        assertFalse(ob3.equalChars('z','a'));
        assertFalse(ob3.equalChars('e','g'));
    }
}
