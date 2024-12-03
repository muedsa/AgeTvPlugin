package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.AgeMobileUrl
import com.muedsa.tvbox.agetv.AgeMobileUrlBase64
import com.muedsa.tvbox.agetv.CardHeight
import com.muedsa.tvbox.agetv.CardWidth
import com.muedsa.tvbox.agetv.model.AgePlayInfoModel
import com.muedsa.tvbox.api.data.DanmakuData
import com.muedsa.tvbox.api.data.DanmakuDataFlow
import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.data.MediaCardType
import com.muedsa.tvbox.api.data.MediaDetail
import com.muedsa.tvbox.api.data.MediaEpisode
import com.muedsa.tvbox.api.data.MediaHttpSource
import com.muedsa.tvbox.api.data.MediaPlaySource
import com.muedsa.tvbox.api.data.SavedMediaCard
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.tool.ChromeUserAgent
import com.muedsa.tvbox.tool.LenientJson
import com.muedsa.tvbox.tool.checkSuccess
import com.muedsa.tvbox.tool.decryptAES128CBCPKCS7
import com.muedsa.tvbox.tool.encryptAES128CBCPKCS7
import com.muedsa.tvbox.tool.feignChrome
import com.muedsa.tvbox.tool.get
import com.muedsa.tvbox.tool.md5
import com.muedsa.tvbox.tool.parseHtml
import com.muedsa.tvbox.tool.toRequestBuild
import kotlinx.serialization.encodeToString
import okhttp3.CookieJar
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.nodes.Document
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import timber.log.Timber
import java.net.URLDecoder
import java.util.UUID

