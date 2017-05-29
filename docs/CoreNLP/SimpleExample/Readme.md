## Introduction

This is one of the simplest applications you can make using CoreNLP. There is [Maven](https://maven.apache.org/)
pom.xml to load the dependencies, a pipeline is configured, a String is processed and the annotations are printed
to console output as a JSON. Let's start:

#### POM

These are the required dependencies to use CoreNLP in a Java application:

```xml
<dependency>
    <groupId>edu.stanford.nlp</groupId>
    <artifactId>stanford-corenlp</artifactId>
    <version>3.7.0</version>
</dependency>
<dependency>
    <groupId>edu.stanford.nlp</groupId>
    <artifactId>stanford-corenlp</artifactId>
    <version>3.7.0</version>
    <classifier>models</classifier>
</dependency>
```

The somewhat unusual `classifier` tag, is used here to specify a secondary artifact with language models, apart from
the main one with java libraries, with both artifacts compiled from the same POM. You may find that some libraries in
CoreNLP don't require language models but they are needed even for this simple example. If you don't load the models
you'll get an Exception like this:

```
Exception in thread "main" java.lang.ExceptionInInitializerError
Caused by: java.lang.RuntimeException: edu.stanford.nlp.io.RuntimeIOException: Error while loading a tagger model (probably missing model file)
```

You may use the latest version instead of 3.7.0. This version requires Java 8 mind you, so make sure specify that for
compiling.

#### Imports

Apart from some `io` and [Properties](https://docs.oracle.com/javase/8/docs/api/java/util/Properties.html)
we need from CoreNLP [Annotation](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/pipeline/Annotation.html)
and [StanfordCoreNLP]().

```java
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
```

`Annotation`
