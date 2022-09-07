package com.example.route

import com.example.model.MyPost
import com.example.model.MyVideo
import com.example.model.Video
import com.example.mysql.DbConnection
import com.example.mysql.entity.EntityMyPost
import com.example.mysql.entity.EntityPost
import com.example.mysql.entity.EntityPostType
import com.example.mysql.entity.EntityVideo
import com.example.util.GenericResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.database.Database
import org.ktorm.dsl.*

fun Application.routeMyPost() {
    val db: Database = DbConnection.getDatabaseInstance()
    routing {
        route("/mypost") {
            get("/list") {
                val userId = call.request.queryParameters["user_id"].toString()
                val list = db.from(EntityMyPost).innerJoin(EntityPost).innerJoin(EntityPostType)
                    .select()
                    .where(EntityMyPost.user_id eq userId and (EntityMyPost.post_id eq EntityPost.post_id) and (EntityPost.post_type_id eq EntityPostType.post_type_id))
                    .map {
                        MyPost(
                            my_post_id = it[EntityMyPost.my_post_id] ?: 0,
                            user_id = it[EntityMyPost.user_id].orEmpty(),
                            post_id = it[EntityMyPost.post_id] ?: 0,
                            post_type_id = it[EntityPost.post_type_id] ?: 0,
                            post_type_name = it[EntityPostType.post_type_name].orEmpty(),
                            theme_id = it[EntityPost.theme_id] ?: 0,
                            title = it[EntityPost.title].orEmpty(),
                            content = it[EntityPost.content].orEmpty(),
                            image = it[EntityPost.image].orEmpty(),
                            description = it[EntityPost.description].orEmpty(),
                            timeline = it[EntityPost.timeline].orEmpty(),
                            place = it[EntityPost.place].orEmpty(),
                            read_count = it[EntityPost.read_count] ?: 0,
                            download_count = it[EntityPost.download_count] ?: 0,
                            rate_count = it[EntityPost.rate_count] ?: 0,
                            date_submitted = it[EntityPost.date_submitted].orEmpty(),
                            isRead = it[EntityMyPost.isRead] ?: 0,
                            isDownload = it[EntityMyPost.isDownload] ?: 0,
                            rate = it[EntityMyPost.rate] ?: 0,
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
                val postId = call.request.queryParameters["post_id"]
                val postIdInt = postId?.toInt() ?: -1

                val list = db.from(EntityMyPost).innerJoin(EntityPost).innerJoin(EntityPostType)
                    .select()
                    .where(EntityMyPost.user_id eq userId
                            and (EntityMyPost.post_id eq postIdInt)
                            and (EntityMyPost.post_id eq EntityPost.post_id)
                            and (EntityPost.post_type_id eq EntityPostType.post_type_id)
                    )
                    .map {
                        MyPost(
                            my_post_id = it[EntityMyPost.my_post_id] ?: 0,
                            user_id = it[EntityMyPost.user_id].orEmpty(),
                            post_id = it[EntityMyPost.post_id] ?: 0,
                            post_type_id = it[EntityPost.post_type_id] ?: 0,
                            post_type_name = it[EntityPostType.post_type_name].orEmpty(),
                            theme_id = it[EntityPost.theme_id] ?: 0,
                            title = it[EntityPost.title].orEmpty(),
                            content = it[EntityPost.content].orEmpty(),
                            image = it[EntityPost.image].orEmpty(),
                            description = it[EntityPost.description].orEmpty(),
                            timeline = it[EntityPost.timeline].orEmpty(),
                            place = it[EntityPost.place].orEmpty(),
                            read_count = it[EntityPost.read_count] ?: 0,
                            download_count = it[EntityPost.download_count] ?: 0,
                            rate_count = it[EntityPost.rate_count] ?: 0,
                            date_submitted = it[EntityPost.date_submitted].orEmpty(),
                            isRead = it[EntityMyPost.isRead] ?: 0,
                            isDownload = it[EntityMyPost.isDownload] ?: 0,
                            rate = it[EntityMyPost.rate] ?: 0,
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
                val myPost: MyPost = call.receive()
                val noOfRowsAffected = db.insert(EntityMyPost)
                {
                    set(it.my_post_id, null)
                    set(it.post_id, myPost.post_id)
                    set(it.user_id, myPost.user_id)
                    set(it.isDownload, myPost.isDownload)
                    set(it.rate, myPost.rate)
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
                        GenericResponse(isSuccess = false, data = "Error to register the user")
                    )
                }
            }

            put("/update/{mypost_id}") {
                val myPostId = call.parameters["mypost_id"].toString()
                val myPostIdInt = myPostId.toInt()

                val myPost: MyPost = call.receive()

                val noOfRowsAffected = db.update(EntityMyPost)
                {
                    set(it.post_id, myPost.post_id)
                    set(it.user_id, myPost.user_id)
                    set(it.isDownload, myPost.isDownload)
                    set(it.rate, myPost.rate)
                    where {
                        it.my_post_id eq myPostIdInt
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
                        GenericResponse(isSuccess = false, data = "Error to update the video")
                    )
                }

            }

            put("/update/read") {
                val myPostId = call.request.queryParameters["mypost_id"].toString()
                val myPostIdInt = myPostId.toInt()

                val noOfRowsAffected = db.update(EntityMyPost)
                {
                    set(it.isRead, 1)
                    where {
                        it.my_post_id eq myPostIdInt
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
                        GenericResponse(isSuccess = false, data = "Error to update the video")
                    )
                }

            }

            put("/update/download") {
                val myPostId = call.request.queryParameters["mypost_id"].toString()
                val myPostIdInt = myPostId.toInt()

                val isDownloadStr = call.request.queryParameters["is_download"].toString()
                val isDownloadInt = isDownloadStr.toInt()

                val noOfRowsAffected = db.update(EntityMyPost)
                {
                    set(it.isDownload, isDownloadInt)
                    where {
                        it.my_post_id eq myPostIdInt
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
                        GenericResponse(isSuccess = false, data = "Error to update the video")
                    )
                }

            }

            put("/update/rate") {
                val myPostId = call.request.queryParameters["mypost_id"].toString()
                val myPostIdInt = myPostId.toInt()

                val rateStr = call.request.queryParameters["rate"].toString()
                val rateInt = rateStr.toInt()

                val noOfRowsAffected = db.update(EntityMyPost)
                {
                    set(it.rate, rateInt)
                    where {
                        it.my_post_id eq myPostIdInt
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
                        GenericResponse(isSuccess = false, data = "Error to update the video")
                    )
                }

            }

            delete("/delete")
            {
                val myPostId = call.request.queryParameters["mypost_id"].toString()
                val myPostIdInt = myPostId?.toInt() ?: -1

                val noOfRowsAffected = db.delete(EntityMyPost)
                {
                    it.my_post_id eq myPostIdInt
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
                        GenericResponse(isSuccess = false, data = "Error to delete the video")
                    )
                }
            }
        }
    }
}