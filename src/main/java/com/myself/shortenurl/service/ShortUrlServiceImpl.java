/**
 * 
 */
package com.myself.shortenurl.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myself.shortenurl.exception.NoUrlMappingAvailableException;
import com.myself.shortenurl.model.ShortUrlMapping;
import com.myself.shortenurl.persistence.ShortUrlMapper;
import com.myself.shortenurl.utils.ShortUrlUtility;

/**
 * @author tanveer
 *
 */

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    //assuming the url expires in 60 minutes
	private Duration expiryDuration=Duration.ofMinutes(60);
	
	public Duration getExpiryDuration() {
		return expiryDuration;
	}

	public void setExpiryDuration(Duration expiryDuration) {
		this.expiryDuration = expiryDuration;
	}

	@Autowired
	private ShortUrlMapper shortUrlMapper;
	
	/* (non-Javadoc)
	 * @see com.myself.shortenurl.service.ShortUrlService#generateShortUrl(java.lang.String)
	 */
	@Override
	public String generateShortUrl(String originalUrl) {
		
		ShortUrlMapping mapping = new ShortUrlMapping(originalUrl, LocalDateTime.now());
		/**
		 * The shortenURL method below should generate a unique short url
		 */
		String shortUrl = ShortUrlUtility.getInstance().generateShortUrl(originalUrl);
		shortUrlMapper.saveUrlMapping(shortUrl, mapping);
		return shortUrl;
	}

	/* (non-Javadoc)
	 * @see com.myself.shortenurl.service.ShortUrlService#getOriginalUrl(java.lang.String)
	 */
	@Override
	public String getOriginalUrl(String shortUrl) throws NoUrlMappingAvailableException {
		
		shortUrl = ShortUrlUtility.getInstance().sanitizeURL(shortUrl);
		Optional<ShortUrlMapping> shortUrlMappingOptional = shortUrlMapper.getOriginalUrl(shortUrl);
		if(shortUrlMappingOptional.isPresent()) {
			ShortUrlMapping shortUrlMapping = shortUrlMappingOptional.get();
			
			Duration duration =  Duration.between(shortUrlMapping.getGeneratedDateTime(),LocalDateTime.now());
		    if(expiryDuration.compareTo(duration)==1)
		    	return shortUrlMapping.getOriginalUrl();
		    else {
		    	shortUrlMapper.removeExpiredUrl(shortUrl);
		    	throw new NoUrlMappingAvailableException(shortUrl);
		    }
		}
		else throw new NoUrlMappingAvailableException(shortUrl);
	}
	

}
;