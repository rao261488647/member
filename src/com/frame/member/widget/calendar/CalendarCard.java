package com.frame.member.widget.calendar;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * 自定义日历卡
 * 
 * @author wuwenjie
 * 
 */
public class CalendarCard extends View {

	private static final int TOTAL_COL = 7; // 7列
	private static final int TOTAL_ROW = 6; // 6行

	private Paint mCirclePaint; // 绘制圆形的画笔
	private Paint mPointPaint; // 绘制圆点的画笔
	private Paint mTextPaint; // 绘制文本的画笔
	private int mViewWidth; // 视图的宽度
	private int mViewHeight; // 视图的高度
	private int mCellHorSpace; // 单元格间距
	private int mCellVerSpace; // 单元格间距
	private Row rows[] = new Row[TOTAL_ROW]; // 行数组，每个元素代表一行
	private static CustomDate mShowDate; // 自定义的日期，包括year,month,day
	private OnCellClickListener mCellClickListener; // 单元格点击回调事件
	private int touchSlop; //
	private boolean callBackCellSpace;

	private Cell mClickCell;
	private float mDownX;
	private float mDownY;
	//已经被选中的
	
	//已选中的日期数量
//	private int num_selected = 0;
	
	public static final int FLAG_DATE_FULL = 0x00;

	/**
	 * 单元格点击的回调接口
	 * 
	 * @author wuwenjie
	 * 
	 */
	public interface OnCellClickListener {
		void clickDate(CustomDate date,boolean isContain); // 回调点击的日期

		void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
		
		void attempAddDateFailed(int flag);
	}

	public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public CalendarCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalendarCard(Context context) {
		super(context);
		init(context);
	}
	private Set<ClickPoint> set_point;
	public CalendarCard(Context context, OnCellClickListener listener,Set<ClickPoint> set_point) {
		super(context);
		this.mCellClickListener = listener;
		this.set_point = set_point;
		init(context);
	}

	private void init(Context context) {
		
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(Color.parseColor("#E7E4E4")); // 灰色圆形
		mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPointPaint.setStyle(Paint.Style.FILL);
		mPointPaint.setColor(Color.parseColor("#f34e4e")); // 红色圆点
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		initDate();
	}

	private void initDate() {
		mShowDate = new CustomDate();
		fillDate();
	}

