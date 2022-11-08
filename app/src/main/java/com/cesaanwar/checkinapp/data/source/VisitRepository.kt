package com.cesaanwar.checkinapp.data.source

import com.cesaanwar.checkinapp.data.Visit

interface VisitRepository {

    suspend fun registerVisit(visit: Visit)

    suspend fun getVisitByStoreIdAndTime(visitDateMilis: Long): List<Visit>

    suspend fun getLatestVisitsByStore(localStoreId: Long, storeId: String): Visit?

}