/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hannesdorfmann.mosby3.mvp


/**
 * The base interface for each mvp presenter.
 *
 *
 *
 * Mosby assumes that all interaction (i.e. updating the View) between Presenter and View is
 * executed on Android's main UI thread.
 *
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
interface MvpPresenter<V : MvpView> {

    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    fun attachView(view: V)

    /**
     * Will be called if the view has been detached from the Presenter.
     * Usually this happens on screen orientation changes or view (like fragment) has been put on the backstack.
     */
    @UiThread
    fun detachView()

    /**
     * Will be called if the presenter is no longer needed because the View has been destroyed permanently.
     * This is where you do clean up stuff.
     */
    @UiThread
    fun destroy()
}
