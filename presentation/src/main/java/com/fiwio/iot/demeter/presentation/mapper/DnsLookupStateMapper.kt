package com.fiwio.iot.demeter.presentation.mapper

import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import com.fiwio.iot.demeter.domain.model.DnsSearchResult
import com.fiwio.iot.demeter.presentation.model.DnsLookupState
import org.buffer.android.boilerplate.presentation.mapper.Mapper
import javax.inject.Inject

// DnsLookupState

/**
 * Map a [DnsLookupState] to and from a [Demeter] instance when data is moving between
 * this layer and the Domain layer
 */
open class DnsLookupStateMapper @Inject constructor() : Mapper<DnsLookupState, DemeterSearchDnsInfo> {

    /**
     * Map a [DnsLookupState] instance to a [DnsLookupState] instance
     */
    override fun mapToView(type: DemeterSearchDnsInfo): DnsLookupState {
        return DnsLookupState(type.dnsSearchResult == DnsSearchResult.FOUND, type.url)
    }
}