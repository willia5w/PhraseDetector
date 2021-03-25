import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by: Dan Williams on 3/24/2021, Cell: (201) 937-5556, Email: williams.dan@northeastern.edu
 *
 * Input: This program requests a document from the user in the form of a String.
 *
 * Output: Up to 10 of the most frequent repeated phrases.
 *
 * Definition: A phrase is a stretch of three to ten consecutive words and cannot span sentences.
 *
 */


public class PhraseDetector {


  public static void main(String[] args) throws Exception {

    Scanner sc= new Scanner(System.in);
    System.out.print("Submit Document for Analysis: ");
    String str= sc.nextLine();

    if (str.length()==0 || str.matches(".*\\d+.*"))
      throw new NullPointerException("Document should not be null or contain digits");

    HashMap<String, Integer> nGramsOccurrences = NlpTools.GenerateCleanNgrams(str);
    ArrayList<String> repeatPhrases = NlpTools.AnalyzeRepeatPhrases(nGramsOccurrences);

    final String matches = repeatPhrases.size() >= 1
        ? "\nRepeated Phrases:\n" + repeatPhrases.toString() :"\nDocument Contains No Repeat Phrases";

    System.out.println(matches);
  }
}
