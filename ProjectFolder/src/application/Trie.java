package application;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * A Class to implement Trie
 * Trie is a Data Structure to store our Dictionary
 * See https://en.wikipedia.org/wiki/Trie
 * Insertion    :   O(N)
 * Deletion     :   O(N)
 * Find         :   O(N)
 * N is the length of Word
 *
 * @author Adamos Ttofari
 */
public class Trie {

    private TrieNode root = new TrieNode();//The trie has access to the root
    private String language;
    
    Trie(String language) {
        root = new TrieNode();
        this.language = language;
    }

    Trie() {
        this("");
    }

    public String getName() {
        return language;
    }

    /**
     * A method to add Word into the Dictionary
     *
     * @param s    The string we want to add
     * @param info The definition of the word
     */
    public void addWord(String s, String info) {
        TrieNode currNode = root;
        String u=new String(s);
        //System.out.println(u);
        s=u.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            TrieNode next = currNode.getNextNodeAt(c);
            if (next == null) {
                next = new TrieNode(c);
                currNode.addNext(c, next);
            }
            currNode = next;
            if (i == s.length() - 1) {
                currNode.setWord(true);
                currNode.info+=info;
                currNode.word=u;
                //System.out.println(u);
            }
        }
    }

    /**
     * A method to find a Word in the Dictionary
     *
     * @param s The word we want to find
     * @return "Word not found" if it doesnt Exist
     */
    public String findWord(String s) {
        TrieNode currNode = root;
        s=s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            TrieNode next = currNode.getNextNodeAt(c);
            if (next == null) {
                return "Word not found";
            }
            currNode = next;
            if (i == s.length() - 1 && currNode.isWordEnd())
                return currNode.info;
        }
        return "Word not found";
    }

    /**
     * A method to check if a word exists in the Trie
     *
     * @param s The string we want to find
     * @return True if it is there Flase if not
     */
    public Boolean checkWord(String s) {
        TrieNode currNode = root;
        s=s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            TrieNode next = currNode.getNextNodeAt(c);
            if (next == null) {
                return false;
            }
            currNode = next;
            if (i == s.length() - 1 && currNode.isWordEnd())
                return true;
        }
        return false;
    }
    /**
     * A Method to return a List of Autocompleted words
     * @param s the prefix
     * @return An ArrayList of The Words
     */
    public ArrayList<String> isPref(String s){
    	TrieNode currNode = root;
        s=s.toUpperCase();
        //System.out.println(s);
        ArrayList<String> ans=new ArrayList<String>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            TrieNode next = currNode.getNextNodeAt(c);
            if (next == null) {
                return ans;
            }
            currNode = next;
            if (i == s.length() - 1){
                currNode.getChildren(ans);
                return ans;
            }
        }
        return ans;
    }
    
    /**
     * A method to delete a Word from the Dictionary
     *
     * @param s The word we want to delete
     */
    public void deleteWord(String s) {
        TrieNode currNode = root;
        s=s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            TrieNode next = currNode.getNextNodeAt(c);
            if (next == null) {
                return;
            }
            currNode = next;
            if (i == s.length() - 1 && currNode.isWordEnd()) {
                currNode.setWord(false);
                return;
            }
        }
    }


    /**
     * An inner Class to implement each node in the Trie
     *
     * @author Adamos Ttofari
     */
    private class TrieNode {
        private Map<Character, TrieNode> next;
        private Boolean isWord;
        private String info, word;
        private Character c;

        /**
         * Create a Trie Node of character C
         *
         * @param c the character the node we want to create
         */
        TrieNode(Character c) {
            this.c = c;
            next = new HashMap<Character, TrieNode>();
            isWord = false;
            info = "";
        }

        /**
         * Create a node of a character that is not used (Space)
         */
        TrieNode() {
            this(' ');
        }

        /**
         * A method to get the handle of the next Node
         *
         * @param c What character leads to it
         * @return The handle of the next node in the Trie
         */
        TrieNode getNextNodeAt(Character c) {
            if (!(next.containsKey(c)))
                return null;
            return next.get(c);
        }

        /**
         * Add the handle of the next character Node
         *
         * @param c    At what Character you want to add the handle
         * @param what What is the handle you want to add
         */
        void addNext(Character c, TrieNode what) {
            next.put(c, what);
        }

        /**
         * To set if the node is a Word
         *
         * @param f True if it is a node False if not
         */
        void setWord(boolean f) {
            isWord = f;
        }

        /**
         * A method to check if the node is a word
         *
         * @return True if it is False if Not
         */
        Boolean isWordEnd() {
            return isWord;
        }
        /**
         * A method to generate an ArrayList of all the children
         * @param ans The ArrayList we want to put the children
         */
        void getChildren(ArrayList<String> ans){
        	//System.out.println(c);
        	//int a=new Scanner(System.in).nextInt();
        	if(isWord)
        		ans.add(word);
        	for(TrieNode i: next.values())
        		i.getChildren(ans);
        	}
        }
    }

