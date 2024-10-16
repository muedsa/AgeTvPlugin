package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.CardHeight
import com.muedsa.tvbox.agetv.CardWidth
import com.muedsa.tvbox.agetv.HorizontalCardHeight
import com.muedsa.tvbox.agetv.HorizontalCardWidth
import com.muedsa.tvbox.agetv.model.PosterAnimeModel
import com.muedsa.tvbox.agetv.model.RankAnimeModel
import com.muedsa.tvbox.agetv.model.WeekAnimeModel
import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.data.MediaCardType
import com.muedsa.tvbox.api.service.IMainScreenService
import java.util.Calendar

class MainScreenService(
    private val ageApiService: AgeApiService
) : IMainScreenService {

    override suspend fun getRowsData(): List<MediaCardRow> {
        val rows = mutableListOf<MediaCardRow>()
        appendSpilt(rows)
        appendHomeList(rows)
        appendRecommend(rows)
        appendUpdate(rows)
        appendRank(rows)
        return rows
    }

    private suspend fun appendHomeList(rows: MutableList<MediaCardRow>) {
        val homeList = ageApiService.homeList()
        rows.add(
            MediaCardRow(
                title = "最近更新",
                list = convertPosterAnimeToCardList(homeList.latest),
                cardWidth = CardWidth,
                cardHeight = CardHeight,
                cardType = MediaCardType.STANDARD
            )
        )
        rows.add(
            MediaCardRow(
                title = "每日推荐",
                list = convertPosterAnimeToCardList(homeList.recommend),
                cardWidth = CardWidth,
                cardHeight = CardHeight,
                cardType = MediaCardType.STANDARD
            )
        )
        homeList.weekList.onEachIndexed { index, entry ->
            rows.add(
                MediaCardRow(
                    title = "每周放送 · ${WEEK_NAMES[index]}",
                    list = convertWeekListToColorCardList(index, entry.value),
                    cardWidth = HorizontalCardWidth,
                    cardHeight = HorizontalCardHeight,
                    cardType = MediaCardType.NOT_IMAGE
                )
            )
        }
    }

    private suspend fun appendSpilt(rows: MutableList<MediaCardRow>) {
        val list = ageApiService.slipic()
        rows.add(
            MediaCardRow(
                title = "每日推荐",
                list = list.map {
                    MediaCard(
                        id = it.aid.toString(),
                        title = it.title,
                        detailUrl = it.aid.toString(),
                        coverImageUrl = it.picUrl
                    )
                },
                cardWidth = HorizontalCardWidth,
                cardHeight = HorizontalCardHeight,
                cardType = MediaCardType.STANDARD
            )
        )
    }

    private suspend fun appendRecommend(rows: MutableList<MediaCardRow>) {
        val paged = ageApiService.recommend()
        rows.add(
            MediaCardRow(
                title = "推荐列表",
                list = convertPosterAnimeToCardList(paged.videos),
                cardWidth = CardWidth,
                cardHeight = CardHeight,
                cardType = MediaCardType.STANDARD
            )
        )
    }

    private suspend fun appendUpdate(rows: MutableList<MediaCardRow>) {
        val paged = ageApiService.update()
        rows.add(
            MediaCardRow(
                title = "更新列表",
                list = convertPosterAnimeToCardList(paged.videos),
                cardWidth = CardWidth,
                cardHeight = CardHeight,
                cardType = MediaCardType.STANDARD
            )
        )
    }

    private suspend fun appendRank(rows: MutableList<MediaCardRow>) {
        val paged = ageApiService.rank(Calendar.getInstance().get(Calendar.YEAR).toString())
        paged.rank.forEachIndexed { index, list ->
            rows.add(
                MediaCardRow(
                    title = if (index < RANK_NAMES.size) RANK_NAMES[index] else "排行榜 $index",
                    list = convertRankListToColorCardList(index, list),
                    cardWidth = HorizontalCardWidth,
                    cardHeight = HorizontalCardHeight,
                    cardType = MediaCardType.NOT_IMAGE
                )
            )
        }
    }

    companion object {
        private fun convertPosterAnimeToCardList(list: List<PosterAnimeModel>): List<MediaCard> =
            list.map {
                MediaCard(
                    id = it.aid.toString(),
                    title = it.title,
                    detailUrl = it.aid.toString(),
                    subTitle = it.newTitle,
                    coverImageUrl = it.picSmall
            )
        }

        private val WEEK_NAMES = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
        private val WEEK_CARD_COLORS = listOf(
            0XFF_15_5A_32,
            0XFF_09_53_45,
            0XFF_15_43_61,
            0XFF_42_49_49,
            0XFF_78_42_13
        )

        private fun convertWeekListToColorCardList(
            weekIndex: Int,
            list: List<WeekAnimeModel>
        ): List<MediaCard> =
            list.mapIndexed { index, item ->
                MediaCard(
                    id = item.id.toString(),
                    title = item.name,
                    detailUrl = item.id.toString(),
                    subTitle = item.nameForNew,
                    backgroundColor = WEEK_CARD_COLORS[(index + weekIndex) % WEEK_CARD_COLORS.size]
                )
            }

        private val RANK_NAMES = listOf("周榜", "月榜", "总榜")
        private fun convertRankListToColorCardList(
            randIndex: Int,
            list: List<RankAnimeModel>
        ): List<MediaCard> =
            list.mapIndexed { index, item ->
                MediaCard(
                    id = item.aid.toString(),
                    title = item.title,
                    detailUrl = item.aid.toString(),
                    subTitle = "No:${item.no} \uD83D\uDD25${item.ccNt}",
                    backgroundColor = WEEK_CARD_COLORS[(index + randIndex) % WEEK_CARD_COLORS.size]
                )
            }
    }

}