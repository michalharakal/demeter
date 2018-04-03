package com.fiwio.iot.demeter.domain.factory

import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.Sensor
import com.fiwio.iot.demeter.domain.model.InputValue
import com.fiwio.iot.demeter.domain.model.Actuator

/**
 * Factory class for [Demeter] related instances
 */
class DemeterFactory {

    companion object Factory {

        fun makeDemeterNoIOs(): Demeter {
            return Demeter(emptyList(), emptyList())
        }

        fun makeDemeterAllOnIOs(count: Int): Demeter {

            val relays = mutableListOf<Actuator>()
            repeat(count) {
                relays.add(makeRelay(true))
            }

            val inputs = mutableListOf<Sensor>()
            repeat(count) {
                inputs.add(makeInputs(true))
            }

            return Demeter(relays, emptyList())
        }

        private fun makeInputs(b: Boolean): Sensor {
            return Sensor("name", InputValue.ON)
        }

        private fun makeRelay(b: Boolean): Actuator {
            return Actuator("name", b)
        }
    }
}