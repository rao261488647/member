package com.frame.member.widget.calendar;


import android.graphics.Point;

public class ClickPoint {
	public Point point;
	public CustomDate date;
	
	@Override
	public boolean equals(Object o) {
		return ((ClickPoint)o).point.equals(this.point) && ((ClickPoint)o).date.equals(this.date) ;
	}
}
