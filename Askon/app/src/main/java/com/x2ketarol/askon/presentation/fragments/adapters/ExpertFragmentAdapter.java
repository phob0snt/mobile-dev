package com.x2ketarol.askon.presentation.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.domain.model.Expert;

import java.util.List;

public class ExpertFragmentAdapter extends RecyclerView.Adapter<ExpertFragmentAdapter.ViewHolder> {

    private List<Expert> experts;
    private OnExpertClickListener listener;

    public interface OnExpertClickListener {
        void onExpertClick(Expert expert);
    }

    public ExpertFragmentAdapter(List<Expert> experts, OnExpertClickListener listener) {
        this.experts = experts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_expert_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expert expert = experts.get(position);
        holder.bind(expert, listener);
    }

    @Override
    public int getItemCount() {
        return experts.size();
    }

    public void updateExperts(List<Expert> newExperts) {
        this.experts = newExperts;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView specialtyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.expertNameTextView);
            specialtyTextView = itemView.findViewById(R.id.expertSpecialtyTextView);
        }

        public void bind(Expert expert, OnExpertClickListener listener) {
            nameTextView.setText(expert.getName());
            specialtyTextView.setText(expert.getSpecialty());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onExpertClick(expert);
                }
            });
        }
    }
}
