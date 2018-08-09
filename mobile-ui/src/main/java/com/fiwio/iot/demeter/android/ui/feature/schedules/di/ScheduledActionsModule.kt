package com.fiwio.iot.demeter.android.ui.feature.schedules.di

import com.fiwio.iot.demeter.domain.features.schedule.GetSchedule
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.feature.schedule.ScheduleContract
import com.fiwio.iot.demeter.presentation.feature.schedule.SchedulesPresenter
import com.fiwio.iot.demeter.presentation.mapper.ScheduledActionMapper
import dagger.Module
import dagger.Provides

@Module
class ScheduledActionsModule {

    @Provides
    internal fun provideActuatorsListPresenter(
            repository: DemeterRepository,
            getSchedule: GetSchedule,
            scheduledActionMapper: ScheduledActionMapper): ScheduleContract.Presenter {
        return SchedulesPresenter(repository, getSchedule, scheduledActionMapper)
    }
}