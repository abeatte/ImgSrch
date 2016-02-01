package com.beatte.art.imgsrch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beatte.art.imgsrch.rest.GettyImage;
import com.beatte.art.imgsrch.rest.GettyImages;
import com.beatte.art.imgsrch.rest.RESTService;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RESTService mRESTService;
    private GridLayoutManager mLayoutManager;
    private ImageAdapter mAdapter;

    @Bind(R.id.search_list) RecyclerView mList;
    @Bind(R.id.search_search_text) EditText mSearchText;
    @OnClick(R.id.search_search_btn) public void submit(View view) {
        if (mSearchText.getText().length() > 0) {
            mRESTService.getImageService().search(mSearchText.getText().toString()).enqueue(new Callback<GettyImages>() {
                @Override
                public void onResponse(Response<GettyImages> response) {
                    mAdapter.setData(response.body().getImages());
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(SearchActivity.this, "Brok'd!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRESTService = new RESTService();
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
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText(mDataset[position].getTitle());
            Picasso.with(getApplicationContext()).load(mDataset[position].getUrl()).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}
