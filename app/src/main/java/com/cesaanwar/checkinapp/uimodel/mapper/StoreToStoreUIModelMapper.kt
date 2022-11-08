package com.cesaanwar.checkinapp.uimodel.mapper

import android.location.Location
import android.location.LocationManager
import com.cesaanwar.checkinapp.data.Store
import com.cesaanwar.checkinapp.data.Visit
import com.cesaanwar.checkinapp.uimodel.StoreDetailUIModel
import com.cesaanwar.checkinapp.uimodel.StoreListUIModel
import com.cesaanwar.checkinapp.util.DateHelper
import com.cesaanwar.checkinapp.util.toStoreDistanceInfo

object StoreToStoreUIModelMapper {

    fun mapStoreToStoreUIModel(stores: List<Store>, todayVisits: Set<String>, location: Location): List<StoreListUIModel> {
        val res = mutableListOf<StoreListUIModel>()
        stores.forEach {
            val distance = getDistance(location,
                Location(LocationManager.GPS_PROVIDER).apply {
                    latitude = it.latitude.toDouble()
                    longitude = it.longitude.toDouble()
                }
            )
            res.add(
                StoreListUIModel(
                    localStoreId = it.localStoreId,
                    storeId = it.storeId,
                    storeName = it.storeName,
                    channelName = it.channelName,
                    accountName = it.accountName,
                    longitude = it.longitude.toDouble(),
                    latitude = it.latitude.toDouble(),
                    distanceValue = distance,
                    distanceInfo = distance.toStoreDistanceInfo(),
                    hasBeenVisited = todayVisits.contains("${it.localStoreId};${it.storeId}")
                )
            )
        }
        return res
    }

    fun mapStoreToStoreDetailUIModel(store: Store, latestVisit: Visit?): StoreDetailUIModel {
        val latestVisitInfo = if (latestVisit != null) {
            DateHelper.getDateInfoStringFromMilis(latestVisit.visitTimeMilis)
        } else {
            "-"
        }
        return StoreDetailUIModel(
            localStoreId = store.localStoreId,
            storeId = store.storeId,
            storeName = store.storeName,
            address = store.address,
            longitude = store.longitude.toDouble(),
            latitude = store.latitude.toDouble(),
            area = store.areaName,
            region = store.regionName,
            lastVisit = latestVisitInfo
        )
    }

    private fun getDistance(location: Location, dest: Location): Double {
        return location.distanceTo(dest).toDouble()
    }

}
