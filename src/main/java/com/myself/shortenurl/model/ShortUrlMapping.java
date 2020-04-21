package com.myself.shortenurl.model;

import java.time.LocalDateTime;
import java.util.List;

public class ShortUrlMapping {

	private String originalUrl;
	private LocalDateTime generatedDateTime;
	
	/**
	 * simple counter not taking account of unique requests
	 */
	private long invokedCounter=0;
	
	/**
	 * Stub for future storing metadata details,
	 * like browser, time/date of access, country of access etc
	 */
	private List<UrlMetaData> urlMetaData;
	
	public long getInvokedCounter() {
		return invokedCounter;
	}


	public ShortUrlMapping(String originalUrl, LocalDateTime generatedDateTime) {
		super();
		this.originalUrl = originalUrl;
		this.generatedDateTime = generatedDateTime;
		invokedCounter = 0;
	}
	
	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public LocalDateTime getGeneratedDateTime() {
		return generatedDateTime;
	}

	public void setGeneratedDateTime(LocalDateTime generatedDateTime) {
		this.generatedDateTime = generatedDateTime;
	}
	
	public void incrementInvokedCounter() {
		invokedCounter++;
	}
}
