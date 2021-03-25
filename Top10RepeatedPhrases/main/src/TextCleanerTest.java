import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

class TextCleanerTest {

  public String example1 =
      "The quick brown fox jumped over the lazy dog.\n"
          + "The lazy dog, peeved to be labeled lazy, jumped over a snoring turtle.\n"
          + "In retaliation the quick brown fox jumped over ten snoring turtles.\n"
          + "Then the quick brown fox refueled with some ice cream.";

  public String example2 =
      "Cool calm and collected. \n"
      + "Cool and collected. \n"
      + "Cool calm and collected. \n";

  public String example3 =
      "I would love to work indoors. \n"
          + "In Washington, people would love to enjoy the rain and sip coffee. \n"
          + "My dog would love to sit on the couch and enjoy the rain safely. \n";


  @org.junit.jupiter.api.Test
  void analyzeRepeatPhrasesTest1() {
    HashMap<String, Integer> map1 = NlpTools.GenerateCleanNgrams(example1);
    ArrayList<String> list1Actual = NlpTools.AnalyzeRepeatPhrases(map1);

    ArrayList<String> list1Expected = new ArrayList<>();
    list1Expected.add("the lazy dog");
    list1Expected.add("the quick brown fox jumped over");

    assertEquals(list1Expected, list1Actual);
  }

  @org.junit.jupiter.api.Test
  void analyzeRepeatPhrasesTest2() {
    HashMap<String, Integer> map2 = NlpTools.GenerateCleanNgrams(example2);
    ArrayList<String> list2Actual = NlpTools.AnalyzeRepeatPhrases(map2);

    ArrayList<String> list2Expected = new ArrayList<>();
    list2Expected.add("cool calm and collected");

    assertEquals(list2Expected, list2Actual);
  }

  @org.junit.jupiter.api.Test
  void analyzeRepeatPhrasesTest3() {
    HashMap<String, Integer> map3 = NlpTools.GenerateCleanNgrams(example3);
    ArrayList<String> list3Actual = NlpTools.AnalyzeRepeatPhrases(map3);

    ArrayList<String> list3Expected = new ArrayList<>();
    list3Expected.add("enjoy the rain");
    list3Expected.add("would love to");

    assertEquals(list3Expected, list3Actual);
  }
}