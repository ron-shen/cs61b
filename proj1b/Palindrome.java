package proj1b;

public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        ArrayDeque<Character> d = new ArrayDeque<>();
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            d.addLast(c);
        }
        return d;
    }
    public boolean isPalindrome(String word){
        Deque<Character> d = wordToDeque(word);
        return isPalindromeHelper(d);
    }
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> d = wordToDeque(word);
        return isPalindromeHelperCC(d, cc);
    }
    private boolean isPalindromeHelper(Deque<Character> d){
        if(d.size() == 0 || d.size() == 1){
            return true;
        }
        return d.removeFirst() == d.removeLast() && isPalindromeHelper(d);
    }

    private boolean isPalindromeHelperCC(Deque<Character> d, CharacterComparator cc){
        if(d.size() == 0 || d.size() == 1){
            return true;
        }
        return cc.equalChars(d.removeFirst(), d.removeLast()) && isPalindromeHelperCC(d, cc);
    }
}
