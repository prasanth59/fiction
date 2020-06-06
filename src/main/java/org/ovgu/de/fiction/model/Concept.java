package org.ovgu.de.fiction.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Concept {

	List<Word> words;
	int numOfSentencesPerBook;
	Map<String, Integer> characterMap;
	
	ArrayList<String> characterNameList;
	ArrayList<Integer> characterCountList;

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	public Map<String, Integer> getCharacterMap() {
		return characterMap;
	}

	public void setCharacterMap(Map<String, Integer> characterMap) {
		this.characterMap = characterMap;
	}

	public int getNumOfSentencesPerBook() {
		return numOfSentencesPerBook;
	}

	public void setNumOfSentencesPerBook(int numOfSentencesPerBook) {
		this.numOfSentencesPerBook = numOfSentencesPerBook;
	}

	public ArrayList<String> getCharacterNameList() {
		return characterNameList;
	}

	public void setCharacterNameList(ArrayList<String> characterNameList) {
		this.characterNameList = characterNameList;
	}

	public ArrayList<Integer> getCharacterCountList() {
		return characterCountList;
	}

	public void setCharacterCountList(ArrayList<Integer> characterCountList) {
		this.characterCountList = characterCountList;
	}
	
	

}
