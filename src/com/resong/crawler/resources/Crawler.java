/**
 * Class that creates a crawler object to search for a search term
 * starting at a specified website address, with a specified max number
 * of links to iterate through each page and a max page depth. The object 
 * takes a search term and a list to store the results in, and has many
 * accessors, mutators and other methods to establish the status of the
 * crawler's search.
 * 
 * @author CS1027 & Rebecca Song
 */
 package com.resong.crawler.resources;

import java.util.ArrayList;

public abstract class Crawler {
	
    
    /////////////////// Attributes /////////////////////
	
    private ArrayList<String> linksVisited; // List of links the crawler has already visited
    
    private String kWord; // String representing keyword to be searched
    
    private int maxDepth; // Integer representing max. depth the crawler will search
    
    private int maxLinks; // Integer representing max number of links crawled on each page
    
    
    private CrawlResultList rList; // List of results of the crawl
    
    private int seq; // Integer indicating sequence number of last page crawled
    
    protected boolean stopCrawl; // Boolean indicating whether or not we should stop crawling
    
    protected boolean crawlingNow; // Boolean indicating if we are currently crawling
    
    
    /////////////////// Constructors ///////////////////
    
    /**
     * Constructor that initializes a crawler with String and CrawlResultList parameters.
     * @param keyword The keyword for which the crawler will search
     * @param list list of the results of crawling
     */
    public Crawler(String keyword, CrawlResultList list)
    {
    	this.linksVisited = new ArrayList<String>();
        this.kWord = keyword;
        this.maxDepth = 0;
        this.maxLinks = 0;
        
        this.rList = list;
        this.seq = 0;
        this.crawlingNow = false;
        this.stopCrawl = false;
    }

    
    //////////////////// Methods ////////////////////
    
    /**
     * Returns a Boolean value indicating if the crawler has visited the
     * specified web site.
     * @param address Address of the web site to test
     * @return True, if the crawler has visited the site; false, otherwise
     */
    public boolean hasVisitedLink(String address)
    {
        return this.linksVisited.contains(address);
    }

    /**
     * Adds the specified address to the list of pages the crawler has
     * already visited.
     * @param address Address of the page the crawler has visited
     */
    public void addVisitedLink(String address)
    {
        this.linksVisited.add(address);
    }
    
    /**
     * Method that retrieves the search term
     * @return the key word being searched for
     */
    
    public String getKeyWord() {
    	return this.kWord;
    }
    
    /**
     * Method that sets the search term
     * @param keyword the term to be searched for
     */
    
    public void setKeyWord(String keyword) {
    	this.kWord = keyword;
    }
    
    /**
     * Method that returns the current maximum page depth
     * @return integer indicating the current max depth
     */
    
    public int getMaxDepth() {
    	return this.maxDepth;
    }
    
    /**
     * Method that sets the maximum page depth
     * @param mDepth integer representing max depth
     */
    
    public void setMaxDepth(int mDepth) {
    	this.maxDepth = mDepth;
    }
    
    /**
     * Method that gets the current maximum number of
     * links to search through per page
     * @return integer indicating max number of links to look through
     */
    
    public int getMaxLinks() {
    	return this.maxLinks;
    }
    
    /**
     * Method to set maximum number of links to search through per page
     * @param mLinks integer representing max number of links to look through
     */
    
    public void setMaxLinks(int mLinks) {
    	this.maxLinks = mLinks;
    }
    
    /**
     * Abstract search method
     * @param address URL of site to search
     */
    
    abstract void search(String address);
    
    /**
     * Method to print a message saying the search term
     * was found on a particular page p
     * @param p page on which search term was found
     */
    
    protected void printMatch(Page p) {
    	System.out.println(this.kWord + " found on " + p.getAddress());
    }
    
    /**
     * Method to print a message indicating that
     * the page p is about to be visited
     * @param p page that is about to be visited
     */
    
    protected void printVisiting(Page p) {
    	System.out.println("About to visit " + p.getAddress());
    }
    
    // Part II Methods
    
    /**
     * Method to add a page to the list of crawled results, indicating
     * whether the page contained the search term or not
     * @param page page that was crawled
     * @param match true if search term found on page, else false
     */
    
    protected void addCrawledPage(Page page, Boolean match) {
    	
    	// creates new CrawlResult object with the appropriate
    	// traits, to be stored in the list of crawled results
    	
    	CrawlResult cResult = new CrawlResult(page, this.seq, match);
    	rList.add(cResult);
    }
    
    /**
     * Method to add a page which failed to download, along with
     * its appropriate error message 
     * @param page page that failed to download
     * @param error error message
     */
    
    protected void addFailedPage(Page page, String error) {
    	
    	// creates new CrawlResult object with the appropriate
    	// traits, to be stored in the list of crawled results
    	
    	CrawlResult cResult = new CrawlResult(page, this.seq, error);
    	rList.add(cResult);
    }
    
    /**
     * Method indicating whether or not the crawler is
     * currently crawling
     * @return true if currently crawling, else false
     */
    
    public boolean isCrawling(){
    	return this.crawlingNow;
    }
    
    /**
     * Method that sets whether the crawler is
     * crawling or not
     * @param setCrawl true if we want the crawler to crawl, else false
     */
    
    public void setCrawling(Boolean setCrawl){
    	this.crawlingNow = setCrawl;
    }
    
    /**
     * Method to indicate the crawler should stop crawling
     */
    
    public void stop(){
    	this.stopCrawl = true;
    }
    
    /**
     * Method to increment the order number of the pages
     * being crawled
     */
    
    protected void crawlingNextPage(){
    	this.seq++;	
    }
    
    
}
