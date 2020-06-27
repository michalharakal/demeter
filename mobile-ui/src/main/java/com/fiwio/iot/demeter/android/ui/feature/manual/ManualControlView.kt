package com.fiwio.iot.demeter.android.ui.feature.manual

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.ext.getComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.presentation.feature.manual.ActuatorsListContract
import com.fiwio.iot.demeter.presentation.model.ActuatorView
import kotlinx.android.synthetic.main.view_actuators.view.*
import javax.inject.Inject

class ManualControlView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        FrameLayout(context, attrs, defStyle), ActuatorsListContract.View, ActuatorsListAdapter.OnItemClickListener {
    override fun onClick(actuator: ActuatorView) {
        presenter.switchRelay(actuator)
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun setData(data: List<ActuatorView>) {
        val adapter = ActuatorsListAdapter(this)

        adapter.data = data
        list!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @Inject
    lateinit var presenter: ActuatorsListContract.Presenter

    init {
        var component = context.getComponent<MainComponent>().actuatorsListComponent()
        component.inject(this)

        LayoutInflater.from(context).inflate(R.layout.view_actuators, this, true)

        // Set the adapter
        list!!.layoutManager = LinearLayoutManager(context)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.attachView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
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