 
package com.nike.ntc_cn.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ValueChooserDialogFragment extends DialogFragment {
  public static final String TAG = ValueChooserDialogFragment.class.getSimpleName();

  public interface OnSettingSelectedListener {
    public void onSettingSelected(int id, int item);
  }

  private static final String ARG_ID = TAG+":id";
  private static final String ARG_TITLE = TAG+":title";
  private static final String ARG_ITEMS_ARRAY = TAG+":items_array";

  private OnSettingSelectedListener mSettingSelectedListener;

  public static ValueChooserDialogFragment newInstance(int id, int titleId, int itemsArrayId) {
    final ValueChooserDialogFragment fragment = new ValueChooserDialogFragment();
    final Bundle args = new Bundle();
    args.putInt(ARG_ID, id);
    args.putInt(ARG_TITLE, titleId);
    args.putInt(ARG_ITEMS_ARRAY, itemsArrayId);
    fragment.setArguments(args);
    return fragment;
  }

  public void setOnSettingsSelectedListener(OnSettingSelectedListener listener) {
    mSettingSelectedListener = listener;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final int id = getArguments().getInt(ARG_ID);
    final int titleId = getArguments().getInt(ARG_TITLE);
    final int itemsArrayId = getArguments().getInt(ARG_ITEMS_ARRAY);
    final String[] items = getResources().getStringArray(itemsArrayId);

    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
    .setTitle(titleId)
    .setItems(items, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int item) {
        if (mSettingSelectedListener != null)
          mSettingSelectedListener.onSettingSelected(id, item);
      }
    });

    return builder.create();
  }
}
