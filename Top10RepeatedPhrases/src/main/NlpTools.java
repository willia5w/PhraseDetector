import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * This utility class contains static methods used to produce ngrams and identify repeat phrases.
 * */
public class NlpTools {

  static final int MAX_PHRASE_LEN = 10;
  static final int MIN_PHRASE_LEN = 3;
  static final int MIN_PHRASE_FREQ = 2;
  static final int MAX_NUM_PHRASES = 10;


  /**
   * Given a min and max phrase length, a map of ngrams is produced for analysis.
   * A BreakIterator is used to ensure that phrases do not span across sentences.
   *
   * @param input the document being submitted with original formatting.
   * @return a map of ngrams and their frequency in the document.
   */
  public static HashMap<String, Integer> GenerateCleanNgrams(String input) {

    HashMap<String, Integer> nGramsMap = new HashMap<>();

    BreakIterator iterator = BreakIterator.getSentenceInstance();
    iterator.setText(input);
    int start = iterator.first();

    for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {

      String[] words = input.substring(start, end).replaceAll("[^a-zA-Z ]", "")
          .toLowerCase().split(" ");
      List<String> wordsList = new ArrayList<>();
      Collections.addAll(wordsList, words); // Verbose but avoiding asList limitations

      int length = words.length;
      int window = MIN_PHRASE_LEN;

      if (length >= window) {
        while (window <= length || window <= MAX_PHRASE_LEN) {
          for (int i = 0; i < length - window + 1; i++) {
            String ngram = String.join(" ", wordsList.subList(i, i + window));
            nGramsMap.merge(ngram, 1, Integer::sum);
          }
          window++;
        }
      }
    }
    return nGramsMap;
  }


  /**
   * Compares ngrams to disqualify sub phrases of the target repeated phrase.
   *
   * @param nGrams a map of ngrams and their frequency in the user's document.
   * @return a String representation of a sorted list containing up to 10 top repeated phrases.
   *
   */
  public static ArrayList<String> AnalyzeRepeatPhrases(HashMap<String, Integer> nGrams) {

    LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
    nGrams.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
        .filter(map -> map.getValue().intValue() >= MIN_PHRASE_FREQ)
        .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

    ArrayList<String> topPhrases = new ArrayList<>();
    String curPhrase = "";
    Integer comparisonCount = 0;

    while (topPhrases.size() < MAX_NUM_PHRASES
        && comparisonCount <= reverseSortedMap.keySet().size()-1) {

      for (String phrase : reverseSortedMap.keySet()) {
        comparisonCount++;

        if (topPhrases.size() == 0) {
          curPhrase = phrase;
          topPhrases.add(curPhrase);
        }

        Integer countCur = reverseSortedMap.get(curPhrase);
        Integer countPhrase = reverseSortedMap.get(phrase);

        if (phrase.contains(curPhrase) && countPhrase == countCur) {
          topPhrases.remove(curPhrase);
          topPhrases.add(phrase);
          curPhrase = phrase;
        } else if (curPhrase.contains(phrase)) {
          continue;
        } else {
          topPhrases.add(phrase);
        }
      }
    }

    Collections.sort(topPhrases);
    return topPhrases;
  }
}