package foss.cnugteren.nlweer

import android.util.Log
import foss.cnugteren.nlweer.data.LiveWeerResponse
import foss.cnugteren.nlweer.data.Liveweer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object WeerApiClient {

    private val TAG = "WeerApiClient"

    const val API_BASE_URL = "https://weerlive.nl/api/"

    private val client: HttpClient by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    suspend fun getWeatherDataFromApi(city: String): Liveweer {
        val weerData: Liveweer = try {
            val response: HttpResponse = client.get(API_BASE_URL + "json-data-10min.php") {
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
            val dataInner: Liveweer = lwr.liveweer[0]
            dataInner
        } catch (e: JsonConvertException) {
            Log.d(TAG, e.toString())
            Log.d(TAG, e.cause.toString())
            val dataInner: Liveweer = Liveweer()
            dataInner
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
            val dataInner: Liveweer = Liveweer()
            dataInner
        }

//        client.close()
        Log.d(TAG, weerData.toString())
        return weerData
    }
}