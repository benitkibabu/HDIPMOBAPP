package com.example.scr3.fragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment1 extends Fragment {


    public static ItemFragment1 newInstance(){
        ItemFragment1 fragment = new ItemFragment1();
//        Bundle args = new Bundle();
//        args.putCharSequence("", "");
//        fragment.setArguments();
        return fragment;
    }

    public ItemFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_fragment1, container, false);
    }

}
