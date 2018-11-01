package com.xfinity.util.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SingleMainScheduler<T> protected constructor() : BaseScheduler<T>(Schedulers.single(), AndroidSchedulers.mainThread())