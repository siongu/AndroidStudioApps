package com.example.androidtest;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.xzw.viewcompat.DividerItemDecoration;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CollapsingToolbarLayout collapseTool;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBar();
        setContentView(R.layout.activity_main);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

//        collapseTool = (CollapsingToolbarLayout) findViewById(R.id.collapseTool);
//        collapseTool.setTitle("TestAndroid");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER, LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(new MyAdapter());

    }

    public void setRecyclerViewLayoutManager(int layoutManagerType, int orientation) {
        int mOrientation = RecyclerView.VERTICAL;
        switch (layoutManagerType) {
            case LayoutManagerType.LINEAR_LAYOUT_MANAGER:
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    mOrientation = RecyclerView.HORIZONTAL;
                } else {
                    mOrientation = RecyclerView.VERTICAL;
                }
                mLayoutManager = new LinearLayoutManager(this);
                ((LinearLayoutManager) mLayoutManager).setOrientation(orientation);
                break;
            case LayoutManagerType.GRID_LAYOUT_MANAGER:
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    mOrientation = RecyclerView.HORIZONTAL;
                    mLayoutManager = new GridLayoutManager(this, 4);
                } else {
                    mLayoutManager = new GridLayoutManager(this, 4);
                    mOrientation = RecyclerView.VERTICAL;
                }
                ((GridLayoutManager) mLayoutManager).setOrientation(orientation);
                break;
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, R.drawable.light_divider, mOrientation);
        itemDecoration.setDividerSize(1);
        mRecyclerView.addItemDecoration(itemDecoration);
    }


    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.blue_bright);
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
            findViewById(android.R.id.content).setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());
//            mRecyclerView.setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class LayoutManagerType {
        public static final int LINEAR_LAYOUT_MANAGER = 0x01;
        public static final int GRID_LAYOUT_MANAGER = 0x02;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;

            public MyViewHolder(View itemView) {
                super(itemView);
                initView(itemView);
            }

            private void initView(View itemView) {
                title = (TextView) itemView.findViewById(R.id.title);
            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.title.setText("title" + position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

}
