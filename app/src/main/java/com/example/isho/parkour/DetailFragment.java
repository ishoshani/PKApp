package com.example.isho.parkour;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class DetailFragment extends Fragment {
    String title;
    Users UserFragment;
    FragmentTabHost host;
    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        host =  new FragmentTabHost(getActivity());
        host.setup(getActivity(),getChildFragmentManager(),android.R.id.tabcontent);

        host.addTab(host.newTabSpec("who").setIndicator("Who",null),Users.class,null);

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
    public void setSpot(String title){
        this.title=title;
    }

}
