package com.zoo.dongyeah

import androidx.appcompat.app.AppCompatActivity
import net.daum.mf.map.api.MapView.POIItemEventListener
import android.os.Bundle
import android.util.Log
import android.view.View
import com.zoo.dongyeah.R
import com.zoo.dongyeah.ActivityMain
import android.widget.Toast
import net.daum.mf.map.api.*
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class ActivityMain : AppCompatActivity(), MapView.MapViewEventListener, POIItemEventListener {
    private var mMapView: MapView? = null
    private var mDefaultMarker: MapPOIItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getHospitalData()

        mMapView = findViewById<View>(R.id.map_view) as MapView
        mMapView!!.setDaumMapApiKey(BuildConfig.KAKAO_API_KEY)
        mMapView!!.setMapViewEventListener(this)
        mMapView!!.setPOIItemEventListener(this)
        createDefaultMarker(mMapView)
        showAll()
        mMapView!!.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }

    override fun onMapViewInitialized(mapView: MapView) {}
    private fun createDefaultMarker(mapView: MapView?) {
        mDefaultMarker = MapPOIItem()
        val name = "Default Marker"
        mDefaultMarker!!.itemName = name
        mDefaultMarker!!.tag = 0
        mDefaultMarker!!.mapPoint = DEFAULT_MARKER_POINT
        mDefaultMarker!!.markerType = MapPOIItem.MarkerType.BluePin
        mDefaultMarker!!.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView!!.addPOIItem(mDefaultMarker)
        mapView.selectPOIItem(mDefaultMarker, true)
        mapView.setMapCenterPoint(DEFAULT_MARKER_POINT, false)
    }

    private fun showAll() {
        val padding = 20
        val minZoomLevel = 7f
        val maxZoomLevel = 10f
        val bounds = MapPointBounds(CUSTOM_MARKER_POINT, DEFAULT_MARKER_POINT)
        mMapView!!.moveCamera(
            CameraUpdateFactory.newMapPointBounds(
                bounds,
                padding,
                minZoomLevel,
                maxZoomLevel
            )
        )
    }

    override fun onMapViewCenterPointMoved(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewZoomLevelChanged(mapView: MapView, i: Int) {}
    override fun onMapViewSingleTapped(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewDoubleTapped(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewLongPressed(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewDragStarted(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewDragEnded(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewMoveFinished(mapView: MapView, mapPoint: MapPoint) {}
    override fun onPOIItemSelected(mapView: MapView, mapPOIItem: MapPOIItem) {}
    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView, mapPOIItem: MapPOIItem) {
        Toast.makeText(
            this,
            "Clicked " + mapPOIItem.itemName + " Callout Balloon",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView,
        mapPOIItem: MapPOIItem,
        calloutBalloonButtonType: CalloutBalloonButtonType
    ) {
    }

    override fun onDraggablePOIItemMoved(
        mapView: MapView,
        mapPOIItem: MapPOIItem,
        mapPoint: MapPoint
    ) {
    }

    private fun getHospitalData() {
        val service = RetrofitClient.getInstance().create(HospitalAPI::class.java)
        val call = service.getData(127.005515, 37.537229, 3000)
        //val call = service.getData()
        call.enqueue(object : Callback<HospitalData> {
            override fun onResponse(call: Call<HospitalData>, response: Response<HospitalData>) {
                if (response.isSuccessful) {
                }
            }

            override fun onFailure(call: Call<HospitalData>, t: Throwable) {
                Log.e(LOG_TAG, t.printStackTrace().toString())
                Log.e(LOG_TAG, t.message)
            }

        })
    }

    companion object {
        private val CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515)
        private val CUSTOM_MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(37.447229, 127.015515)
        private val DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.4020737, 127.1086766)
        private const val LOG_TAG = "com.zoo.dongyeah"
    }
}