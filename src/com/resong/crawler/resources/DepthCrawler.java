package com.resong.crawler.resources;
/**
 * Subclass of the Crawler class that creates a web crawler, searching for a term using the
 * depth first approach. Takes a keyword to search for and a list to
 * store the crawl results in, and has a search method.
 * 
 * @author Rebecca Song
 */

import com.resong.crawler.structures.stack.*;

import java.util.Iterator;


public class DepthCrawler extends Crawler {
	
	/////////////// Attributes ////////////////
	
	private LinkedStack<Page> pagesToVisit;	// stack of pages to visit
	
	
	////////////// Constructors ////////////////
	
	/**
	 * Constructor that creates a depth first crawler taking
	 * a search term and a list to store results in.
	 * @param keyword search term
	 * @param list list that stores results of each page crawled
	 */
	public DepthCrawler(String keyword, CrawlResultList list) {
		super(keyword, list);
		this.pagesToVisit = new LinkedStack<Page>();
	}
	
	
	////////////// Methods ///////////////
	
	/**
	 * Search method looks for the search term starting
	 * at the String address while it has not been requested
	 * that the crawl be stopped.
	 * @param address URL of page to start searching from 
	 */
	
	public void search(String address) {
		
		int linksPushed = 0; // no links pushed onto stack
		super.setCrawling(true); // set to indicate crawler is crawling
		
		// create page object for starting URL
		// and push it onto the stack
		
		Page startPage = new Page(address);
		this.pagesToVisit.push(startPage);
		linksPushed++;
		
		// while there are still pages to visit 
		// and it hasn't been requested to stop crawling
		
		while(this.pagesToVisit.size() != 0 && this.stopCrawl == false)
		{			
			
			// pop the next page from the pagesToVisit stack and 
			// print a message indicating it's about to be visited
			
			Page page = this.pagesToVisit.pop();
			linksPushed--;
			this.printVisiting(page);
			
			try {
				
				// increment the order of the page about to be visited,
				// download the page and add its address to the visited links
				
				this.crawlingNextPage();
				WebHelper.downloadPage(page);
				this.addVisitedLink(page.getAddress());
				
				boolean containsText = page.containsText(this.getKeyWord()); // true if page contains search term, false otherwise
			
				
				// if it does contain the search term, print a message
				// indicating a match has been found
				
				if(containsText == true)
				{
					this.printMatch(page);
				}
			
				super.addCrawledPage(page, containsText); // add page and its search outcome to crawl results
				
				
				// if current page depth is less than maximum page depth
				
				if(page.getDepth() < this.getMaxDepth())
				{		
					Iterator<Page> it = page.linkedPageIterator();
					
					// while there are links to iterate over, and the number of 
					// links added don't exceed the maximum number of links set
					
					while(it.hasNext() == true && linksPushed < this.getMaxLinks())
					{	
						Page link = it.next(); // assign the page object link to the next link on the current page
								
						boolean visited = this.hasVisitedLink(link.getAddress()); // true if link has been visited, else false				
						boolean check = this.check(link); // true if link is already stored in pagesToVisit stack, else false
						
						// if it hasn't been visited already, and it's not
						// stored in pagesToVisit stack, add it to the stack
						
						if( visited == false && check == false)
						{
							this.pagesToVisit.push(link);
							linksPushed++;
						}
					}
				}
			}
			
			catch (Exception ex){
				
				// prints out error message and adds the failed page to crawl results
				
				String error = "An error occurred while accessing " + page.getAddress();
				super.addFailedPage(page, error);
			}
		}
		
		super.setCrawling(false); // set so crawler is no longer crawling
	}
	
	
	/**
	 * Private method to check if the page object link
	 * is already in the stack or not.
	 * @param link page currently being crawled
	 * @return true if link is already in stack, else false
	 */
	
	private boolean check(Page link){
		
		boolean check = false; // initialize check to false
		
		Page p; // create page p reference variable
		LinkedStack<Page> temp = new LinkedStack<Page>(); // create temporary stack
		
		// while check is false
		
		while(check = false){	
			
			// if pagesToVisit stack is not empty
			
			if(!this.pagesToVisit.isEmpty()){
				
				// pop the pagesToVisit stack
				// and push it onto the temp stack
				
				p = this.pagesToVisit.pop();
				temp.push(p);
							
				// if address of recently pushed page is the same
				// as address of parameter link, assign true to check
				
				if(p.getAddress().equals(link.getAddress())){
					check = true;
				}
			}	
			
			// else assign temp stack to pagesToVisit stack
			
			else{
				/*while(!temp.isEmpty()){
					this.pagesToVisit.push(temp.pop());
				}*/
				this.pagesToVisit = temp;
			}
		}
		
		return check; // return check
		
	}

}
