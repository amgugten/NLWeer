package foss.cnugteren.nlweer.data

import kotlinx.serialization.Serializable

@Serializable
data class LiveWeerResponse(
    val liveweer: List<Liveweer>
)
