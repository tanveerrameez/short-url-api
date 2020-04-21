URL Shortening Application

The application returns a short version of a link when a link is registered (to be shortened). When the shortened URL is sent to the application, the application redirects the request
to the original URL.
If the URL is registered by a registered user, the user can track these URLs and view details pertaining to it, as well as making certain amends to the URL data.
Requirements:
1. The application maintains record of the original URL and its shortened version, so that when the shortened version is used, the request can be redirected to the original URL. The
short URL generated should be unique based on an algorithm that take the original URL, timestamp etc into factor.
2. The URL mapping is persisted
3. Link Management:
• Record how many times the shortened URL has been invoked
• Record should be made how many unique requests made for the shortened URL
• Record should be made of the origin of the request - country, browser, device (tablet, phone, PC), operating system
• If link is registered by a registered user, the user account should record all the shortened URL registered by that user.
• Registered user needs to have dashboard to view the data pertaining to their registered URLs, including graphs and other illustrations, for example usage, country of origin
and other attributes viewable by hour, day, month etc. Certain amends to the URL details should be possible
Challenge: Determining the data that the user will find useful, and the workflow to process that data and provide in a medium which gives a clear picture to the user of how his online
business is being driven.

Instrucitons on how to test using postman or web browser: 
1. Start in spring boot

2. GET method to generate short Url:
http://localhost:8080/shortenurl?url=http://www.google.com

This return a url for example: http://localhost:8080/shortenurl/PdYn9AQFEI

3. access url: http://localhost:8080/shortenurl/PdYn9AQFEI  and you will be redirect to the original url : http://www.google.com



