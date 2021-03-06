package com.hongsup.explog.view.cover.presenter;

import android.util.Log;

import com.hongsup.explog.data.post.PostCover;
import com.hongsup.explog.data.post.source.PostRepository;
import com.hongsup.explog.view.cover.contract.CoverContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Android Hong on 2017-12-14.
 */

public class CoverPresenter implements CoverContract.iPresenter {

    private CoverContract.iView view;
    private PostRepository repository;

    public CoverPresenter() {
        repository = PostRepository.getInstance();
    }

    @Override
    public void attachView(CoverContract.iView view) {
        this.view = view;
    }

    @Override
    public void uploadCover(PostCover cover) {
        view.showProgress();
        Observable<Response<PostCover>> observable = repository.uploadPostCover(cover);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.e(TAG, "uploadCover: " + data.code() +", " + data.message() );
                    if(data.isSuccessful()){
                        view.hideProgress();
                        view.goToPost(data.body());
                    }else{
                        view.hideProgress();
                        view.showError("error 1");
                    }
                }, throwable -> {
                    view.hideProgress();
                    view.showError("error 2" + throwable.getMessage());
                    Log.e(TAG, "uploadCover: " + throwable.getMessage());
                });
    }
}
