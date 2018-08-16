package demo;

import java.util.HashMap;

public class TrieNode
{
    private HashMap<Character, TrieNode> children;
    private boolean isEnd;
    TrieNode trie;



    public TrieNode()
    {
        children=new HashMap<>();
        isEnd=false;
    }


    public void add(String word, int index)
    {
        if(index==word.length())
        {
            isEnd=true;
            return;
        }

        char ch=word.charAt(index);
        if(!children.containsKey(ch))
        {
            children.put(ch, new TrieNode());
        }
        children.get(ch).add(word, index+1);
    }

    public boolean has(String word, int index)
    {
        if(index==word.length())
        {
            return isEnd;
        }

        char ch=word.charAt(index);
        if(!children.containsKey(ch))
        {
            return false;
        }
        return children.get(ch).has(word, index+1);
    }
}
