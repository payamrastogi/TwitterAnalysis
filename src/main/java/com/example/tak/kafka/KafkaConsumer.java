package com.example.tak.kafka;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.example.tak.alchemy.Alchemy;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
 
 
/**
 * Created by user on 8/4/14.
 */
public class KafkaConsumer extends  Thread {
    final static String clientId = "SimpleConsumerDemoClient";
    final String topic;
    final Map<String, Integer> topicCountMap;
    final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap;
    final ConsumerConnector consumerConnector;
    final KafkaStream<byte[], byte[]> stream;
    ByteArrayInputStream bis;
    ObjectInputStream ois;
    Alchemy alchemy;
    
    
    public KafkaConsumer(String topic)
    {
    	this.topic = topic;
        Properties properties = new Properties();
        properties.put("zookeeper.connect","localhost:2181");
        properties.put("group.id","test-group");
        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        topicCountMap = new HashMap<String, Integer>();
    	topicCountMap.put(this.topic, new Integer(1));
    	consumerMap = consumerConnector.createMessageStreams(topicCountMap);
    	stream = consumerMap.get(this.topic).get(0);
    	alchemy = new Alchemy();
    }
 
    @Override
    public void run() 
    {
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while(it.hasNext())	
        {
        	//System.out.println("consumer:"+" "+new String(it.next().message()));
        	alchemy.fetchSentiments(new String(it.next().message()));
        }
    }
    
    public static void main(String[] argv) throws UnsupportedEncodingException {
    	KafkaConsumer helloKafkaConsumer = new KafkaConsumer("KafkaTest");
        helloKafkaConsumer.start();
    }
}