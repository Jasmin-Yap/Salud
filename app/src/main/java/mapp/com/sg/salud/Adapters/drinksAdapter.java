package mapp.com.sg.salud.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mapp.com.sg.salud.Fragments.cart;
import mapp.com.sg.salud.R;
import mapp.com.sg.salud.model.drinksData;

public class drinksAdapter extends RecyclerView.Adapter<drinksAdapter.drinksViewHolder> {

    private Context mCtx;
    private List<drinksData> cartItems;

    public drinksAdapter(Context mCtx, List<drinksData> cartItems) {
        this.mCtx = mCtx;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public drinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.all_cart_items_display_layout, null);
        drinksViewHolder holder = new drinksViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull drinksViewHolder holder, int position) {
            cartItems.get(position);
            if(cartItems.get(position).getQuan() == 1){
                holder.minus.setVisibility(View.INVISIBLE);
            }
            holder.textViewName.setText(cartItems.get(position).getBName());
            holder.textViewPrice.setText(String.valueOf(cartItems.get(position).getBPrice()));
            holder.textViewQuantity.setText(String.valueOf(cartItems.get(position).getQuan()));
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(cartItems.get(position).getImage()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class drinksViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName, textViewPrice,textViewQuantity;
        Button plus, minus;
        ImageButton delete;

        public drinksViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_items_image);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewPrice = itemView.findViewById(R.id.Price);
            textViewQuantity = itemView.findViewById(R.id.quantity);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            delete = itemView.findViewById(R.id.deleteButton);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt((textViewQuantity.getText()).toString());
                    count++;
                    if(count > 1) {
                        minus.setVisibility(View.VISIBLE);
                    }
                    setQuan(count, getAdapterPosition());
                    textViewQuantity.setText(String.valueOf(count));
                    double price = Double.parseDouble(((textViewPrice.getText()).toString()).substring(1));
                    cart.setNewTPrice(price, 0);
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt((textViewQuantity.getText()).toString());
                    count--;
                    if(count == 1) {
                        minus.setVisibility(View.INVISIBLE);
                    }
                    textViewQuantity.setText(String.valueOf(count));
                    setQuan(count, getAdapterPosition());
                    double price = Double.parseDouble(((textViewPrice.getText()).toString()).substring(1));
                    cart.setNewTPrice(0, price);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt((textViewQuantity.getText()).toString());
                    removeItem(getAdapterPosition());
                    double price = Double.parseDouble(((textViewPrice.getText()).toString()).substring(1));
                    double itemP = price * count;
                    cart.setNewTPrice(0, itemP);
                }
            });
        }
    }

    public void setQuan(int count, int pos){
        cartItems.get(pos).setQuan(count);
    }

    public void removeItem (int pos){
        cartItems.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, cartItems.size());
    }
}
