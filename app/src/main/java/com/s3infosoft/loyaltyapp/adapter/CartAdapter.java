package com.s3infosoft.loyaltyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.s3infosoft.loyaltyapp.R;
import com.s3infosoft.loyaltyapp.model.CartItem;
import com.s3infosoft.loyaltyapp.model.Hotel;
import com.s3infosoft.loyaltyapp.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<CartItem> cartItems;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1, tv2, tv3;
        public ImageView imageView;
        public Button plus, minus, remove;
        public EditText quantity;

        public MyViewHolder(View view)
        {
            super(view);
            tv1 = (TextView) view.findViewById(R.id.product_name);
            tv2 = (TextView) view.findViewById(R.id.product_desc);
            tv3 = (TextView) view.findViewById(R.id.product_points);
            imageView = (ImageView) view.findViewById(R.id.logo_url);
            minus = (Button) view.findViewById(R.id.minus);
            quantity = (EditText) view.findViewById(R.id.quantity);
            plus = (Button) view.findViewById(R.id.plus);
        }
    }

    public CartAdapter(Context context, List<CartItem> cartItems)
    {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv1.setText(cartItems.get(position).getItem_name());
        holder.tv2.setText(cartItems.get(position).getItem_desc());
        holder.tv3.setText(""+cartItems.get(position).getAmount()+" PTS");
        Glide.with(context).load(cartItems.get(position).getItem_logo_url()).into(holder.imageView);

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.quantity.getText().toString());
                holder.quantity.setText(""+--count);
                holder.tv3.setText(""+(count*cartItems.get(position).getAmount())+" PTS");
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.quantity.getText().toString());
                holder.quantity.setText(++count+"");
                holder.tv3.setText(""+(count*cartItems.get(position).getAmount())+" PTS");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}