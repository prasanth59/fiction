package org.ovgu.de.fiction.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import nl.siegmann.epublib.domain.Metadata;

/**
 * @author Suhita
 */
public class BookDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5335435328859228630L;
	private String bookId;
	private List<Chunk> chunks;
	private Metadata metadata;
	private String content;
	private BigDecimal averageTTR;
	
	private int numOfChars; // added by Sayantan, at Book level
	
	
	
	// each book genere is represented in 20 dimensions.
	private float newFeature22;
	private float newFeature23;
	private float newFeature24;
	private float newFeature25;
	private float newFeature26;
	private float newFeature27;
	private float newFeature28;
	private float newFeature29;
	private float newFeature30;
	private float newFeature31;
	private float newFeature32;
	private float newFeature33;
	private float newFeature34;
	private float newFeature35;
	private float newFeature36;
	private float newFeature37;
	private float newFeature38;
	private float newFeature39;
	private float newFeature40;
	private float newFeature41;


	
	

	
	/**
	 * @param bookId
	 * @param chunks
	 * @param metadata
	 * @param feature
	 */
	public BookDetails(String bookId, List<Chunk> chunks, Metadata metadata, String content, int numOfChars) {
		super();
		this.bookId = bookId;
		this.chunks = chunks;
		this.metadata = metadata;
		this.content = content;
		this.numOfChars = numOfChars;
	}

	/**
	 * 
	 */
	public BookDetails() {
		super();
	}

	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the chunks
	 */
	public List<Chunk> getChunks() {
		return chunks;
	}

	/**
	 * @param chunks
	 *            the chunks to set
	 */
	public void setChunks(List<Chunk> chunks) {
		this.chunks = chunks;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the metadata
	 */
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the averageTTR
	 */
	public BigDecimal getAverageTTR() {
		return averageTTR;
	}

	/**
	 * @param averageTTR
	 *            the averageTTR to set
	 */
	public void setAverageTTR(BigDecimal averageTTR) {
		this.averageTTR = averageTTR;
	}
	
	

	public int getNumOfChars() {
		return numOfChars;
	}

	public void setNumOfChars(int numOfChars) {
		this.numOfChars = numOfChars;
	}

	
	
	
	
	
	
	
	
	
	
	public float getNewFeature22() {
		return newFeature22;
	}

	public void setNewFeature22(float newFeature22) {
		this.newFeature22 = newFeature22;
	}

	public float getNewFeature23() {
		return newFeature23;
	}

	public void setNewFeature23(float newFeature23) {
		this.newFeature23 = newFeature23;
	}

	public float getNewFeature24() {
		return newFeature24;
	}

	public void setNewFeature24(float newFeature24) {
		this.newFeature24 = newFeature24;
	}

	public float getNewFeature25() {
		return newFeature25;
	}

	public void setNewFeature25(float newFeature25) {
		this.newFeature25 = newFeature25;
	}

	public float getNewFeature26() {
		return newFeature26;
	}

	public void setNewFeature26(float newFeature26) {
		this.newFeature26 = newFeature26;
	}

	public float getNewFeature27() {
		return newFeature27;
	}

	public void setNewFeature27(float newFeature27) {
		this.newFeature27 = newFeature27;
	}

	public float getNewFeature28() {
		return newFeature28;
	}

	public void setNewFeature28(float newFeature28) {
		this.newFeature28 = newFeature28;
	}

	public float getNewFeature29() {
		return newFeature29;
	}

	public void setNewFeature29(float newFeature29) {
		this.newFeature29 = newFeature29;
	}

	public float getNewFeature30() {
		return newFeature30;
	}

	public void setNewFeature30(float newFeature30) {
		this.newFeature30 = newFeature30;
	}

	public float getNewFeature31() {
		return newFeature31;
	}

	public void setNewFeature31(float newFeature31) {
		this.newFeature31 = newFeature31;
	}

	public float getNewFeature32() {
		return newFeature32;
	}

	public void setNewFeature32(float newFeature32) {
		this.newFeature32 = newFeature32;
	}

	public float getNewFeature33() {
		return newFeature33;
	}

	public void setNewFeature33(float newFeature33) {
		this.newFeature33 = newFeature33;
	}

	public float getNewFeature34() {
		return newFeature34;
	}

	public void setNewFeature34(float newFeature34) {
		this.newFeature34 = newFeature34;
	}

	public float getNewFeature35() {
		return newFeature35;
	}

	public void setNewFeature35(float newFeature35) {
		this.newFeature35 = newFeature35;
	}

	public float getNewFeature36() {
		return newFeature36;
	}

	public void setNewFeature36(float newFeature36) {
		this.newFeature36 = newFeature36;
	}

	public float getNewFeature37() {
		return newFeature37;
	}

	public void setNewFeature37(float newFeature37) {
		this.newFeature37 = newFeature37;
	}

	public float getNewFeature38() {
		return newFeature38;
	}

	public void setNewFeature38(float newFeature38) {
		this.newFeature38 = newFeature38;
	}

	public float getNewFeature39() {
		return newFeature39;
	}

	public void setNewFeature39(float newFeature39) {
		this.newFeature39 = newFeature39;
	}

	public float getNewFeature40() {
		return newFeature40;
	}

	public void setNewFeature40(float newFeature40) {
		this.newFeature40 = newFeature40;
	}

	public float getNewFeature41() {
		return newFeature41;
	}

	public void setNewFeature41(float newFeature41) {
		this.newFeature41 = newFeature41;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + " having " + chunks.size() + " chunks => " + chunks + ", metadata=" + metadata.getFirstTitle()
				+ ", averageTTR=" + averageTTR + ", num of Chars="+numOfChars+ "]";
	}

}
