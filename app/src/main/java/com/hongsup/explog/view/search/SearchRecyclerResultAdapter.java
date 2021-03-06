package com.hongsup.explog.view.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hongsup.explog.R;
import com.hongsup.explog.data.Const;
import com.hongsup.explog.data.post.PostCover;
import com.hongsup.explog.view.post.PostActivity;
import com.hongsup.explog.view.search.insuptest.SearchResponse;
import com.hongsup.explog.view.setting.editprofile.insuptest.Posts;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 정인섭 on 2017-12-13.
 */

public class SearchRecyclerResultAdapter extends RecyclerView.Adapter<SearchRecyclerResultAdapter.MyHolder> {
    ArrayList<PostCover> resultList = new ArrayList<>();
    Context context;

    public SearchRecyclerResultAdapter(Context context) {
        this.context = context;
    }

    public void resultNotifier(ArrayList<PostCover> list) {
        this.resultList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textWriter.setText(resultList.get(position).getAuthor().getUsername());
        holder.textTitle.setText(resultList.get(position).getTitle());
        String date = resultList.get(position).getStartDate() + " ~ " + resultList.get(position).getEndDate();
        holder.textDate.setText(date);
        Glide.with(context).load(resultList.get(position).getCoverPath()).centerCrop().into(holder.imgCover);
        holder.imgCover.setColorFilter(ContextCompat.getColor(context, R.color.colorMainTint), PorterDuff.Mode.SRC_OVER);
        holder.postCover = resultList.get(position);
        holder.textLike.setText(resultList.get(position).getLikeCount() + "");
    }

    @Override
    public int getItemCount() {
        Log.d("searchRechcleradapter", resultList.size() + "");
        return resultList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textTitle)
        TextView textTitle;
        @BindView(R.id.textDate)
        TextView textDate;
        @BindView(R.id.textWriter)
        TextView textWriter;
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.textLike)
        TextView textLike;


        PostCover postCover;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), PostActivity.class);
                    intent.putExtra(Const.INTENT_EXTRA_COVER, postCover);
                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}