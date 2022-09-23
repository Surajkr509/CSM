package com.nixi.bean;

public enum FeedbackEnum {
	
	Complaints,Suggestions,Question;
	
	private String feedbackType;
	
	private FeedbackEnum( ) {
		
	}


	private FeedbackEnum(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	
	

}
