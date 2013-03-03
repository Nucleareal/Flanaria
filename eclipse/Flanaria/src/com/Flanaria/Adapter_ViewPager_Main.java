package com.Flanaria;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Adapter_ViewPager_Main extends PagerAdapter implements IAsyncTaskParent
{
	private int[] pages = { R.layout.pg_mention, R.layout.pg_timeline, R.layout.pg_msg };
	private LinearLayout[] views;
	private ListView[] _lvs;
	private Adapter_TwitterElement[] _adaps;
	private LayoutInflater _inf;
	private Context _con;
	private LinearLayout[] _head;
	private AsyncTask_ProgressOnListView[] _ptasks;
	
	public Adapter_ViewPager_Main(Context context)
	{
		_con = context;
		_inf = ((Activity)_con).getLayoutInflater();
		views = new LinearLayout[getCount()];
		_lvs = new ListView[getCount()];
		_adaps = new Adapter_TwitterElement[getCount()];
		_head = new LinearLayout[getCount()];
		_ptasks = new AsyncTask_ProgressOnListView[getCount()];
		for(int i = 0; i < getCount(); i++)
		{
			views[i] = (LinearLayout)_inf.inflate(pages[i], null);
			_head[i] = new LinearLayout(_con);
		}
		AsyncTask_RefreshFirst_Main task = new AsyncTask_RefreshFirst_Main(_con, this);
		task.execute("");
	}
	
	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		if(task instanceof AsyncTask_RefreshFirst_Main)
		{
			AsyncTask_LoadUsers task1 = new AsyncTask_LoadUsers(_con, this);
			task1.execute("");
			Logger.d("Refresh Completed!");
		}
		if(task instanceof AsyncTask_LoadUsers)
		{
			for(int i = 0; i < getCount(); i++)
			{
				_lvs[i] = (ListView)views[i].findViewById(R.id.TwitterListView);
				_lvs[i].addHeaderView(_head[i]);
				_adaps[i] = new Adapter_TwitterElement(_con, R.layout.tweet, new LinkedList<TwitterElement>());
				_lvs[i].setAdapter(_adaps[i]);
			}
			
			for(int i = 0; i < getCount(); i++)
			{
				_ptasks[i] = new AsyncTask_ProgressOnListView(_con, _head[i]);
				_ptasks[i].execute("");
			}
			
			AsyncTask_LoadStatuses task1 = new AsyncTask_LoadStatuses(this, 1);
			task1.execute("");
			Logger.d("Load Users Completed!");
		}
		if(task instanceof AsyncTask_LoadStatuses)
		{
			for(int i = 0; i < getCount(); i++)
			{
				_ptasks[i].stop();
			}
			
			AsyncTask_LoadStatuses tas = (AsyncTask_LoadStatuses)task;
			for(int i = 0; i < getCount(); i++)
			{
				List<TwitterElement> list = tas.getResult().get(i);
				for(TwitterElement e : list)
				{
					Utils_TwitterElement.insert(_adaps[i], e);
				}
			}
			Logger.d("Load Statuses Completed!");
		}
	}
	
	@Override
    public int getCount()
	{
        return 3;
    }

    /**
     * ページを生成する
     * position番目のViewを生成し返却するために利用
     * @param container: 表示するViewのコンテナ
     * @param position : インスタンス生成位置
     * @return ページを格納しているコンテナを返却すること。サンプルのようにViewである必要は無い。
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
    	((ViewPager)container).addView(views[position]); //setContentView
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager)container).removeView(views[position]);
    }

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg1.equals(arg0);
	}
}
