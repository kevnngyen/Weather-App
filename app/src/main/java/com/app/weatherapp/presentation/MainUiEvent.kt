sealed class MainUiEvents {
    data class OnSeacrchWordChange(val newWord: String): MainUiEvents()
    object OnSearchClick : MainUiEvents()
}