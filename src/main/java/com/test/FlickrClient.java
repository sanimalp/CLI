package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;

//assume there is not already a flickr client in the whole world
public class FlickrClient {
		
	HttpTransport HTTP_TRANSPORT;
	
	public FlickrClient(HttpTransport httpTransport){
		this.HTTP_TRANSPORT = httpTransport;
	}
	
	public String getFeedWithTag(String tag) throws IOException {
		HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(constructUrl(tag));
        HttpResponse response = request.execute();
    
        //woohoo java 8!
		return new BufferedReader(new InputStreamReader(response.getContent()))
				  .lines().collect(Collectors.joining("\n"));
	}

	private GenericUrl constructUrl(String tag) {
		GenericUrl url = new GenericUrl("https://api.flickr.com/services/feeds/photos_public.gne");
		url.set("tags", tag);
		url.set("format", "json");
		url.set("tagmode", "any");
		return url;
	}
}
