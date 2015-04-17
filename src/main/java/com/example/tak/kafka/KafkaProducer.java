package com.example.tak.kafka;

import java.util.Properties;

import com.example.tak.tweets.TweetsData;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer 
{
	public final String topic;
	public final ProducerConfig producerConfig;
	public final Properties properties;
	public kafka.javaapi.producer.Producer<String,String> producer;
	
	public KafkaProducer(String topic)
	{
		this.topic = topic;
		properties = new Properties();
		properties.put("metadata.broker.list","localhost:9092");
	    properties.put("serializer.class","kafka.serializer.StringEncoder");
		this.producerConfig = new ProducerConfig(properties);
		producer = new kafka.javaapi.producer.Producer<String, String>(producerConfig);
	}
	
	public void sendMessage(String msg)
	{
		//System.out.println("sending message");
		KeyedMessage<String, String> message =new KeyedMessage<String, String>(this.topic, msg);
	    producer.send(message);
	}
	
	
	public void close()
	{
		producer.close();
	}
	public static void main(String args[])
	{
		TweetsData td = new TweetsData();
		td.setTweet("happy birthday to you");
	    KafkaProducer kafkaProducer = new KafkaProducer("KafkaTest");
	    kafkaProducer.sendMessage(td.getTweet());
	}
}
