package info.yauhen.nlproc.summary;

public class RunnerSample {

    public static void main(String[] args) {
        String toSummarize =
                "As summer interns at Microsoft, my friends and I used to take \"field trips\" to the company supply room to stock up on school supplies. " +
                        "Among the floppy disks, mouse pads, and post-it notes was a stack of small paperback books, so I took one home to read. " +
                        "The book was Peopleware, by Tom DeMarco and Timothy Lister. " +
                        "This book was one of the most influential books I've ever read. " +
                        "The best way to describe it would be as an Anti-Dilbert Manifesto. " +
                        "Ever wonder why everybody at Microsoft gets their own office, with walls and a door that shuts? " +
                        "It's in there. " +
                        "Why do managers give so much leeway to their teams to get things done? " +
                        "That's in there too. " +
                        "Why are there so many jelled SWAT teams at Microsoft that are remarkably productive? " +
                        "Mainly because Bill Gates has built a company full of managers who read Peopleware. " +
                        "I can't recommend this book highly enough. " +
                        "It is the one thing every software manager needs to read... not just once, but once a year.";
        System.out.println(new Summarizer().summarise(toSummarize, 3));
    }
}
