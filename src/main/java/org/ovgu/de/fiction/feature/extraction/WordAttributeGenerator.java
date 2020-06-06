package org.ovgu.de.fiction.feature.extraction;

import java.io.BufferedReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ovgu.de.fiction.model.Concept;
import org.ovgu.de.fiction.model.Word;
import org.ovgu.de.fiction.utils.FRConstants;
import org.ovgu.de.fiction.utils.FRGeneralUtils;
import org.ovgu.de.fiction.utils.FRFileOperationUtils;
import org.ovgu.de.fiction.utils.StanfordPipeline;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * @author Suhita, Sayantan
 * @version - Changes for sentiment features
 */
public class WordAttributeGenerator {

	private static final String NNP = "NNP";

	/**
	 * @param path
	 *            = path of whole book, not chunk
	 * @return = List of "Word" objects, each object has original token, POS tag, lemma, NER as
	 *         elements
	 * @author Suhita, Modified by Sayantan for # of characters
	 * @param language 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public Concept generateWordAttributes(Path path, String language) throws IOException, InterruptedException {

		FeatureExtractorUtility feu = new FeatureExtractorUtility();
		Concept cncpt = new Concept();
		Annotation document = new Annotation(FRFileOperationUtils.readFile(path.toString()));
		if (language.equals(FRConstants.ENGLISH)) {
			StanfordPipeline.getPipeline(null).annotate(document);
		}else if (language.equals(FRConstants.GERMAN)){
			StanfordPipeline.getGermanPipeline(null).annotate(document);
		}
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		System.out.println(sentences); 
		List<Word> tokenList = new ArrayList<>();
		Map<String, Integer> charMap = new HashMap<>(); // a new object per new
														 // book // Book
		StringBuffer charName = new StringBuffer();
		int numOfSyllables = 0;
		int numOfSentences =0;

		for (CoreMap sentence : sentences) { // this loop will iterate each of the sentences
			tokenList.add(new Word(FRConstants.S_TAG, FRConstants.S_TAG, null, null, 0));
			numOfSentences++;
			for (CoreLabel cl : sentence.get(CoreAnnotations.TokensAnnotation.class)) {// this
																						// loop
																						// iterates
																						// each
																						// token
																						// of
																						// a
		

				String original = cl.get(CoreAnnotations.OriginalTextAnnotation.class);
				String pos = cl.get(CoreAnnotations.PartOfSpeechAnnotation.class);
				String ner = cl.get(CoreAnnotations.NamedEntityTagAnnotation.class);
				String lemma =  language.equals(FRConstants.ENGLISH) ? cl.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase(): original;
			
				/*
				 * logic 2: check if ner is "P", then further check next 2 element in sentence , ex.
				 * Tom Cruise, Mr. Tom Cruise if yes, then concatenate all two or three tokens i.e.
				 * "Mr" +"Tom" + "Cruise" into a single token the single concatenated token is added
				 * to a Map , where key is number of times "Mr. Tom Cruise" appears
				 */
				if (ner.equals(FRConstants.NER_CHARACTER) && !original.matches(FRConstants.CHARACTER_STOPWORD_REGEX)) {
					if (charName.length() == 0)
						charName.append(original.toLowerCase());
					else
						charName.append(FRConstants.SPACE).append(original.toLowerCase());
				} else if (!ner.equals(FRConstants.NER_CHARACTER) && charName.length() != 0) {

					// calculate for character
					numOfSyllables = FRGeneralUtils.countSyllables(charName.toString().toLowerCase(), language);
					addToTokenList(tokenList, charName.toString(), NNP, FRConstants.NER_CHARACTER, charName.toString(), numOfSyllables);

					String charNameStr = charName.toString().replaceAll(FRConstants.REGEX_ALL_PUNCTUATION, " ")
							.replaceAll(FRConstants.REGEX_TRAILING_SPACE, "");
					int count = charMap.containsKey(charNameStr) ? charMap.get(charNameStr) : 0;
					charMap.put(charNameStr, count + 1);

					// add the next word after character
					addToTokenList(tokenList, original, pos, ner, lemma.toString(), FRGeneralUtils.countSyllables(original.toLowerCase(),language));
					// rest the string buffer
					charName = new StringBuffer();

				} else {
					addToTokenList(tokenList, original, pos, ner, lemma.toString(), FRGeneralUtils.countSyllables(original.toLowerCase(),language));
				}

			}
		}
		cncpt.setWords(tokenList);
		
		
		Map<String,Integer> characterCloneMap = feu.getUniqueCharacterMap(charMap);
		cncpt.setCharacterMap(characterCloneMap);
		
		// code for getting main character.
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		charMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		ArrayList<Integer>  characterCountList = new ArrayList<Integer>(sortedMap.values());
		ArrayList<String>  characternameList = new ArrayList<String>(sortedMap.keySet());
		System.out.println(characternameList);
		System.out.println(characterCountList);
		cncpt.setCharacterNameList(characternameList);
		cncpt.setCharacterCountList(characterCountList);
		
		cncpt.setNumOfSentencesPerBook(numOfSentences);
		StanfordPipeline.resetPipeline();
		return cncpt;
	}

	
	public void addToTokenList(List<Word> tokenList, String original, String pos, String ner, String lemma, int numOfSyllbles) {
		if (lemma.matches("^'.*[a-zA-Z]$")) { // 's o'clock 'em
			StringBuffer sbf = new StringBuffer();
			Arrays.stream(lemma.split("'")).forEach(l -> sbf.append(l));
			tokenList.add(new Word(original, sbf.toString(), pos, ner, numOfSyllbles));
		} // mr. mrs.
		else if (lemma.matches("[a-zA-Z0-9].*[.].*") && ner.matches("(O|MISC)")) {
			tokenList.add(new Word(original, lemma.split("\\.")[0], pos, ner, numOfSyllbles));
		} else {
			tokenList.add(new Word(original, lemma, pos, ner, numOfSyllbles));
		}
	}
	
	
	private String getlemmaForGermanWord(String original, String pos) throws IOException, InterruptedException {
		String[] cmd = { "py","-W ignore", "/Users/bhargavmuktevi/Desktop/SIMFIC Project/pythonIntegration/Feature3.py", original, pos};
		Process p = Runtime.getRuntime().exec(cmd);

		String s = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((s = in.readLine()) != null) {
			System.out.println(s);
		}
		p.waitFor();
		p.destroy();
		return s;
	}
	
	
	

}
