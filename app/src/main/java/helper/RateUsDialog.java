package helper;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamvinay.sketch.R;


public class RateUsDialog {
    private static RateUsDialog INSTANCE;
    private ImageView alert;
    private boolean feedback = false;
    private ImageView icon;
    private Button not_button;
    private TextView rateDialogText;
    private Button yes_button;

    private RateUsDialog() {
    }

    public static RateUsDialog getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RateUsDialog();
        }
        return INSTANCE;
    }

    public void showDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.RateUsDialogTheme);
        dialog.getWindow().setDimAmount(0.0f);
        this.yes_button = (Button) dialog.findViewById(R.id.yes);
        this.not_button = (Button) dialog.findViewById(R.id.no);
        this.rateDialogText = (TextView) dialog.findViewById(R.id.rating_text);
        dialog.setCancelable(true);
        dialog.show();
    }
}

