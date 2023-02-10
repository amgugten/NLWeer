package foss.cnugteren.nlweer.data

import kotlinx.serialization.Serializable

@Serializable
data class Liveweer(
    val alarm: String,
    val alarmtxt: String,
    val d0neerslag: String,
    val d0tmax: String,
    val d0tmin: String,
    val d0weer: String,
    val d0windk: String,
    val d0windkmh: String,
    val d0windknp: String,
    val d0windms: String,
    val d0windr: String,
    val d0windrgr: String,
    val d0zon: String,
    val d1neerslag: String,
    val d1tmax: String,
    val d1tmin: String,
    val d1weer: String,
    val d1windk: String,
    val d1windkmh: String,
    val d1windknp: String,
    val d1windms: String,
    val d1windr: String,
    val d1windrgr: String,
    val d1zon: String,
    val d2neerslag: String,
    val d2tmax: String,
    val d2tmin: String,
    val d2weer: String,
    val d2windk: String,
    val d2windkmh: String,
    val d2windknp: String,
    val d2windms: String,
    val d2windr: String,
    val d2windrgr: String,
    val d2zon: String,
    val dauwp: String,
    val gtemp: String,
    val image: String,
    val ldmmhg: String,
    val luchtd: String,
    val lv: String,
    val plaats: String,
    val samenv: String,
    val sunder: String,
    val sup: String,
    val temp: String,
    val verw: String,
    val windk: String,
    val windkmh: String,
    val windms: String,
    val windr: String,
    val windrgr: String,
    val winds: String,
    val zicht: String
)
