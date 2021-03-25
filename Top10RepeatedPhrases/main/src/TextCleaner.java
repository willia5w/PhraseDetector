import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextCleaner {

  static final int MAX_NUM_PHRASES = 10;
  static final int MIN_PHRASE_LEN = 3;
  static final int MIN_PHRASE_FREQ = 2;

  /**
   * This class
   *
   * @param input
   * @return cleaned version of the original document
   */
  public static void Clean(String input) {

    // Using a BreakIterator to create individual sentences as phrases do not span sentences
    BreakIterator iterator = BreakIterator.getSentenceInstance();
    iterator.setText(input);
    int start = iterator.first();

    HashMap<String, Integer> nGramsMap = new HashMap<>();

    for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) { // For each sentence

      String[] words = input.substring(start, end).replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
      List<String> wordsList = new ArrayList<>();
      Collections.addAll(wordsList, words); // Verbose but avoiding asList limitations

      int length = words.length;
      System.out.println(length);
      int window = MIN_PHRASE_LEN;

      if (length >= window) {
        while (window <= length || window <= MAX_NUM_PHRASES) {
          for (int i = 0; i < length - window + 1; i++) {
            String ngram = String.join(" ", wordsList.subList(i, i + window));
            nGramsMap.merge(ngram, 1, Integer::sum);
          }
          window++;  // Add sliding window of words to map tracking occurrences
        }
      }
    }

    //    TODO: Make a map of built up phrases with a value counting the number of times this phrase
    // If a phrase is part of a larger phrase in the final output, do not show it

    Map<String, Integer> result =
        nGramsMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .filter(map -> map.getValue().intValue() >= MIN_PHRASE_FREQ)
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new));

//    System.out.println(result.toString());

    System.out.println(result.keySet().toString());

    ArrayList<String> topPhrases = new ArrayList<>();
    String curPhrase = "";

    while (topPhrases.size() < MAX_NUM_PHRASES) {
      for (String phrase : result.keySet()) {
        if (!curPhrase.contains(phrase)) {
          curPhrase = phrase;
        }
      }
    }
  }

//   Iterate through the entire result, building up one of the answers "the quick brown fox jumped over" (longest string wth adjacent contained thus far)
  // Move onto building the next of the 10 top phrases once the partial match is no more
}
// SAMPLE:
// The quick brown fox jumped over the lazy dog. The lazy dog, peeved to be labeled lazy, jumped over a snoring turtle. In retaliation the quick brown fox jumped over ten snoring turtles. Then the quick brown fox refueled with some ice cream.
