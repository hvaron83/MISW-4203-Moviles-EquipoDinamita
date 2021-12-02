package com.uniandes.vinilo.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.models.Collector
import com.uniandes.vinilo.models.Comment
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {

    companion object{
        const val BAND_TYPE = 1
        const val MUSICIAN_TYPE = 2
        const val BASE_URL= "http://fierce-island-35443.herokuapp.com/"
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),
                        cover = item.getString("cover"),
                        description = item.getString("description")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    item = resp.getJSONObject(i)
                    val collector = Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"))
                    list.add(i, collector) //se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getComments(albumId:Int) = suspendCoroutine<List<Comment>>{ cont->
        requestQueue.add(getRequest("albums/$albumId/comments",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Comment>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    Log.d("Response", item.toString())
                    list.add(i, Comment(albumId = albumId, rating = item.getInt("rating").toString(), description = item.getString("description")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getAlbum(albumId:Int) = suspendCoroutine<Album>{ cont->
        requestQueue.add(getRequest("albums/$albumId",
            { response ->
                val resp = JSONObject(response)
                val album = Album(albumId = resp.getInt("id"),name = resp.getString("name"), cover = resp.getString("cover"), recordLabel = resp.getString("recordLabel"), releaseDate = resp.getString("releaseDate"), genre = resp.getString("genre"), description = resp.getString("description"))

                cont.resume(album)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun addAlbum(album:Album) = suspendCoroutine<Album>{ cont->
        val jsonString = Gson().toJson(album)
        requestQueue.add(postRequest("albums", JSONObject(jsonString),
            { response ->
                val album = Album(
                    albumId = response.getInt("id"),
                    name = response.getString("name"),
                    cover = response.getString("cover"),
                    recordLabel = response.getString("recordLabel"),
                    releaseDate = response.getString("releaseDate"),
                    genre = response.getString("genre"),
                    description = response.getString("description")
                )

                cont.resume(album)
            },
            {
                cont.resumeWithException(it)
            }))
    }
    
    suspend fun getBands() = suspendCoroutine<List<Artista>>{ cont->
        requestQueue.add(getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artista>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Artista(
                        artistaId = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        artistaType = BAND_TYPE
                    )) //se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getBand(artistaId:Int) = suspendCoroutine<Artista>{ cont->
        requestQueue.add(getRequest("bands/$artistaId",
            { response ->
                val resp = JSONObject(response)
                cont.resume(Artista(
                    artistaId = resp.getInt("id"),
                    name = resp.getString("name"),
                    date = resp.getString("creationDate"),
                    description = resp.getString("description"),
                    image = resp.getString("image"),
                    artistaType = BAND_TYPE
                ))
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getMusicians() = suspendCoroutine<List<Artista>>{ cont->
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artista>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    item = resp.getJSONObject(i)
                    list.add(i, Artista(
                        artistaId = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        artistaType = MUSICIAN_TYPE
                    )) //se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getMusician(artistaId:Int) = suspendCoroutine<Artista>{ cont->
        requestQueue.add(getRequest("musicians/$artistaId",
            { response ->
                val resp = JSONObject(response)
                cont.resume(Artista(
                    artistaId = resp.getInt("id"),
                    name = resp.getString("name"),
                    date = resp.getString("birthDate"),
                    description = resp.getString("description"),
                    image = resp.getString("image"),
                    artistaType = MUSICIAN_TYPE
                ))
            },
            {
                cont.resumeWithException(it)
            }))
    }


    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    /*
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }*/
}