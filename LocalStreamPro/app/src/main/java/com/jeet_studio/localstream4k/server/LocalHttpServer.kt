package com.jeet_studio.localstream4k.server

import fi.iki.elonen.NanoHTTPD
import java.io.File
import java.io.FileInputStream

class LocalHttpServer(port: Int, private val rootDir: File) : NanoHTTPD(port) {

    override fun serve(session: IHTTPSession): Response {
        val uri = session.uri
        if (uri == "/" || uri == "/index.html") {
            return newFixedLengthResponse(Response.Status.OK, "text/html", "<html><body><h1>LocalStream Pro Active</h1></body></html>")
        }

        val file = File(rootDir, uri.removePrefix("/"))
        if (!file.exists() || !file.canonicalPath.startsWith(rootDir.canonicalPath)) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "File Not Found")
        }

        // Support HTTP Range Requests for video/audio seeking
        val rangeHeader = session.headers["range"]
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            return serveRange(file, rangeHeader)
        }

        return try {
            val fis = FileInputStream(file)
            val mime = getMimeTypeForFile(file.name)
            val res = newChunkedResponse(Response.Status.OK, mime, fis)
            res.addHeader("Accept-Ranges", "bytes")
            res.addHeader("Content-Length", file.length().toString())
            res
        } catch (e: Exception) {
            newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", e.localizedMessage ?: "Error")
        }
    }

    private fun serveRange(file: File, rangeHeader: String): Response {
        val fileLength = file.length()
        val range = rangeHeader.substring("bytes=".length)
        val parts = range.split("-")
        val start = parts[0].toLongOrNull() ?: 0L
        val end = if (parts.size > 1 && parts[1].isNotEmpty()) parts[1].toLong() else fileLength - 1

        val contentLength = end - start + 1
        val fis = FileInputStream(file)
        fis.skip(start)

        val res = newFixedLengthResponse(Response.Status.PARTIAL_CONTENT, getMimeTypeForFile(file.name), fis, contentLength)
        res.addHeader("Content-Range", "bytes $start-$end/$fileLength")
        res.addHeader("Accept-Ranges", "bytes")
        res.addHeader("Content-Length", contentLength.toString())
        return res
    }

    private fun getMimeTypeForFile(name: String): String {
        return when {
            name.endsWith(".mp4", true) -> "video/mp4"
            name.endsWith(".mkv", true) -> "video/x-matroska"
            name.endsWith(".mp3", true) -> "audio/mpeg"
            name.endsWith(".flac", true) -> "audio/flac"
            name.endsWith(".jpg", true) || name.endsWith(".jpeg", true) -> "image/jpeg"
            name.endsWith(".png", true) -> "image/png"
            name.endsWith(".pdf", true) -> "application/pdf"
            else -> "application/octet-stream"
        }
    }
}
