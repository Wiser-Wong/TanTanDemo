package com.wiser.tantan;

import com.wiser.library.base.WISERBiz;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 */
public class HomeBiz extends WISERBiz<HomeActivity> {

    public void setData() {
        List<NearbyPeopleModel> peopleModels = new ArrayList<>();
        NearbyPeopleModel model1 = new NearbyPeopleModel();
        model1.url = "http://pic1.16pic.com/00/50/63/16pic_5063862_b.jpg";
        NearbyPeopleModel model2 = new NearbyPeopleModel();
        model2.url = "http://pic28.photophoto.cn/20130903/0005018323970603_b.jpg";
        NearbyPeopleModel model3 = new NearbyPeopleModel();
        model3.url = "http://pic27.photophoto.cn/20130526/0005018300648072_b.jpg";
        peopleModels.add(model1);
        peopleModels.add(model2);
        peopleModels.add(model3);
        ui().updateSlideView(peopleModels);
    }

}
