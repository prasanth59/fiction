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
	private double newFeature22;
	private double newFeature23;
	private double newFeature24;
	private double newFeature25;
	private double newFeature26;
	private double newFeature27;
	private double newFeature28;
	private double newFeature29;
	private double newFeature30;
	private double newFeature31;
	private double newFeature32;
	private double newFeature33;
	private double newFeature34;
	private double newFeature35;
	private double newFeature36;
	private double newFeature37;
	private double newFeature38;
	private double newFeature39;
	private double newFeature40;
	private double newFeature41;

	// feature 42 is chunk level
	
	private double newFeature43;
	
	private double newFeature44;
	private double newFeature45;
	private double newFeature46;

	
	

	
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



	public double getNewFeature22() {
		return newFeature22;
	}

	public void setNewFeature22(double newFeature22) {
		this.newFeature22 = newFeature22;
	}

	public double getNewFeature23() {
		return newFeature23;
	}

	public void setNewFeature23(double newFeature23) {
		this.newFeature23 = newFeature23;
	}

	public double getNewFeature24() {
		return newFeature24;
	}

	public void setNewFeature24(double newFeature24) {
		this.newFeature24 = newFeature24;
	}

	public double getNewFeature25() {
		return newFeature25;
	}

	public void setNewFeature25(double newFeature25) {
		this.newFeature25 = newFeature25;
	}

	public double getNewFeature26() {
		return newFeature26;
	}

	public void setNewFeature26(double newFeature26) {
		this.newFeature26 = newFeature26;
	}

	public double getNewFeature27() {
		return newFeature27;
	}

	public void setNewFeature27(double newFeature27) {
		this.newFeature27 = newFeature27;
	}

	public double getNewFeature28() {
		return newFeature28;
	}

	public void setNewFeature28(double newFeature28) {
		this.newFeature28 = newFeature28;
	}

	public double getNewFeature29() {
		return newFeature29;
	}

	public void setNewFeature29(double newFeature29) {
		this.newFeature29 = newFeature29;
	}

	public double getNewFeature30() {
		return newFeature30;
	}

	public void setNewFeature30(double newFeature30) {
		this.newFeature30 = newFeature30;
	}

	public double getNewFeature31() {
		return newFeature31;
	}

	public void setNewFeature31(double newFeature31) {
		this.newFeature31 = newFeature31;
	}

	public double getNewFeature32() {
		return newFeature32;
	}

	public void setNewFeature32(double newFeature32) {
		this.newFeature32 = newFeature32;
	}

	public double getNewFeature33() {
		return newFeature33;
	}

	public void setNewFeature33(double newFeature33) {
		this.newFeature33 = newFeature33;
	}

	public double getNewFeature34() {
		return newFeature34;
	}

	public void setNewFeature34(double newFeature34) {
		this.newFeature34 = newFeature34;
	}

	public double getNewFeature35() {
		return newFeature35;
	}

	public void setNewFeature35(double newFeature35) {
		this.newFeature35 = newFeature35;
	}

	public double getNewFeature36() {
		return newFeature36;
	}

	public void setNewFeature36(double newFeature36) {
		this.newFeature36 = newFeature36;
	}

	public double getNewFeature37() {
		return newFeature37;
	}

	public void setNewFeature37(double newFeature37) {
		this.newFeature37 = newFeature37;
	}

	public double getNewFeature38() {
		return newFeature38;
	}

	public void setNewFeature38(double newFeature38) {
		this.newFeature38 = newFeature38;
	}

	public double getNewFeature39() {
		return newFeature39;
	}

	public void setNewFeature39(double newFeature39) {
		this.newFeature39 = newFeature39;
	}

	public double getNewFeature40() {
		return newFeature40;
	}

	public void setNewFeature40(double newFeature40) {
		this.newFeature40 = newFeature40;
	}

	public double getNewFeature41() {
		return newFeature41;
	}

	public void setNewFeature41(double newFeature41) {
		this.newFeature41 = newFeature41;
	}

	public double getNewFeature43() {
		return newFeature43;
	}

	public void setNewFeature43(double newFeature43) {
		this.newFeature43 = newFeature43;
	}

	public double getNewFeature44() {
		return newFeature44;
	}

	public void setNewFeature44(double newFeature44) {
		this.newFeature44 = newFeature44;
	}

	public double getNewFeature45() {
		return newFeature45;
	}

	public void setNewFeature45(double newFeature45) {
		this.newFeature45 = newFeature45;
	}

	public double getNewFeature46() {
		return newFeature46;
	}

	public void setNewFeature46(double newFeature46) {
		this.newFeature46 = newFeature46;
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
