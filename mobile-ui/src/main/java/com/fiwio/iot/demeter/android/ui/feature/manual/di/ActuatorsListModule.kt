package com.fiwio.iot.demeter.android.ui.feature.manual.di

import com.fiwio.iot.demeter.domain.features.manual.GetDemeter
import com.fiwio.iot.demeter.domain.features.manual.SetActuatorUseCase
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
            setActuatorUseCase: SetActuatorUseCase,
            actuatorViewMapper: ActuatorViewMapper): ActuatorsListContract.Presenter {
        return ActuatorsListPresenter(repository, getDemeter, setActuatorUseCase, actuatorViewMapper)
    }
}