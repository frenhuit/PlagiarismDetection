package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SynonymsHandler
{
    public static HashMap<String, List<String>> generateSynonymList(String file)
    {
        HashMap<String, List<String>> output=new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while((line=br.readLine())!=null)
            {
                line=line.trim().toLowerCase();
                line=line.replaceAll("[^0-9a-z]", " "); //assume only letter is valid
                String[] words=line.split("\\s+"); //split by one or multiple blanks

                int length=words.length;
                if(length<=1)
                {
                    continue;
                }
                for(int i=0; i<words.length; i++)
                {
                    if(!output.containsKey(words[i]))
                    {
                        output.put(words[i], new ArrayList<>());
                    }
                    for(int j=0; j<words.length; j++)
                    {
                        output.get(words[i]).add(words[j]);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Synonyms file not found");
        }
        catch (IOException e)
        {
            System.out.println("Synonyms file not found");
        }
        finally
        {
            return output;
        }
    }
}
