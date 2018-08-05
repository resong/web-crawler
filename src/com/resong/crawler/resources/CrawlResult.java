package com.resong.crawler.resources;

/**
 * Class that creates an object to store the results of crawling one page. Contains
 * information on the page object crawled, the crawl success, and the outcome of the
 * search. 
 * 
 * @author Rebecca Song
 *
 */

import com.resong.crawler.*;

public class CrawlResult {

	/////////////// Attributes ////////////////
	
	private Page p; // page crawled
	private String error; // error message if crawl failed
	private boolean flag; // flag indicating if page matched search term
	private int order; // integer indicating order page was crawled
	
	/////////////// Constructors ////////////////
	
	/**
	 * Constructs object that takes a page object, order it
	 * was crawled and outcome of the search.
	 * @param page crawled page
	 * @param sequence order page was crawled in
	 * @param result outcome of whether page contained search term
	 */
	
	public CrawlResult(Page page, int sequence, boolean result) {
		this.p = page;
		this.flag = result;
		this.order = sequence;
		this.error = "";
	}
	
	/**
	 * Constructs object that takes a page object, order it 
	 * was crawled and an appropriate error message.
	 * @param page crawled page
	 * @param sequence order page was crawled in
	 * @param message error message if exception is thrown
	 */
	
	public CrawlResult(Page page, int sequence, String message) {
		this.p = page;
		this.flag = false;
		this.order = sequence;
		this.error = message;
	}
	
	
	////////////////// Methods ///////////////////
	
	/**
	 * Accessor method that returns the current page
	 * @return page
	 */
	
	public Page getPage() {
		return this.p;
	}
	
	/**
	 * Accessor method that returns the appropriate
	 * error message for the page
	 * @return error 
	 */
	
	public String getErrorMessage() {
		return this.error;
	}
	
	/**
	 * Accessor method that returns sequence integer
	 * of the current page
	 * @return order 
	 */
	
	public int getSequence() {
		return this.order;
	}
	
	/**
	 * Accessor method that returns if the search term
	 * @return true if search term found, else false
	 */
	
	public boolean isMatch() {
		return this.flag;
	}
	
	/**
	 * Accessor method that returns if
	 * the crawl was successful
	 * @return true if page downloaded and term was found, else false
	 */
	
	public boolean crawlSuccess() {
		if(this.flag == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
