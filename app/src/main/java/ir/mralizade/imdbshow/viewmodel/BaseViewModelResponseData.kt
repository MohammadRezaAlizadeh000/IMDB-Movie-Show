package ir.mralizade.imdbshow.viewmodel

interface BaseViewModelResponseData<T> {
    val errorMessage: String?
    val data: T?
    val page: Int?
}