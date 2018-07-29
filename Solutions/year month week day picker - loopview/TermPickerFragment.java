package com.ifs.vpbs.view.contracts;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifs.vpbs.R;
import com.weigan.loopview.LoopView;

import java.io.PrintWriter;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermPickerFragment extends BottomSheetDialogFragment {
    public static final String TAG = TermPickerFragment.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    TermPickerListener listener;

    @BindView(R.id.loop_view_y)
    LoopView loopViewY;
    @BindView(R.id.loop_view_m)
    LoopView loopViewM;
    @BindView(R.id.loop_view_w)
    LoopView loopViewW;
    @BindView(R.id.loop_view_d)
    LoopView loopViewD;
    String message = "";

    private String year = "";
    private String month = "";
    private String week = "";
    private String day = "";

    public static TermPickerFragment newInstance(String message) {
        TermPickerFragment f = new TermPickerFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_term_picker, container, false);
        ButterKnife.bind(this, rootView);
        init();
        if (getArguments() != null) {
            message = getArguments().getString(EXTRA_MESSAGE);
        }
        return rootView;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    private int getTerm(String term, String s) {
        if (term.length() == 0) {
            return 0;
        }
        term = term + "_";
        String[] y = term.split(s);
        if (y.length > 1) {
            String[] x = y[0].split("[a-zA-Z]");
            int p = Integer.valueOf(x[x.length - 1]);
            if (s.equals("Y"))
                year = p + "Y";
            if (s.equals("M"))
                month = p + "M";
            if (s.equals("W"))
                week = p + "W";
            if (s.equals("D"))
                day = p + "D";

            return p;
        }

        return 0;
    }


    public void setTermListener(TermPickerListener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.tv_select)
    public void selectTerm() {
        String term = String.format("%s%s%s%s", year, month, week, day);
        listener.onSelectTerm(term);
        this.dismiss();
    }

    @OnClick(R.id.tv_cancel)
    public void cancelTerm() {
        this.dismiss();
    }

    private void init() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i + "Y");
        }

        ArrayList<String> listM = new ArrayList<>();
        for (int i = 0; i < 61; i++) {
            listM.add(i + "M");
        }

        ArrayList<String> listW = new ArrayList<>();
        for (int i = 0; i < 53; i++) {
            listW.add(i + "W");
        }

        ArrayList<String> listD = new ArrayList<>();
        for (int i = 0; i < 366; i++) {
            listD.add(i + "D");
        }


        loopViewY.setListener(index -> {
            if (index != 0) {
                year = list.get(index);
            } else {
                year = "";
            }
        });
        loopViewY.setItems(list);
        loopViewY.setInitPosition(getTerm(message, "Y"));

        loopViewM.setListener(index -> {
            if (index != 0) {
                month = listM.get(index);
            } else {
                month = "";
            }
        });
        loopViewM.setItems(listM);
        loopViewM.setInitPosition(getTerm(message, "M"));

        loopViewW.setListener(index -> {
            if (index != 0 && index != 4) {
                week = listW.get(index);
            } else {
                week = "";
            }
        });
        loopViewW.setItems(listW);
        loopViewW.setInitPosition(getTerm(message, "W"));

        loopViewD.setListener(index -> {
            if (index != 0) {
                day = listD.get(index);
            } else {
                day = "";
            }

        });
        loopViewD.setItems(listD);
        loopViewD.setInitPosition(getTerm(message, "D"));
    }

    public interface TermPickerListener {
        void onSelectTerm(String term);
    }

}
