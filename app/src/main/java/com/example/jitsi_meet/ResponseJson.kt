package com.example.jitsi_meet

data class ResponseJson(
    var data: Data? = Data(),
    var message: String? = ""
) {
    data class Data(
        var video_conference: VideoConference? = VideoConference()
    ) {
        data class VideoConference(
            var domain: String? = "",
            var password: String? = "",
            var room: String? = "",
            var user: User? = User()
        ) {
            data class User(
                var avatar_url: Any? = Any(),
                var email: String? = "",
                var name: String? = ""
            )
        }
    }
}