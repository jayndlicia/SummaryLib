package info.yauhen.nlproc.summary;

import java.text.BreakIterator;
import java.util.*;

public class Summarizer {

    public String summarise(String input, int numSentences) {

        Locale currentLocale = new Locale("en", "US");
        BreakIterator sentenceIterator =
                BreakIterator.getSentenceInstance(currentLocale);
        BreakIterator wordIterator =
                BreakIterator.getWordInstance(currentLocale);

        List<Sentence> sentences = extractSentences(input, sentenceIterator); // 1. split text into sentences
        setWordsToSentences(sentences, wordIterator);  // 2. bind words and sentences built with these words
        setWeightsForSentences(sentences); // 3. set weights to each sentence to make them comparable
        // (weight of the sentence A is the the sum of common words between the A and each other sentences)
        Collections.sort(sentences); //4. sorting by weight, comparable sentence class saves the order of sentences in the input
        return getResultSummary(numSentences, sentences).toString();
    }

    private void setWordsToSentences(List<Sentence> sentences, BreakIterator wordIterator) {
        for (Sentence sentence : sentences) {
            List<String> words = extractWordsLowerCase(sentence.getRawSentence(), wordIterator);
            sentence.setWords(words);
        }
    }

    private void setWeightsForSentences(List<Sentence> sentences) {
        for (int i = 0; i < sentences.size(); i++) {
            int weight = 0;
            for (int j = 0; j < sentences.size(); j++) {
                if (i != j) {
                    weight += getAmountOfCommonWordsBetweenSentences(sentences.get(i), sentences.get(j));
                }
            }
            sentences.get(i).setWeight(weight);
        }
    }

    private StringBuilder getResultSummary(int numSentences, List<Sentence> sentences) {
        StringBuilder result = new StringBuilder();
        int iterationCount = Math.min(numSentences, sentences.size());
        sentences = sentences.subList(0, iterationCount);
        if (!sentences.isEmpty()) {
            Collections.sort(sentences, sentences.get(0).getComparator());
            for (Sentence s : sentences) {
                result.append(s.getRawSentence());
            }
        }
        return result;
    }

    List<Sentence> extractSentences(String target, BreakIterator iterator) {
        iterator.setText(target);
        int boundary = iterator.first();
        target.trim();
        List<Sentence> sentences = new LinkedList<Sentence>();
        int boundaryFirst = boundary;
        int naturalOrder = 0;
        while (boundary != BreakIterator.DONE) {
            if (boundary - boundaryFirst > 0) {
                Sentence sentence = new Sentence(target.substring(boundaryFirst, boundary));
                if (!containsAbbreviation(sentence)) {
                    sentence.setNaturalOrder(naturalOrder++);
                    sentences.add(sentence);
                    boundaryFirst = boundary;
                }
            }
            boundary = iterator.next();
        }
        return sentences;
    }

    private boolean containsAbbreviation(Sentence sentence) {
        String[] tokens = sentence.getRawSentence().trim().split("\\s");
        String lastWord = tokens[tokens.length - 1];
        return lastWord.split("\\.").length > 1;
    }

    List<String> extractWordsLowerCase(String target, BreakIterator wordIterator) {

        List<String> words = new LinkedList<String>();
        wordIterator.setText(target);
        int start = wordIterator.first();
        int end = wordIterator.next();

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            if (Character.isLetterOrDigit(word.charAt(0))) {
                words.add(word.toLowerCase());
            }
            start = end;
            end = wordIterator.next();
        }
        return words;
    }

    public List<String> extractWords(String target, BreakIterator wordIterator) {

        List<String> words = new LinkedList<String>();
        wordIterator.setText(target);
        int start = wordIterator.first();
        int end = wordIterator.next();

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            if (Character.isLetterOrDigit(word.charAt(0))) {
                words.add(word);
            }
            start = end;
            end = wordIterator.next();
        }
        return words;
    }

    int getAmountOfCommonWordsBetweenSentences(Sentence sentenceOne, Sentence sentenceTwo) {
        List<String> firstSentence = sentenceOne.getWords();
        List<String> secondSentence = sentenceTwo.getWords();
        int result = 0;
        for (String s : firstSentence) {
            if (secondSentence.contains(s)) {
                result++;
            }
        }
        return result;
    }
}
