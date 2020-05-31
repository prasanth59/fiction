package org.ovgu.de.fiction.feature.extraction;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.QuoteAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class quoteFeatureExtraction {

	public List<Double> getquoteFeatureListPerChunk(String path, Integer noOfChunks) throws IOException {
		// TODO Auto-generated method stub
		File input = new File(path);
		Document doc;
		Properties props = new Properties();
		props.setProperty("annotators","tokenize, ssplit, quote");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		doc = Jsoup.parse(input, "UTF-8");      
		Elements ps = doc.select("p");
		String text = ps.text();
		double breakingPoint = Math.floor(text.length()/noOfChunks);
		LinkedList<Double> featureList = new LinkedList<Double>();
		for(int i = 0; i < noOfChunks; i = i+1) {
			String subText = text.substring((int)(breakingPoint * i), (int)(breakingPoint * (i+1))); 
			Annotation annotation = new Annotation(subText);
			pipeline.annotate(annotation);
			Integer quoteLength = 0;
			if (annotation.get(CoreAnnotations.QuotationsAnnotation.class) != null) {
				List<CoreMap> allQuotes = QuoteAnnotator.gatherQuotes(annotation);
				List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
				for (CoreMap quote : allQuotes) {
					quoteLength = quoteLength + (quote.get(CoreAnnotations.CharacterOffsetEndAnnotation.class) - quote.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class));
				}
				double quoteDensity = ((double)quoteLength/subText.length());
				double quotesPerText = ((double)allQuotes.size()/sentences.size());
				double featureValue = quotesPerText * quoteDensity;
				featureList.add(featureValue);
			} 
		}
		return featureList;
	}

}
