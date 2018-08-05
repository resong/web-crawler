package com.resong.crawler;

/**

 * Subclass of CrawlerWindow that establishes a GUI crawler window,
 * facilitating the search for a key term. Different GUI components
 * and the conditions for their actions are set. 
 * 
 * @author CS1027 & Rebecca Song
 *
 */

import javax.swing.UIManager;

import com.resong.crawler.resources.*;

public class MyCrawlerWindow extends CrawlerWindow {

	///////////////// Constructor ///////////////////
	
	/**
	 * Constructor that initializes its components
	 * using the .initComponents() method
	 */
	
    public MyCrawlerWindow() {
        this.initComponents();
    }

    //////////////// Methods ///////////////////
    
    /**
     * Method that sets actions for when search button is clicked.
     * If crawler is crawling, stop the crawl. Otherwise, if the
     * URL and search term fields are not empty, start crawling. If
     * they are empty, print an error message
     * @param evt method is called when search button is clicked
     */
    
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {

    	// if currently crawling, stop the crawler
    	
    	if(this.isCrawling() == true){
    		this.stopCrawler();
    	}
    	
    	
    	else{
    	
    		// else if either search term or URL fields or both are empty, print an error message
    		
    		if(((searchTermField.getText()).equals("") || (seedURLField.getText()).equals(""))){
    			this.showErrorMessage("Please fill out the URL and search term fields.");
    		}
    		
    		// if not, start crawling
    		
    		else{
    			this.startCrawler();
    		}
    	}
    }

    /**
     * Method that sets actions for when reset button is clicked.
     * Set the search term and URL fields to null, set the crawl 
     * type to breadth-first, and set the max depth and links to 3
     * @param evt method is called when reset button is clicked
     */
    
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {

    	// set text fields to null
    	
    	searchTermField.setText(null);
    	seedURLField.setText(null);
    	
    	// change crawl type to breadth first
    	
    	crawlTypeBFSOption.setSelected(true);
    	
    	// change max page depth to 3
    	
    	Integer maxDepth = new Integer(3);
    	maxDepthSpinner.setValue(maxDepth);
    	this.crawler.setMaxDepth(maxDepth);
    	
    	// change max number of links access per page to 3
    	
    	Integer maxLinks = new Integer(3);
    	maxLinksPerPageSpinner.setValue(maxLinks);
    	this.crawler.setMaxLinks(maxLinks);
    }

    
    /**
     * Method to start the crawler, change the search button to 
     * indicate 'stop' and inactivate other GUI components.
     * If breadth first search is selected, create a BreadthCrawler.
     * If depth first search is selected, create a DepthCrawler.
     * Set max page depth and max links accessed from the spinners
     */
    
    private void startCrawler() {

    	// clear previous results, change text on search button to stop
    	// and disable the other buttons on the GUI
    	
    	this.clearResults();
    	searchButton.setText("Stop");
    	this.setComponentsEnabled(false);
    	
    	// if BFS is selected, create a BreadthCrawler to use for searching
    	
    	if(crawlTypeBFSOption.isSelected()){
 
    		BreadthCrawler bug = new BreadthCrawler(searchTermField.getText(), this.getResultList());
    		this.crawler = bug;
    	}    
    	
    	// if DFS is selected, create a DepthCrawler to use for searching
    	
    	if(crawlTypeDFSOption.isSelected()){
    		 
    		DepthCrawler bug = new DepthCrawler(searchTermField.getText(), this.getResultList());
    		this.crawler = bug;
    	}    
    	
    	// set max depth and links accessed per page 
    	// to the value of the corresponding spinners
    	
    	int maxDepth = (Integer) maxDepthSpinner.getValue();
    	this.crawler.setMaxDepth(maxDepth);
    	
    	int maxLinks = (Integer) maxLinksPerPageSpinner.getValue();
    	this.crawler.setMaxLinks(maxLinks);
    	
    	// start crawling from the address entered into the seed URL field
    	
    	this.startCrawlerThread(seedURLField.getText());
    }

    /**
     * Method to stop the crawler if there is a Crawler object
     * and it is crawling, and to set the text on the search button
     * back to search and enable all the other GUI components
     */
    
    public void stopCrawler() {

    	// if the crawler isn't null and it is crawling
    	// indicate it should stop crawling, set its status
    	// so it's no longer crawling and stop the crawling 
    	
    	if(this.crawler != null && this.isCrawling() == true){
    		this.crawler.stop();
    		this.crawler.setCrawling(false);
    		this.stopCrawlerThread();    		
    	}
    	
    	// set text on search button to search
    	// and enable other GUI components
    	
    	searchButton.setText("Search");
    	this.setComponentsEnabled(true);
    }
    
    /**
     * Method to enable or disable all labels, text fields,
     * buttons (except search/stop), radio buttons and spinners
     * @param enabled true if components are enabled, else false
     */
    
    public void setComponentsEnabled(boolean enabled) {

    	// enable or disable labels
    	
    	searchTermLabel.setEnabled(enabled);
    	maxDepthLabel.setEnabled(enabled);
    	maxLinksPerPageLabel.setEnabled(enabled);
    	seedURLLabel.setEnabled(enabled);
    	
    	// enable or disable text fields
    	
    	searchTermField.setEnabled(enabled);
    	seedURLField.setEnabled(enabled);
    	
    	// enable or disable reset button
    	
    	resetButton.setEnabled(enabled);
    	
    	// enable or disable radio buttons
    	
    	crawlTypeBFSOption.setEnabled(enabled);
    	crawlTypeDFSOption.setEnabled(enabled);
    	
    	// enable or disable spinners
    	
    	maxLinksPerPageSpinner.setEnabled(enabled);
    	maxDepthSpinner.setEnabled(enabled);
    	
    }

    /**
     * Method to initialize components of GUI crawler window
     */
    
    private void initComponents() {

        searchButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });       

        resetButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Ignore the exception -- not fatal
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MyCrawlerWindow().setVisible(true);
            }
        });

    }
}
