package com.xfinity.util.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewThreadMainScheduler<T> protected constructor() : BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread())
