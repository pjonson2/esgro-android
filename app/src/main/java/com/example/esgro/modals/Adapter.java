package com.example.esgro.modals;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esgro.R;

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
//            imageView = itemView.findViewById(R.id.bankImg);bankImg
//            textView = itemView.findViewById(R.id.bankNameTxt);
        }
    }
}
