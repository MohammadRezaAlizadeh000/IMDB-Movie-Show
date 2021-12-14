package ir.mralizade.imdbshow.data.database

import com.google.gson.Gson

class SingleMovieTypeConverter {

    val gson = Gson()

    /*@TypeConverter
    fun languageToString(languages: List<SpokenLanguage>): String =  gson.toJson(languages)

    @TypeConverter
    fun stringToLanguages(languages: String): List<SpokenLanguage> {
        val listType = object : TypeToken<SpokenLanguage>(){}.type
        return gson.fromJson(languages, listType)
    }

    @TypeConverter
    fun countriesToString(countries: List<ProductionCountry>): String =  gson.toJson(countries)

    @TypeConverter
    fun stringToCountries(countries: String): List<ProductionCountry> {
        val listType = object : TypeToken<ProductionCountry>(){}.type
        return gson.fromJson(countries, listType)
    }

    @TypeConverter
    fun genresToString(genres: List<Genre>): String =  gson.toJson(genres)

    @TypeConverter
    fun stringToGenres(genres: String): List<Genre> {
        val listType = object : TypeToken<Genre>(){}.type
        return gson.fromJson(genres, listType)
    }*/
}