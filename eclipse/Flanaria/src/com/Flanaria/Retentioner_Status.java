package com.Flanaria;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;

public class Retentioner_Status
{
	private Retentioner_Status()
	{
	}
	private static final Retentioner_Status _ins = new Retentioner_Status();
	public static Retentioner_Status getInstance()
	{
		return _ins;
	}
	/*Singleton*/

	private HashMap<Long, List<Status>> _ConvMap;

	public void refresh()
	{
		_ConvMap = new HashMap<Long, List<Status>>();
	}

	public void onStatus(Status status)
	{
		if(StatusUtils.isThereInReplyTo(status))
		{
			Long InReplyTo = Long.valueOf(status.getInReplyToStatusId());
			if(_ConvMap.containsKey(InReplyTo))
			{
				List<Status> list = _ConvMap.get(InReplyTo);
				insert(list, status);
			}
			else
			{
				ArrayList<Status> list = new ArrayList<Status>(); list.clear();

				insert(list, status);
				_ConvMap.put(Long.valueOf(status.getId()), list);
				_ConvMap.put(Long.valueOf(InReplyTo), list);

				Status sta = null;
				while((sta = getInReplyToStatus(status)) != null)
				{
					insert(list, sta);
					_ConvMap.put(Long.valueOf(sta.getId()), list);
					status = sta;
				}
			}
		}
	}

	public ArrayList<Status> getContainsList(Status status)
	{
		onStatus(status);
		return (ArrayList<Status>)_ConvMap.get(Long.valueOf(status.getInReplyToStatusId()));
	}

	private void insert(List<Status> list, Status status)
	{
		if(list.isEmpty())
		{
			list.add(status);
			return;
		}
		if(list.contains(status))
		{
			return;
		}
		int i;
		Status _status = null;
		Date createdAt = status.getCreatedAt();
		Date _createdAt = null;
		int firstEqualsStatus = -1;
		for(i = list.size()-1; 0 <= i; i--)
		{
			_status = list.get(i);
			if(_status == null)
			{
				break;
			}
			_createdAt = _status.getCreatedAt();
			int var = _createdAt.compareTo(createdAt);
			if(var == 0)
			{
				if(firstEqualsStatus < 0)
				{
					firstEqualsStatus = i;
				}
				if(equalsStatusesNeighbor(_status, status))
				{
					return;
				}
			}
			if(var < 0)
			{
				break;
			}
		}
		if(firstEqualsStatus >= 0)
		{
			i = firstEqualsStatus;
		}
		if(i < 0)
		{
			i = 0;
		}
		list.add(i, status);
	}

	private static boolean equalsStatusesNeighbor(Status _status, Status status)
	{
		if(_status == null)
		{
			return status == null;
		}
		return 	status.getUser().getId() == _status.getUser().getId() &&
				status.getText().equals(_status.getText());
	}

	private Status getInReplyToStatus(Status status)
	{
		long id = status.getInReplyToStatusId();
		Status sta = null;
		Twitter twitter = Retentioner_Users.getInstance().getAPITwitter();
		try
		{
			sta = twitter.showStatus(id);
		}
		catch(Exception e)
		{
		}
		return sta;
	}
}
