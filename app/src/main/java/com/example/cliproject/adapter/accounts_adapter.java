package com.example.cliproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliproject.R;
import com.example.cliproject.models.Customer;

import java.util.List;

public class accounts_adapter extends RecyclerView.Adapter<com.example.cliproject.adapter.accounts_adapter.MyViewHolder> {
    Context context;
    Activity activity;
    private List<Customer> list;
    private SlotAdapterListener slotAdapterListener;
    private int selectedItem = 0;
    private int lastSelected = 0;
    int privous_position=-1;
    Customer customer;

    int index;
    public accounts_adapter(List<Customer> list, Context con,Customer customer) {
        this.list = list;
        this.context = con;
        this.activity = activity;
        this.customer=customer;
    }

    @Override
    public com.example.cliproject.adapter.accounts_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_item_list, parent, false);
        return new com.example.cliproject.adapter.accounts_adapter.MyViewHolder(itemView);
    }

  


    @Override
    public void onBindViewHolder(com.example.cliproject.adapter.accounts_adapter.MyViewHolder holder, final int position) {
        Customer cartAddons= list.get(position);
        final Customer object = list.get(position);

        holder.name.setText(object.getName().toString());

        if(customer.getName().equals(object.getName()))
        {
            holder.layout.setVisibility(View.GONE);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                index=position;
                notifyDataSetChanged();



                privous_position =position;
                if (slotAdapterListener != null)
                    slotAdapterListener.onCategoryClick(object);


            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slotAdapterListener != null)
                    slotAdapterListener.onCategoryClick(object);
            }
        });


        // holder.price.setText(cartAddons.getPrice());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }






    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,add;
        ImageView closeImg;
        LinearLayout layout;
        CheckBox checkBox;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.addon_detail);
            layout = view.findViewById(R.id.item_layout);
            // price= view.findViewById(R.id.addon_price);



        }
    }
    public interface SlotAdapterListener {
        void onCategoryClick(Customer category);


    }

    public void setCategoryAdapterListener(SlotAdapterListener slotAdapterListener ) {
        this.slotAdapterListener = slotAdapterListener;
    }

}

