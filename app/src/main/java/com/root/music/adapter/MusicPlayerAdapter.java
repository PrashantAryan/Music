package com.root.music.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.root.music.interfaces.MusicPositionInterface;
import com.root.music.R;
import com.root.music.util.MediaItem;
import com.root.music.util.MusicListUtilFunctions;

import java.util.ArrayList;

/**
 * Created by Ashutosh on 20/7/17.
 */
public class MusicPlayerAdapter extends RecyclerView.Adapter<MusicPlayerAdapter.ViewHolder> {
    private ArrayList<MediaItem> listOfSongs;
    private MusicPositionInterface mMusicPositionInterface;


    public MusicPlayerAdapter(ArrayList<MediaItem> listOfSongs, MusicPositionInterface musicPositionInterface) {
        this.listOfSongs = listOfSongs;
        this.mMusicPositionInterface = musicPositionInterface;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicPlayerAdapter.ViewHolder holder, final int position) {
        MediaItem detail = listOfSongs.get(position);
        holder.textViewSongName.setText(detail.toString());
        holder.textViewArtist.setText(detail.getAlbum() + " - " + detail.getArtist());
        holder.textViewDuration.setText(MusicListUtilFunctions.getDuration(detail.getDuration()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicPositionInterface.musicListPosition(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listOfSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSongName;
        private TextView textViewArtist;
        private TextView textViewDuration;
        private android.support.v7.widget.CardView cardView;

        public ViewHolder(View myView) {
            super(myView);
            textViewSongName = (TextView) myView.findViewById(R.id.textViewSongName);
            textViewArtist = (TextView) myView.findViewById(R.id.textViewArtist);
            textViewDuration = (TextView) myView.findViewById(R.id.textViewDuration);
            cardView = (CardView) myView.findViewById(R.id.cardView);

        }
    }
}





