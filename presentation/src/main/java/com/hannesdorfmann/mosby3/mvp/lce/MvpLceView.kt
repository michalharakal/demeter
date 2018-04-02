package com.hannesdorfmann.mosby3.mvp.lce

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



import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.UiThread

/**
 * A [MvpView] that assumes that there are 3 display operation:
 *
 *  * [.showLoading]: Display a loading animation while loading data in background
 * by
 * invoking the corresponding presenter method
 *
 *  * [.showError]: Display a error view (a TextView) on the screen if
 * an error has occurred while loading data. You can distinguish between a pull-to-refresh error by
 * checking the boolean parameter and display the error message in another, more suitable way like
 * a
 * Toast
 *
 *  * [.showContent]: After the content has been loaded the presenter calls [ ][.setData] to fill the view with data. Afterwards, the presenter calls [ ][.showContent] to display the data
 *
 *
 * @param <M> The underlying data model
 * @author Hannes Dorfmann
 * @since 1.0.0
</M> */
interface MvpLceView<M> : MvpView {

    /**
     * Display a loading view while loading data in background.
     * **The loading view must have the id = R.id.loadingView**
     *
     * @param pullToRefresh true, if pull-to-refresh has been invoked loading.
     */
    @UiThread
    fun showLoading(pullToRefresh: Boolean)

    /**
     * Show the content view.
     *
     * **The content view must have the id = R.id.contentView**
     */
    @UiThread
    fun showContent()

    /**
     * Show the error view.
     * **The error view must be a TextView with the id = R.id.errorView**
     *
     * @param e The Throwable that has caused this error
     * @param pullToRefresh true, if the exception was thrown during pull-to-refresh, otherwise
     * false.
     */
    @UiThread
    fun showError(e: Throwable, pullToRefresh: Boolean)

    /**
     * The data that should be displayed with [.showContent]
     */
    @UiThread
    fun setData(data: M)

    /**
     * Load the data. Typically invokes the presenter method to load the desired data.
     *
     *
     * **Should not be called from presenter** to prevent infinity loops. The method is declared
     * in
     * the views interface to add support for view state easily.
     *
     *
     * @param pullToRefresh true, if triggered by a pull to refresh. Otherwise false.
     */
    @UiThread
    fun loadData(pullToRefresh: Boolean)
}