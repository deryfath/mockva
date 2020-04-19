package com.project.daksatest.mvp

interface Presenter<in T: View> {

    fun onAttach(view: T)
    fun onDetach()

}