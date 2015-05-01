package com.example.tak.tweets;

import com.example.tak.kafka.KafkaProducer;
import com.example.tak.tweets.TweetsData;
import com.google.gson.Gson;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public final class TweetGet 
{
	final KafkaProducer kafkaProducer;
	final Gson gson;
	
	public TweetGet()
	{
		this.kafkaProducer = new KafkaProducer("tweets");
		this.gson = new Gson();
	}
	
    public void streamTweets()throws TwitterException 
    {
    	//just fill this
    	 ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true)
           .setOAuthConsumerKey("T9pnFkuq0GTNav4ByYNPZIsNx")
           .setOAuthConsumerSecret("dthVi84DZKzIUnxkhfIAlgmHIA7EAKyWbSCK5C0yfE0s0Jkkum")
           .setOAuthAccessToken("39455623-CykQ5gFjdW4eTLVrCCFOQTFA6JTRRK6Bwi1ioXugJ")
           .setOAuthAccessTokenSecret("XR9XcwCWSHyrozLBZA2g2VJwghcw2Hl5JOuiReP4wvuoi");
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        StatusListener listener = new StatusListener() 
        {
            public void onStatus(Status status) 
            {
            	if(status.getGeoLocation()!=null)
            	{
            		TweetsData td = new TweetsData();
            		//System.out.println(status.getText().replaceAll("[^a-zA-Z0-9.,:!@#/_\\-\\s]", ""));
            		td.setLatitude(status.getGeoLocation().getLatitude());
            		td.setLongitude(status.getGeoLocation().getLongitude());
            		td.setCreatedAt(new java.sql.Timestamp(status.getCreatedAt().getTime()));
            		td.setTweet(status.getText().replaceAll("[^a-zA-Z0-9.,:!@#/_\\-\\s]", ""));
            		//System.out.println(td.getTweet());
					kafkaProducer.sendMessage(gson.toJson(td));
            	}
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) 
            {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) 
            {
               // System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId)
            {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onStallWarning(StallWarning warning) 
            {
                //System.out.println("Got stall warning:" + warning);
            }

            public void onException(Exception ex) 
            {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.sample();
    }
    
    public static void main(String args[]) throws TwitterException
    {
    	TweetGet tg = new TweetGet();
    	tg.streamTweets();
    }
}