package com.myself.shortenurl.utils;

import java.net.URL;
import java.util.Random;

public class ShortUrlUtility {

	private static ShortUrlUtility shortUrlUtilty;
	
	//range of alphabets and characters to generate the random shortUrl
	private String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?";
	
	private ShortUrlUtility()  throws IllegalAccessException{
		if(shortUrlUtilty!=null)
			throw new IllegalAccessException("Cannot instantiate instance of ShortUrlUtility. Please use getInstance");

	}
	
	public static ShortUrlUtility getInstance()
	{
		try {
			if(shortUrlUtilty==null)
				shortUrlUtilty=new ShortUrlUtility();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		return shortUrlUtilty;
	}
	
	     // sanitizeURL
		// This method should take care various issues with a valid url
		// e.g. www.google.com,www.google.com/, http://www.google.com,
		// http://www.google.com/
		// all the above URL should point to same shortened URL
		// There could be several other cases like these.
		public String sanitizeURL(String url) {
			if (url.substring(0, 7).equals("http://"))
				url = url.substring(7);
			else if (url.substring(0, 8).equals("https://"))
				url = url.substring(8);

			if (url.charAt(url.length() - 1) == '/')
				url = url.substring(0, url.length() - 1);
			return url;
		}

		// Validate URL
		// not implemented, but should be implemented to check whether the given URL
		// is valid or not
		public boolean validateURL(String url) {
			/* Try creating a valid URL */
	        try { 
	            new URL(url).toURI(); 
	            return true; 
	        } 
	          
	        // If there was an Exception 
	        // while creating URL object 
	        catch (Exception e) { 
	            return false; 
	        } 
		}
		
		/**
		 * this is a incomplete method
		 * The actual implementation should generate the short Url based on 
		 * original Url and current timestamp so that its unique
		 * the current implementation create a url with randomly generated characters
		 * However its uniqueness is not guaranteed as per current implementation
		 * 
		 * @param originalUrl
		 * @return
		 */
		public String generateShortUrl(String originalUrl) 
		{ 
			originalUrl = sanitizeURL(originalUrl);
			StringBuilder shortUrlBuilder=new StringBuilder();
			for(int i=0;i<10;i++) {
			Random rnd = new Random();
	        char character = alphabet.charAt(rnd.nextInt(alphabet.length()));
	        shortUrlBuilder.append(character);
			}
			return shortUrlBuilder.toString();
		} 
		
		
	   
	
}
