package com.fiwio.iot.demeter.domain.model

enum class DnsSearchResult {
    FOUND, NOT_FOUND
}

data class DemeterSearchDnsInfo(val dnsSearchResult: DnsSearchResult, val url:String = "")