package mapp.com.sg.salud.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import mapp.com.sg.salud.R;
import mapp.com.sg.salud.model.drinksData;

/**
 * Created by Jasmin on 2/8/2018.
 */

public class Favsadaptor extends RecyclerView.Adapter<Favsadaptor.productViewHolder> {


    List<drinksData> drinks;
    Context dContext;
    private Button addToCart, RemoveFav;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private String userID;



    public Favsadaptor(Context context, List<drinksData> dData){
        this.dContext = context;
        this.drinks = dData;
    }

    @Override
    public Favsadaptor.productViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(dContext)
                .inflate(R.layout.fav_items, parent, false);
        return new Favsadaptor.productViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Favsadaptor.productViewHolder holder, int position) {
        //replaces content of the view (chatTextView) based on chatlist
        //chatTextView is defined in the inner class MainViewHolder
        drinksData data = drinks.get(position);
        // Set contents for each view
        holder.price.setText(data.getBPrice());
        holder.name.setText(data.getBName());
        holder.pic.setImageResource(data.getImage());
    }

    @Override
    public int getItemCount() { return drinks.size(); }

    public class productViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ListingIV)
        ImageView pic;
        @BindView(R.id.nameTV)
        TextView name;
        @BindView(R.id.priceTV)
        TextView price;

        public productViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.priceTV);
            name = itemView.findViewById(R.id.nameTV);
            pic = itemView.findViewById(R.id.ListingIV);
            addToCart = itemView.findViewById(R.id.addCart);
            RemoveFav = itemView.findViewById(R.id.RemoveFavs);

            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(cartCheck(pos)== false){
                            drinks.get(pos).setQuan(1);
                            adaptor.cart.add(drinks.get(pos));
                            Toast.makeText(dContext, "Item added to Cart", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(dContext, "Item has already been added to Cart", Toast.LENGTH_SHORT).show();
                        }
                }
            });

            RemoveFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(getAdapterPosition());
                }
            });
        }

    }

    public void removeItem (int pos){
        drinks.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, drinks.size());
    }

    public boolean cartCheck(int pos) {
        boolean check = false;
        for (int i = 0; i < adaptor.cart.size(); i++) {
            if (drinks.get(pos).getBName().equals(adaptor.cart.get(i).getBName())) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }

}


