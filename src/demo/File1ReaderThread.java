package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

public class File1ReaderThread implements Callable<int[]>
{
    private String file;
    private int i;
    private int n;
    private TrieNode trie;
    private HashMap<String, List<String>> map;

    public File1ReaderThread(String file, int i, int n, TrieNode trie, HashMap<String, List<String>> map)
    {
        this.file = file;
        this.i = i;
        this.n = n;
        this.trie = trie;
        this.map = map;
    }

    @Override
    public int[] call() throws Exception
    {
        int[] output = new int[2];
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            for (int j = 0; j < i; j++)
            {
                line = br.readLine();
                if (line == null)
                {
                    return output;
                }
            }
            int count = 0;

            while ((line = br.readLine()) != null)
            {
                if (count % 10 == 0)
                {
                    line = line.trim().toLowerCase();
                    line = line.replaceAll("[^0-9a-z]", " ");
                    String[] words = line.split("\\s+");

                    int length = words.length;
                    if (length < n)
                    {
                        continue;
                    }
                    output[1] = length - n + 1;

                    Deque<String> deque = new LinkedList<>();
                    for (int j = 0; j < length; j++)
                    {
                        deque.addLast(words[j]);
                        if (deque.size() > n)
                        {
                            deque.removeFirst();
                        }
                        List<String> candidates = generateSynonymTuples(deque, map);
                        for (String candidate : candidates)
                        {
                            if (trie.has(candidate, 0))
                            {
                                output[0]++;
                                break;
                            }
                        }
                    }
                }
                count++;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File1 not found");
        }
        catch (IOException e)
        {
            System.out.println("File1 not found");
        }
        finally
        {
            return output;
        }
    }


    private List<String> generateSynonymTuples(Deque<String> deque, HashMap<String, List<String>> map)
    {
        List<String> output = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < deque.size(); i++)
        {
            list.add(new ArrayList<>());
        }

        List<String> array = new ArrayList<>(deque);
        for (int i = 0; i < array.size(); i++)
        {
            if (map.containsKey(array.get(i)))
            {
                list.get(i).addAll(map.get(array.get(i)));
            }
            else
            {
                list.get(i).add(array.get(i));
            }
        }

        searchCandidateNTuples(list, array, 0, output);
        return output;
    }


    private void searchCandidateNTuples(List<List<String>> list, List<String> array, int index, List<String> output)
    {
        if (index == array.size())
        {
            String candidate = generateNTupleString(array);
            output.add(candidate);
            return;
        }

        List<String> synonyms = list.get(index);
        for (String str : synonyms)
        {
            array.set(index, str);
            searchCandidateNTuples(list, array, index + 1, output);
        }
    }


    public static String generateNTupleString(List<String> array)
    {
        StringBuilder sb = new StringBuilder();
        for (String word : array)
        {
            sb.append(word + " ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
