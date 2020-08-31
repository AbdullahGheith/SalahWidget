package abdulg.widget.salahny.UI.Fragments.MainActivity.Adapters;

import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.R;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Abdullah Gheith on 16/05/2018.
 */
public class BigPrayerTimeAdapter extends RecyclerView.Adapter<BigPrayerTimeAdapter.PrayerPackViewHolder> {

    private Context context;
    private PrayerPack prayerPack;

    public BigPrayerTimeAdapter(Context context, PrayerPack prayerPack) {
        this.context = context;
        this.prayerPack = prayerPack;
    }

    public PrayerPack getPrayerPack() {
        return prayerPack;
    }

    public void setPrayerPack(PrayerPack prayerPack) {
        this.prayerPack = prayerPack;
    }

    @NonNull
    @Override
    public PrayerPackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bigprayertimecardview, parent, false);
        return new PrayerPackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerPackViewHolder holder, int position) {
        switch(position){
            case 0:
                holder.text.setText(context.getText(R.string.fajr));
                holder.time.setText(prayerPack.getFajrInHHmm());
                holder.imageViewBackground.setImageResource(R.drawable.fajr);
                break;
            case 1:
                holder.text.setText(context.getText(R.string.shuruk));
                holder.time.setText(prayerPack.getShurukInHHmm());
                holder.imageViewBackground.setImageResource(R.drawable.shuruk);
                break;
            case 2:
                holder.text.setText(context.getText(R.string.dhuhr));
                holder.time.setText(prayerPack.getDhuhrInHHmm());
                holder.imageViewBackground.setImageResource(R.drawable.dhuhr);
                break;
            case 3:
                holder.text.setText(context.getText(R.string.asr));
                holder.time.setText(prayerPack.getAsrInHHmm());
                holder.imageViewBackground.setImageResource(R.drawable.asr);
                break;
            case 4:
                holder.text.setText(context.getText(R.string.maghrib));
                holder.time.setText(prayerPack.getMaghribInHHmm());
                holder.imageViewBackground.setImageResource(R.drawable.maghrib);
                break;
            case 5:
                holder.text.setText(context.getText(R.string.isha));
                holder.time.setText(prayerPack.getIshaInHHmm());
                holder.imageViewBackground.setImageResource(R.drawable.isha);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class PrayerPackViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView text;
        TextView time;
        ImageView imageViewBackground;

        public PrayerPackViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.bigprayertime_cardview);
            text = itemView.findViewById(R.id.bigprayertime_salahText);
            time = itemView.findViewById(R.id.bigprayertime_salahTime);
            imageViewBackground = itemView.findViewById(R.id.cardview_imageview);
        }
    }

}
