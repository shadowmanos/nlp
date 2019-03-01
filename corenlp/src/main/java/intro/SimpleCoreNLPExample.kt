package intro

import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation
import edu.stanford.nlp.pipeline.JSONOutputter
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import org.apache.commons.lang3.StringUtils.isBlank
import java.util.Properties

fun main() {
    val props = Properties()

    // tokenize: split the text in tokens (words and punctuation)
    // ssplit: group the above tokens in sentences according to punctuation
    // pos: tags words as nouns, verbs etc
    props["annotators"] = "tokenize ssplit pos"

    // initialize the processing engine for the above annotators
    val pipeline = StanfordCoreNLP(props)

    // appropriate sample text for starting our journey
//    val text = "Space: the final frontier. " +
//            "These are the voyages of the starship Enterprise. " +
//            "Its continuing mission: to explore strange new worlds, " +
//            "to seek out new life and new civilizations, " +
//            "to boldly go where no one has gone before."
    // cue pompous theme music

    while (true) {
        val text = readLine()

        if (isBlank(text)) return

        // the Annotation instance, containing both input and output of the processing
        val annotation = pipeline.process(text)

        // POS tag of "explore"
        val explorePOStag = annotation.get(TokensAnnotation::class.java)
                .find { label -> label.originalText() == "explore" }
                ?.tag()
        println(explorePOStag)

        // output the annotation at stdout as a JSON
        JSONOutputter.jsonPrint(annotation, System.out)
    }
}
