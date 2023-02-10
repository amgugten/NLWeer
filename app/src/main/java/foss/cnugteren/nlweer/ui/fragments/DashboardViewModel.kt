package foss.cnugteren.nlweer.ui.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import foss.cnugteren.nlweer.BuildConfig
import foss.cnugteren.nlweer.Constants
import foss.cnugteren.nlweer.data.LiveWeerResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel() {

    private val TAG = "MainViewModel"

    private var _city: String = ""
    val city: String
        get() = _city

    private val _prediction = MutableLiveData<String>("")
    val prediction: LiveData<String> = _prediction

    private val _temperature = MutableLiveData<Float>()
    val temperature: LiveData<Float> = _temperature

    fun getKnmiData(city: String) {
        viewModelScope.launch {
            getWeatherDataFromApi(city)
        }
    }

    private suspend fun getWeatherDataFromApi(city: String) {
        try {
            val client = HttpClient(Android) {
                install(ContentNegotiation) {
                    json()
                }
            }
            val response: HttpResponse =
                client.get(Constants.API_BASE_URL + "json-data-10min.php") {
                    url {
                        parameters.append("key", BuildConfig.API_KEY)
                        parameters.append("locatie", city)
                    }
                }
            when (response.status) {
                HttpStatusCode.OK -> {
                    Log.d(TAG, "API call was successful ($response.status)")
                }
                HttpStatusCode.NotFound -> {
                    Log.d(TAG, "Not found ($response.status)")
                }
                else -> {
                    Log.d(TAG, "Status: $response.status")
                }
            }

            val lwr: LiveWeerResponse = response.body()
            _temperature.value = lwr.liveweer[0].temp.toFloat()
            Log.d(TAG, "Temperature: ${temperature.value}")

//            _prediction.value = lwr.liveweer[0].verw
//            Log.d(TAG, "Prediction: '${prediction.value}'")

            client.close()
        } catch (e: ClientRequestException) {
            Log.d(TAG, "EXCEPTION: ${e.message}")
        } catch (e: ServerResponseException) {
            Log.d(TAG, "EXCEPTION: ${e.message}")
        } catch (e: Exception) {
            Log.d(TAG, "EXCEPTION: $e")
        }
    }
}