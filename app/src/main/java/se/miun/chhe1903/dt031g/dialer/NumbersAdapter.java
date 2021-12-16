package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import se.miun.chhe1903.dt031g.dialer.data.Number;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {
    private List<Number> numbersData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    NumbersAdapter(Context context, List<Number> data) {
        this.mInflater = LayoutInflater.from(context);
        this.numbersData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Number storedCall = numbersData.get(position);
        holder.numberTextView.setText(storedCall.getNumber());
        holder.timeStampTextView.setText(storedCall.getTimestamp());
        holder.locationTextView.setText("(" + String.valueOf(storedCall.getLatitude()) + ", " + String.valueOf(storedCall.getLongitude()) + ")");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return numbersData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numberTextView;
        TextView timeStampTextView;
        TextView locationTextView;

        ViewHolder(View itemView) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.call_number);
            timeStampTextView = itemView.findViewById(R.id.call_timestamp);
            locationTextView = itemView.findViewById(R.id.call_location);
        }
    }
}
