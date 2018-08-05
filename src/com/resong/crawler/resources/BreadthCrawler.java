package com.resong.crawler.resources;

/**
 * Subclass of the Crawler class that creates a web crawler, searching for a term using the
 * breadth first approach. Takes a keyword to search for and a list to
 * store the crawl results in, and has a search method.
 * 
 * @author Rebecca Song
 */

import java.util.Iterator;
import com.resong.crawler.structures.queue.*;

public class BreadthCrawler extends Crawler {
	
	/////////////// Attributes ////////////////
	
	private CircularArrayQueue<Page> pagesToVisit;	// queue of pages to visit
	
	
	////////////// Constructors ////////////////
	
	/**
	 * Constructor that creates a breadth first crawler taking
	 * a search term and a list to store results in.
	 * @param keyword search term
	 * @param list list that stores results of each page crawled
	 */
	
	public BreadthCrawler(String keyword, CrawlResultList list) {
		super(keyword, list);
		this.pagesToVisit = new CircularArrayQueue<Page>();
	}
	
	
	//////////////Methods ///////////////
	
	/**
	 * Search method looks for the search term starting
	 * at the String address while it has not been requested
	 * that the crawl be stopped.
	 * @param address URL of page to start searching from 
	 */
	
	public void search(String address) {
		
		int linksEnqueued = 0; // no links enqueued 
		
		super.setCrawling(true); // set to indicate crawler is crawling
		
		
		// create page object for starting URL
		// and enqueue it to the queue
		
		Page startPage = new Page(address);
		this.pagesToVisit.enqueue(startPage);
		linksEnqueued++;
		
		// while there are still pages to visit 
		// and it hasn't been requested to stop crawling
		
		while(this.pagesToVisit.size() != 0 && this.stopCrawl == false)
		{
			
			// dequeue the next page from the pagesToVisit queue 
			// and print a message indicating it's about to be visited
			
			Page page = this.pagesToVisit.dequeue();
			linksEnqueued--;
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
					Iterator<Page> it = page.linkedPageIterator(); // create an iterator
					
					// while there are links to iterate over, and the links added
					// don't exceed the maximum number of links set
					
					while(it.hasNext() == true && linksEnqueued < this.getMaxLinks())
					{	
						Page link = it.next(); // assign the page object link to the next link on the current page
						
						
						boolean visited = this.hasVisitedLink(link.getAddress()); // true if link has been visited, else false				
						boolean check = this.check(link); // true if link is already stored in pagesToVisit queue, else false
						
						// if it hasn't been visited already, and it's not
						// stored in pagesToVisit queue, add it to the queue
						
						if( visited == false && check == false)
						{
							this.pagesToVisit.enqueue(link);
							linksEnqueued++;
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
	 * is already in the queue or not.
	 * @param link page currently being crawled
	 * @return true if link is already in queue, else false
	 */
	
	private boolean check(Page link){
		
		boolean check = false; // initialize check to false
		
		Page p; // create page p reference variable
		CircularArrayQueue<Page> temp = new CircularArrayQueue<Page>(); // create temporary queue
		
		// while check is false
		
		while(check = false){	
			
			// if pagesToVisit queue is not empty
			
			if(!this.pagesToVisit.isEmpty()){
				
				// dequeue the pagesToVisit queue
				// and enqueue the temp queue
				
				p = this.pagesToVisit.dequeue();
				temp.enqueue(p);
								
				// if address of recently enqueued page is the same
				// as address of parameter link, assign true to check
				
				if(p.getAddress().equals(link.getAddress())){
					check = true;
				}
			}			
			
			// else assign temp queue to pagesToVisit queue
			
			else{
				this.pagesToVisit = temp;
			}
		}
		
		return check; // return check
	}
	
	
}