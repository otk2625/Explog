package com.hongsup.explog.view.signup.contract;

import android.view.View;

import com.hongsup.explog.data.sign.SignUp;

/**
 * Created by Android Hong on 2017-11-30.
 */

public interface SignUpContract {

    interface iView{
        View getView();
        void setPresenter(iPresenter presenter);

        void showProgress();
        void hideProgress();
        void showError();

        void goSignIn();
    }

    interface iPresenter{
        void attachView(iView view);
        void setSignUp(SignUp signUp);
    }
}
