package es.parrotgames.restaurantcit.presentation.states

sealed class JokerState<out T>

object Loader : JokerState<Nothing>()
data class FacebookDataLoaded<out T>(val data: T) : JokerState<T>()
data class TridentDataLoaded<out T>(val data: T) : JokerState<T>()