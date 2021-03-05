package com.littlecorgi.middle.ui.teacher;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.model.LocationShow;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocationAdapt extends BaseQuickAdapter<LocationShow, BaseViewHolder> {

  public LocationAdapt(int layoutResId, @Nullable List<LocationShow> data) {
    super(layoutResId, data);
  }

  @Override
  protected void convert(@NotNull BaseViewHolder baseViewHolder, LocationShow locationShow) {
    baseViewHolder
        .setText(R.id.location_item_placeName, locationShow.getPlaceName())
        .setText(R.id.location_item_distance, locationShow.getDistance() + "米");
    baseViewHolder.setGone(R.id.location_item_right, locationShow.isGone());
  }
}
