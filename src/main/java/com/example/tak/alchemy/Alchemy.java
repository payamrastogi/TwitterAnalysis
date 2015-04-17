package com.example.tak.alchemy;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import com.example.tak.tweets.TweetsData;
import com.google.gson.Gson;
import com.likethecolor.alchemy.api.Client;
import com.likethecolor.alchemy.api.call.AbstractCall;
import com.likethecolor.alchemy.api.call.SentimentCall;
import com.likethecolor.alchemy.api.call.type.CallTypeText;
import com.likethecolor.alchemy.api.entity.Response;
import com.likethecolor.alchemy.api.entity.SentimentAlchemyEntity;

public class Alchemy 
{
	final Gson gson;
	public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	public static double averageScore=0.0;
	public static int count=0;
	
	public Alchemy()
	{
		this.gson = new Gson();
	}
	public void fetchSentiments(String text)
	{
		String result = null;
		final String apiKey = "7e51f8247d558adc439f588695375e75f105a86c";
		final Client client = new Client(apiKey);
		System.out.println(text);
		TweetsData td = gson.fromJson(text, TweetsData.class);
		try
		{
			
			final AbstractCall<SentimentAlchemyEntity> sentimentCall = new SentimentCall(new CallTypeText(td.getTweet()));
			final Response<SentimentAlchemyEntity> sentimentResponse = client.call(sentimentCall);

		    SentimentAlchemyEntity entity;
		    final Iterator<SentimentAlchemyEntity> iter = sentimentResponse.iterator();
		    while(iter.hasNext()) 
		    {
		    	entity = iter.next();
		    	
		    	result = entity.getType().toString();
		    	td.setSentiment(result);
		    	//System.out.println(text+":"+ td.getSentiment());
		    	//System.out.println(gson.toJson(td));
		    	averageScore  = (averageScore*count + entity.getScore());
		    	count++;
		    	averageScore = averageScore/count;
		    	td.setAverageScore(averageScore+"");
		    	queue.add(gson.toJson(td));
		    }
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			td.setAverageScore(averageScore+"");
			td.setSentiment("LIMITEXCEEDED");
			queue.add(gson.toJson(td));
		}
	}
	
	public static void main(String args[])
	{
		//Alchemy a = new Alchemy();
	}
}



//System.out.println("Language: " + sentimentResponse.getLanguage());
//System.out.println("Status: " + sentimentResponse.getStatus());
//System.out.println("Status Info: " + sentimentResponse.getStatusInfo());
//System.out.println("Text: " + sentimentResponse.getText());
//System.out.println("Usage: " + sentimentResponse.getUsage());
//System.out.println("URL: " + sentimentResponse.getURL());
//while loop
//System.out.println("isMixed: " + (entity.isMixed() ? "true" : "false"));
//System.out.println("Score: " + entity.getScore());
//System.out.println("Type: " + entity.getType());
