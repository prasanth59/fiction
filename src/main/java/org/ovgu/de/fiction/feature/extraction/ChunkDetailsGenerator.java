package org.ovgu.de.fiction.feature.extraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ovgu.de.fiction.model.BookDetails;
import org.ovgu.de.fiction.model.Chunk;
import org.ovgu.de.fiction.model.Concept;
import org.ovgu.de.fiction.model.Feature;
import org.ovgu.de.fiction.model.Word;
import org.ovgu.de.fiction.utils.FRConstants;
import org.ovgu.de.fiction.utils.FRFileOperationUtils;
import org.ovgu.de.fiction.utils.FRGeneralUtils;
import org.ovgu.de.fiction.utils.StanfordPipeline;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


/**
 * @author Suhita
 */
public class ChunkDetailsGenerator {

	final static Logger LOG = Logger.getLogger(ChunkDetailsGenerator.class);

	private static Set<String> LOCATIVE_PREPOSITION_LIST;
	private static Set<String> LOCATIVE_PREPOSITION_LIST_GERMAN;
	private static Set<String> PUNCT_QUOTES_LIST_GERMAN;

	private static List<String> negativeList;
	private static List<String> positiveList;
	private static List<String> neutralList;
	
	
	
	protected static Integer CHUNK_SIZE;
	protected static Integer TTR_CHUNK_SIZE;
	protected static String OUT_FOLDER_TOKENS;
	private static long TIME ;
	private int BOOK_NO;
	private int NUM_OF_CHARS_PER_BOOK = -1;
	private String CONTENT_EXTRCT_FOLDER;
	StanfordCoreNLP SENTI_PIPELINE;
	
	
	protected void init() throws NumberFormatException, IOException {

		CHUNK_SIZE = Integer.parseInt(FRGeneralUtils.getPropertyVal(FRConstants.CHUNK_SIZE));
		TTR_CHUNK_SIZE = Integer.parseInt(FRGeneralUtils.getPropertyVal(FRConstants.CHUNK_SIZE_FOR_TTR));

		OUT_FOLDER_TOKENS = FRGeneralUtils.getPropertyVal(FRConstants.OUT_FOLDER_TOKENS);
		CONTENT_EXTRCT_FOLDER = FRGeneralUtils.getPropertyVal(FRConstants.OUT_FOLDER_CONTENT);

		LOCATIVE_PREPOSITION_LIST = FRGeneralUtils.getPrepositionList();
		LOCATIVE_PREPOSITION_LIST_GERMAN = FRGeneralUtils.getGermanPrepositionList();
		PUNCT_QUOTES_LIST_GERMAN = FRGeneralUtils.getPunctQuoteList();
		// list of german words for sentiments positive,negative,neutral.
		negativeList = FRGeneralUtils.getNegativeWordsList();
		positiveList = FRGeneralUtils.getPositiveWordsList();
		neutralList = FRGeneralUtils.getNeutralWordsList();
		// list of german words for sentiments positive,negative,neutral.

		BOOK_NO = 0;
		TIME = System.currentTimeMillis();

		SENTI_PIPELINE = StanfordPipeline.getPipeline(FRConstants.STNFRD_SENTI_ANNOTATIONS);

	}

