package com.Flanaria;

import java.io.InputStream;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public interface IActionOnUser
{
	public void action(Status status, String text, long inReplyToStatusId, Twitter twitter, VerifiedUser user, InputStream img) throws TwitterException;
}