package com.example.jitsi_meet

import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URL


class MainActivity : JitsiMeetActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUserCall()
    }

    private fun initUserCall() {

/*Some example that you can pass your data through request body*/ /*Some example that you can pass your data through request body*/
        val requestQueue = Volley.newRequestQueue(this)
        val jsonBodyObj = JSONObject()
        val url = "https://domain/video_conference/"
        try {
            jsonBodyObj.put("user", "{\"name\":\"Manpreet\"}")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody = jsonBodyObj.toString()

        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            url, null,
            Response.Listener { response ->
                runOnUiThread {
                    val ja = JSONArray(response)
                    val serverUrl = ja.getJSONObject(0).getString("domain")
                    val room = ja.getJSONObject(0).getString("room")

                    val options = JitsiMeetConferenceOptions.Builder()
                        .setServerURL(URL(serverUrl))
                        .setRoom(room)
                        .setAudioMuted(false)
                        .setVideoMuted(false)
                        .setAudioOnly(false)
                        .setWelcomePageEnabled(false)
                        .build()
                    jitsiView.join(options)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers =
                    HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }

            override fun getBody(): ByteArray {
                return try {
                    requestBody?.toByteArray(charset("utf-8"))!!
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf(
                        "Unsupported Encoding while trying to get the bytes of %s using %s",
                        requestBody, "utf-8"
                    )
                    ByteArray(7)
                }
            }
        }

        requestQueue.add(jsonObjectRequest)
    }
}
