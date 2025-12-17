package com.x2ketarol.askon.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.domain.model.Expert;

import java.util.List;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder> {

    private final List<Expert> experts;
    private final OnExpertClickListener listener;

    public interface OnExpertClickListener {
        void onExpertClick(Expert expert);
    }

    public ExpertAdapter(List<Expert> experts, OnExpertClickListener listener) {
        this.experts = experts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expert, parent, false);
        return new ExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertViewHolder holder, int position) {
        Expert expert = experts.get(position);
        holder.bind(expert, listener);
    }

    @Override
    public int getItemCount() {
        return experts.size();
    }

    static class ExpertViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatarImage;
        private final TextView nameText;
        private final TextView ratingText;
        private final TextView descriptionText;
        private final TextView priceText;
        private final ChipGroup skillsChipGroup;

        public ExpertViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.expertAvatar);
            nameText = itemView.findViewById(R.id.expertName);
            ratingText = itemView.findViewById(R.id.expertRating);
            descriptionText = itemView.findViewById(R.id.expertDescription);
            priceText = itemView.findViewById(R.id.expertPrice);
            skillsChipGroup = itemView.findViewById(R.id.skillsChipGroup);
        }

        public void bind(Expert expert, OnExpertClickListener listener) {
            nameText.setText(expert.getName());
            ratingText.setText(String.format("⭐ %.1f (%d)", expert.getRating(), expert.getReviewCount()));
            descriptionText.setText(expert.getSpecialization());
            priceText.setText(String.format("$%d/hr", expert.getHourlyRate()));

            // Add skills as chips
            skillsChipGroup.removeAllViews();
            List<String> skills = expert.getSkills();
            int maxSkills = Math.min(skills.size(), 3); // Show max 3 skills
            for (int i = 0; i < maxSkills; i++) {
                Chip chip = new Chip(itemView.getContext());
                chip.setText(skills.get(i));
                chip.setClickable(false);
                chip.setChipBackgroundColorResource(R.color.chip_background);
                skillsChipGroup.addView(chip);
            }

            itemView.setOnClickListener(v -> listener.onExpertClick(expert));
        }
    }
}
