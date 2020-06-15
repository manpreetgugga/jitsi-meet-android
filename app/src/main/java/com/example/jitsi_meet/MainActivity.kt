package com.example.jitsi_meet

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
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
        initUserCall2()
    }

    private fun initUserCall2() {
        val options = JitsiMeetConferenceOptions.Builder()
            .setServerURL(URL("https://meet.jit.si"))
            .setRoom("Demo")
            .setAudioMuted(false)
            .setVideoMuted(false)
            .setAudioOnly(false)
            .setWelcomePageEnabled(false)
            .build()

        jitsiView.join(options)
    }

    private fun initUserCall() {

/*Some example that you can pass your data through request body*/ /*Some example that you can pass your data through request body*/
        val requestQueue = Volley.newRequestQueue(this)
        val jsonBodyObj = JSONObject()
        val jsonBodyObj2 = JSONObject()
        val url = "https://josh-meet.herokuapp.com/video_conferences"
        try {
            jsonBodyObj2.put("name", "Manpreet")
            jsonBodyObj.put("user", jsonBodyObj2.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody = "{\"user\": {\n" +
                " \"name\": \"Manpreet\"\n" +
                " }\n" +
                "}"

        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            url, null,
            Response.Listener { response ->
                runOnUiThread {
                    val responseJson: ResponseJson =
                        Gson().fromJson(response.toString(), ResponseJson::class.java)
                    val options = JitsiMeetConferenceOptions.Builder()
                        .setServerURL(URL("https://" + responseJson.data?.video_conference?.domain))
                        .setRoom(responseJson.data?.video_conference?.room)
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
                headers["content-type"] = "application/json"
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
