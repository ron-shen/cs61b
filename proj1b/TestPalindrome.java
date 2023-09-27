package proj1b;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }/* Uncomment this class once you've created your Palindrome class. */
    @Test
    public void testIsPalindrome(){
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("c"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("aaaa"));
    }

    @Test
    public void testIsPalindromeCharComparator(){
        OffByOne obo = new OffByOne();
        assertFalse(palindrome.isPalindrome("racecar", obo));
        assertTrue(palindrome.isPalindrome("c", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("acbb", obo));
        assertTrue(palindrome.isPalindrome("bcda", obo));
        assertTrue(palindrome.isPalindrome("fgzhe", obo));
        OffByN ob3 = new OffByN(3);
        assertTrue(palindrome.isPalindrome("acbfd", ob3));
    }
}
