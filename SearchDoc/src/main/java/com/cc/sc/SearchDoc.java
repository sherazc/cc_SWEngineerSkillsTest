package com.cc.sc;

import java.util.Arrays;
import java.util.List;

public class SearchDoc {

  private static final List<String> IGNORE_SEARCH_WORDS = Arrays.asList("of", "the", "to", "and”, “for");
  private static final List<String> CONSTITUTION_WORDS = Arrays.asList(Data.CONSTITUTION.toLowerCase().split("\\W+"));
  private static final List<String> MAGNA_CARTA_WORDS = Arrays.asList(Data.MAGNA_CARTA.toLowerCase().split("\\W+"));
  private static final List<String> DECLARATION_WORDS = Arrays.asList(Data.DECLARATION.toLowerCase().split("\\W+"));

  public static void main(String[] args) {
    // String searchWord = args[0].toLowerCase();
    String searchWord = "our";

    final int countConstitution = countWords(CONSTITUTION_WORDS, searchWord);
    final int countMagnaCarta = countWords(MAGNA_CARTA_WORDS, searchWord);
    final int countDeclaration = countWords(DECLARATION_WORDS, searchWord);
    int maxCount = Math.max(countDeclaration, Math.max(countConstitution, countMagnaCarta));

    if (maxCount == 0) {
      System.out.println("The word \"" + searchWord + "\" does not appear in any document.");
      return;
    }

    String maxDoc = (maxCount == countMagnaCarta) ? "Magna Carta"
        : (maxCount == countConstitution) ? "U.S. Constitution"
        : "Declaration of Independence";

    System.out.printf("occurs %d times in %s.\n", maxCount, maxDoc);
  }

  private static int countWords(List<String> words, String searchWord) {
    return (int) words.stream()
        .filter(word -> word.contains(searchWord))
        .count();
  }
}
