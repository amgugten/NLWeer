package foss.cnugteren.nlweer.model

import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import foss.cnugteren.nlweer.R
import foss.cnugteren.nlweer.WeerApiClient
import foss.cnugteren.nlweer.data.Liveweer
import foss.cnugteren.nlweer.data.WeatherUIData
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val TAG = "DashboardViewModel"

//    private val _weerData = MutableLiveData<Liveweer>()
//    val weerData: LiveData<Liveweer>
//        get() = _weerData

    private val _weatherUIData = MutableLiveData<WeatherUIData>()
    val weatherUIData: LiveData<WeatherUIData>
        get() = _weatherUIData

    fun getKnmiData(city: String) {
        viewModelScope.launch {
            val client = WeerApiClient
            val data = client.getWeatherDataFromApi(city)
//            Log.d(TAG, data.toString())
//            _weerData.value = data
            _weatherUIData.value = WeatherUIData(
                city = data.plaats,
                temperature = data.temperature + "°C",
                sunUp = data.sunUp.dropWhile { it == '0' },
                sunUnder = data.sunUnder,
                forecast = data.verw,
                imageId = mapImageToIcon(data.image)
            )
        }
    }

    private fun mapImageToIcon(imageDescription: String): Int {
        return when (imageDescription) {
            "zonnig" -> R.drawable.sun_1
            "bliksem" -> R.drawable.thunderstorm
            "regen" -> R.drawable.rain_1
            "buien" -> R.drawable.rain_1
            "hagel" -> R.drawable.hail_1
            "mist" -> R.drawable.fog_1
            "sneeuw" -> R.drawable.snow
            "bewolkt" -> R.drawable.mostly_cloudy_1
            "lichtbewolkt" -> R.drawable.mostly_cloudy_1
            "halfbewolkt" -> R.drawable.mostly_cloudy_2
            "halfbewolkt_regen" -> R.drawable.rain_1
            "zwaarbewolkt" -> R.drawable.mostly_cloudy_2
            "nachtmist" -> R.drawable.fog_4
            "helderenacht" -> R.drawable.full_moon_1
            "nachtbewolkt" -> R.drawable.partly_cloudy_3
            "wolkennacht" -> R.drawable.partly_cloudy_3
            else -> R.drawable.weather_unknown
        }
    }
}