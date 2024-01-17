package com.pixelnx.eacademy.ui.galary.galleryvideos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.ui.video.ActivityYoutubeVideo;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;
import com.pixelnx.eacademy.utils.widgets.CustomeTextRegular;

import java.util.ArrayList;

public class AdapterGalleryVideos extends RecyclerView.Adapter<AdapterGalleryVideos.AdapterGalleryVideosViewHolder> {

    Context mContext;
    String cId = "";
    ModelLogin modelLogin;
    ArrayList<YouTubeVideosModel.Data> videosUrlsList;
    SharedPref pref;


    public AdapterGalleryVideos(Context mContext, ArrayList<YouTubeVideosModel.Data> videosUrlsList, String cId) {
        this.mContext = mContext;
        this.videosUrlsList = videosUrlsList;
        this.cId = cId;
        pref = SharedPref.getInstance(mContext);
        modelLogin = pref.getUser(AppConsts.STUDENT_DATA);
    }

    @NonNull
    @Override
    public AdapterGalleryVideosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_gallery_videos, viewGroup, false);
        return new AdapterGalleryVideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGalleryVideosViewHolder holder, @SuppressLint("RecyclerView") int i) {

        holder.videoName.setText("" + videosUrlsList.get(i).getTitle());
        holder.videoName.setTextSize(14f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("saloni123456", "------ typeee " + videosUrlsList.get(i).getVideoType() + "  " + videosUrlsList.get(i).getUrl());

                if (videosUrlsList.get(i).getVideoType().equalsIgnoreCase("youtube")) {
                    mContext.startActivity(new Intent(mContext, ActivityYoutubeVideo.class)
                            .putExtra("vId", "" + videosUrlsList.get(i).getVideoId())
                            .putExtra("title", "" + videosUrlsList.get(i).getTitle())
                            .putExtra("type", "" + videosUrlsList.get(i).getVideoType())
                            .putExtra("WEB_URL", "" + videosUrlsList.get(i).getUrl()));
                } else {
                    Log.v("saloni123456", "------ typeee " + videosUrlsList.get(i).getVideoType() + "  "  +AppConsts.MAIN_URl+"/"+ videosUrlsList.get(i).getUrl());
                    if (videosUrlsList.get(i).getVideoType().equalsIgnoreCase("video")) {
                        mContext.startActivity(new Intent(mContext, ExoplayerVideos.class).putExtra("WEB_URL", "" +AppConsts.MAIN_URl+"/"+ videosUrlsList.get(i).getUrl()));
                    } else {
                        mContext.startActivity(new Intent(mContext, ActivityVimeoVideo.class)
                                .putExtra("vId", "" + videosUrlsList.get(i).getVideoId())
                                .putExtra("title", "" + videosUrlsList.get(i).getTitle())
                                .putExtra("type", "" + videosUrlsList.get(i).getVideoType())
                                .putExtra("WEB_URL", "" + videosUrlsList.get(i).getUrl()));
                    }
                }


            }
        });


        if (50 < videosUrlsList.get(i).getTitle().length()) {
            holder.viewMore.setVisibility(View.VISIBLE);
            holder.tvVideoDes.setMaxLines(2);

        } else {
            holder.viewMore.setVisibility(View.GONE);
            holder.videoName.setMaxLines(Integer.MAX_VALUE);
        }
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.videoName.getMaxLines() == 2) {
                    holder.viewMore.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    holder.videoName.setMaxLines(Integer.MAX_VALUE);
                } else {
                    holder.viewMore.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    holder.videoName.setMaxLines(2);

                }
                if (holder.tvVideoDes.getMaxLines() == 3) {
                    holder.viewMore.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    holder.tvVideoDes.setMaxLines(Integer.MAX_VALUE);

                } else {
                    holder.viewMore.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    holder.tvVideoDes.setMaxLines(3);

                }
            }
        });
if(videosUrlsList.get(i).getDescription().isEmpty()){
    holder.tvVideoDes.setVisibility(View.GONE);
}else{
        holder.tvVideoDes.setText(videosUrlsList.get(i).getDescription());
        holder.tvVideoDes.setVisibility(View.VISIBLE);
}
        if (35 <= videosUrlsList.get(i).getDescription().length()) {
            holder.viewMore.setVisibility(View.VISIBLE);
            holder.tvVideoDes.setMaxLines(3);
        } else {
            holder.viewMore.setVisibility(View.GONE);

        }
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return videosUrlsList.size();
    }

    class AdapterGalleryVideosViewHolder extends RecyclerView.ViewHolder {
        CustomTextExtraBold videoName;
        ImageView viewMore;
        CustomeTextRegular tvVideoDes;

        public AdapterGalleryVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.tvVideoName);
            viewMore = itemView.findViewById(R.id.viewMore);
            tvVideoDes = itemView.findViewById(R.id.tvVideoDes);
        }
    }
}
