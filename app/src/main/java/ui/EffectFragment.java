package ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Registry;

import com.teamvinay.sketch.EffectFilterTask;
import com.teamvinay.sketch.EffectsAdapter;
import com.teamvinay.sketch.MainActivity;
import com.teamvinay.sketch.PencilEffect;
import com.teamvinay.sketch.PhotoActivity;
import com.teamvinay.sketch.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import helper.Constants;
import helper.EffectConstant;
import helper.ExportDetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import listeners.RecyclerViewClickListener;
import listeners.ResetButtonListener;
import listeners.ResetListener;
import listeners.TaskCompleteListener;
import org.opencv.core.Mat;
import util.AdUtil;
import util.BitmapCache;
import util.Effect;
import util.FireBaseHelper;
import util.NativeAdUtil;
import util.Utils;

/* renamed from: ui.EffectFragment */
public class EffectFragment extends Fragment implements RecyclerViewClickListener, OnSeekChangeListener, ResetListener {
    /* access modifiers changed from: private */
    public int adjustValue = -1;
    /* access modifiers changed from: private */
    public BitmapCache bitmapCache;
    /* access modifiers changed from: private */
    public Bitmap blendBitmap;
    /* access modifiers changed from: private */
    public final CharSequence[] colorchalk = {"Thin Edge", "Medium Edge", "Thick Edge"};
    /* access modifiers changed from: private */
    public Effect effect;
    /* access modifiers changed from: private */
    public Dialog effectLoadingDialog;
    /* access modifiers changed from: private */
    public String effectName = null;
    /* access modifiers changed from: private */
    public boolean effectSelected = false;
    /* access modifiers changed from: private */
    public Mat filtered;
    /* access modifiers changed from: private */
    public Mat finalmat;
    private boolean freshStart = true;
    /* access modifiers changed from: private */
    public HashMap<String, Boolean> hashMap;
    /* access modifiers changed from: private */
    public ImageView imageView;
    LayoutInflater layout_inflater = null;
    /* access modifiers changed from: private */
    public Dialog loadingDialog;
    private EffectsAdapter mAdapter;
    /* access modifiers changed from: private */
    public Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public Mat original;
    private List<Object> pencilEffects = new ArrayList();
    private RecyclerView recyclerView;
    /* access modifiers changed from: private */
    public ResetButtonListener resetButtonListener;
    /* access modifiers changed from: private */
    public boolean resume = false;
    private IndicatorSeekBar seekBar;

    public void onSeeking(SeekParams seekParams) {
    }

