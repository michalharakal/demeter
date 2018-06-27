package com.fiwio.iot.demeter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log

import com.fiwio.iot.demeter.android.ui.app.DemeterApplication
import com.fiwio.iot.demeter.configuration.Configuration
import com.fiwio.iot.demeter.device.model.DigitalPins
import com.fiwio.iot.demeter.discovery.NdsService
import com.fiwio.iot.demeter.events.IEventBus
import com.fiwio.iot.demeter.fsm.FsmBackgroundService
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine
import com.fiwio.iot.demeter.http.DemeterHttpServer
import com.fiwio.iot.demeter.scheduler.ReminderEngine
import com.google.android.things.pio.PeripheralManager

import java.io.IOException

import sun.audio.AudioDevice.device

class MainActivity : Activity() {
    private var pioThread: HandlerThread? = null
    private var handler: Handler? = null
    private var relays: DigitalPins? = null
    private var api: DemeterHttpServer? = null
    private var service: NdsService? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setup our background threading mechanism
        // this is used to send commands to the peripherals
        pioThread = HandlerThread("pioThread")
        pioThread!!.start()
        handler = Handler(pioThread!!.looper)
        // instantiate a connection to our peripheral
        relays = (application as DemeterApplication).getDemeter()
        val fsm = (application as DemeterApplication).getFsm()

        val eventBus = (application as DemeterApplication).getEventBus()

        val configuration = (application as DemeterApplication).getConfiguration()
        val reminderEngine = (application as DemeterApplication).getRemainderEngine()

        try {
            api = DemeterHttpServer(relays, fsm, eventBus, reminderEngine, configuration)
            api!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        service = NdsService(this)

        service!!.startServer("demeter", null)

        Log.d(TAG, "Available GPIO: " + PeripheralManager.getInstance().gpioList)


        val intent = Intent(Intent.ACTION_SYNC, null, this, FsmBackgroundService::class.java)
        startService(intent)

    }

    public override fun onDestroy() {
        super.onDestroy()
        pioThread!!.quitSafely()
        api!!.stop()
        service!!.stopServer()
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }

}
