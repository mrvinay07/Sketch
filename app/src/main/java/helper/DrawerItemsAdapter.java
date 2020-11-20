package helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamvinay.sketch.R;

import java.util.List;
import listeners.DrawerItemListener;

public class DrawerItemsAdapter extends RecyclerView.Adapter<DrawerItemsAdapter.MyViewHolder> {
    /* access modifiers changed from: private */
    public DrawerItemListener drawerItemListener;
    private List<ViewElements> viewElements;

    public DrawerItemListener getDrawerItemListener() {
        return this.drawerItemListener;
    }

    public void setDrawerItemListener(DrawerItemListener drawerItemListener2) {
        this.drawerItemListener = drawerItemListener2;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        RelativeLayout layout;
        TextView textView;

        public void onClick(View view) {
        }

        public MyViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.menu_item_name);
            this.imageView = (ImageView) view.findViewById(R.id.menu_item);
            this.layout = (RelativeLayout) view.findViewById(R.id.layout);
            view.setOnClickListener(this);
        }
    }

    public DrawerItemsAdapter(List<ViewElements> list) {
        this.viewElements = list;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_items_layout, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        final ViewElements viewElements2 = this.viewElements.get(i);
        myViewHolder.imageView.setImageResource(viewElements2.imageId);
        myViewHolder.textView.setText(viewElements2.txt);
        myViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DrawerItemsAdapter.this.drawerItemListener != null) {
                    DrawerItemsAdapter.this.drawerItemListener.OnDrawerItemClick(viewElements2.getTxt());
                }
            }
        });
    }

    public int getItemCount() {
        return this.viewElements.size();
    }
}