package com.example.esgro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esgro.R;
import com.example.esgro.modals.Bank;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.BankViewHolder> {
    private Context context;
    private List<Bank> bankist;

    public Adapter(Context context,List<Bank> bankist){
        this.context = context;
        this.bankist = bankist;
    }

    @Override
    public BankViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_bank_layout,viewGroup,false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankViewHolder bankViewHolder, int i) {
        Bank bank = bankist.get(i);
        bankViewHolder.textView.setText(bank.getBankName());
    }


    @Override
    public int getItemCount() {
        return bankist.size();
    }

     class BankViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public BankViewHolder(View itemView) {
            super(itemView);
        }
    }
}
