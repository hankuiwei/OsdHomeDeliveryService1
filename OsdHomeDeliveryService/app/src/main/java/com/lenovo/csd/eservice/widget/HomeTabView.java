package com.lenovo.csd.eservice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lenovo.csd.eservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页容纳四个tab底部的tab view
 * Created by shengtao
 * on 2016/7/27
 * 08:35
 */
public class HomeTabView extends LinearLayout {
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private int mChildSize;
    private List<HomeTabItem> mTabItems;
    private OnItemIconTextSelectListener mListener;

    private int mTextSize = 12;
    private int mTextColorSelect = 0xff45c01a;
    private int mTextColorNormal = 0xff777777;
    private int mPadding = 10;
    private int mIconSize = 0;
    private GestureDetectorCompat mGestureDetector;  
    private OnItemClickListener mOnItemClickListener;
    private int touchedPos = 0;
    	
    /**
     * 判断是不是通过code产生的viewpager滑动动画
     */
    private boolean mViewPageScrollByCode = false;
    
    private boolean mHandleItemTouchUpEvent = false;

    public HomeTabView(Context context) {
        this(context, null);
    }

    public HomeTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造函数，读取属性
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public HomeTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        mGestureDetector = new GestureDetectorCompat(context, new OnGestureListener(){

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}});
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener(){  
        	@Override
            public boolean onDoubleTap(MotionEvent e) {
        		try {
        			if(mOnItemClickListener != null){
            			mOnItemClickListener.onDoubleClick(mTabItems.get(touchedPos), touchedPos);
            		}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
              return true;   
            }  
        	@Override
            public boolean onDoubleTapEvent(MotionEvent e) {
              return false;  
            }   
        	@Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
              return false;   
            }   
          });  

        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.HomeTabView);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.HomeTabView_icon_size:
                    mIconSize = (int) typedArray.getDimension(i, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            0, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.HomeTabView_text_size:
                    mTextSize = (int) typedArray.getDimension(i, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            mTextSize, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HomeTabView_text_normal_color:
                    mTextColorNormal = typedArray.getColor(i, mTextColorNormal);
                    break;
                case R.styleable.HomeTabView_text_select_color:
                    mTextColorSelect = typedArray.getColor(i, mTextColorSelect);
                    break;
                case R.styleable.HomeTabView_item_padding:
                    mPadding = (int) typedArray.getDimension(i, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            mPadding, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        mTabItems = new ArrayList<HomeTabItem>();
    }

    public void setViewPager(final ViewPager mViewPager) {
        if (mViewPager == null) {
            return;
        }
        this.mViewPager = mViewPager;
        this.mPagerAdapter = mViewPager.getAdapter();
        if (this.mPagerAdapter == null) {
            throw new RuntimeException("在设置TabView的ViewPager时，请先设置ViewPager的PagerAdapter");
        }
        this.mChildSize = this.mPagerAdapter.getCount();
        this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            	if(mViewPageScrollByCode){
            		//如果是通过code产生的viewpager滑动动画，不处理底栏的变色
            		if(positionOffset == 0){
            			mViewPageScrollByCode = false;
            		}
            		return;
            	}

                if (positionOffset > 0) {//在滑动过程中
                    mTabItems.get(position).setTabAlpha(1 - positionOffset);
                    mTabItems.get(position + 1).setTabAlpha(positionOffset);
                } else {//滑动结束
                    for(int i = 0 ; i < mTabItems.size() ; i++)
                    {
    					if(i != position){
    						HomeTabItem tabItem = mTabItems.get(i);
                    		tabItem.setTabAlpha(0f);
    					}
                    }
                    mTabItems.get(position).setTabAlpha(1);
                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        if (mPagerAdapter instanceof OnItemIconTextSelectListener) {
            mListener = (OnItemIconTextSelectListener) mPagerAdapter;
        }else {
            throw new RuntimeException("请让你的pageAdapter实现OnItemIconTextSelectListener接口");
        }
        initItem();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    /**
     * 根据传入的viewpager生成ItemView添加进来
     */
    private void initItem() {
        for (int i = 0; i < mChildSize; i++) {
            final HomeTabItem tabItem = new HomeTabItem(getContext());
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            tabItem.setPadding(mPadding, mPadding, mPadding, mPadding);
            tabItem.setIconSize(mIconSize);
            tabItem.setIconText(mListener.onIconSelect(i), mListener.onTextSelect(i));
            tabItem.setTextSize(mTextSize);
            tabItem.setTextColorNormal(mTextColorNormal);
            tabItem.setTextColorSelect(mTextColorSelect);
            tabItem.setLayoutParams(params);
            tabItem.setTag(i);//位置保持在tag中
            tabItem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try {
                    	if(mOnItemClickListener != null){
                    		mOnItemClickListener.onSingleClick(v, (Integer) v.getTag());
                  		}
 					} catch (Exception e) {
 						e.printStackTrace();
 					}
				}
			});
            tabItem.setOnTouchListener(new OnTouchListener() {
                // 模拟selector的效果
                @Override
                public boolean onTouch(View v, MotionEvent ev) {
                    
                    touchedPos = (Integer) v.getTag();
                    if (mViewPager.getCurrentItem() == touchedPos) {
                    	// 考虑双击事件
                    	return mGestureDetector.onTouchEvent(ev);
                        //return false;
                    }
                    
                    switch (ev.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            mTabItems.get(touchedPos).setTabAlpha(1f);
                            mHandleItemTouchUpEvent = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                        	if(mHandleItemTouchUpEvent && isTouchEventOutView(ev, tabItem)){
                        		mHandleItemTouchUpEvent = false;
                        		mTabItems.get(touchedPos).setTabAlpha(0f);
                        	}
                        	
                        	break;
                        case MotionEvent.ACTION_CANCEL:
                        	if(mHandleItemTouchUpEvent){
                                mViewPageScrollByCode = true;
                        	}
                        	mTabItems.get(touchedPos).setTabAlpha(0f);
                        	break;
                        case MotionEvent.ACTION_UP:
                        	if(mHandleItemTouchUpEvent){
                                mViewPageScrollByCode = true;
                        	}
                            break;
                    }
                    //return mGestureDetector.onTouchEvent(ev);
                    return false;//返回false，继续响应click事件
                }
            });
            mTabItems.add(tabItem);
            addView(tabItem);
        }
    }

    /**
     * touch事件是否在view的外部
     * @param ev
     * @param view
     * @return
     */
    private boolean isTouchEventOutView(MotionEvent ev, View view){
    	if(ev.getX() < 0 || ev.getY() < 0 || ev.getX() > view.getMeasuredWidth()
    			|| ev.getY() > view.getMeasuredHeight()){
    		return true;
    	}
    	return false;
    }
    
    
    /**
     * 设定底栏的选中位置
     * @param position 位置
     * @param withAni viewpager切换时，是否有动画
     */
    public void setSelection(int position, boolean withAni){
    	if(position < mChildSize){
    		 for(int i = 0 ; i < mChildSize ; i++){
    			 HomeTabItem tabItem = mTabItems.get(i);
    			 if(position == i){
    				 tabItem.setTabAlpha(1f);//该位置选中
    			 }
    			 else{
    				 tabItem.setTabAlpha(0f);
    			 }
             }
    		 if(withAni){
    			 mViewPageScrollByCode = true;
    			 mViewPager.setCurrentItem(position, true);
    		 }
    		 else{
    			 mViewPager.setCurrentItem(position, false);
    		 }
    	}
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
    	mOnItemClickListener = listener;
    }
       
    public interface OnItemClickListener {
        void onSingleClick(View v, int itemPos);
        void onDoubleClick(View v, int itemPos);
    }
    public interface OnItemIconTextSelectListener {

        int[] onIconSelect(int position);

        String onTextSelect(int position);
    }

    public void setItemIcon(int pos, Bitmap iconSelect, Bitmap iconNormal){
       if(pos < getChildCount() && pos > 0){
           View childView = getChildAt(pos);
           if(childView != null && childView instanceof HomeTabItem){
               ((HomeTabItem)childView).setIconBitmap(iconSelect, iconNormal);
           }
       }
    }

}
