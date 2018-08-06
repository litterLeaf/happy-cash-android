package com.yinshan.happycash.network.common.base;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2018/3/26.
 */

public class RxTransformer {

    public static <T> ObservableTransformer<T, T> io_main() {
        return  upstream ->
                upstream.subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread());
    }

}
