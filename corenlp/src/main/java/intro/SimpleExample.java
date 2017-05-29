package intro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

class SimpleExample {
    private static final StanfordCoreNLP pipeline;

    static {
        final Properties props = new Properties();

        // tokenize: split the text in tokens (words and punctuation)
        // ssplit: group the above tokens in sentences according to punctuation
        // pos: tags words as nouns, verbs etc
        props.put("annotators", "tokenize ssplit pos");

        // initialize the processing engine for the above annotators
        pipeline = new StanfordCoreNLP(props);
    }

    public static void main(String[] args) throws IOException {
        // appropriate sample text for starting our journey
        final String text = "Space: the final frontier. " +
            "These are the voyages of the starship Enterprise. " +
            "Its continuing mission: to explore strange new worlds, " +
            "to seek out new life and new civilizations, " +
            "to boldly go where no one has gone before.";
        // cue pompous music after the above

        // the Annotation instance, containing both input and output of the processing
        final Annotation document = new Annotation(text);

        // process the text and store the result in Annotation instance
        pipeline.annotate(document);

        // output the annotation at stdout as a JSON
        pipeline.jsonPrint(document, new PrintWriter(System.out));
    }
}
