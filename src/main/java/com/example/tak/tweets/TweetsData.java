package com.example.tak.tweets;

public class TweetsData 
{
	private String keyword;
	private double latitude;
	private double longitude;
	private java.sql.Timestamp createdAt;
	private String tweet;
	private String sentiment;
	private String averageScore;
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public String getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
	}
}