	/**
	 * @return
	 * @throws IOException
	 */
	public List<BookDetails> getChunksFromAllFiles() throws IOException {
		init();

		List<BookDetails> books = new ArrayList<>();
		FeatureExtractorUtility feu = new FeatureExtractorUtility();
		
	
		// following loop runs, over path of each book
		FRFileOperationUtils.getFileNames(CONTENT_EXTRCT_FOLDER).stream().forEach(file -> {
			String fileName = file.getFileName().toString().replace(FRConstants.CONTENT_FILE, FRConstants.NONE);

			try {
				BookDetails book = new BookDetails();
				book.setBookId(fileName);
				book.setMetadata(FRGeneralUtils.getMetadata(fileName));
				String language = book.getMetadata().getLanguage();
				book.setChunks(getChunksFromFile(file.toString(),language)); // this is a
																	 // list of
																	 // chunks,
																	 // each
																	 // chunk
																	 // again has
																	 // a feature
																	 // object/vector
				book.setAverageTTR(feu.getAverageTTR(getEqualChunksFromFile(getTokensFromAllChunks(book.getChunks()))));
				book.setNumOfChars(NUM_OF_CHARS_PER_BOOK == 0 ? 1 : NUM_OF_CHARS_PER_BOOK);
				
				// extracting features from python generated csv feature file
				HashMap<Integer,Double> pythonFeaturesMap = new HashMap<Integer, Double>();
				String pythonIntegratedCsvPath = "/Users/bhargavmuktevi/Desktop/SIMFIC Project/pythonIntegration/features.csv";
				String line = ""; 
				try   
				{  
					BufferedReader br = new BufferedReader(new FileReader(pythonIntegratedCsvPath));  

					while ((line = br.readLine()) != null) {                  
						String[] pythonFeatures = line.split(","); 
						for (int i = 0; i < pythonFeatures.length ; i++) {
							pythonFeaturesMap.put(i, Double.parseDouble(pythonFeatures[i]));
						}  
					} 
				}
				catch (IOException e)   
				{  
					e.printStackTrace();  
				} 
				
				
				//setting genere features 
				book.setNewFeature22(pythonFeaturesMap.get(0));
				book.setNewFeature23(pythonFeaturesMap.get(1));
				book.setNewFeature24(pythonFeaturesMap.get(2));
				book.setNewFeature25(pythonFeaturesMap.get(3));
				book.setNewFeature26(pythonFeaturesMap.get(4));
				book.setNewFeature27(pythonFeaturesMap.get(5));
				book.setNewFeature28(pythonFeaturesMap.get(6));
				book.setNewFeature29(pythonFeaturesMap.get(7));
				book.setNewFeature30(pythonFeaturesMap.get(8));
				book.setNewFeature31(pythonFeaturesMap.get(9));
				book.setNewFeature32(pythonFeaturesMap.get(10));
				book.setNewFeature33(pythonFeaturesMap.get(11));
				book.setNewFeature34(pythonFeaturesMap.get(12));
				book.setNewFeature35(pythonFeaturesMap.get(13));
				book.setNewFeature36(pythonFeaturesMap.get(14));
				book.setNewFeature37(pythonFeaturesMap.get(15));
				book.setNewFeature38(pythonFeaturesMap.get(16));
				book.setNewFeature39(pythonFeaturesMap.get(17));
				book.setNewFeature40(pythonFeaturesMap.get(18));
				book.setNewFeature41(pythonFeaturesMap.get(19));
				//setting main character feature
				book.setNewFeature43(pythonFeaturesMap.get(20));
				//setting start to end features
				book.setNewFeature44(pythonFeaturesMap.get(21));
				book.setNewFeature45(pythonFeaturesMap.get(22));
				book.setNewFeature46(pythonFeaturesMap.get(23));
				
				books.add(book);

				 //LOG.debug("End Generate token for : " + fileName + " " + ((System.currentTimeMillis() - TIME) / 1000));
				TIME = System.currentTimeMillis();

				System.out.println(++BOOK_NO + " > " + book.toString());

			} catch (IOException e) {
				LOG.error("IOException in generating chunks -" + e.getMessage() + " for book " + fileName);
			} catch (ArrayIndexOutOfBoundsException ai) {
				LOG.error("ArrayIndexOutOfBoundsException in generating chunks -" + ai.getMessage() + " for book " + fileName);
			} catch (Exception e) {
				// LOG.error("Error in generating chunks -" + e.getMessage() + " for book " +
				// fileName);
				e.printStackTrace();
			}

		});
		return books;
	}

