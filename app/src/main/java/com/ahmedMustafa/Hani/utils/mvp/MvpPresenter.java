package com.ahmedMustafa.Hani.utils.mvp;

public interface MvpPresenter<V extends MvpVeiw> {

    void onAttach(V view);

    void onDetach();

    V getView();

    void setUserAsLoggedOut();
}
