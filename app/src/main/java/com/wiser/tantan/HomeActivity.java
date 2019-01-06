package com.wiser.tantan;

import android.content.Intent;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.tantan.view.SlidePageView;

import java.util.List;

import butterknife.BindView;

public class HomeActivity extends WISERActivity<HomeBiz> {

    @BindView(R.id.slide_view)
    SlidePageView pageView;

    @Override
    protected WISERBuilder build(WISERBuilder builder) {
        builder.layoutId(R.layout.activity_main);
        return builder;
    }

    @Override
    protected void initData(Intent intent) {
        biz().setData();
    }

    public void updateSlideView(List<NearbyPeopleModel> model) {
        pageView.setAdapter(new NearbyPeopleAdapter(model));
    }
}
