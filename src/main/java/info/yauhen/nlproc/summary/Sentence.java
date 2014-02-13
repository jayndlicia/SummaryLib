package info.yauhen.nlproc.summary;

import java.util.Comparator;
import java.util.List;

class Sentence implements Comparable<Sentence> {

    private String rawSentence;
    private List<String> words;
    private int weight; // sum of intersected words with other sentences
    private int naturalOrder; //number of the sentence in an input text
    private Comparator<Sentence> comparator = new Comparator<Sentence>() {
        @Override
        public int compare(Sentence sentence, Sentence sentence2) {
            Integer naturalOrder1 = sentence.getNaturalOrder();
            Integer naturalOrder2 = sentence2.getNaturalOrder();
            return naturalOrder1.compareTo(naturalOrder2);
        }
    };

    Sentence(String rawSentence) {
        this.rawSentence = rawSentence;
    }

    List<String> getWords() {
        return words;
    }

    void setWords(List<String> words) {
        this.words = words;
    }

    String getRawSentence() {
        return rawSentence;
    }

    void setRawSentence(String rawSentence) {
        this.rawSentence = rawSentence;
    }

    int getWeight() {
        return weight;
    }

    void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Sentence sentence) {
        if (this.getWeight() >= sentence.getWeight()) {
            return -1;
        }
        return 1;
    }

    int getNaturalOrder() {
        return naturalOrder;
    }

    void setNaturalOrder(int naturalOrder) {
        this.naturalOrder = naturalOrder;
    }

    Comparator<Sentence> getComparator() {
        return comparator;
    }
}