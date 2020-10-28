# Courseji

课记-你的课堂签到小助手

# 1. 简介

近几年，全国各大高校都在加强对学生考勤的管理，并把考勤作为学生考核的一个指标。目前，传统的学生考勤系统存在失误率较高、功能较少、无法识别学生是否本人到场等问题，导致很多学校出现了考勤系统使用率低、易代刷等现象，并且没有做到真正的智能化。为了帮助学校更好地管理学生的考勤信息，同时也让学生们能便捷地查询自己的考勤及个人课表等信息，该课题拟设计并实现一款通过人脸识别、活体检测进行考勤及个人信息查询的系统。
该系统主要可以分为学生端和教师端。
学生端包括个人信息管理、个人课表查询、学生上课人脸识别签到、学生考勤查询等模块。学生在注册个人信息时，需要上传自己的人脸信息，并根据人脸信息及身份证信息来判断该学生是否如实上传。学生来上课时需要开启蓝牙连接对应教室的蓝牙设备后再通过人脸识别进行考勤签到，签到时可以判断学生是否佩戴口罩、是否处于教室内、照片内是否是真人等。同时也可以在系统中发起请假申请，待教师审核。
教师端包括教师个人信息管理、教师课表查询、学生到课率查询等模块。教师可以调整上课时间，调整后系统会自动通知对应的学生；也可以对学生的请假申请进行处理。教师在上课途中可以随时开启学生点名签到，也可以查询本节课学生的到课率。

# 2. 使用到的技术

- [ConstraintLayout 约束布局](https://developer.android.com/training/constraint-layout?hl=zh-cn)
- ViewPager2
- [Jetpack-Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle?hl=zh-cn)
- [Jetpack-ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Jetpack-LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata)
- [Jetpack-Startup](https://developer.android.com/topic/libraries/app-startup)
- [square/retrofit 网络请求库](https://github.com/square/retrofit)
- [square/okhttp 网络请求库](https://github.com/square/okhttp)
- [square/leakcanary 内存泄漏检测](https://github.com/square/leakcanary)
- [bumptech/glide 图片加载库](https://github.com/bumptech/glide)
- [InsertKoinIO/koin Kotlin的依赖注入](https://github.com/InsertKoinIO/koin)
- [yanzhenjie/AndPermission Android动态权限请求封装库](https://github.com/yanzhenjie/AndPermission)
- [GrenderG/Toasty 定制化Toast](https://github.com/GrenderG/Toasty)
- [renxuelong/ComponentDemo 一个组件化模板库](https://github.com/renxuelong/ComponentDemo)
- [alibaba/ARouter 组件化路由库](https://github.com/alibaba/ARouter)
- [Travis CI/CD](https://travis-ci.org/)
- [CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

# 3. 版本日志

## v0.0.1
- 初始化项目
- 初始化 [Travis CI/CD](https://travis-ci.org/)
- 初始化Github Actions