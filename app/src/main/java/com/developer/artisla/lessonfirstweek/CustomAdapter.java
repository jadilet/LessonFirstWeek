package com.developer.artisla.lessonfirstweek;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.developer.artisla.lessonfirstweek.model.Company;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<Company> mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final TextView created_at;
        private final ImageView logo;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");

                    Intent update = new Intent(v.getContext(),UpdateCompany.class);
                    update.putExtra(CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE,((TextView) v.findViewById(R.id.titleTxt)).getText());
                    update.putExtra(CompanyReaderContract.CompanyEntry.COLUMN_NAME_DESCRIPTION,((TextView) v.findViewById(R.id.descriptionTxt)).getText());
                    v.getContext().startActivity(update);
                }
            });
            title = (TextView) v.findViewById(R.id.titleTxt);
            description = (TextView) v.findViewById(R.id.descriptionTxt);
            created_at = (TextView) v.findViewById(R.id.created_atTxt);
            logo = (ImageView) v.findViewById(R.id.logoImg);
        }

        public ImageView getLogoView() { return logo; }
        public TextView getCreated_atView() { return created_at; }
        public TextView getDescriptionView() { return description; }
        public TextView getTitleView() {
            return title;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(List<Company> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.company_list, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTitleView().setText(mDataSet.get(position).getTitle());
        viewHolder.getDescriptionView().setText(mDataSet.get(position).getDescription());
        viewHolder.getCreated_atView().setText(mDataSet.get(position).getCreated_at());
        viewHolder.getLogoView().setImageURI(Uri.parse(mDataSet.get(position).getImagePath()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}