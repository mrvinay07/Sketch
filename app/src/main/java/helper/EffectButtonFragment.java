package helper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamvinay.sketch.EffectsAdapter;
import com.teamvinay.sketch.PencilEffect;
import com.teamvinay.sketch.R;

import java.util.ArrayList;
import java.util.List;
import listeners.AdLoadListener;
import util.AdUtil;
import util.NativeAdUtil;

public class EffectButtonFragment extends Fragment implements AdLoadListener {
    private int effectIndex = -1;
    private EffectButtonListener listener;
    private EffectsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private List<Object> pencilEffects = new ArrayList();
    boolean start = true;

    /* renamed from: v */
    View v;

    public void OnAdLoaded() {
    }

    public EffectButtonFragment() {
        Log.d("EffectFragment", "Constructor");
        initializeContent();
    }

    public void setEffectIndex(int i) {
        this.effectIndex = i;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (!Constants.REMOVE_ADS) {
            AdUtil.getInstance().setAdLoadListener(this);
        }
        Log.d("EffectFragment", "Create");
    }

    private void initializeContent() {
        this.pencilEffects.add(new PencilEffect(EffectConstant.PENCIL, R.drawable.sketch));
        this.pencilEffects.add(new PencilEffect(EffectConstant.SKETCH, R.drawable.sketch_));
        this.pencilEffects.add(new PencilEffect(EffectConstant.PENCILDARK, R.drawable.dark_pencil));
        this.pencilEffects.add(new PencilEffect(EffectConstant.LIGHT_SKETCH, R.drawable.pencil_sketch));
        this.pencilEffects.add(new PencilEffect(EffectConstant.LIGHTSKETCH, R.drawable.light_pencil_shade));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DARK_SKETCH, R.drawable.dark_pencil_sketch));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DARK_STROKE, R.drawable.dark_stroke));
        this.pencilEffects.add(new PencilEffect(EffectConstant.CARTOON_FILTER, R.drawable.cartoon_edge));
        this.pencilEffects.add(new PencilEffect(EffectConstant.PAPER_SKETCH, R.drawable.sketch_texture1_icon));
        this.pencilEffects.add(new PencilEffect(EffectConstant.COLOR_SKETCH, R.drawable.color_sketch));
        this.pencilEffects.add(new PencilEffect(EffectConstant.COLOR_SKETCH_TWO, R.drawable.color_pencil));
        this.pencilEffects.add(new PencilEffect(EffectConstant.PENCIL_SKETCH,R.drawable.light_pencil));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DRAWING, R.drawable.drawing));
        this.pencilEffects.add(new PencilEffect(EffectConstant.CHALK, R.drawable.color_chalk));
        this.pencilEffects.add(new PencilEffect(EffectConstant.WATER_PAINTING, R.drawable.water_paint));
        this.pencilEffects.add(new PencilEffect(EffectConstant.WATER_PAINTING_TWO, R.drawable.water_paint_two));
        this.pencilEffects.add(new PencilEffect(EffectConstant.GRAIN, R.drawable.grain));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DARK_SHADING, R.drawable.dark_shade));
        this.pencilEffects.add(new PencilEffect(EffectConstant.WATER_COLOR,R.drawable.water_color));
        this.pencilEffects.add(new PencilEffect(EffectConstant.SILHOUTE, R.drawable.silhoutte));
        this.pencilEffects.add(new PencilEffect(EffectConstant.CRAYON, R.drawable.crayon));
        this.pencilEffects.add(new PencilEffect(EffectConstant.SILHOUTE_TWO, R.drawable.gothic));
        this.pencilEffects.add(new PencilEffect(EffectConstant.CARTOON, R.drawable.graffiti));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DRAWING_TWO, R.drawable.drawing_two));
        this.pencilEffects.add(new PencilEffect(EffectConstant.COLOR_PENCIL, R.drawable.colorpencil));
        this.pencilEffects.add(new PencilEffect(EffectConstant.BLACK_N_WHITE, R.drawable.black_and_white));
        int i = 0;
        for (int i2 = Constants.EFFECT_AD_BUTTON_SPACE; i2 <= this.pencilEffects.size(); i2 += Constants.EFFECT_AD_BUTTON_SPACE + 1) {
            if (i < NativeAdUtil.getInstance().getEffectNativeAds().size()) {
                this.pencilEffects.add(i2, NativeAdUtil.getInstance().getEffectNativeAds().get(i));
                i++;
            }
        }
        Log.d("effectnative no of ads", String.valueOf(NativeAdUtil.getInstance().getEffectNativeAds().size()));
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        this.v = layoutInflater.inflate(R.layout.effect_fragment_layout, viewGroup, false);
        this.mRecyclerView = (RecyclerView) this.v.findViewById(R.id.my_recycler_view);
        this.mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        Log.d("EffectFragment", "index " + String.valueOf(this.effectIndex));
        return this.v;
    }

    public void setEffectListener(EffectButtonListener effectButtonListener) {
        this.listener = effectButtonListener;
    }

    public void reset() {
        this.mAdapter.UncheckAll();
        this.mAdapter.notifyDataSetChanged();
    }

    public void onStart() {
        super.onStart();
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        Log.d("EffectFragment", "Start");
    }

    public void onResume() {
        super.onResume();
        showMessage("EffectFragment   ", "Resume");
    }

    public void onPause() {
        super.onPause();
        showMessage("EffectFragment", "Pause");
    }

    public void onStop() {
        super.onStop();
        showMessage("EffectFragment", "Stop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        showMessage("EffectFragment", "DestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        showMessage("EffectFragment", "Destroy");
    }

    public void onDetach() {
        super.onDetach();
        showMessage("EffectFragment", "Detach");
        unbindDrawables(this.mRecyclerView);
    }

    public void unbindDrawables(View view) {
        try {
            if (view.getBackground() != null) {
                view.getBackground().setCallback((Drawable.Callback) null);
            }
            if (view instanceof ImageView) {
                Log.d("View", "imageView");
                ((ImageView) view).setImageBitmap((Bitmap) null);
            } else if (view instanceof ViewGroup) {
                Log.d("View", "viewGroup");
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    unbindDrawables(viewGroup.getChildAt(i));
                }
                if (!(view instanceof AdapterView)) {
                    viewGroup.removeAllViews();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String str, String str2) {
        Log.d(str, str2);
    }
}