	/**
	 * @author Suhita, Sayantan
	 * @see - The method generates List of Chunk out of the file passed in the
	 *      signature. this path is a path to a single book, mind it!
	 * @param path
	 *            to book location
	 * @param language 
	 * @return : List of Chunks, Chunk has a feature vector object
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public List<Chunk> getChunksFromFile(String path, String language) throws IOException, InterruptedException {

		int batchNumber;
		List<Chunk> chunksList = new ArrayList<>();
		Annotation annotation = null;

		WordAttributeGenerator wag = new WordAttributeGenerator();
		quoteFeatureExtraction qfe = new quoteFeatureExtraction();
		FeatureExtractorUtility feu = new FeatureExtractorUtility();
		List<String> stopwords = Arrays.asList(FRGeneralUtils.getPropertyVal(FRConstants.STOPWORD_FICTION).split("\\|"));
		List<String> stopwords_german = Arrays.asList(FRGeneralUtils.getPropertyVal(FRConstants.STOPWORD_GERMAN).split("\\|"));
		Concept cncpt = wag.generateWordAttributes(Paths.get(path), language); // this is
																	 // a
																	 // "word-token-pos-ner"
																	 // list
																	 // of
																	 // whole
																	 // book!

		// dummu

		for (Entry<String,Integer> c : cncpt.getCharacterMap().entrySet()) {
			LOG.info(c.getKey()+" "+c.getValue());
		}
		NUM_OF_CHARS_PER_BOOK = cncpt.getCharacterMap().size();

		List<Word> wordList = cncpt.getWords();
		int numOfSntncPerBook  = cncpt.getNumOfSentencesPerBook();

		String fileName = Paths.get(path).getFileName().toString().replace(FRConstants.CONTENT_FILE, FRConstants.NONE);

		ParagraphPredicate filter = new ParagraphPredicate();
		List<Word> copy = new ArrayList<>(wordList);
		copy.removeIf(filter);
		
//		Calculate Feature to encode start and end of book
		List<String>wordLemma= new  ArrayList<>();
		wordLemma.add(fileName);
		for (Word item : copy) {
			wordLemma.add(item.getLemma());
		}
// Function to call python script to encode start and end of book	
		//encode_book(wordLemma,fileName,path);
		
		int length = copy.size();

		int remainder = 0;

		if (length < CHUNK_SIZE) {
			batchNumber = 0;
			remainder = length;
		} else {
			batchNumber = length / CHUNK_SIZE;
			remainder = (length % CHUNK_SIZE);
			if (remainder <= CHUNK_SIZE / 2) {
				batchNumber--;
				remainder = CHUNK_SIZE + remainder;
			}
		}

		List<Word> raw = new ArrayList<>();
		List<String> stpwrdPuncRmvd = new ArrayList<>();
		int wordcntr = 0;
		int chunkNo = 1;
		int paragraphCount = 0;
		int chunkSize = 0;
		Map<Integer, Integer> wordCountPerSntncMap = null;
		int senti_negetiv_cnt = 0;
		int senti_positiv_cnt = 0;
		int senti_neutral_cnt = 0;
		int wordCountPerSntnc = 0;
		double totalNumOfRandomSntnPerChunk =0; // sentiment_calculated_over_these_randm_sentences_per_chunk
		
		
		if(batchNumber==0) //very_small_book
			totalNumOfRandomSntnPerChunk =  (FRConstants.PERCTG_OF_SNTNC_FOR_SENTIM * numOfSntncPerBook);
		else
			totalNumOfRandomSntnPerChunk = FRConstants.PERCTG_OF_SNTNC_FOR_SENTIM * ((numOfSntncPerBook)/(batchNumber));//10%_of_sentences_per_chunk
			
		List<Double> quoteFeatureListPerChunk = qfe.getquoteFeatureListPerChunk(path,batchNumber + 1);
		
		for (int batchCtr = 0; batchCtr <= batchNumber; batchCtr++) { //loop_over_number_of_chunks_of_a_book

			chunkSize = batchCtr < batchNumber ? CHUNK_SIZE : remainder;

			double malePrpPosPronounCount = 0;
			double femalePrpPosPronounCount = 0;
			double personalPronounCount = 0;
			double possPronounCount = 0;
			double locativePrepositionCount = 0;
			double coordConj = 0;
			double commaCount = 0;
			double periodCount = 0;
			double colonCount = 0;
			double semiColonCount = 0;
			double hyphenCount = 0;
			double intrjctnCount = 0;
			double convCount = 0;
			int sentenceCount = 0;
			wordCountPerSntnc = 0;
			wordCountPerSntncMap = new HashMap<>();
			senti_negetiv_cnt = 0;
			senti_positiv_cnt = 0;
			senti_neutral_cnt = 0;
			int properWordCount = 0;
			int numOfSyllables = 0;
			int randomSntnCount =0;
			double quoteValue = (double) quoteFeatureListPerChunk.get(batchCtr);
			StringBuffer sentenceSbf = new StringBuffer();

			

			
			if (language.equals(FRConstants.ENGLISH)) {

				for (int index = 0; index < chunkSize; index++) {// loop_over_tokens_of_a_given_chunk
					Word token = wordList.get(wordcntr);
					String l = token.getLemma();

					if (l.equals(FRConstants.P_TAG)) {
						paragraphCount++;
						wordcntr++;
						index--;
						continue;
					}
					if (l.equals(FRConstants.S_TAG)) {
						/**
						 * calculate sentiment for the previous formed sentence,
						 * the choice of selecting a sentence is totally random
						 * example, when the random number >5k and the num of selected sentences have not crossed , sampling bound = 10%_of_sentences_per_chunk
						 */
						Random rnd = new Random();
						int randNum = rnd.nextInt(FRConstants.RANDOM_SENTENCES_SENTIM_TOP_VAL); //get_an_INT_less_than_10k


