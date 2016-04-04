package com.beatte.art.imgsrch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beatte.art.imgsrch.models.GettyImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    private GridLayoutManager mLayoutManager;
    private ImageAdapter mAdapter;
    private BroadcastReceiver mGetImagesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (ImageGetIntentService.ACTION_GET_IMAGES_SUCCESS.equals(action)) {
                ArrayList<GettyImage> images = intent.getParcelableArrayListExtra(ImageGetIntentService.EXTRA_IMAGES);
                mAdapter.setData(images.toArray(new GettyImage[images.size()]));
            } else if (ImageGetIntentService.ACTION_GET_IMAGES_FAILURE.equals(action)) {
                Toast.makeText(SearchActivity.this, "Brok'd!", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Bind(R.id.search_list) RecyclerView mList;
    @Bind(R.id.search_search_text) EditText mSearchText;
    @OnClick(R.id.search_search_btn) public void submit(View view) {
        if (mSearchText.getText().length() > 0) {
            startService(new Intent(SearchActivity.this,
                    ImageGetIntentService.class)
                    .setAction(ImageGetIntentService.ACTION_GET_IMAGES)
                    .putExtra(ImageGetIntentService.EXTRA_SEARCH_TEXT, mSearchText.getText().toString()));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mList.setHasFixedSize(false);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 3);
        mList.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ImageAdapter(new GettyImage[0]);
        mList.setAdapter(mAdapter);

        registerReceiver(mGetImagesReceiver, new IntentFilter(ImageGetIntentService.ACTION_GET_IMAGES_SUCCESS));
        registerReceiver(mGetImagesReceiver, new IntentFilter(ImageGetIntentService.ACTION_GET_IMAGES_FAILURE));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mGetImagesReceiver);
        super.onDestroy();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public ImageView image;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.image_title);
            image= (ImageView) v.findViewById(R.id.image_image);
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ViewHolder> {
        private GettyImage[] mDataset;

        public ImageAdapter(GettyImage[] data) {
            mDataset = data;
        }

        public void setData(GettyImage[] data) {
            mDataset = data;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText(mDataset[position].getTitle());
            Picasso.with(getApplicationContext()).load(mDataset[position].getUrl()).placeholder(android.R.drawable.ic_menu_gallery).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}
