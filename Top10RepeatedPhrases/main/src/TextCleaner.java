public class TextCleaner {

  /**
   * This class
   * @param document
   * @return cleaned version of the original document
   */

  public static String[] Clean(String document) {
    String[] words = document.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\.");
    return words;
  }
}