						if (sentenceSbf.toString().length()>0 && randNum<FRConstants.RANDOM_SENTENCES_SENTIM_MID_VAL && randomSntnCount<totalNumOfRandomSntnPerChunk) { // making_a_random_choice_here
							// calculateSenti as=>
							annotation = SENTI_PIPELINE.process(sentenceSbf.toString());
							int score = 2; // Default as Neutral. 1 = Negative, 2 =
							// Neutral, 3 = Positive
							for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))// ideally
								// this
								// loop
								// runs
								// once!
							{
								Tree tree = sentence.get(SentimentAnnotatedTree.class);
								score = RNNCoreAnnotations.getPredictedClass(tree);
							}
							if (score == 2)
								senti_neutral_cnt++;
							if (score == 1)
								senti_negetiv_cnt++;
							if (score == 3)
								senti_positiv_cnt++;

							randomSntnCount++;
						}
						// reset the sentence buffer for next sentence
						sentenceSbf = new StringBuffer();

						/* partly end of sentiment calculation */
						if (wordCountPerSntnc != 0) {
							addToWordCountMap(raw, wordCountPerSntncMap, wordCountPerSntnc);
						}
						wordCountPerSntnc = 0;
						sentenceCount++;
						wordcntr++;
						index--;
						continue;
					}
					/* append the token to form sentence. Below part needed for sentiment calculation */

					else if (!l.equals(FRConstants.S_TAG) && !l.equals(FRConstants.P_TAG)) {
						sentenceSbf.append(" ").append(token.getOriginal());
					}

					if (!FRGeneralUtils.hasPunctuation(l) && !stopwords.contains(l))
						stpwrdPuncRmvd.add(l);

					raw.add(token);
					/* calculate Flesch reading score */
					if (token.getNumOfSyllables() > 0) {
						numOfSyllables += token.getNumOfSyllables();
						properWordCount++;
					}
					/* calculate pos stats */
					if (token.getPos().equals(FRConstants.PERSONAL_P)) {
						personalPronounCount++;
						if (l.equals(FRConstants.HE))
							malePrpPosPronounCount++;
						else if (l.equals(FRConstants.SHE))
							femalePrpPosPronounCount++;

					} else if (token.getPos().equals(FRConstants.POSSESIV_P)) {
						possPronounCount++;
						if (l.equals(FRConstants.HE))
							malePrpPosPronounCount++;
						else if (l.equals(FRConstants.SHE))
							femalePrpPosPronounCount++;

					} else if (token.getPos().equals(FRConstants.PREPOSITION)) {
						if (LOCATIVE_PREPOSITION_LIST.contains(l))
							locativePrepositionCount++;
						if (l.equals(FRConstants.IN)) {
							int temp = wordcntr;
							if ((l.equals(FRConstants.IN) && wordList.get(++temp).getLemma().equals(FRConstants.FRONT)
									&& wordList.get(++temp).getLemma().equals(FRConstants.OF)))
								locativePrepositionCount++;
						}

					} else if (l.equals(FRConstants.NEXT)) {
						int temp = wordcntr;
						if (l.equals(FRConstants.NEXT) && wordList.get(++temp).getLemma().equals(FRConstants.TO))
							locativePrepositionCount++;
					} else if (token.getPos().equals(FRConstants.THERE_EX) || token.getLemma().equals(FRConstants.COME))
						locativePrepositionCount++;
					else if (token.getPos().equals(FRConstants.INTERJECTION))
						intrjctnCount++;
					else if (token.getPos().equals(FRConstants.COORD_CONJUNCTION))
						coordConj++;
					else if (token.getLemma().equals(FRConstants.COMMA))
						commaCount++;
					else if (token.getLemma().equals(FRConstants.PERIOD)) {
						periodCount++;
					} else if (token.getLemma().equals(FRConstants.COLON))
						colonCount++;
					else if (token.getLemma().equals(FRConstants.SEMI_COLON))
						semiColonCount++;
					else if (token.getLemma().equals(FRConstants.HYPHEN))
						hyphenCount++;
					else if (token.getLemma().equals(FRConstants.EXCLAMATION))
						intrjctnCount++;
					else if (token.getLemma().equals(FRConstants.DOUBLE_QUOTES))
						convCount++;

					wordcntr++;
					wordCountPerSntnc++;
				}
				
			} else if (language.equals(FRConstants.GERMAN)){ 
				//chunk level features for german text
				for (int index = 0; index < chunkSize; index++) {
					Word token = wordList.get(wordcntr);
					String l = token.getLemma();
					if (l.equals(FRConstants.P_TAG)) {
						paragraphCount++;
						wordcntr++;
						index--;
						continue;
					}
					if (l.equals(FRConstants.S_TAG)) {
						Random rnd = new Random();
						int randNum = rnd.nextInt(FRConstants.RANDOM_SENTENCES_SENTIM_TOP_VAL); //get_an_INT_less_than_10k


						if (sentenceSbf.toString().length()>0 && randNum<FRConstants.RANDOM_SENTENCES_SENTIM_MID_VAL && randomSntnCount<totalNumOfRandomSntnPerChunk) {
							int postiveWordCount = 0;
							int negativeWordCount = 0;
							int neutralWordCount = 0;
					
							String sentence = sentenceSbf.toString();
							String[] sentenceList = sentence.split(" ");
							for (int i = 0; i < sentenceList.length; i++) {
								String word = sentenceList[i];
								if(positiveList.contains(word)) {
									postiveWordCount++;
								}
								if(negativeList.contains(word)) {
									negativeWordCount++;
								}
								if(neutralList.contains(word)) {
									neutralWordCount++;
								}	
							}
							if(postiveWordCount > negativeWordCount && postiveWordCount > neutralWordCount)
					        {
								senti_positiv_cnt++;
					        }
					        else if(negativeWordCount > neutralWordCount)
					        {
					        	senti_negetiv_cnt++;
					        }
					        else
					        {
					        	senti_neutral_cnt++;
					        }
							randomSntnCount++;
						}
						// reset the sentence buffer for next sentence
						sentenceSbf = new StringBuffer();

						/* partly end of sentiment calculation */
						if (wordCountPerSntnc != 0) {
							addToWordCountMap(raw, wordCountPerSntncMap, wordCountPerSntnc);
						}
						wordCountPerSntnc = 0;
						sentenceCount++;
						wordcntr++;
						index--;
						continue;
						
					}else if (!l.equals(FRConstants.S_TAG) && !l.equals(FRConstants.P_TAG)) {
						sentenceSbf.append(" ").append(token.getOriginal());
					}
					if (!FRGeneralUtils.hasGermanPunctuation(l) && !stopwords_german.contains(l)) 
						stpwrdPuncRmvd.add(l);
					raw.add(token);
					if (token.getNumOfSyllables() > 0) {
						numOfSyllables += token.getNumOfSyllables();
						properWordCount++;
					}
					if (token.getPos().equals(FRConstants.PERSONAL_P_GERMAN)) {
						personalPronounCount++;
						if (l.equals(FRConstants.ER))
							malePrpPosPronounCount++;
						else if (l.equals(FRConstants.SIE))
							femalePrpPosPronounCount++;

					} else if (token.getPos().equals(FRConstants.POSSESIV_P_GERMAN)) {
						possPronounCount++;
						if (l.equals(FRConstants.SEIN))
							malePrpPosPronounCount++;
						else if (l.equals(FRConstants.IHR))
							femalePrpPosPronounCount++;

					} else if (token.getPos().equals(FRConstants.GERMAN_PREPOSITION) && LOCATIVE_PREPOSITION_LIST_GERMAN.contains(l)) {
						locativePrepositionCount++;
					}
					else if (token.getPos().equals(FRConstants.GERMAN_INTERJECTION))
						intrjctnCount++;
					else if (token.getPos().equals(FRConstants.COORD_CONJUNCTION_GERMAN))
						coordConj++;
					else if (token.getLemma().equals(FRConstants.COMMA))
						commaCount++;
					else if (token.getLemma().equals(FRConstants.PERIOD)) {
						periodCount++;
					} else if (token.getLemma().equals(FRConstants.COLON))
						colonCount++;
					else if (token.getLemma().equals(FRConstants.SEMI_COLON))
						semiColonCount++;
					else if (token.getLemma().equals(FRConstants.HYPHEN))
						hyphenCount++;
					else if (token.getLemma().equals(FRConstants.EXCLAMATION))
						intrjctnCount++;
					else if (token.getPos().equals(FRConstants.QUOTES_GERMAN) && PUNCT_QUOTES_LIST_GERMAN.contains(l))
						convCount++;

					wordcntr++;
					wordCountPerSntnc++;

				}
			}
				
			
			addToWordCountMap(raw, wordCountPerSntncMap, wordCountPerSntnc);

			Chunk chunk = new Chunk();
			chunk.setChunkNo(chunkNo);
			String chunkFileName = OUT_FOLDER_TOKENS + fileName + "-" +
					chunkNo + FRConstants.CHUNK_FILE;
			try (Writer contentWtr = new BufferedWriter(new
					OutputStreamWriter(new FileOutputStream(chunkFileName)));) {
				contentWtr.write(Chunk.getOriginalText(raw));
				chunk.setChunkFileLocation(chunkFileName);
			 }
			System.out.println("numbr of sentences for sentiment  ="+randomSntnCount+" for chunknum ="+chunkNo+", and total sentc  ="+numOfSntncPerBook+" for book path "+path);
			chunk.setTokenListWithoutStopwordAndPunctuation(stpwrdPuncRmvd);
			Feature feature = feu.generateFeature(chunkNo, paragraphCount, sentenceCount, raw, null, stpwrdPuncRmvd, malePrpPosPronounCount,
					femalePrpPosPronounCount, personalPronounCount, possPronounCount, locativePrepositionCount, coordConj, commaCount,
					periodCount, colonCount, semiColonCount, hyphenCount, intrjctnCount, convCount, wordCountPerSntncMap, senti_negetiv_cnt,
					senti_positiv_cnt, senti_neutral_cnt, properWordCount, numOfSyllables, quoteValue);
			chunk.setFeature(feature);
			chunksList.add(chunk);
			chunkNo++;

			// reset all var for next chunk
			raw = new ArrayList<>();
			stpwrdPuncRmvd = new ArrayList<>();
			paragraphCount = 0;

		}

		return chunksList;
	}

	public void addToWordCountMap(List<Word> raw, Map<Integer, Integer> wordCountPerSntncMap, int wordCount) {
		wordCountPerSntncMap.put(wordCount, !wordCountPerSntncMap.containsKey(wordCount) ? 1 : wordCountPerSntncMap.get(wordCount) + 1);
	}

	/**
	 * @param path
	 * @param stopwords
	 * @return List of Equal Chunks per file
	 * @throws IOException
	 *             The method generates List of equal sized Chunk from the
	 *             tokens list passed in the signature.It has been developed
	 *             especially for ttr
	 */
	public List<Chunk> getEqualChunksFromFile(List<String> tokens) throws Exception {

		int batchNumber;
		int remainder;
		List<Chunk> chunksList = new ArrayList<>();
		int length = tokens.size();

		if (length < TTR_CHUNK_SIZE) {
			batchNumber = 0;
			remainder = TTR_CHUNK_SIZE - length;

		} else {
			batchNumber = length / TTR_CHUNK_SIZE;
			remainder = length % TTR_CHUNK_SIZE;
		}

		List<String> textTokens = new ArrayList<>();
		List<String> appendAtEnd = new ArrayList<>();

		int chunkNo = 1;
		int wordcntr = 0;
		int chunkSize = 0;

		for (int batchCtr = 0; batchCtr <= batchNumber; batchCtr++) {

			chunkSize = batchCtr < batchNumber ? TTR_CHUNK_SIZE : remainder;
			for (int index = 0; index < chunkSize; index++) {
				if(wordcntr < length) {

				String token = tokens.get(wordcntr);
				textTokens.add(token);

				if (batchCtr == 0 && wordcntr < (TTR_CHUNK_SIZE - remainder)) // tokens
																				 // to
																				 // be
																				 // appended
																				 // to
																				 // the
																				 // last
																				 // chunk,
																				 // to
																				 // make
																				 // it
																				 // equal
																				 // sized
																				 // as
																				 // CHUNK_SIZE
				{
					appendAtEnd.add(token);
				}
				wordcntr++;
			}}

			if (remainder != 0 && batchCtr == batchNumber)
				textTokens.addAll(appendAtEnd);

			Chunk chunk = new Chunk();
			chunk.setChunkNo(chunkNo);
			chunk.setTokenListWithoutStopwordAndPunctuation(textTokens);
			chunksList.add(chunk);
			textTokens = new ArrayList<>();
			chunkNo++;
		}

		return chunksList;
	}

	/**
	 * @param chunks
	 * @return Returns list of tokens from all chunks
	 */
	private List<String> getTokensFromAllChunks(List<Chunk> chunks) {

		List<String> tokens = new ArrayList<>();
		chunks.forEach(c -> tokens.addAll(c.getTokenListWithoutStopwordAndPunctuation()));
		return tokens;

	}
	
	private void encode_book(List<String>wordLemma, String book_id,String filePath) throws IOException, InterruptedException {

		File file = new File("/Users/bhargavmuktevi/Desktop/SIMFIC Project/pythonIntegration/output.txt");
		if (file.exists() == true) {
			System.out.println("deleted file");
			file.delete();
		}
		FileOutputStream fo = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fo);

		for (String elem : wordLemma) {
			pw.println(elem);
		}
		pw.close();
		fo.close();
//		Code to execute python script with passed parameters
		String[] cmd = { "py","-W ignore", "/Users/bhargavmuktevi/Desktop/SIMFIC Project/pythonIntegration/Feature3.py", "/Users/bhargavmuktevi/Desktop/SIMFIC Project/pythonIntegration/output.txt", filePath,"/Users/bhargavmuktevi/Desktop/SIMFIC Project/pythonIntegration/features.csv"};
		Process p = Runtime.getRuntime().exec(cmd);

		String s = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((s = in.readLine()) != null) {
			System.out.println(s);
		}
		p.waitFor();
		p.destroy();

	}
	
	
	

}
