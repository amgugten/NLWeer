package foss.cnugteren.nlweer.data

data class WeatherUIData(
    val city: String = "",
    val temperature: String = "",
    val sunUnder: String = "",
    val sunUp: String = "",
    val forecast: String = "",
    val imageId: Int = 0,
)
