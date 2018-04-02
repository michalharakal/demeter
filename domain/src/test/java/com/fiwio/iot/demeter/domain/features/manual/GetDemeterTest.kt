package com.fiwio.iot.demeter.domain.features.manual

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.factory.DemeterFactory
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetDemeterTest {

    private lateinit var getDemeter: GetDemeter

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockBufferooRepository: DemeterRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockBufferooRepository = mock()
        getDemeter = GetDemeter(mockBufferooRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getDemeter.buildUseCaseObservable(null)
        verify(mockBufferooRepository).getDemeterImage()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubBufferooRepositoryGetBufferoos(Single.just(DemeterFactory.makeDemeterNoIOs()))
        val testObserver = getDemeter.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val demeter = DemeterFactory.makeDemeterAllOnIOs(3)
        val demeter1 = DemeterFactory.makeDemeterAllOnIOs(13)
        stubBufferooRepositoryGetBufferoos(Single.just(demeter))
        val testObserver = getDemeter.buildUseCaseObservable(null).test()
        testObserver.assertValue(demeter)
    }

    private fun stubBufferooRepositoryGetBufferoos(single: Single<Demeter>) {
        whenever(mockBufferooRepository.getDemeterImage())
                .thenReturn(single)
    }
}