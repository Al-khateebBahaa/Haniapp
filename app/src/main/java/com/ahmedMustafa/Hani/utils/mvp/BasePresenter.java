package com.ahmedMustafa.Hani.utils.mvp;

public class BasePresenter<V extends MvpVeiw> implements MvpPresenter<V> {
    private V view;
    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void setUserAsLoggedOut() {

    }
}
