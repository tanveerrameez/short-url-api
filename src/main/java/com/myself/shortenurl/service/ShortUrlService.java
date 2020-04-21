package com.myself.shortenurl.service;

import java.time.Duration;

import com.myself.shortenurl.exception.NoUrlMappingAvailableException;

public interface ShortUrlService {

	String generateShortUrl(String longURL);
	String getOriginalUrl(String shortUrl) throws NoUrlMappingAvailableException;
	public void setExpiryDuration(Duration expiryDuration);
}
