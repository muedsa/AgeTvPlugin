package com.muedsa.tvbox.agetv

import com.muedsa.tvbox.api.data.MediaCatalogOption
import com.muedsa.tvbox.api.data.MediaCatalogOptionItem
import com.muedsa.tvbox.tool.decodeBase64ToStr

const val AgeMobileUrlBase64 = "aHR0cHM6Ly9tLmFnZWZhbnMubGEv"
val AgeMobileUrl = AgeMobileUrlBase64.decodeBase64ToStr()
const val AgeMobileApiUrlBase64 = "aHR0cHM6Ly9hcGkuYWdlZmFucy5sYS8="
val AgeMobileApiUrl = AgeMobileApiUrlBase64.decodeBase64ToStr()

const val CardWidth = 128
const val CardHeight = 178
const val HorizontalCardWidth = 240
const val HorizontalCardHeight = 135


val AgeCatalogOptions = listOf(
    MediaCatalogOption(
        name = "地区",
        value = "region",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "日本",
                value = "日本",
            ),
            MediaCatalogOptionItem(
                name = "中国",
                value = "中国",
            ),
            MediaCatalogOptionItem(
                name = "欧美",
                value = "欧美",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "版本",
        value = "genre",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "TV",
                value = "TV",
            ),
            MediaCatalogOptionItem(
                name = "剧场版",
                value = "剧场版",
            ),
            MediaCatalogOptionItem(
                name = "OVA",
                value = "OVA",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "首字母",
        value = "letter",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "A",
                value = "A",
            ),
            MediaCatalogOptionItem(
                name = "B",
                value = "B",
            ),
            MediaCatalogOptionItem(
                name = "C",
                value = "C",
            ),
            MediaCatalogOptionItem(
                name = "D",
                value = "D",
            ),
            MediaCatalogOptionItem(
                name = "E",
                value = "E",
            ),
            MediaCatalogOptionItem(
                name = "F",
                value = "F",
            ),
            MediaCatalogOptionItem(
                name = "G",
                value = "G",
            ),
            MediaCatalogOptionItem(
                name = "H",
                value = "H",
            ),
            MediaCatalogOptionItem(
                name = "I",
                value = "I",
            ),
            MediaCatalogOptionItem(
                name = "J",
                value = "J",
            ),
            MediaCatalogOptionItem(
                name = "K",
                value = "K",
            ),
            MediaCatalogOptionItem(
                name = "L",
                value = "L",
            ),
            MediaCatalogOptionItem(
                name = "M",
                value = "M",
            ),
            MediaCatalogOptionItem(
                name = "N",
                value = "N",
            ),
            MediaCatalogOptionItem(
                name = "O",
                value = "O",
            ),
            MediaCatalogOptionItem(
                name = "P",
                value = "P",
            ),
            MediaCatalogOptionItem(
                name = "Q",
                value = "Q",
            ),
            MediaCatalogOptionItem(
                name = "R",
                value = "R",
            ),
            MediaCatalogOptionItem(
                name = "S",
                value = "S",
            ),
            MediaCatalogOptionItem(
                name = "T",
                value = "T",
            ),
            MediaCatalogOptionItem(
                name = "U",
                value = "U",
            ),
            MediaCatalogOptionItem(
                name = "V",
                value = "V",
            ),
            MediaCatalogOptionItem(
                name = "W",
                value = "W",
            ),
            MediaCatalogOptionItem(
                name = "X",
                value = "X",
            ),
            MediaCatalogOptionItem(
                name = "Y",
                value = "Y",
            ),
            MediaCatalogOptionItem(
                name = "Z",
                value = "Z",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "年份",
        value = "year",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "2024",
                value = "2024",
            ),
            MediaCatalogOptionItem(
                name = "2023",
                value = "2023",
            ),
            MediaCatalogOptionItem(
                name = "2022",
                value = "2022",
            ),
            MediaCatalogOptionItem(
                name = "2021",
                value = "2021",
            ),
            MediaCatalogOptionItem(
                name = "2020",
                value = "2020",
            ),
            MediaCatalogOptionItem(
                name = "2019",
                value = "2019",
            ),
            MediaCatalogOptionItem(
                name = "2018",
                value = "2018",
            ),
            MediaCatalogOptionItem(
                name = "2017",
                value = "2017",
            ),
            MediaCatalogOptionItem(
                name = "2016",
                value = "2016",
            ),
            MediaCatalogOptionItem(
                name = "2015",
                value = "2015",
            ),
            MediaCatalogOptionItem(
                name = "2014",
                value = "2014",
            ),
            MediaCatalogOptionItem(
                name = "2013",
                value = "2013",
            ),
            MediaCatalogOptionItem(
                name = "2012",
                value = "2012",
            ),
            MediaCatalogOptionItem(
                name = "2011",
                value = "2011",
            ),
            MediaCatalogOptionItem(
                name = "2010",
                value = "2010",
            ),
            MediaCatalogOptionItem(
                name = "2009",
                value = "2009",
            ),
            MediaCatalogOptionItem(
                name = "2008",
                value = "2008",
            ),
            MediaCatalogOptionItem(
                name = "2007",
                value = "2007",
            ),
            MediaCatalogOptionItem(
                name = "2006",
                value = "2006",
            ),
            MediaCatalogOptionItem(
                name = "2005",
                value = "2005",
            ),
            MediaCatalogOptionItem(
                name = "2004",
                value = "2004",
            ),
            MediaCatalogOptionItem(
                name = "2003",
                value = "2003",
            ),
            MediaCatalogOptionItem(
                name = "2002",
                value = "2002",
            ),
            MediaCatalogOptionItem(
                name = "2001",
                value = "2001",
            ),
            MediaCatalogOptionItem(
                name = "2000以前",
                value = "2000",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "季度",
        value = "season",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "1月",
                value = "1",
            ),
            MediaCatalogOptionItem(
                name = "4月",
                value = "4",
            ),
            MediaCatalogOptionItem(
                name = "7月",
                value = "7",
            ),
            MediaCatalogOptionItem(
                name = "10月",
                value = "10",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "状态",
        value = "status",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "连载",
                value = "连载",
            ),
            MediaCatalogOptionItem(
                name = "完结",
                value = "完结",
            ),
            MediaCatalogOptionItem(
                name = "未播放",
                value = "未播放",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "类型",
        value = "label",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "搞笑",
                value = "搞笑",
            ),
            MediaCatalogOptionItem(
                name = "运动",
                value = "运动",
            ),
            MediaCatalogOptionItem(
                name = "励志",
                value = "励志",
            ),
            MediaCatalogOptionItem(
                name = "热血",
                value = "热血",
            ),
            MediaCatalogOptionItem(
                name = "战斗",
                value = "战斗",
            ),
            MediaCatalogOptionItem(
                name = "竞技",
                value = "竞技",
            ),
            MediaCatalogOptionItem(
                name = "校园",
                value = "校园",
            ),
            MediaCatalogOptionItem(
                name = "青春",
                value = "青春",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "爱情",
            ),
            MediaCatalogOptionItem(
                name = "恋爱",
                value = "恋爱",
            ),
            MediaCatalogOptionItem(
                name = "冒险",
                value = "冒险",
            ),
            MediaCatalogOptionItem(
                name = "后宫",
                value = "后宫",
            ),
            MediaCatalogOptionItem(
                name = "百合",
                value = "百合",
            ),
            MediaCatalogOptionItem(
                name = "治愈",
                value = "治愈",
            ),
            MediaCatalogOptionItem(
                name = "萝莉",
                value = "萝莉",
            ),
            MediaCatalogOptionItem(
                name = "魔法",
                value = "魔法",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "悬疑",
            ),
            MediaCatalogOptionItem(
                name = "推理",
                value = "推理",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "奇幻",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "科幻",
            ),
            MediaCatalogOptionItem(
                name = "游戏",
                value = "游戏",
            ),
            MediaCatalogOptionItem(
                name = "神魔",
                value = "神魔",
            ),
            MediaCatalogOptionItem(
                name = "恐怖",
                value = "恐怖",
            ),
            MediaCatalogOptionItem(
                name = "血腥",
                value = "血腥",
            ),
            MediaCatalogOptionItem(
                name = "机战",
                value = "机战",
            ),
            MediaCatalogOptionItem(
                name = "战争",
                value = "战争",
            ),
            MediaCatalogOptionItem(
                name = "犯罪",
                value = "犯罪",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "历史",
            ),
            MediaCatalogOptionItem(
                name = "社会",
                value = "社会",
            ),
            MediaCatalogOptionItem(
                name = "职场",
                value = "职场",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "剧情",
            ),
            MediaCatalogOptionItem(
                name = "伪娘",
                value = "伪娘",
            ),
            MediaCatalogOptionItem(
                name = "耽美",
                value = "耽美",
            ),
            MediaCatalogOptionItem(
                name = "童年",
                value = "童年",
            ),
            MediaCatalogOptionItem(
                name = "教育",
                value = "教育",
            ),
            MediaCatalogOptionItem(
                name = "亲子",
                value = "亲子",
            ),
            MediaCatalogOptionItem(
                name = "真人",
                value = "真人",
            ),
            MediaCatalogOptionItem(
                name = "歌舞",
                value = "歌舞",
            ),
            MediaCatalogOptionItem(
                name = "肉番",
                value = "肉番",
            ),
            MediaCatalogOptionItem(
                name = "美少女",
                value = "美少女",
            ),
            MediaCatalogOptionItem(
                name = "轻小说",
                value = "轻小说",
            ),
            MediaCatalogOptionItem(
                name = "吸血鬼",
                value = "吸血鬼",
            ),
            MediaCatalogOptionItem(
                name = "女性向",
                value = "女性向",
            ),
            MediaCatalogOptionItem(
                name = "泡面番",
                value = "泡面番",
            ),
            MediaCatalogOptionItem(
                name = "欢乐向",
                value = "欢乐向",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "资源",
        value = "resource",
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "all",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "BDRIP",
                value = "BDRIP",
            ),
            MediaCatalogOptionItem(
                name = "AGERIP",
                value = "AGERIP",
            ),
        ),
        required = true,
    ),
    MediaCatalogOption(
        name = "排序",
        value = "order",
        items = listOf(
            MediaCatalogOptionItem(
                name = "更新时间",
                value = "time",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "名称",
                value = "name",
            ),
            MediaCatalogOptionItem(
                name = "点击量",
                value = "hits",
            ),
        ),
        required = true,
    ),
)