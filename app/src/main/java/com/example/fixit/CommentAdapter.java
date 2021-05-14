package com.example.fixit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fixit.databinding.ItemCommentBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    public static final String TAG = "CommentAdapter";
    private ItemCommentBinding itemCommentBinding;

    private Context context;
    private List<Comment> comments;


    public CommentAdapter(Context context, List<Comment> comments){
        this.context = context;
        this.comments = comments;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemCommentBinding = ItemCommentBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.itemCommentBinding.tvComment.setText(comment.getComment());
        ParseUser parseuser = comment.getUserId();

        User user = new User();
        user.setObjectID(parseuser.getObjectId());
        if(parseuser.getParseFile(User.KEY_PROFILE_IMAGE)!=null){
            user.setImage(parseuser.getParseFile(User.KEY_PROFILE_IMAGE));
        }
        user.setUsername(parseuser.getUsername());
        user.setLastName(parseuser.getString(User.KEY_LAST_NAME));
        user.setFirstName(parseuser.getString(User.KEY_FIRST_NAME));
        user.setIsProfessional(parseuser.getBoolean(User.KEY_IS_PROFESSIONAL));
        itemCommentBinding.setUser(user);



    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemCommentBinding itemCommentBinding;

        public ViewHolder(@NonNull ItemCommentBinding itemCommentBinding) {
            super(itemCommentBinding.getRoot());
            this.itemCommentBinding = itemCommentBinding;
        }
        public void clear() {
            comments.clear();
            notifyDataSetChanged();
        }

        // Add a list of items -- change to type used
        public void addAll(List<Comment> list) {
            comments.addAll(list);
            notifyDataSetChanged();
        }

    }

}