    public void setResetButtonListener(ResetButtonListener resetButtonListener2) {
        this.resetButtonListener = resetButtonListener2;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        Log.d("EffectFragmentActivity", "onAttach");
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Log.d("EffectFragmentActivity", "onCreate");
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.effect_fragment, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.my_recycler_view);
        this.imageView = (ImageView) inflate.findViewById(R.id.gpuimage);
        this.seekBar = (IndicatorSeekBar) inflate.findViewById(R.id.seek_second);
        if (this.freshStart) {
            initializeContent();
            this.freshStart = false;
            this.bitmapCache = new BitmapCache(getActivity());
            this.effect = new Effect(getActivity(), this.bitmapCache);
            new StartUpLoadingTask(getActivity()).execute(new Void[0]);
        }
        this.layout_inflater = layoutInflater;
        this.mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        this.seekBar.setOnSeekChangeListener(this);
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffect(this.effect);
        ExportDetails.getInstance().setBitmapCache(this.bitmapCache);
        this.hashMap = new HashMap<>();
        this.original = new Mat();
        this.filtered = new Mat();
        this.finalmat = new Mat();
        return inflate;
    }

    public void setImageViewBitmap() {
        if (PhotoActivity.bitmap != null) {
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
            return;
        }
        try {
            PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), (TaskCompleteListener) null, (Dialog) null).execute(new Void[0]).get();
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        Log.d("EffectFragmentActivity", "onViewStateRestored");
    }

    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    public void onStart() {
        super.onStart();
        Log.d("EffectFragmentActivity", "onStart");
    }

    public void onResume() {
        super.onResume();
        ExportDetails.getInstance().setFragment_title("EffectFragment");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.d("EffectContext", "context");
                if (EffectFragment.this.getActivity() != null) {
                    EffectFragment.this.Resume();
                } else if (EffectFragment.this.mContext != null) {
                    EffectFragment.this.Resume();
                } else {
                    handler.postDelayed(this, 50);
                }
            }
        }, 300);
    }

    /* access modifiers changed from: package-private */
    public void Resume() {
        if (!this.freshStart) {
            if (this.effectName == null) {
                setImageViewBitmap();
                if (!this.effectSelected && this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonDeActivate();
                }
            } else if (this.effectSelected) {
                this.imageView.setImageBitmap((Bitmap) null);
                this.resume = true;
                if (getActivity() != null) {
                    new EffectApplyingTask(getActivity(), this.effectName).execute(new Void[0]);
                } else if (this.mContext != null) {
                    new EffectApplyingTask(this.mContext, this.effectName).execute(new Void[0]);
                }
                Log.d("EffectContext", "context");
            } else {
                setImageViewBitmap();
                if (!this.effectSelected && this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonDeActivate();
                }
            }
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (EffectFragment.this.getActivity() != null) {
                    ((PhotoActivity) EffectFragment.this.getActivity()).setResetListener(EffectFragment.this);
                } else {
                    handler.postDelayed(this, 50);
                }
            }
        }, 100);
    }

    public void onPause() {
        super.onPause();
        Log.d("EffectFragmentActivity", "onPause");
        this.imageView.setImageBitmap((Bitmap) null);
        resetSequence();
    }

    public void onStop() {
        super.onStop();
        Log.d("EffectFragmentActivity", "onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d("EffectFragmentActivity", "onDestroyView   ");
        PhotoActivity.effectFilterbitmap = null;
        this.imageView.setImageBitmap((Bitmap) null);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.effectLoadingDialog != null && this.effectLoadingDialog.isShowing()) {
            this.effectLoadingDialog.dismiss();
        }
        Log.d("EffectFragmentActivity", "onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d("EffectFragmentActivity", "onDetach");
        if (this.original != null) {
            this.original.release();
        }
        if (this.filtered != null) {
            this.filtered.release();
        }
        if (this.finalmat != null) {
            this.finalmat.release();
        }
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
        this.pencilEffects.add(new PencilEffect(EffectConstant.PENCIL_SKETCH, R.drawable.light_pencil));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DRAWING, R.drawable.drawing));
        this.pencilEffects.add(new PencilEffect(EffectConstant.CHALK, R.drawable.color_chalk));
        this.pencilEffects.add(new PencilEffect(EffectConstant.WATER_PAINTING, R.drawable.water_paint));
        this.pencilEffects.add(new PencilEffect(EffectConstant.WATER_PAINTING_TWO, R.drawable.water_paint_two));
        this.pencilEffects.add(new PencilEffect(EffectConstant.GRAIN, R.drawable.grain));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DARK_SHADING, R.drawable.dark_shade));
        this.pencilEffects.add(new PencilEffect(EffectConstant.WATER_COLOR, R.drawable.water_color));
        this.pencilEffects.add(new PencilEffect(EffectConstant.SILHOUTE, R.drawable.silhoutte));
        this.pencilEffects.add(new PencilEffect(EffectConstant.CRAYON, R.drawable.crayon));
        this.pencilEffects.add(new PencilEffect(EffectConstant.SILHOUTE_TWO, R.drawable.gothic));
        this.pencilEffects.add(new PencilEffect(EffectConstant.CARTOON, R.drawable.graffiti));
        this.pencilEffects.add(new PencilEffect(EffectConstant.DRAWING_TWO, R.drawable.drawing_two));
        this.pencilEffects.add(new PencilEffect(EffectConstant.COLOR_PENCIL, R.drawable.colorpencil));
        this.pencilEffects.add(new PencilEffect(EffectConstant.BLACK_N_WHITE, R.drawable.black_and_white));
        if (!Constants.REMOVE_ADS && Constants.BUTTON_ADS) {
            int i = 0;
            for (int i2 = Constants.EFFECT_AD_BUTTON_SPACE; i2 <= this.pencilEffects.size(); i2 += Constants.EFFECT_AD_BUTTON_SPACE + 1) {
                if (i < NativeAdUtil.getInstance().getEffectNativeAds().size() && !Constants.REMOVE_ADS) {
                    this.pencilEffects.add(i2, NativeAdUtil.getInstance().getEffectNativeAds().get(i));
                    i++;
                }
            }
        }
        this.mAdapter = new EffectsAdapter(getContext(), this.pencilEffects, this);
        Log.d("effectnative no of ads", String.valueOf(NativeAdUtil.getInstance().getEffectNativeAds().size()));
    }

    public void onEffect(String str, int i) {
        if (str.equals("CHALK")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Color Chalk-Choose Effect");
            builder.setItems(this.colorchalk, new DialogInterface.OnClickListener() {
                /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
                /* JADX WARNING: Removed duplicated region for block: B:18:0x00c2  */
                /* JADX WARNING: Removed duplicated region for block: B:19:0x013d  */
                /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onClick(android.content.DialogInterface r7, int r8) {
                    /*
                        r6 = this;
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        java.lang.CharSequence[] r7 = r7.colorchalk
                        r7 = r7[r8]
                        java.lang.String r7 = r7.toString()
                        int r0 = r7.hashCode()
                        r1 = -1728623736(0xffffffff98f74b88, float:-6.392427E-24)
                        r2 = 0
                        r3 = 1
                        if (r0 == r1) goto L_0x0036
                        r1 = 751101088(0x2cc4e4a0, float:5.5960375E-12)
                        if (r0 == r1) goto L_0x002c
                        r1 = 1240849124(0x49f5dae4, float:2014044.5)
                        if (r0 == r1) goto L_0x0022
                        goto L_0x0040
                    L_0x0022:
                        java.lang.String r0 = "Thin Edge"
                        boolean r7 = r7.equals(r0)
                        if (r7 == 0) goto L_0x0040
                        r7 = 0
                        goto L_0x0041
                    L_0x002c:
                        java.lang.String r0 = "Thick Edge"
                        boolean r7 = r7.equals(r0)
                        if (r7 == 0) goto L_0x0040
                        r7 = 2
                        goto L_0x0041
                    L_0x0036:
                        java.lang.String r0 = "Medium Edge"
                        boolean r7 = r7.equals(r0)
                        if (r7 == 0) goto L_0x0040
                        r7 = 1
                        goto L_0x0041
                    L_0x0040:
                        r7 = -1
                    L_0x0041:
                        switch(r7) {
                            case 0: goto L_0x013d;
                            case 1: goto L_0x00c2;
                            case 2: goto L_0x0046;
                            default: goto L_0x0044;
                        }
                    L_0x0044:
                        goto L_0x01b7
                    L_0x0046:
                        ui.EffectFragment$EffectApplyingTask r7 = new ui.EffectFragment$EffectApplyingTask
                        ui.EffectFragment r0 = p049ui.EffectFragment.this
                        ui.EffectFragment r1 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r1 = r1.getActivity()
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        r4.<init>()
                        java.lang.String r5 = "Color "
                        r4.append(r5)
                        ui.EffectFragment r5 = p049ui.EffectFragment.this
                        java.lang.CharSequence[] r5 = r5.colorchalk
                        r5 = r5[r8]
                        java.lang.String r5 = r5.toString()
                        r4.append(r5)
                        java.lang.String r4 = r4.toString()
                        r7.<init>(r1, r4)
                        java.lang.Void[] r0 = new java.lang.Void[r2]
                        r7.execute(r0)
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        boolean unused = r7.effectSelected = r3
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Color "
                        r0.append(r1)
                        ui.EffectFragment r1 = p049ui.EffectFragment.this
                        java.lang.CharSequence[] r1 = r1.colorchalk
                        r8 = r1[r8]
                        java.lang.String r8 = r8.toString()
                        r0.append(r8)
                        java.lang.String r8 = r0.toString()
                        java.lang.String unused = r7.effectName = r8
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r7 = r7.getActivity()
                        com.hqgames.pencil.sketch.photo.PhotoActivity r7 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r7
                        com.hqgames.pencil.sketch.photo.EffectFilterDetails r7 = r7.getEffectFilterDetails()
                        r7.setEffectSelected(r3)
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r7 = r7.getActivity()
                        com.hqgames.pencil.sketch.photo.PhotoActivity r7 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r7
                        com.hqgames.pencil.sketch.photo.EffectFilterDetails r7 = r7.getEffectFilterDetails()
                        ui.EffectFragment r8 = p049ui.EffectFragment.this
                        java.lang.String r8 = r8.effectName
                        r7.setEffectName(r8)
                        goto L_0x01b7
                    L_0x00c2:
                        ui.EffectFragment$EffectApplyingTask r7 = new ui.EffectFragment$EffectApplyingTask
                        ui.EffectFragment r0 = p049ui.EffectFragment.this
                        ui.EffectFragment r1 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r1 = r1.getActivity()
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        r4.<init>()
                        java.lang.String r5 = "Color "
                        r4.append(r5)
                        ui.EffectFragment r5 = p049ui.EffectFragment.this
                        java.lang.CharSequence[] r5 = r5.colorchalk
                        r5 = r5[r8]
                        java.lang.String r5 = r5.toString()
                        r4.append(r5)
                        java.lang.String r4 = r4.toString()
                        r7.<init>(r1, r4)
                        java.lang.Void[] r0 = new java.lang.Void[r2]
                        r7.execute(r0)
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        boolean unused = r7.effectSelected = r3
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Color "
                        r0.append(r1)
                        ui.EffectFragment r1 = p049ui.EffectFragment.this
                        java.lang.CharSequence[] r1 = r1.colorchalk
                        r8 = r1[r8]
                        java.lang.String r8 = r8.toString()
                        r0.append(r8)
                        java.lang.String r8 = r0.toString()
                        java.lang.String unused = r7.effectName = r8
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r7 = r7.getActivity()
                        com.hqgames.pencil.sketch.photo.PhotoActivity r7 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r7
                        com.hqgames.pencil.sketch.photo.EffectFilterDetails r7 = r7.getEffectFilterDetails()
                        r7.setEffectSelected(r3)
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r7 = r7.getActivity()
                        com.hqgames.pencil.sketch.photo.PhotoActivity r7 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r7
                        com.hqgames.pencil.sketch.photo.EffectFilterDetails r7 = r7.getEffectFilterDetails()
                        ui.EffectFragment r8 = p049ui.EffectFragment.this
                        java.lang.String r8 = r8.effectName
                        r7.setEffectName(r8)
                        goto L_0x01b7
                    L_0x013d:
                        ui.EffectFragment$EffectApplyingTask r7 = new ui.EffectFragment$EffectApplyingTask
                        ui.EffectFragment r0 = p049ui.EffectFragment.this
                        ui.EffectFragment r1 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r1 = r1.getActivity()
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        r4.<init>()
                        java.lang.String r5 = "Color "
                        r4.append(r5)
                        ui.EffectFragment r5 = p049ui.EffectFragment.this
                        java.lang.CharSequence[] r5 = r5.colorchalk
                        r5 = r5[r8]
                        java.lang.String r5 = r5.toString()
                        r4.append(r5)
                        java.lang.String r4 = r4.toString()
                        r7.<init>(r1, r4)
                        java.lang.Void[] r0 = new java.lang.Void[r2]
                        r7.execute(r0)
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        boolean unused = r7.effectSelected = r3
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Color "
                        r0.append(r1)
                        ui.EffectFragment r1 = p049ui.EffectFragment.this
                        java.lang.CharSequence[] r1 = r1.colorchalk
                        r8 = r1[r8]
                        java.lang.String r8 = r8.toString()
                        r0.append(r8)
                        java.lang.String r8 = r0.toString()
                        java.lang.String unused = r7.effectName = r8
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r7 = r7.getActivity()
                        com.hqgames.pencil.sketch.photo.PhotoActivity r7 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r7
                        com.hqgames.pencil.sketch.photo.EffectFilterDetails r7 = r7.getEffectFilterDetails()
                        r7.setEffectSelected(r3)
                        ui.EffectFragment r7 = p049ui.EffectFragment.this
                        androidx.fragment.app.FragmentActivity r7 = r7.getActivity()
                        com.hqgames.pencil.sketch.photo.PhotoActivity r7 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r7
                        com.hqgames.pencil.sketch.photo.EffectFilterDetails r7 = r7.getEffectFilterDetails()
                        ui.EffectFragment r8 = p049ui.EffectFragment.this
                        java.lang.String r8 = r8.effectName
                        r7.setEffectName(r8)
                    L_0x01b7:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: p049ui.EffectFragment.C51263.onClick(android.content.DialogInterface, int):void");
                }
            });
            AlertDialog create = builder.create();
            create.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                }
            });
            create.show();
        } else {
            this.effectName = str;
            this.effectSelected = true;
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffectSelected(true);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffectName(this.effectName);
            new EffectApplyingTask(getActivity(), str).execute(new Void[0]);
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence() != null) {
            for (int i2 = 0; i2 < ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().size(); i2++) {
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().get(i2).equals("effect")) {
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().remove(i2);
                } else if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().get(i2).equals("adjust")) {
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().remove(i2);
                }
            }
        }
        ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("effect");
    }

    public void onRemove() {
        this.effectSelected = false;
        ExportDetails.getInstance().setEffectSelected(false);
        if (PhotoActivity.bitmap != null) {
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
        } else {
            try {
                PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), (TaskCompleteListener) null, (Dialog) null).execute(new Void[0]).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
        }
        this.seekBar.setVisibility(View.INVISIBLE);
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffectSelected(false);
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffectName((String) null);
        ExportDetails.getInstance().setAdjustValue(0);
        this.adjustValue = 0;
        if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence() != null) {
            for (int i = 0; i < ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().size(); i++) {
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().get(i).equals("effect")) {
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().remove(i);
                } else if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().get(i).equals("adjust")) {
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().remove(i);
                }
            }
        }
        if (this.resetButtonListener != null) {
            this.resetButtonListener.OnResetButtonDeActivate();
        }
    }

    public void onStartTrackingTouch(IndicatorSeekBar indicatorSeekBar) {
        if (PhotoActivity.bitmap == null) {
            try {
                PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), (TaskCompleteListener) null, (Dialog) null).execute(new Void[0]).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        if (PhotoActivity.bitmap != null && this.effectName != null && PhotoActivity.effectFilterbitmap == null) {
            PhotoActivity.effectFilterbitmap = Utils.convert(this.effect.getEffect(this.effectName, PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true)), Bitmap.Config.RGB_565);
        }
    }

    public void onStopTrackingTouch(IndicatorSeekBar indicatorSeekBar) {
        org.opencv.android.Utils.bitmapToMat(PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true), this.original);
        org.opencv.android.Utils.bitmapToMat(PhotoActivity.effectFilterbitmap.copy(PhotoActivity.effectFilterbitmap.getConfig(), true), this.filtered);
        this.blendBitmap = PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true);
        this.blendBitmap = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.blendBitmap, indicatorSeekBar.getProgress());
        this.imageView.setImageBitmap(this.blendBitmap);
        this.adjustValue = indicatorSeekBar.getProgress();
        ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("adjust");
        ExportDetails.getInstance().setAdjustValue(this.adjustValue);
        Log.d("adjustValue", String.valueOf(this.adjustValue));
    }

    public void OnReset() {
        this.effectSelected = false;
        if (this.mAdapter != null) {
            this.mAdapter.UncheckAll();
            this.mAdapter.notifyDataSetChanged();
        }
        ExportDetails.getInstance().setEffectSelected(false);
        if (PhotoActivity.bitmap != null) {
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
        } else {
            try {
                PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), (TaskCompleteListener) null, (Dialog) null).execute(new Void[0]).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
        }
        this.seekBar.setVisibility(View.INVISIBLE);
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffectSelected(false);
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffectName((String) null);
        ExportDetails.getInstance().setAdjustValue(0);
        this.adjustValue = 0;
        if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence() != null) {
            for (int i = 0; i < ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().size(); i++) {
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().get(i).equals("effect")) {
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().remove(i);
                } else if (((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().get(i).equals("adjust")) {
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().remove(i);
                }
            }
        }
        if (this.resetButtonListener != null) {
            this.resetButtonListener.OnResetButtonDeActivate();
        }
    }

    /* renamed from: ui.EffectFragment$EffectApplyingTask */
    private class EffectApplyingTask extends AsyncTask<Void, Void, Void> {
        String effectName = null;

        public EffectApplyingTask(Context context, String str) {
            this.effectName = str;
            Dialog unused = EffectFragment.this.effectLoadingDialog = new Dialog(context, R.style.EffectDialogTheme);
            EffectFragment.this.effectLoadingDialog.requestWindowFeature(1);
            EffectFragment.this.effectLoadingDialog.setContentView(R.layout.effect_loading_dialog);
            EffectFragment.this.effectLoadingDialog.setCancelable(false);
            EffectFragment.this.effectLoadingDialog.show();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            if (PhotoActivity.effectFilterbitmap != null) {
                PhotoActivity.effectFilterbitmap = null;
            }
            if (EffectFragment.this.blendBitmap != null) {
                Bitmap unused = EffectFragment.this.blendBitmap = null;
            }
            if (!EffectFragment.this.resume) {
                int unused2 = EffectFragment.this.adjustValue = -1;
                ExportDetails.getInstance().setAdjustValue(-1);
                Log.d("EffectNewTask", "three pre");
            } else if (EffectFragment.this.adjustValue == -1 || ExportDetails.getInstance().getAdjustValue() == -1) {
                int unused3 = EffectFragment.this.adjustValue = -1;
                ExportDetails.getInstance().setAdjustValue(-1);
                Log.d("EffectNewTask", "two pre");
            } else {
                Log.d("EffectNewTask", "one pre");
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (EffectFragment.this.effectLoadingDialog != null && EffectFragment.this.effectLoadingDialog.isShowing()) {
                EffectFragment.this.effectLoadingDialog.dismiss();
            }
            if (EffectFragment.this.blendBitmap != null) {
                EffectFragment.this.imageView.setImageBitmap(EffectFragment.this.blendBitmap);
            } else {
                EffectFragment.this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
            }
            if (!EffectFragment.this.resume) {
                EffectFragment.this.showSeekbar(true);
            } else if (EffectFragment.this.adjustValue == -1 || ExportDetails.getInstance().getAdjustValue() == -1) {
                EffectFragment.this.showSeekbar(true);
            } else {
                EffectFragment.this.showSeekbar(false);
            }
            if (EffectFragment.this.effectSelected && EffectFragment.this.resetButtonListener != null) {
                EffectFragment.this.resetButtonListener.OnResetButtonActivate();
            }
            if (EffectFragment.this.hashMap.get(this.effectName) == null) {
                EffectFragment.this.hashMap.put(this.effectName, true);
                EffectFragment.this.effectApplied(EffectFragment.this.effect.getEffectName());
                Bundle bundle = new Bundle();
                bundle.putString("AdNetwork", "Genuine_Effect_AD_Count");
                FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
            }
            EffectFragment.this.LogEvent(this.effectName);
            Constants.EFFECT_AD_COUNT++;
            EffectFragment.this.checkForAd();
            boolean unused = EffectFragment.this.resume = false;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (PhotoActivity.bitmap != null) {
                PhotoActivity.effectFilterbitmap = Utils.convert(EffectFragment.this.effect.getEffect(this.effectName, PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true)), Bitmap.Config.RGB_565);
                if (ExportDetails.getInstance().getAdjustValue() == -1 || PhotoActivity.bitmap == null || PhotoActivity.effectFilterbitmap == null) {
                    return null;
                }
                org.opencv.android.Utils.bitmapToMat(PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true), EffectFragment.this.original);
                org.opencv.android.Utils.bitmapToMat(PhotoActivity.effectFilterbitmap.copy(PhotoActivity.effectFilterbitmap.getConfig(), true), EffectFragment.this.filtered);
                Bitmap unused = EffectFragment.this.blendBitmap = PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true);
                Bitmap unused2 = EffectFragment.this.blendBitmap = Utils.blendBitmaps(EffectFragment.this.original, EffectFragment.this.filtered, EffectFragment.this.finalmat, EffectFragment.this.blendBitmap, ExportDetails.getInstance().getAdjustValue());
                int unused3 = EffectFragment.this.adjustValue = ExportDetails.getInstance().getAdjustValue();
                Log.d("EffectNewTask", "first");
                return null;
            } else if (EffectFragment.this.bitmapCache == null || EffectFragment.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE) == null) {
                return null;
            } else {
                PhotoActivity.bitmap = EffectFragment.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE);
                PhotoActivity.effectFilterbitmap = Utils.convert(EffectFragment.this.effect.getEffect(this.effectName, PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true)), Bitmap.Config.RGB_565);
                if (ExportDetails.getInstance().getAdjustValue() == -1) {
                    return null;
                }
                org.opencv.android.Utils.bitmapToMat(PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true), EffectFragment.this.original);
                org.opencv.android.Utils.bitmapToMat(PhotoActivity.effectFilterbitmap.copy(PhotoActivity.effectFilterbitmap.getConfig(), true), EffectFragment.this.filtered);
                Bitmap unused4 = EffectFragment.this.blendBitmap = PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true);
                Bitmap unused5 = EffectFragment.this.blendBitmap = Utils.blendBitmaps(EffectFragment.this.original, EffectFragment.this.filtered, EffectFragment.this.finalmat, EffectFragment.this.blendBitmap, ExportDetails.getInstance().getAdjustValue());
                int unused6 = EffectFragment.this.adjustValue = ExportDetails.getInstance().getAdjustValue();
                Log.d("EffectNewTask", "second");
                return null;
            }
        }
    }

    /* renamed from: ui.EffectFragment$StartUpLoadingTask */
    private class StartUpLoadingTask extends AsyncTask<Void, Void, Void> {

        /* renamed from: re */
        Mat f6043re;

        public StartUpLoadingTask(Context context) {
            Dialog unused = EffectFragment.this.loadingDialog = new Dialog(context);
            EffectFragment.this.loadingDialog.requestWindowFeature(1);
            EffectFragment.this.loadingDialog.setContentView(R.layout.loading_dialog);
            EffectFragment.this.loadingDialog.setCancelable(false);
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            EffectFragment.this.loadingDialog.show();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            EffectFragment.this.loadingDialog.dismiss();
            EffectFragment.this.imageView.setImageBitmap(PhotoActivity.bitmap);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            EffectFragment.this.bitmapCache.addBitmapToCache(Constants.ORIGINAL_IMAGE, PhotoActivity.bitmap);
            if (PhotoActivity.bitmap == null) {
                return null;
            }
            EffectFragment.this.effect.loadImages(PhotoActivity.bitmap.getWidth(), PhotoActivity.bitmap.getHeight());
            return null;
        }
    }

    public void showSeekbar(boolean z) {
        this.seekBar.setMax(100.0f);
        if (z) {
            this.seekBar.setProgress(100.0f);
            this.adjustValue = -1;
        } else if (this.adjustValue >= 100 || this.adjustValue <= 0) {
            this.seekBar.setProgress(100.0f);
        } else {
            this.seekBar.setProgress((float) this.adjustValue);
        }
        Log.d("adjustValue", String.valueOf(this.adjustValue));
        this.seekBar.setVisibility(View.VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void resetSequence() {
        ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().clear();
        if (((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("effect");
        }
        if (ExportDetails.getInstance().getAdjustValue() != -1 && ExportDetails.getInstance().getAdjustValue() >= 0) {
            ExportDetails.getInstance().setAdjustValue(ExportDetails.getInstance().getAdjustValue());
            ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("adjust");
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails().isFilterSelected()) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("filter");
        }
    }

    public void LogEvent(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("effect_name", str);
        FireBaseHelper.getInstance().LogEvent("effect_applied", bundle);
    }

    /* access modifiers changed from: private */
    public void checkForAd() {
        if (Constants.REMOVE_ADS) {
            return;
        }
        if (Constants.AD_SHOWN_SPACE == null || !Constants.AD_SHOWN_SPACE.equals("EFFECT_EVENT")) {
            if (Constants.EFFECT_INTERSTITIAL && Constants.EFFECT_AD_COUNT >= Constants.EFFECT_AD_COUNT_THRESHOLD) {
                Bundle bundle = new Bundle();
                bundle.putString("AdNetwork", "Effect_AD_Count");
                FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
                Constants.EFFECT_AD_COUNT = 0;
                if (!Constants.FULL_SCREEN_Effect_NATIVE_FIRST) {
                    if (AdUtil.getInstance().isEffectInterstitialLoaded()) {
                        AdUtil.getInstance().ShowEffectInterstitial();
                    } /*else if (AdUtil.getInstance().isEffectNativeLoaded() && AdUtil.getInstance().getEffect_fb_native() != null) {
                        ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEffect_fb_native(), false);
                    }
                } else if (AdUtil.getInstance().isEffectNativeLoaded()) {
                    if (AdUtil.getInstance().getEffect_fb_native() != null) {
                        ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEffect_fb_native(), false);
                    }*/
                } else if (AdUtil.getInstance().isEffectInterstitialLoaded()) {
                    AdUtil.getInstance().ShowEffectInterstitial();
                }
            }
        } else if (Constants.EFFECT_AD_COUNT >= Constants.EFFECT_AD_COUNT_THRESHOLD) {
            Constants.EFFECT_AD_COUNT = 0;
            if (AdUtil.getInstance().isStartUpAdAvailable()) {
                AdUtil.getInstance().ShowStartUpInterstitial();
            }
        }
    }

    public void effectApplied(String str) {
        if (this.layout_inflater != null) {
            View inflate = this.layout_inflater.inflate(R.layout.custom_toast, (ViewGroup) null, false);
           // Log.d("Check", TtmlNode.TAG_LAYOUT);
            ((TextView) inflate.findViewById(R.id.text)).setText(str);
            final Toast toast = new Toast(getActivity());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(17, 0, 0);
            toast.setView(inflate);
            Log.d("Check", "toast layout");
            toast.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    toast.cancel();
                }
            }, 800);
        }
    }
}