package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class ReferenceHandler
{
    public static TrieNode generateTrie(String file, int n)
    {
        TrieNode root = new TrieNode();

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                line = line.trim().toLowerCase();
                line = line.replaceAll("[^0-9a-z]", " "); //replace non letter digit to space to avoid handle punctuation
                String[] words = line.split("\\s+"); //split by one or multiple blanks

                int length = words.length;

                if (length < n)
                {
                    continue;
                }

                Deque<String> deque = new LinkedList<>();
                for (int i = 0; i < n; i++)
                {
                    deque.addLast(words[i]);
                }
                root.add(generateNTupleString(deque), 0);
                for (int i = n; i < length; i++)
                {
                    deque.addLast(words[i]);
                    deque.removeFirst();
                    root.add(generateNTupleString(deque), 0);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File2 not found");
        }
        catch (IOException e)
        {
            System.out.println("File2 not found");
        }
        finally
        {
            return root;
        }
    }


    public static String generateNTupleString(Deque<String> deque)
    {
        StringBuilder sb = new StringBuilder();
        for (String word : deque)
        {
            sb.append(word + " ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