class MediaDetailService(
    private val ageApiService: AgeApiService,
    private val okHttpClient: OkHttpClient,
    private val cookieJar: CookieJar,
    private val debug: Boolean = false
) : IMediaDetailService {

    override suspend fun getDetailData(mediaId: String, detailUrl: String): MediaDetail {
        val longId = mediaId.toLong()
        val detail = ageApiService.detail(longId)
        val strId = detail.video.id.toString()
        return MediaDetail(
            id = strId,
            title = detail.video.name,
            subTitle = "${detail.video.area} | ${detail.video.type} | ${detail.video.writer} | ${detail.video.company}",
            description = StringBuilder().also {
                it.append("原版名称：${detail.video.nameOriginal}\n")
                it.append("其他名称：${detail.video.nameOther}\n")
                it.append("首播时间：${detail.video.premiere}\n")
                it.append("播放状态：${detail.video.status}\n")
                it.append("剧情类型：${detail.video.tags}\n")
                it.append("简介：${detail.video.introClean}\n")
            }.toString(),
            detailUrl = strId,
            backgroundImageUrl = detail.video.cover,
            playSourceList = detail.video.playLists.map { source ->
                MediaPlaySource(
                    id = source.key,
                    name = detail.playerLabelArr[source.key] ?: source.key,
                    episodeList = source.value.map {
                        MediaEpisode(
                            id = it[0],                     // 剧集名称
                            name = it[0],                   // 剧集名称
                            flag5 = it[1],                  // 剧集密钥
                            flag6 = detail.playerJx["vip"], // vip播放源地址
                            flag7 = detail.playerJx["zj"],  // 其他播放源地址
                            flag8 = detail.playerVip        // 那些是vip播放源 英文逗号分隔
                        )
                    }
                )
            },
            favoritedMediaCard = SavedMediaCard(
                id = strId,
                title = detail.video.name,
                detailUrl = strId,
                coverImageUrl = detail.video.cover,
                cardWidth = CardWidth,
                cardHeight = CardHeight,
            ),
            rows = buildList {
                if (detail.series.isNotEmpty()) {
                    add(
                        MediaCardRow(
                            title = "相关动画",
                            list = MainScreenService.convertPosterAnimeToCardList(detail.series),
                            cardWidth = CardWidth,
                            cardHeight = CardHeight,
                            cardType = MediaCardType.STANDARD
                        )
                    )
                }
                if (detail.similar.isNotEmpty()) {
                    add(
                        MediaCardRow(
                            title = "相关推荐",
                            list = MainScreenService.convertPosterAnimeToCardList(detail.similar),
                            cardWidth = CardWidth,
                            cardHeight = CardHeight,
                            cardType = MediaCardType.STANDARD
                        )
                    )
                }
            }
        )
    }

    override suspend fun getEpisodePlayInfo(
        playSource: MediaPlaySource,
        episode: MediaEpisode
    ): MediaHttpSource {
        checkNotNull(episode.flag5) { "flag5为空" }
        checkNotNull(episode.flag6) { "flag6为空" }
        checkNotNull(episode.flag7) { "flag7为空" }
        checkNotNull(episode.flag8) { "flag8为空" }
        val isVip = episode.flag8!!.split(",").contains(playSource.id)
        val url = "${if (isVip) episode.flag6 else episode.flag7}${episode.flag5}"
        val playInfo = getPlayInfo(url)
        check(playInfo.vUrl.isNotEmpty()) { "get vUrl is empty, from:$url" }
        val httpUrl = url.toHttpUrl()
        val portStr = if ((httpUrl.scheme === "http" && httpUrl.port == 80)
            || (httpUrl.scheme === "https" && httpUrl.port == 443)
        ) "" else ":${httpUrl.port}"
        decryptPlayUrl(
            playInfo = playInfo,
            baseUrl = "${httpUrl.scheme}://${httpUrl.host}${portStr}${httpUrl.encodedPath}"
        )
        return MediaHttpSource(
            url = playInfo.realUrl
        )
    }

    private fun getPlayInfo(url: String): AgePlayInfoModel {
        val doc = jsoupGet(url = url)
        val head = doc.head()
        val body = doc.body()
        val key1 = head.selectFirst("meta[http-equiv=\"Content-Type\"]")
            ?.attr("id")
            ?: ""
        val key2 = head.selectFirst("meta[name=\"viewport\"]")
            ?.attr("id")
            ?.replace("viewport", "")
            ?: ""
        val script = body.selectFirst("script")?.html() ?: ""
        return AgePlayInfoModel(
            pageUrl = url,
            metaKey = "$key1$key2",
            host = url.toHttpUrl().host,
            vUrl = getJsStringVar("Vurl", script),
            version = getJsStringVar("Version", script),
            time = getJsStringVar("Time", script),
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private suspend fun decryptPlayUrl(playInfo: AgePlayInfoModel, baseUrl: String) {
        if (playInfo.vUrl.startsWith("https://")
            || playInfo.vUrl.startsWith("http://")
        ) {
            playInfo.realUrl = playInfo.vUrl
            playInfo.realUrlType = if (playInfo.vUrl.contains(".m3u8")) "hls" else "mp4"
        } else {
            val uuid = UUID.randomUUID().toString().uppercase()
            val req = AgeVipPlayerApiService.ApiRequest(
                url = playInfo.vUrl,
                host = playInfo.host,
                referer = AgeMobileUrlBase64,
                time = playInfo.time
            )
            Timber.i("decrypt api req: $req")
            val encryptReq = LenientJson.encodeToString(req)
                .encryptAES128CBCPKCS7(WASM_AES_KEY, WASM_AES_KEY)
                .toHexString(HexFormat.UpperCase)
            Timber.i("api baseUrl: $baseUrl")
            val resp =
                getVipApiService(baseUrl = baseUrl, debug = debug, cookieJar = cookieJar).api(
                params = encryptReq,
                uuid = uuid,
                time = playInfo.time,
                version = playInfo.version,
                sign = "${playInfo.host} | $uuid | ${playInfo.time} | ${playInfo.version} | $encryptReq"
                    .encryptAES128CBCPKCS7(WASM_AES_KEY, WASM_AES_KEY)
                    .toHexString(HexFormat.UpperCase),
                referer = playInfo.pageUrl
            )
            Timber.i("decrypt api resp: $resp")
            if (resp.status == 1) {
                val decryptData = if (resp.code == 10) {
                    val keyAndIv = "${resp.code}${playInfo.metaKey}${resp.appKey}${resp.version}"
                        .md5()
                        .toHexString()
                    resp.data.hexToByteArray(HexFormat.UpperCase).decryptAES128CBCPKCS7(
                        keyAndIv.substring(0, 16),
                        keyAndIv.substring(16, 32)
                    ).toString(Charsets.UTF_8)

                } else {
                    val keyAndIv = "${resp.code}${resp.appKey}${resp.version}"
                        .md5()
                        .toHexString()
                    resp.data.hexToByteArray(HexFormat.UpperCase).decryptAES128CBCPKCS7(
                        keyAndIv.substring(0, 16),
                        keyAndIv.substring(16, 32)
                    ).toString(Charsets.UTF_8)
                }
                Timber.i("decryptData: $decryptData")
                val playUrlResp =
                    LenientJson.decodeFromString<AgeVipPlayerApiService.PlayUrlResp>(decryptData)
                if (playUrlResp.code == 200) {
                    playInfo.realUrl = playUrlResp.url.let {
                        val encodeUrl = if (resp.code == 10) {
                            it.hexToByteArray(HexFormat.UpperCase)
                                .decryptAES128CBCPKCS7(WASM_AES_KEY, WASM_AES_KEY)
                                .toString(Charsets.UTF_8)
                        } else it
                        URLDecoder.decode(encodeUrl, Charsets.UTF_8.name())
                    }
                    playInfo.realUrlType = playUrlResp.type
                }
            }
        }
    }

    override suspend fun getEpisodeDanmakuDataList(episode: MediaEpisode): List<DanmakuData> =
        emptyList()

    override suspend fun getEpisodeDanmakuDataFlow(episode: MediaEpisode): DanmakuDataFlow? = null

    private fun jsoupGet(url: String): Document =
        url.toRequestBuild()
            .feignChrome(referer = AgeMobileUrl)
            .get(okHttpClient = okHttpClient)
            .checkSuccess()
            .parseHtml()

    companion object {
        private const val WASM_AES_KEY = "ni po jie ni ** "

        private fun getJsStringVar(field: String, content: String): String {
            val pattern = "var\\s+$field\\s*=\\s*['\"](.*?)['\"]\\s*;*".toRegex()
            return pattern.find(content)?.groups.let {
                if (!it.isNullOrEmpty() && it.size > 1) {
                    it[1]?.value ?: ""
                } else ""
            }
        }

        private var DEFAULT_RETROFIT: Retrofit? = null
        private var DEFAULT_VIP_API_SERVICE: AgeVipPlayerApiService? = null

        @Synchronized
        private fun createDefaultRetrofit(baseUrl: String, debug: Boolean, cookieJar: CookieJar) {
            if (DEFAULT_RETROFIT == null) {
                DEFAULT_RETROFIT = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(LenientJson.asConverterFactory("image/vnd.microsoft.icon".toMediaType()))
                    .client(OkHttpClient.Builder().addInterceptor {
                        it.proceed(
                            it.request().newBuilder()
                                .header("User-Agent", ChromeUserAgent)
                                .header("Referer", AgeMobileUrl).build()
                        )
                    }.also {
                        if (debug) {
                            val loggingInterceptor = HttpLoggingInterceptor()
                            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                            it.addInterceptor(loggingInterceptor)
                        }
                        it.cookieJar(cookieJar)
                    }.build())
                    .build()
                DEFAULT_VIP_API_SERVICE =
                    DEFAULT_RETROFIT!!.create(AgeVipPlayerApiService::class.java)
            }
        }

        private fun getVipApiService(
            baseUrl: String,
            debug: Boolean,
            cookieJar: CookieJar
        ): AgeVipPlayerApiService {
            if (DEFAULT_RETROFIT == null) {
                createDefaultRetrofit(baseUrl, debug, cookieJar)
            }
            return if (DEFAULT_RETROFIT!!.baseUrl().toString() == baseUrl) {
                DEFAULT_VIP_API_SERVICE!!
            } else {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(LenientJson.asConverterFactory("image/vnd.microsoft.icon".toMediaType()))
                    .client(OkHttpClient.Builder().addInterceptor {
                        it.proceed(
                            it.request().newBuilder()
                                .header("User-Agent", ChromeUserAgent)
                                .header("Referer", AgeMobileUrl).build()
                        )
                    }.also {
                        if (debug) {
                            val loggingInterceptor = HttpLoggingInterceptor()
                            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                            it.addInterceptor(loggingInterceptor)
                        }
                    }.build())
                    .build()
                retrofit.create(AgeVipPlayerApiService::class.java)
            }
        }
    }
}