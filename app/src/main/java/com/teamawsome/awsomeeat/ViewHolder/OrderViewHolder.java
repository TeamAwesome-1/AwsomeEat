package com.teamawsome.awsomeeat.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView txtOrderId,txtOrderStatus, txtOrderRoll, txtOrderAddress;
    private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderAddress = itemView.findViewById(R.id.order_address);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderRoll = itemView.findViewById(R.id.order_roll);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
