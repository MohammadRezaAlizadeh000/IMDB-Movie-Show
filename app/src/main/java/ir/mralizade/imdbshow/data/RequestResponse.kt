package ir.mralizade.imdbshow.data

interface RequestResponse<T> {

    fun getFromLocal(data: T?)

    fun getFromRemote(data: T?)

}