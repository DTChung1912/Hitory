package com.example.route

import com.example.model.MyVideo
import com.example.mysql.DbConnection
import com.example.mysql.entity.EntityMyVideo
import com.example.mysql.entity.EntityVideo
import com.example.util.GenericResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.database.Database
import org.ktorm.dsl.*

fun Application.routeMyVideo() {
    val db: Database = DbConnection.getDatabaseInstance()
    routing {
        route("/myvideo") {
            get("/list") {
                val userId = call.request.queryParameters["user_id"].toString()
                val list = db.from(EntityMyVideo).innerJoin(EntityVideo)
                    .select()
                    .where(EntityMyVideo.user_id eq userId and (EntityMyVideo.video_id eq EntityVideo.video_id))
                    .map {
                        MyVideo(
                            my_video_id = it[EntityMyVideo.my_video_id] ?: 0,
                            user_id = it[EntityMyVideo.user_id].orEmpty(),
                            video_id = it[EntityVideo.video_id] ?: 0,
                            title = it[EntityVideo.title].orEmpty(),
                            theme_id = it[EntityVideo.theme_id] ?: 0,
                            creater_image = it[EntityVideo.creater_image].orEmpty(),
                            creater = it[EntityVideo.creater].orEmpty(),
                            platform = it[EntityVideo.platform].orEmpty(),
                            like_count = it[EntityVideo.like_count] ?: 0,
                            view_count = it[EntityVideo.view_count] ?: 0,
                            dislike_count = it[EntityVideo.dislike_count] ?: 0,
                            comment_count = it[EntityVideo.comment_count] ?: 0,
                            share_count = it[EntityVideo.share_count] ?: 0,
                            video_url = it[EntityVideo.video_url].orEmpty(),
                            poster_image = it[EntityVideo.poster_image].orEmpty(),
                            date_submitted = it[EntityVideo.date_submitted].orEmpty(),
                            isLike = it[EntityMyVideo.isLike] ?: 0,
                            isLater = it[EntityMyVideo.isLater] ?: 0,
                            isDownload = it[EntityMyVideo.isDownload] ?: 0,
                            isView = it[EntityMyVideo.isView] ?: 0,
                            isShare = it[EntityMyVideo.isShare] ?: 0,
                            isDontCare = it[EntityMyVideo.isDontCare] ?: 0
                        )
                    }

                if (list.isNotEmpty()) {
                    call.respond(list)
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = false, data = null)
                    )
                }
            }

            get("/detail") {
                val userId = call.request.queryParameters["user_id"].toString()
                val videoId = call.request.queryParameters["video_id"].toString()
                val videoIdInt = videoId.toInt() ?: -1

                val list = db.from(EntityMyVideo).innerJoin(EntityVideo)
                    .select()
                    .where(
                        EntityMyVideo.user_id eq userId
                                and (EntityMyVideo.video_id eq videoIdInt)
                                and (EntityMyVideo.video_id eq EntityVideo.video_id)
                    )
                    .map {
                        MyVideo(
                            my_video_id = it[EntityMyVideo.my_video_id] ?: 0,
                            user_id = it[EntityMyVideo.user_id].orEmpty(),
                            video_id = it[EntityVideo.video_id] ?: 0,
                            title = it[EntityVideo.title].orEmpty(),
                            theme_id = it[EntityVideo.theme_id] ?: 0,
                            creater_image = it[EntityVideo.creater_image].orEmpty(),
                            creater = it[EntityVideo.creater].orEmpty(),
                            platform = it[EntityVideo.platform].orEmpty(),
                            like_count = it[EntityVideo.like_count] ?: 0,
                            view_count = it[EntityVideo.view_count] ?: 0,
                            dislike_count = it[EntityVideo.dislike_count] ?: 0,
                            comment_count = it[EntityVideo.comment_count] ?: 0,
                            share_count = it[EntityVideo.share_count] ?: 0,
                            video_url = it[EntityVideo.video_url].orEmpty(),
                            poster_image = it[EntityVideo.poster_image].orEmpty(),
                            date_submitted = it[EntityVideo.date_submitted].orEmpty(),
                            isLike = it[EntityMyVideo.isLike] ?: 0,
                            isLater = it[EntityMyVideo.isLater] ?: 0,
                            isDownload = it[EntityMyVideo.isDownload] ?: 0,
                            isView = it[EntityMyVideo.isView] ?: 0,
                            isShare = it[EntityMyVideo.isShare] ?: 0,
                            isDontCare = it[EntityMyVideo.isDontCare] ?: 0
                        )
                    }

                if (list.isNotEmpty()) {
                    call.respond(list)
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = false, data = null)
                    )
                }
            }

            get("/test") {
//                val formParameters = call.receiveParameters()
//                val userId = formParameters["user_id"].toString()
//                val videoId = formParameters["video_id"]

                val ui = call.request.queryParameters["user_id"].toString()
                val vi = call.request.queryParameters["video_id"]


                val list = db.from(EntityMyVideo).innerJoin(EntityVideo)
                    .select()
                    .where(
                        EntityMyVideo.user_id eq ui
                                and (EntityMyVideo.video_id eq EntityVideo.video_id
                                and (EntityVideo.video_id eq vi!!.toInt()))
                    )
                    .map {
                        MyVideo(
                            my_video_id = it[EntityMyVideo.my_video_id] ?: 0,
                            user_id = it[EntityMyVideo.user_id].orEmpty(),
                            video_id = it[EntityVideo.video_id] ?: 0,
                            title = it[EntityVideo.title].orEmpty(),
                            theme_id = it[EntityVideo.theme_id] ?: 0,
                            creater_image = it[EntityVideo.creater_image].orEmpty(),
                            creater = it[EntityVideo.creater].orEmpty(),
                            platform = it[EntityVideo.platform].orEmpty(),
                            like_count = it[EntityVideo.like_count] ?: 0,
                            view_count = it[EntityVideo.view_count] ?: 0,
                            dislike_count = it[EntityVideo.dislike_count] ?: 0,
                            comment_count = it[EntityVideo.comment_count] ?: 0,
                            share_count = it[EntityVideo.share_count] ?: 0,
                            video_url = it[EntityVideo.video_url].orEmpty(),
                            poster_image = it[EntityVideo.poster_image].orEmpty(),
                            date_submitted = it[EntityVideo.date_submitted].orEmpty(),
                            isLike = it[EntityMyVideo.isLike] ?: 0,
                            isLater = it[EntityMyVideo.isLater] ?: 0,
                            isDownload = it[EntityMyVideo.isDownload] ?: 0,
                            isView = it[EntityMyVideo.isView] ?: 0,
                            isShare = it[EntityMyVideo.isShare] ?: 0,
                            isDontCare = it[EntityMyVideo.isDontCare] ?: 0
                        )
                    }

                if (list.isNotEmpty()) {
                    call.respond(list)
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = false, data = null)
                    )
                }
            }

            post("/create") {
                val myVideo: MyVideo = call.receive()
                val noOfRowsAffected = db.insert(EntityMyVideo)
                {
                    set(it.my_video_id, null)
                    set(it.user_id, myVideo.title)
                    set(it.video_id, myVideo.theme_id)
                    set(it.isLike, myVideo.isLike)
                    set(it.isLater, myVideo.isLater)
                    set(it.isDownload, myVideo.isDownload)
                    set(it.isView, myVideo.isView)
                    set(it.isShare, myVideo.isShare)
                    set(it.isDontCare, myVideo.isDontCare)
                    set(it.view_time, myVideo.view_time)
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to register the my video")
                    )
                }
            }

            put("/update/{myvideo_id}") {
                val myVideoId = call.parameters["myvideo_id"].toString()
                val myVideoIdInt = myVideoId.toInt()

                val myVideo: MyVideo = call.receive()

                val noOfRowsAffected = db.update(EntityMyVideo)
                {
                    set(it.user_id, myVideo.title)
                    set(it.video_id, myVideo.theme_id)
                    set(it.isLike, myVideo.isLike)
                    set(it.isLater, myVideo.isLater)
                    set(it.isDownload, myVideo.isDownload)
                    set(it.isView, myVideo.isView)
                    set(it.isShare, myVideo.isShare)
                    set(it.isDontCare, myVideo.isDontCare)
                    set(it.view_time, myVideo.view_time)
                    where {
                        it.my_video_id eq myVideoIdInt
                    }
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to update the my video")
                    )
                }

            }

            put("/update/like") {
                val myVideoId = call.request.queryParameters["myvideo_id"].toString()
                val myVideoIdInt = myVideoId.toInt()

                val isLikeStr = call.request.queryParameters["is_like"].toString()
                val isLikeInt = isLikeStr.toInt()

                val noOfRowsAffected = db.update(EntityMyVideo)
                {

                    set(it.isLike, isLikeInt)
                    where {
                        it.my_video_id eq myVideoIdInt
                    }
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to update the my video")
                    )
                }

            }

            put("/update/later") {
                val myVideoId = call.request.queryParameters["myvideo_id"].toString()
                val myVideoIdInt = myVideoId.toInt()

                val isLaterStr = call.request.queryParameters["is_later"].toString()
                val isLaterInt = isLaterStr.toInt()

                val noOfRowsAffected = db.update(EntityMyVideo)
                {

                    set(it.isLater, isLaterInt)
                    where {
                        it.my_video_id eq myVideoIdInt
                    }
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to update the my video")
                    )
                }

            }

            put("/update/download") {
                val myVideoId = call.request.queryParameters["myvideo_id"].toString()
                val myVideoIdInt = myVideoId.toInt()

                val isDownloadStr = call.request.queryParameters["is_download"].toString()
                val isDownloadInt = isDownloadStr.toInt()

                val noOfRowsAffected = db.update(EntityMyVideo)
                {

                    set(it.isDownload, isDownloadInt)
                    where {
                        it.my_video_id eq myVideoIdInt
                    }
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to update the my video")
                    )
                }

            }

            put("/update/dontcare") {
                val myVideoId = call.request.queryParameters["myvideo_id"].toString()
                val myVideoIdInt = myVideoId.toInt()

                val isDontCareStr = call.request.queryParameters["is_dont_care"].toString()
                val isDontCareInt = isDontCareStr.toInt()

                val noOfRowsAffected = db.update(EntityMyVideo)
                {

                    set(it.isDontCare, isDontCareInt)
                    where {
                        it.my_video_id eq myVideoIdInt
                    }
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to update the my video")
                    )
                }

            }

            put("/update/viewtime") {
                val myVideoId = call.request.queryParameters["myvideo_id"].toString()
                val myVideoIdInt = myVideoId.toInt()

                val viewTimeStr = call.request.queryParameters["viewtime"].toString()
                val viewTime = viewTimeStr.toInt()

                val noOfRowsAffected = db.update(EntityMyVideo)
                {
                    set(it.view_time, viewTime)
                    where {
                        it.my_video_id eq myVideoIdInt
                    }
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to update the my video")
                    )
                }

            }

            delete("/delete")
            {
                val myVideoId = call.request.queryParameters["myvideo_id"].toString()
                val myVideoIdInt = myVideoId?.toInt() ?: -1

                val noOfRowsAffected = db.delete(EntityMyVideo)
                {
                    it.my_video_id eq myVideoIdInt
                }

                if (noOfRowsAffected > 0) {
                    //success
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                    )
                } else {
                    //fail
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(isSuccess = false, data = "Error to delete the my video")
                    )
                }
            }
        }
    }
}