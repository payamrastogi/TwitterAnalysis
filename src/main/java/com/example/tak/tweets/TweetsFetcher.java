package com.example.tak.tweets;

import twitter4j.TwitterException;


public class TweetsFetcher implements Runnable
{
	final TweetGet tweetGet;
	
	public TweetsFetcher()
	{
		tweetGet = new TweetGet();
	}

	public void run()
	{
		try 
		{
			tweetGet.streamTweets();
		} 
		catch (TwitterException e) 
		{
			e.printStackTrace();
		}
	}
}
