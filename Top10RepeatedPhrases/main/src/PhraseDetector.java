import java.util.Scanner;

/**
 * Created by: Dan Williams on 3/24/2021 Cell: (201) 937-5556 Email: williams.dan@northeastern.edu
 *
 * This program requests a document from the user in the form of a String.
 *
 * It then outputs the top 10 most frequent repeated phrases.
 */


public class PhraseDetector {
  /**
   * A phrase is a stretch of three to ten consecutive words and cannot span sentences.
   *
   * Omit a phrase if it is a subset of another, longer phrase, even if the shorter phrase occurs
   * more frequently (for example, if “cool and collected” and “calm cool and collected” are
   * repeated, do not include “cool and collected” in the returned set).
   *
   * INPUT:
   * The quick brown fox jumped over the lazy dog. The lazy dog, peeved to be labeled lazy,
   * jumped over a snoring turtle. In retaliation the quick brown fox jumped over ten snoring
   * turtles. Then the quick brown fox refueled with some ice cream.
   *
   * OUTPUT:
   * ['the lazy dog', 'the quick brown fox jumped over']
   */


  public static void main(String[] args) throws Exception {

    TextCleaner cleaner = new TextCleaner();

    Scanner sc= new Scanner(System.in);
    System.out.print("Submit Document: ");
    String str= sc.nextLine();
//    System.out.print("\n");

    if (str.length()==0) throw new NullPointerException("Document should not be null");

    cleaner.Clean(str);
  }
}
