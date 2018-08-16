package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlagiarismDetector
{
    public float detect(String synonymsFile, String file, String reference, int n)
    {
        TrieNode trie = ReferenceHandler.generateTrie(reference, n);
        HashMap<String, List<String>> map = SynonymsHandler.generateSynonymList(synonymsFile);
        int totalNTuple = 0;
        int matchedNTuple = 0;

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        List<Future> futureList = new ArrayList<Future>();

        for (int i = 0; i < 10; i++)
        {
            File1ReaderThread f = new File1ReaderThread(file, i, n, trie, map);
            Future<int[]> future = cachedThreadPool.submit(f);
            futureList.add(future);
        }

        try
        {
            for (Future<int[]> future : futureList)
            {
                int[] result = future.get();
                matchedNTuple += result[0];
                totalNTuple += result[1];
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            cachedThreadPool.shutdown();
            return (float) matchedNTuple / (float) totalNTuple;
        }
    }


}
