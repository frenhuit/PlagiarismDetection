package demo;

import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        System.out.println("Please enter parameters: synonyms_path, file1_path, file2_path, n(optional)");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] inputs = line.split("\\s+");
        while ((inputs.length != 3 && inputs.length != 4) || (inputs.length == 4 && !isInteger(inputs[3])))
        {
            System.out.println("Invalid input, parameters should be: synonyms_path, file1_path, file2_path, n(optional)");
            line = sc.nextLine();
            inputs = line.split("\\s+");
        }
        String synsFile = inputs[0];
        String file1 = inputs[1];
        String file2 = inputs[2];
        int n;
        if (inputs.length == 4)
        {
            n = Integer.parseInt(inputs[3]);
        }
        else
        {
            n = 3;
        }

        PlagiarismDetector pd = new PlagiarismDetector();
        float rate = pd.detect(synsFile, file1, file2, n);
        if (Float.isNaN(rate))
        {
            System.out.println(rate);
            System.exit(0);
        }
        String ratePercentage = String.format("%.2f", rate * 100);
        System.out.println(ratePercentage + "%");
    }


    private static boolean isInteger(String str)
    {
        try
        {
            Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {
            return false;
        }
        return true;
    }
}
