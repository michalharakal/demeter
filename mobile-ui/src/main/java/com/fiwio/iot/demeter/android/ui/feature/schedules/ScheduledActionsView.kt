package com.fiwio.iot.demeter.android.ui.feature.manual

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.ext.getComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.OfflineComponent
import com.fiwio.iot.demeter.presentation.feature.schedule.ScheduleContract
import com.fiwio.iot.demeter.presentation.model.ScheduledActionModel
import com.fiwio.iot.demeter.presentation.model.ScheduledActionsModel
import kotlinx.android.synthetic.main.view_actuators.view.*
import javax.inject.Inject

class ScheduledActionsView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        FrameLayout(context, attrs, defStyle), ScheduleContract.View, ScheduledActionsListAdapter.OnItemClickListener {

    override fun onClick(actuator: ScheduledActionModel) {
        // NTD ?
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun setData(data: ScheduledActionsModel) {
        val adapter = ScheduledActionsListAdapter(this)

        adapter.data = data
        list!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @Inject
    lateinit var presenter: ScheduleContract.Presenter

    private var columnCount = 2

    init {
        var component = context.getComponent<OfflineComponent>().scheduledComponent()
        component.inject(this)

        LayoutInflater.from(context).inflate(R.layout.view_actuators, this, true)

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