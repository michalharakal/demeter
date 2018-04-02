package com.fiwio.iot.demeter.domain.factory

import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.Input
import com.fiwio.iot.demeter.domain.model.InputValue
import com.fiwio.iot.demeter.domain.model.Relay

/**
 * Factory class for [Demeter] related instances
 */
class DemeterFactory {

    companion object Factory {

        fun makeDemeterNoIOs(): Demeter {
            return Demeter(emptyList(), emptyList())
        }

        fun makeDemeterAllOnIOs(count: Int): Demeter {

            val relays = mutableListOf<Relay>()
            repeat(count) {
                relays.add(makeRelay(true))
            }

            val inputs = mutableListOf<Input>()
            repeat(count) {
                inputs.add(makeInputs(true))
            }

            return Demeter(relays, emptyList())
        }

        private fun makeInputs(b: Boolean): Input {
            return Input("name", InputValue.ON)
        }

        private fun makeRelay(b: Boolean): Relay {
            return Relay("name", b)
        }
    }
}