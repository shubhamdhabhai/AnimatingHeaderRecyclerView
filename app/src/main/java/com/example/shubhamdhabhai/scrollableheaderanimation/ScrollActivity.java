package com.example.shubhamdhabhai.scrollableheaderanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScrollActivity extends AppCompatActivity {

    private final static int TODAY_TV_LEFT_MARGIN = 40;

    TextView todayTv;
    TextView todayAmountTv;
    TextView todayDeliveryTv;
    TextView weekTv;
    TextView weekAmountTv;
    TextView weekDeliveryTv;
    LinearLayout weekLl;
    LinearLayout todayLl;

    float todayTvX;
    float todayTvY;
    float todayAmountTvX;
    float todayAmountTvY;
    float todayDeliveryTvX;
    float todayDeliveryTvY;
    float weekTvX;
    float weekTvY;
    float weekAmountTvX;
    float weekAmountTvY;
    float weekDeliveryTvX;
    float weekDeliveryTvY;

    float todayTvWidth;
    float weekTvWidth;
    float weekLlX;

    float todayTvXShift;
    float weekTvShift;
    float todayLlX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        final CardView header = findViewById(R.id.ll_header);
        ScrollAdapter scrollAdapter = new ScrollAdapter();
        recyclerView.setAdapter(scrollAdapter);
        final float minHeight = getResources().getDimension(R.dimen.header_height_min);
        final float maxHeight = getResources().getDimension(R.dimen.header_height);
        populateData(scrollAdapter);

        todayTv = findViewById(R.id.tv_today);
        todayAmountTv = findViewById(R.id.tv_today_amount);
        todayDeliveryTv = findViewById(R.id.tv_today_delivery);

        weekTv = findViewById(R.id.tv_week);
        weekAmountTv = findViewById(R.id.tv_week_amount);
        weekDeliveryTv = findViewById(R.id.tv_week_delivery);

        weekLl = findViewById(R.id.ll_week);
        todayLl = findViewById(R.id.ll_today);

        todayTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            public void onGlobalLayout() {
                todayTvX = todayTv.getX();
                todayTvY = todayTv.getY();
                todayAmountTvX = todayAmountTv.getX();
                todayAmountTvY = todayAmountTv.getY();
                todayDeliveryTvX = todayDeliveryTv.getX();
                todayDeliveryTvY = todayDeliveryTv.getY();
                weekTvX = weekTv.getX();
                weekTvY = weekTv.getY();
                weekAmountTvX = weekAmountTv.getX();
                weekAmountTvY = weekAmountTv.getY();
                weekDeliveryTvX = weekDeliveryTv.getX();
                weekDeliveryTvY = weekDeliveryTv.getY();
                todayTvWidth = todayTv.getWidth();
                weekTvWidth = weekTv.getWidth();
                weekLlX = weekLl.getX();
                todayLlX = todayLl.getX();
                weekTvShift = weekTvX;
                // this 40 is the left margin of today textview when view is completely collapsed.
                todayTvXShift = todayTvX - TODAY_TV_LEFT_MARGIN;
                todayTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d("", "");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    // scrolled down
                    if (header.getHeight() >= maxHeight) {
                        // do nothing as header is at max length
                    } else {
                        recyclerView.removeOnScrollListener(this);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) header.getLayoutParams();
                        params.height = params.height - dy;
                        if (params.height >= maxHeight) {
                            params.height = (int) maxHeight;
                        }
                        header.setLayoutParams(params);

                        float ratio = (params.height - minHeight) / (maxHeight - minHeight);
                        // this 40 is the left margin of today textview when view is completely collapsed.
                        todayTv.setX(TODAY_TV_LEFT_MARGIN + (todayTvXShift) * (ratio));
                        todayAmountTv.setX((todayTvWidth + 40 + 30) - (todayTvWidth + 40 + 30 - todayAmountTvX) * ratio);
                        todayAmountTv.setY(todayTvY + (todayAmountTvY - todayTvY) * ratio);
                        float finalScaleFactor = 0.8f;
                        float scaleFactor = (float) ((ratio + finalScaleFactor) / (1 + finalScaleFactor));
                        if (scaleFactor < 0.8) {
                            scaleFactor = 0.8f;
                        }
                        todayAmountTv.setScaleX(scaleFactor);
                        todayAmountTv.setScaleY(scaleFactor);
                        todayDeliveryTv.setAlpha(ratio);

                        weekTv.setX((weekTvShift) * (ratio));
                        weekAmountTv.setX((weekTvWidth + 30) - (weekTvWidth + 30 - weekAmountTvX) * ratio);
                        weekAmountTv.setY(weekTvY + (weekAmountTvY - weekTvY) * ratio);

                        weekAmountTv.setScaleX(scaleFactor);
                        weekAmountTv.setScaleY(scaleFactor);
                        weekDeliveryTv.setAlpha(ratio);
                        recyclerView.addOnScrollListener(this);
                    }

                    Log.d("acrolling", "dy is less than 0" + dy);
                } else if (dy > 0) {
                    // scrolled up
                    if (header.getHeight() <= minHeight) {
                        // do nothing as header is min length
                    } else {
                        recyclerView.removeOnScrollListener(this);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) header.getLayoutParams();
                        params.height = params.height - dy;
                        if (params.height <= minHeight) {
                            params.height = (int) minHeight;
                        }
                        Log.d("acrolling", "param height = " + params.height);
                        header.setLayoutParams(params);


                        float ratio = (params.height - minHeight) / (maxHeight - minHeight);
                        todayTv.setX(todayTvX - (todayTvXShift) * (1 - ratio));
                        // here 40 is the laft margin of today text view and 30 is the margin b/w today and amount
                        todayAmountTv.setX(todayAmountTvX - (todayAmountTvX - (todayTvWidth + 40 + 30)) * (1 - ratio));
                        todayAmountTv.setY(todayAmountTvY - (todayAmountTvY - todayTvY) * (1 - ratio));
                        float finalScaleFactor = 0.8f;
                        float scaleFactor = (float) ((ratio + finalScaleFactor) / (1 + finalScaleFactor));
                        if (scaleFactor < finalScaleFactor) {
                            scaleFactor = finalScaleFactor;
                        }
                        todayAmountTv.setScaleX(scaleFactor);
                        todayAmountTv.setScaleY(scaleFactor);
                        todayDeliveryTv.setAlpha(ratio);

                        weekTv.setX(weekTvX - (weekTvShift) * (1 - ratio));
                        // 30 is the margin b/w week and amount
                        weekAmountTv.setX(weekAmountTvX - (weekAmountTvX - (weekTvWidth + 30)) * (1 - ratio));
                        weekAmountTv.setY(weekAmountTvY - (weekAmountTvY - weekTvY) * (1 - ratio));
                        if (scaleFactor < finalScaleFactor) {
                            scaleFactor = finalScaleFactor;
                        }
                        weekAmountTv.setScaleX(scaleFactor);
                        weekAmountTv.setScaleY(scaleFactor);
                        weekDeliveryTv.setAlpha(ratio);
                        recyclerView.addOnScrollListener(this);

                    }
                    Log.d("acrolling", "dy is greater than 0" + dy);
                }
            }
        });
    }

    private void populateData(ScrollAdapter scrollAdapter) {
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
        scrollAdapter.add("this is text");
    }
}
