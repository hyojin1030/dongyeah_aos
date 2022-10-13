package com.zoo.dongyeah

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zoo.dongyeah.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.POIItemEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMain : AppCompatActivity(), MapView.MapViewEventListener, POIItemEventListener {
    private val LOG_TAG = "com.zoo.dongyeah"

    private lateinit var binding: ActivityMainBinding

    private var mMapView: MapView? = null
    private var mDefaultMarker: MapPOIItem? = null

    private var latitude = 37.537229
    private var longitude = 127.005515
    private val mapRadius = 500



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getHospitalData(latitude, longitude)

        initView()
    }

    private fun initView() {
        mMapView = binding.mapView
        mMapView!!.setDaumMapApiKey(BuildConfig.KAKAO_API_KEY)
        mMapView!!.setMapViewEventListener(this)
        mMapView!!.setPOIItemEventListener(this)

        binding.editPlace.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER)) {

                if (binding.editPlace.text.isEmpty()) {
                    Toast.makeText(applicationContext, "동을 입력해주세요.", Toast.LENGTH_LONG)
                } else {
                    getSearchData(binding.editPlace.text.toString())
                }

            }
            return@setOnKeyListener false
        }

    }

    override fun onMapViewInitialized(p0: MapView?) {}
    override fun onMapViewCenterPointMoved(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewZoomLevelChanged(mapView: MapView, i: Int) {}
    override fun onMapViewSingleTapped(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewDoubleTapped(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewLongPressed(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewDragStarted(mapView: MapView, mapPoint: MapPoint) {}
    override fun onMapViewDragEnded(mapView: MapView, mapPoint: MapPoint) {
        val mapPointGeo = mapView.mapCenterPoint.mapPointGeoCoord
        getHospitalData(mapPointGeo.latitude, mapPointGeo.longitude)
    }
    override fun onMapViewMoveFinished(mapView: MapView, mapPoint: MapPoint) {}


    override fun onPOIItemSelected(mapView: MapView, mapPOIItem: MapPOIItem) {}
    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView, mapPOIItem: MapPOIItem) {
        Toast.makeText(
            this,
            "Clicked " + mapPOIItem.itemName + " Callout Balloon",
            Toast.LENGTH_SHORT
        ).show()


        val intent: Intent = Intent(this, ActivityHosInfo::class.java)
        intent.putExtra("latitude", mapPOIItem.mapPoint.mapPointGeoCoord.latitude)
        intent.putExtra("longitude", mapPOIItem.mapPoint.mapPointGeoCoord.longitude)
        startActivity(intent)
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

    private fun getHospitalData(latitude: Double, longitude: Double) {
        val service = RetrofitClient.getInstance().create(HospitalAPI::class.java)
        val call = service.getHospitalData(latitude, longitude, mapRadius)
        call.enqueue(object : Callback<HospitalData> {
            override fun onResponse(call: Call<HospitalData>, response: Response<HospitalData>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val arrays = body.body.items.item

                        mMapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);

                        for (data in arrays) {
                            mDefaultMarker = MapPOIItem()
                            mDefaultMarker!!.itemName = data.hosName
                            mDefaultMarker!!.tag = 0
                            mDefaultMarker!!.mapPoint = MapPoint.mapPointWithGeoCoord(data.yPos!!, data.xPos!!)
                            mDefaultMarker!!.markerType = MapPOIItem.MarkerType.BluePin
                            mDefaultMarker!!.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                            mMapView!!.addPOIItem(mDefaultMarker)
                        }
                    } else {
                        Log.e(LOG_TAG, "response error")
                    }
                }
            }

            override fun onFailure(call: Call<HospitalData>, t: Throwable) {
                Log.e(LOG_TAG, t.printStackTrace().toString())
                Log.e(LOG_TAG, t.message)

                Toast.makeText(applicationContext, "검색 결과가 없습니다.", Toast.LENGTH_LONG)
            }

        })
    }

    private fun getSearchData(searchData: String) {
        val service = RetrofitClient.getInstance().create(HospitalAPI::class.java)
        val call = service.getSearchData(searchData)
        call.enqueue(object : Callback<HospitalData> {
            override fun onResponse(call: Call<HospitalData>, response: Response<HospitalData>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        latitude = body.body.items.item.get(0).yPos!!
                        longitude = body.body.items.item.get(0).xPos!!

                        getHospitalData(latitude, longitude)
                    } else {
                        Toast.makeText(applicationContext, "검색 결과가 없습니다.", Toast.LENGTH_LONG)
                    }
                }
            }

            override fun onFailure(call: Call<HospitalData>, t: Throwable) {
                Log.e(LOG_TAG, t.printStackTrace().toString())
                Log.e(LOG_TAG, t.message)
            }

        })
    }
}