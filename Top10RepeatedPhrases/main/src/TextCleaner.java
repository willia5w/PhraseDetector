import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TextCleaner {

  static final int MAX_NUM_PHRASES = 10;
  static final int MIN_PHRASE_LEN = 3;
  static final int MIN_PHRASE_FREQ = 2;

  /**
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
      int window = MIN_PHRASE_LEN;

      if (length >= window) {
        while (window <= length || window <= MAX_NUM_PHRASES) {
          for (int i = 0; i < length - window + 1; i++) {
            String ngram = String.join(" ", wordsList.subList(i, i + window));
            nGramsMap.merge(ngram, 1, Integer::sum);
          }
          window++;
        }
      }
    }

    LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
    nGramsMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
        .filter(map -> map.getValue().intValue() >= MIN_PHRASE_FREQ)
        .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));  // Sorted by key to rollup

    ArrayList<String> topPhrases = new ArrayList<>();
    String curPhrase = "";
    Integer comparisonCount = 0;

    while (topPhrases.size() < MAX_NUM_PHRASES && comparisonCount <= reverseSortedMap.keySet().size()-1) {

      for (String phrase : reverseSortedMap.keySet()) {
        comparisonCount++;

        if (topPhrases.size() == 0) {  // Assign a current phrase to start comparisons
          curPhrase = phrase;
          topPhrases.add(curPhrase);
        }

        Integer countCur = reverseSortedMap.get(curPhrase);
        Integer countPhrase = reverseSortedMap.get(phrase);

        if (phrase.contains(curPhrase) && countPhrase == countCur) { // If sub phrase occurred same amount, it can be assumed that they are from the same phrase
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
    System.out.println(topPhrases.toString());
  }
}

// The quick brown fox jumped over the lazy dog. The lazy dog, peeved to be labeled lazy, jumped over a snoring turtle. In retaliation the quick brown fox jumped over ten snoring turtles. Then the quick brown fox refueled with some ice cream.

// Cool calm and collected. Cool and collected. Cool calm and collected.