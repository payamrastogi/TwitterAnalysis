package com.example.tak.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import twitter4j.TwitterException;

import com.example.tak.kafka.KafkaConsumer;
import com.example.tak.tweets.TweetsFetcher;

public class Main 
{
	public static void main(String args[]) throws TwitterException
	{
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new TweetsFetcher());
		KafkaConsumer kafkaConsumer = new KafkaConsumer("tweets");
		kafkaConsumer.start();
	}
}