	private void fillDate() {
		
		int monthDay = DateUtil.getCurrentMonthDay(); // 今天
		int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
				mShowDate.month - 1); // 上个月的天数
		int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
				mShowDate.month); // 当前月的天数
		int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
				mShowDate.month);
		boolean isCurrentMonth = false;
		
		if (DateUtil.isCurrentMonth(mShowDate)) {
			isCurrentMonth = true;
		}
		int day = 0;
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL; // 单元格位置
				// 这个月的
				if (position >= firstDayWeek
						&& position < firstDayWeek + currentMonthDays) {
					day++;
					rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
							mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
					
					// 今天
					if (isCurrentMonth && day == monthDay ) {
						CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
						rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
					}

					if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到
						rows[j].cells[i] = new Cell(
								CustomDate.modifiDayForObject(mShowDate, day),
								State.UNREACH_DAY, i, j);
					}
					if (isCurrentMonth && day < monthDay) { // 如果比这个月的今天要小，表示已经过了
						rows[j].cells[i] = new Cell(
								CustomDate.modifiDayForObject(mShowDate, day),
								State.REACHED_DAY, i, j);
						rows[j].cells[i].isClickable = false;
					}
					if(mShowDate.month<DateUtil.getMonth()){  //比今天的月份小的月份
						rows[j].cells[i] = new Cell(
								CustomDate.modifiDayForObject(mShowDate, day),
								State.REACHED_DAY, i, j);
						rows[j].cells[i].isClickable = false;
					}
					for(ClickPoint point:set_point){
						CustomDate customDate = new CustomDate(mShowDate.year, mShowDate.month, 
								point.point.y*TOTAL_COL+point.point.x-DateUtil.getWeekDayFromDate(mShowDate.year, mShowDate.month)+1);
						if(!point.date.equals(customDate))
							continue;
						rows[point.point.y].cells[point.point.x] = new Cell(CustomDate.modifiDayForObject(customDate
								,
								point.point.y*TOTAL_COL+point.point.x-DateUtil.getWeekDayFromDate(mShowDate.year, mShowDate.month)+1), 
								State.SELECTED_DAY, point.point.x, point.point.y);
					}

					// 过去一个月
				} else if (position < firstDayWeek) {
					rows[j].cells[i] = new Cell(new CustomDate(mShowDate.year,
							mShowDate.month - 1, lastMonthDays
									- (firstDayWeek - position - 1)),
							State.PAST_MONTH_DAY, i, j);
					rows[j].cells[i].isClickable = false;
					// 下个月
				} else if (position >= firstDayWeek + currentMonthDays) {
					rows[j].cells[i] = new Cell((new CustomDate(mShowDate.year,
							mShowDate.month + 1, position - firstDayWeek
									- currentMonthDays + 1)),
							State.NEXT_MONTH_DAY, i, j);
					rows[j].cells[i].isClickable = false;
				}
				
			}
		}
		mCellClickListener.changeDate(mShowDate);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < TOTAL_ROW; i++) {
			if (rows[i] != null) {
				rows[i].drawCells(canvas);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewWidth = w;
		mViewHeight = h;
		mCellHorSpace = mViewWidth / TOTAL_COL;
		mCellVerSpace = mViewHeight / TOTAL_ROW;
//		mCellSpace = Math.min(mViewHeight / TOTAL_ROW, mViewWidth / TOTAL_COL);
		if (!callBackCellSpace) {
			callBackCellSpace = true;
		}
		mTextPaint.setTextSize(mCellHorSpace / 3);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX();
			mDownY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			float disX = event.getX() - mDownX;
			float disY = event.getY() - mDownY;
			if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
				int col = (int) (mDownX / mCellHorSpace);
				int row = (int) (mDownY / mCellVerSpace);
				if(rows[row].cells[col].isClickable){
					measureClickCell(col, row);
				}
				
			}
			break;
		}

		return true;
	}

	/**
	 * 计算点击的单元格
	 * @param col
	 * @param row
	 */
	private void measureClickCell(int col, int row) {
		if (col >= TOTAL_COL || row >= TOTAL_ROW)
			return;
		if (mClickCell != null) {
			rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
		}
		if (rows[row] != null) {
			mClickCell = new Cell(rows[row].cells[col].date,
					rows[row].cells[col].state, rows[row].cells[col].i,
					rows[row].cells[col].j);

			CustomDate date = rows[row].cells[col].date;
			date.week = col;
			
			boolean isContain = false;
			Point mPoint = new Point(col,row);
			ClickPoint clickPoint = new ClickPoint();
			clickPoint.point = mPoint;
			clickPoint.date = date;
			//设置点击的位置
			for(ClickPoint point:set_point){
				if(clickPoint.equals(point)){
					isContain = true;
					set_point.remove(point);
					break;
				}
			}
			if(!isContain){
				if(set_point.size() < 6){
//					set_point.add(new Point(col, row));
					set_point.add(clickPoint);
				}else
					mCellClickListener.attempAddDateFailed(FLAG_DATE_FULL);
				
			}
			mCellClickListener.clickDate(date,isContain);
			// 刷新界面	
			update();
		}
	}

	/**
	 * 组元素
	 * 
	 * @author wuwenjie
	 * 
	 */
	class Row {
		public int j;

		Row(int j) {
			this.j = j;
		}

		public Cell[] cells = new Cell[TOTAL_COL];

		// 绘制单元格
		public void drawCells(Canvas canvas) {
			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					cells[i].drawSelf(canvas);
				}
			}
		}

	}

	/**
	 * 单元格元素
	 * 
	 * @author wuwenjie
	 * 
	 */
	class Cell {
		public CustomDate date;
		public State state;
		public int i;
		public int j;
		public boolean isClickable = true;

		public Cell(CustomDate date, State state, int i, int j) {
			super();
			this.date = date;
			this.state = state;
			this.i = i;
			this.j = j;
		}

		public void drawSelf(Canvas canvas) {
			switch (state) {
			case TODAY: // 今天
//				mTextPaint.setColor(Color.parseColor("#fffffe"));
//				canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
//						(float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
//						mCirclePaint);
				mTextPaint.setColor(Color.parseColor("#ababab"));
				canvas.drawCircle((float) (mCellHorSpace * (i + 0.5)),
						(float) ((j + 0.8) * mCellVerSpace), mCellVerSpace / 20,
						mPointPaint);
//				canvas.drawRect(0, 0, mViewWidth, mViewHeight, mPointPaint);
				break;
			case CURRENT_MONTH_DAY: // 当前月日期
				mTextPaint.setColor(Color.parseColor("#ababab"));
				break;
			case PAST_MONTH_DAY: // 过去一个月
				mTextPaint.setColor(Color.parseColor("#fffffe"));
			case NEXT_MONTH_DAY: // 下一个月
				mTextPaint.setColor(Color.parseColor("#fffffe"));
				break;
			case REACHED_DAY:
				mTextPaint.setColor(Color.parseColor("#e3e3e3"));
				break;
			case UNREACH_DAY: // 还未到的天
				mTextPaint.setColor(Color.parseColor("#ababab"));
				break;
			case SELECTED_DAY:
				mTextPaint.setColor(Color.parseColor("#6d6d6d"));
				canvas.drawCircle((float) (mCellHorSpace * (i + 0.5)),
						(float) ((j + 0.37) * mCellVerSpace), (float)(mCellVerSpace / 2.5),
						mCirclePaint);
				break;
			case BOOKED_DAY:
				mTextPaint.setColor(Color.GREEN);
				break;
			default:
				break;
			}
			// 绘制文字
			String content;
			if(date.day < 10){
				content = "0"+date.day;
			}else{
				content = date.day + "";
			}
			
			canvas.drawText(content,
					(float) ((i + 0.5) * mCellHorSpace - mTextPaint
							.measureText(content) / 2), (float) ((j + 0.7)
							* mCellVerSpace - mTextPaint
							.measureText(content, 0, 1) / 2), mTextPaint);
		}
	}
	

	/**
	 * 
	 * @author wuwenjie 单元格的状态    今天，当前月日期，过去的月的日期，下个月的日期，已经过了的日期，还未到的日期，被选中的日期，被预约的日期
	 */
	enum State {
		TODAY,CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, REACHED_DAY,UNREACH_DAY, SELECTED_DAY, BOOKED_DAY;
	}

	// 从左往右划，上一个月
	public void leftSlide() {
		if (mShowDate.month == 1) {
			mShowDate.month = 12;
			mShowDate.year -= 1;
		} else {
			mShowDate.month -= 1;
		}
		update();
	}

	// 从右往左划，下一个月
	public void rightSlide() {
		if (mShowDate.month == 12) {
			mShowDate.month = 1;
			mShowDate.year += 1;
		} else {
			mShowDate.month += 1;
		}
		update();
	}

	public void update() {
		fillDate();
		invalidate();
	}

}
