import java.util.Scanner;

public class PhraseDetector {
  /**
   * Created by: Dan Williams on 3/24/2021 Cell: (201) 937-5556 Email: williams.dan@northeastern.edu
   *
   * This program takes a string representing a document, write a function or class which returns
   * the top 10 most frequent repeated phrases.
   *
   * A phrase is a stretch of three to ten consecutive words and cannot span sentences.
   *
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

    TextCleaner tc = new TextCleaner();

    Scanner sc= new Scanner(System.in); //System.in is a standard input stream
    System.out.print("Enter a string: ");
    String str= sc.nextLine();
    System.out.print("\n");

    String[] words = tc.Clean(str);
    for (Object obj : words) {System.out.println(obj);}
  }
}
