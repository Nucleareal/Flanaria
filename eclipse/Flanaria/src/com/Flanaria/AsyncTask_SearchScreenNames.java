package com.Flanaria;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AsyncTask_SearchScreenNames extends AsyncTask<String, Integer, Long>
{
	private Activity _act;
	private IAsyncTaskParent _par;
	private List<String> _openNames;
	private String _msg;
	private ProgressDialog _diag;

	private final String API = "https://twitter.com/users/username_available?context=signup&custom=true&email=&full_name=&suggest=1&suggest_on_username=true&username=";
	private final String AC_CHAR = "abcdefghijklmnopqrstuvwxyz0123456789_";
	//			  0123456789          0123456789
	//                      0123456789          0123456789
	//61.23.86.178

	public AsyncTask_SearchScreenNames(Activity act)
	{
		_act = act;
		_par = (IAsyncTaskParent)_act;
	}

	@Override
	protected void onPreExecute()
	{
		_diag = new ProgressDialog(_act);
	    _diag.setTitle("Please wait");
	    _diag.setMessage("Reciving Users...");
	    _diag.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    _diag.setCancelable(false);
	    _diag.setMax((AC_CHAR.length()*AC_CHAR.length())/100+1);
	    _diag.setProgress(0);
	    _diag.show();
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_diag.dismiss();
		_par.onTaskCompleted(this);
	}
	
	@Override
	protected void onProgressUpdate(Integer... values)
	{
		_diag.setProgress(values[0]);
		if(values.length >= 2)
		{
			_diag.setMax(values[1]);
		}
	}
	
	String[] getPlusAccounterStr(String[] res)
	{
		String[] result = new String[res.length*AC_CHAR.length()];
		for(int i = 0; i < res.length; i++)
		{
			for(int j = 0; j < AC_CHAR.length(); j++)
			{
				result[i*AC_CHAR.length()+j] = res[i]+AC_CHAR.charAt(j);
			}
		}
		return result;
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_openNames = new LinkedList<String>();
		_msg = "";

		String[] scrnames = new String[]{""};
		scrnames = getPlusAccounterStr(scrnames); //@a   ~ @_
		scrnames = getPlusAccounterStr(scrnames); //@aa  ~ @__
		//scrnames = getPlusAccounterStr(scrnames); //@aaa ~ @___
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		List<String[]> list = ConnectUtils.splitArray(scrnames, 100, String.class);

		int cotet = 0;
		int resc = 0;

		for(String[] sar : list)
		{
			try
			{
				Twitter twitter = Retentioner_Users.getInstance().getAPITwitter();
				ResponseList<User> resl = twitter.lookupUsers(sar);
				cotet += resl.size();
				Logger.n("Size:"+resl.size());
				for(User u : resl)
				{
					try
					{
						map.put(u.getScreenName(), Boolean.valueOf(true));
					}
					catch(Exception e)
					{
						Logger.e(e);
					}
				}
			}
			catch(TwitterException e)
			{
				_msg = e.toString();
			}
			publishProgress(++resc);
		}

		double rate = (double)cotet / scrnames.length;
		rate *= 100.0;
		int cout = 0;

		Logger.n("Normal Response: "+rate+"%");
		
		publishProgress(0, scrnames.length);

		for(String s : scrnames)
		{
			Boolean b = map.get(s);
			if(b == null || !b.booleanValue())
			{
				boolean isEmpty = false;
				URL url;
				try
				{
					Logger.d("trying @"+s);
					
					url = new URL(API+s);
	
			        HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			        urlconn.setRequestMethod("GET");
			        urlconn.setInstanceFollowRedirects(false);
			        urlconn.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
	
			        urlconn.connect();
	
			        BufferedReader reader =
			            new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
	
			        String line;
			        while((line = reader.readLine()) != null)
			        {
			            if (line.contains("true"))
			            {
			            	isEmpty = true;
			            	break;
			            }
			        }
			        reader.close();
			        urlconn.disconnect();
				}
		        catch (Exception e)
				{
					Logger.e(e);
				}
				if(isEmpty)
					_openNames.add(s);
			}
			publishProgress(++cout);
		}

		return Long.valueOf(0L);
	}

	public List<String> getOpenedList()
	{
		return _openNames;
	}

	public String getMsg()
	{
		return _msg;
	}
}
