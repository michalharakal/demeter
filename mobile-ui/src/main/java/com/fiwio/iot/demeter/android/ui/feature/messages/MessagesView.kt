package com.fiwio.iot.demeter.android.ui.feature.messages

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.ext.getComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.android.ui.feature.offline.di.OfflineComponent
import com.fiwio.iot.demeter.presentation.feature.message.MessagesContract
import com.fiwio.iot.demeter.presentation.model.MessagesModel
import kotlinx.android.synthetic.main.view_actuators.view.*
import javax.inject.Inject

class MessagesView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        FrameLayout(context, attrs, defStyle), MessagesContract.View {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun setData(data: MessagesModel) {
        val adapter = MessagesListAdapter(iconsMapper)

        adapter.data = data
        list!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @Inject
    lateinit var iconsMapper: IconsMapper

    @Inject
    lateinit var presenter: MessagesContract.Presenter


    init {
        var component = context.getComponent<OfflineComponent>().messagesComponent()
        component.inject(this)

        LayoutInflater.from(context).inflate(R.layout.view_messages, this, true)

        // Set the adapter
        list!!.layoutManager = LinearLayoutManager(context)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.attachView(this)
    }


    override fun showLoading(pullToRefresh: Boolean) {

    }

    override fun showContent() {
    }

    override fun showError(e: Throwable, pullToRefresh: Boolean) {
    }


    override fun loadData(pullToRefresh: Boolean) {
    }
}
