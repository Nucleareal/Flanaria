package com.Flanaria;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.LinearLayout;

public class AsyncTask_Search extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private LinearLayout _head;
	private AsyncTask_ProgressOnListView _task;
	private String _text;
	private IAsyncTaskParent _parent;
	private List<twitter4j.Status> _list;
	
	public AsyncTask_Search(Context context, String text, LinearLayout header)
	{
		_context = context;
		_head = header;
		_text = text;
		_parent = (IAsyncTaskParent)_context;
	}

	@Override
	protected void onPreExecute()
	{
		_task = new AsyncTask_ProgressOnListView(_context, _head);
		_task.execute("");
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_task.stop();
		_parent.onTaskCompleted(this);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		Logger.d("Searching ["+_text+"] ...");
		
		Twitter twitter = Retentioner_Users.getInstance().getAPITwitter();
		_list = new ArrayList<twitter4j.Status>();
		try
		{
            QueryResult result;
            for(int i = 0; i < 1; i++)
            {
                result = twitter.search(new Query(_text.toString()).count(100));
                if( result.getTweets().size() == 0 )
                {
                    break;
                }
                for(twitter4j.Status tweet : result.getTweets())
                {
                    _list.add(tweet);
                }
            }
		}
		catch(TwitterException e)
		{
			Logger.e(e);
		}
		return Long.valueOf(0);
	}
	
	public List<twitter4j.Status> getResult()
	{
		return _list;
	}
}
