package com.fiwio.iot.demeter.android.ui.feature.manual.di

import com.fiwio.iot.demeter.domain.features.manual.GetDemeter
import com.fiwio.iot.demeter.domain.features.manual.SwitchActuator
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.feature.manual.ActuatorsListContract
import com.fiwio.iot.demeter.presentation.feature.manual.ActuatorsListPresenter
import dagger.Module
import dagger.Provides
import org.buffer.android.boilerplate.presentation.mapper.ActuatorViewMapper

@Module
class ActuatorsListModule {

    @Provides
    internal fun provideActuatorsListPresenter(
            repository: DemeterRepository,
            getDemeter: GetDemeter,
            setActuator: SwitchActuator,
            actuatorViewMapper: ActuatorViewMapper): ActuatorsListContract.Presenter {
        return ActuatorsListPresenter(repository, getDemeter, setActuator, actuatorViewMapper)
    }
}