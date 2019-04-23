package com.wiser.tantan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wiser.tantan.view.SlidePageAdapter;

import java.util.List;

/**
 * @author Wiser
 */
public class NearbyPeopleAdapter extends SlidePageAdapter<NearbyPeopleModel> {

    private List<NearbyPeopleModel> peopleModels;

    public NearbyPeopleAdapter(List<NearbyPeopleModel> peopleModels) {
        this.peopleModels = peopleModels;
    }

    @Override
    public int getCounts() {
        return peopleModels.size();
    }

    @Override
    public NearbyPeopleModel getItem(int position) {
        return peopleModels.get(position);
    }

    @Override
    public View getItemView(ViewGroup viewGroup, int position) {
        View view = inflater(viewGroup, R.layout.item_slide);
        ImageView ivNearlyPeople = view.findViewById(R.id.iv_nearby_people);
        Glide.with(context()).load(peopleModels.get(position).url).into(ivNearlyPeople);
        return view;
    }

    @Override
    public void removeTopView() {
        peopleModels.remove(0);
//        notifyDataAdapter();
    }
}
