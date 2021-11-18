package com.uniandes.vinilo.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.models.Collector
import com.uniandes.vinilo.models.Comment
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
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
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
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
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    val item = resp.getJSONObject(i)
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


    suspend fun getBands() = suspendCoroutine<List<Artista>>{ cont->
        requestQueue.add(getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artista>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Artista(
                        artistaId = item.getInt("id"),
                        name = item.getString("name"),
                        date = item.getString("creationDate"),
                        description = item.getString("description"),
                        image = item.getString("image")
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
                    image = resp.getString("image")
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
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    val item = resp.getJSONObject(i)
                    list.add(i, Artista(
                        artistaId = item.getInt("id"),
                        name = item.getString("name"),
                        date = item.getString("birthDate"),
                        description = item.getString("description"),
                        image = item.getString("image")
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
                    image = resp.getString("image")
                ))
            },
            {
                cont.resumeWithException(it)
            }))
    }


    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    /*private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }*/
}