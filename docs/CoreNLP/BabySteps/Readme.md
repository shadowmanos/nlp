---
Simple CoreNLP Example
---

### Show me the code

```java
// appropriate sample text for starting our journey
String text = "Space: the final frontier. " +
    "These are the voyages of the starship Enterprise. " +
    "Its continuing mission: to explore strange new worlds, " +
    "to seek out new life and new civilizations, " +
    "to boldly go where no one has gone before.";

// cue pompous theme music and then
Annotation annotation = pipeline.process(text);
```

This is probably the simplest program you can write using Stanford's [CoreNLP](https://stanfordnlp.github.io/CoreNLP/).
A String is supplied to an [AnnotationPipeline](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/pipeline/AnnotationPipeline.html),
it is processed and an [Annotation](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/pipeline/Annotation.html)
is produced as a result.

### Annotators and pipelines thereof

The main job of CoreNLP is to attach labels to each word in the text. For instance "explore" is a *verb* and, after CoreNLP deduces that,
it has to somehow attach the label *verb* to the word "explore". `Annotation` implements [CoreMap](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/util/CoreMap.html)
and is basically a map from words to their labels. An [Annotator](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/pipeline/Annotator.html)
is a processor that comes up with the appropriate label to attach to each word.

NLP in CoreNLP is performed by applying a sequence (pipeline) of `Annotator`, each depending on the result of the previous one.
For example we have to break a `String` down to words (tokens) before one of the words, "explore", is identified as a *verb*.
`pipeline` is in this case an instance of [StanfordCoreNLP](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/pipeline/StanfordCoreNLP.html).
When we call the `process` method each `Annotator` is applied to the whole text in order.
Here is how you can configure a pipeline with [Properties](https://docs.oracle.com/javase/8/docs/api/java/util/Properties.html):

```java
Properties props = new Properties();

// tokenize: split the text in tokens (words and punctuation)
// ssplit: group the above tokens in sentences according to punctuation
// pos: tags words as nouns, verbs etc
props.put("annotators", "tokenize,ssplit,pos");

// initialize the processing engine for the above annotators
pipeline = new StanfordCoreNLP(props);
```

If it wasn't adequately obvious before, the order of tokenize, ssplit and pos matters. Alternatively,
and arguably more professionally, we can say:

```java
pipeline = new StanfordCoreNLP("pipeline");
```

and have a `pipeline.properties` file with this content:

```
annotators=tokenize,ssplit,pos
```

### The resulting Annotation

The result will contain 3 key-value pairs

1. `TextAnnotation` will point to the original text
2. `SentenceAnnotation` will point to the tokenization of the text. The value will be a list of sentences and each
sentence will have a list of words.
3. `TokensAnnotation` will point to a list of [CoreLabel](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/ling/CoreLabel.html)
that contain a linguistic description for each word.

Let's explore "explore" for example:

```java
final String explorePOStag = annotation.get(TokensAnnotation.class)
    .stream()
    .filter(label -> label.originalText().equals("explore"))
    .findFirst()
    .map(CoreLabel::tag)
    .orElse(null);
```

which will result in "VB" i.e. *verb*. That is a **P**art **O**f **S**peech tag and you can see a list of potential tags
at the [Penn tree bank](https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html). You loved grammar
at school didn't you ?

You can print out the whole resulting annotation as a String in various ways.
So, according to your favourite Enterprise captain:

Chris Pine i.e. the enthusiastic youth's choice:
```java
JSONOutputter.jsonPrint(annotation, System.out);
```

William Shatner i.e. the old but not so wise person's choice:
```java
XMLOutputter.xmlPrint(annotation, System.out);
```

Patrick Stewart i.e. the scientist's choice. [What's CoNLL?](http://www.conll.org/):
```java
CoNLLOutputter.conllPrint(annotation, System.out);
```

#### Coming soon
The next part of this introduction will be about unit tests, threads and expedient initialization of CoreNLP.
