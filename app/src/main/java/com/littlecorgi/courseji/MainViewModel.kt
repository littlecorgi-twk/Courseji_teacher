package com.littlecorgi.courseji

import androidx.lifecycle.ViewModel

/**
 * MainActivity的ViewModel
 * @author littlecorgi 2020/10/20
 */
class MainViewModel : ViewModel() {

    val mHeaderImageUrl =
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603190176378&di=26e4109c7a9fa324180f9deab9e8e595&imgtype=0&src=http%3A%2F%2Fdik.img.kttpdq.com%2Fpic%2F70%2F48558%2F2ca2385f8d04b4a5.jpg"
    val mBgImageUrl =
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603190406700&di=83c762b9884862270c98e3f2b1326286&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201504%2F02%2F140308w5n0nz3ns9b3bbzj.png"

    // 当前课程表正在显示的第几周的课程。但是ViewPager是从0开始的，所以
    // 0 -> 第一周   第1周 -> 0
    var currentWeek = 0
        get() = field + 1
        set(value) {
            field = value - 1
        }
}
