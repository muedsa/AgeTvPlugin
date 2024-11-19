package com.muedsa.tvbox.agetv

import com.muedsa.tvbox.agetv.model.AgeFilterOption
import com.muedsa.tvbox.tool.LenientJson
import org.junit.Test

class CatalogOptionsGenerator {

    @Test
    fun generate() {
        val filters = LenientJson.decodeFromString<List<AgeFilterOption>>(FILTERS_JSON_STR)
        var code = "val AgeCatalogOptions = listOf<MediaCatalogOption>(\n"
        filters.forEach { filter ->
            code += "    MediaCatalogOption(\n"
            code += "        name = \"${filter.label}\",\n"
            code += "        value = \"${filter.name}\",\n"
            code += "        items = listOf(\n"
            filter.data.forEachIndexed { index, data ->
                code += "            MediaCatalogOptionItem(\n"
                code += "                name = \"${data.text}\",\n"
                code += "                value = \"${data.value}\",\n"
                if (index == 0) {
                    code += "                defaultChecked = true,\n"
                }
                code += "            ),\n"
            }
            code += "        ),\n"
            code += "        required = true,\n"
            code += "    ),\n"
        }
        code += ")\n"
        println(code)
    }

    companion object {
        const val FILTERS_JSON_STR =
            "[{\"name\":\"region\",\"label\":\"地区\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"日本\",\"value\":\"日本\"},{\"text\":\"中国\",\"value\":\"中国\"},{\"text\":\"欧美\",\"value\":\"欧美\"}]},{\"name\":\"genre\",\"label\":\"版本\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"TV\",\"value\":\"TV\"},{\"text\":\"剧场版\",\"value\":\"剧场版\"},{\"text\":\"OVA\",\"value\":\"OVA\"}]},{\"name\":\"letter\",\"label\":\"首字母\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"A\",\"value\":\"A\"},{\"text\":\"B\",\"value\":\"B\"},{\"text\":\"C\",\"value\":\"C\"},{\"text\":\"D\",\"value\":\"D\"},{\"text\":\"E\",\"value\":\"E\"},{\"text\":\"F\",\"value\":\"F\"},{\"text\":\"G\",\"value\":\"G\"},{\"text\":\"H\",\"value\":\"H\"},{\"text\":\"I\",\"value\":\"I\"},{\"text\":\"J\",\"value\":\"J\"},{\"text\":\"K\",\"value\":\"K\"},{\"text\":\"L\",\"value\":\"L\"},{\"text\":\"M\",\"value\":\"M\"},{\"text\":\"N\",\"value\":\"N\"},{\"text\":\"O\",\"value\":\"O\"},{\"text\":\"P\",\"value\":\"P\"},{\"text\":\"Q\",\"value\":\"Q\"},{\"text\":\"R\",\"value\":\"R\"},{\"text\":\"S\",\"value\":\"S\"},{\"text\":\"T\",\"value\":\"T\"},{\"text\":\"U\",\"value\":\"U\"},{\"text\":\"V\",\"value\":\"V\"},{\"text\":\"W\",\"value\":\"W\"},{\"text\":\"X\",\"value\":\"X\"},{\"text\":\"Y\",\"value\":\"Y\"},{\"text\":\"Z\",\"value\":\"Z\"}]},{\"name\":\"year\",\"label\":\"年份\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"2024\",\"value\":\"2024\"},{\"text\":\"2023\",\"value\":\"2023\"},{\"text\":\"2022\",\"value\":\"2022\"},{\"text\":\"2021\",\"value\":\"2021\"},{\"text\":\"2020\",\"value\":\"2020\"},{\"text\":\"2019\",\"value\":\"2019\"},{\"text\":\"2018\",\"value\":\"2018\"},{\"text\":\"2017\",\"value\":\"2017\"},{\"text\":\"2016\",\"value\":\"2016\"},{\"text\":\"2015\",\"value\":\"2015\"},{\"text\":\"2014\",\"value\":\"2014\"},{\"text\":\"2013\",\"value\":\"2013\"},{\"text\":\"2012\",\"value\":\"2012\"},{\"text\":\"2011\",\"value\":\"2011\"},{\"text\":\"2010\",\"value\":\"2010\"},{\"text\":\"2009\",\"value\":\"2009\"},{\"text\":\"2008\",\"value\":\"2008\"},{\"text\":\"2007\",\"value\":\"2007\"},{\"text\":\"2006\",\"value\":\"2006\"},{\"text\":\"2005\",\"value\":\"2005\"},{\"text\":\"2004\",\"value\":\"2004\"},{\"text\":\"2003\",\"value\":\"2003\"},{\"text\":\"2002\",\"value\":\"2002\"},{\"text\":\"2001\",\"value\":\"2001\"},{\"text\":\"2000以前\",\"value\":\"2000\"}]},{\"name\":\"season\",\"label\":\"季度\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"1月\",\"value\":\"1\"},{\"text\":\"4月\",\"value\":\"4\"},{\"text\":\"7月\",\"value\":\"7\"},{\"text\":\"10月\",\"value\":\"10\"}]},{\"name\":\"status\",\"label\":\"状态\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"连载\",\"value\":\"连载\"},{\"text\":\"完结\",\"value\":\"完结\"},{\"text\":\"未播放\",\"value\":\"未播放\"}]},{\"name\":\"label\",\"label\":\"类型\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"搞笑\",\"value\":\"搞笑\"},{\"text\":\"运动\",\"value\":\"运动\"},{\"text\":\"励志\",\"value\":\"励志\"},{\"text\":\"热血\",\"value\":\"热血\"},{\"text\":\"战斗\",\"value\":\"战斗\"},{\"text\":\"竞技\",\"value\":\"竞技\"},{\"text\":\"校园\",\"value\":\"校园\"},{\"text\":\"青春\",\"value\":\"青春\"},{\"text\":\"爱情\",\"value\":\"爱情\"},{\"text\":\"恋爱\",\"value\":\"恋爱\"},{\"text\":\"冒险\",\"value\":\"冒险\"},{\"text\":\"后宫\",\"value\":\"后宫\"},{\"text\":\"百合\",\"value\":\"百合\"},{\"text\":\"治愈\",\"value\":\"治愈\"},{\"text\":\"萝莉\",\"value\":\"萝莉\"},{\"text\":\"魔法\",\"value\":\"魔法\"},{\"text\":\"悬疑\",\"value\":\"悬疑\"},{\"text\":\"推理\",\"value\":\"推理\"},{\"text\":\"奇幻\",\"value\":\"奇幻\"},{\"text\":\"科幻\",\"value\":\"科幻\"},{\"text\":\"游戏\",\"value\":\"游戏\"},{\"text\":\"神魔\",\"value\":\"神魔\"},{\"text\":\"恐怖\",\"value\":\"恐怖\"},{\"text\":\"血腥\",\"value\":\"血腥\"},{\"text\":\"机战\",\"value\":\"机战\"},{\"text\":\"战争\",\"value\":\"战争\"},{\"text\":\"犯罪\",\"value\":\"犯罪\"},{\"text\":\"历史\",\"value\":\"历史\"},{\"text\":\"社会\",\"value\":\"社会\"},{\"text\":\"职场\",\"value\":\"职场\"},{\"text\":\"剧情\",\"value\":\"剧情\"},{\"text\":\"伪娘\",\"value\":\"伪娘\"},{\"text\":\"耽美\",\"value\":\"耽美\"},{\"text\":\"童年\",\"value\":\"童年\"},{\"text\":\"教育\",\"value\":\"教育\"},{\"text\":\"亲子\",\"value\":\"亲子\"},{\"text\":\"真人\",\"value\":\"真人\"},{\"text\":\"歌舞\",\"value\":\"歌舞\"},{\"text\":\"肉番\",\"value\":\"肉番\"},{\"text\":\"美少女\",\"value\":\"美少女\"},{\"text\":\"轻小说\",\"value\":\"轻小说\"},{\"text\":\"吸血鬼\",\"value\":\"吸血鬼\"},{\"text\":\"女性向\",\"value\":\"女性向\"},{\"text\":\"泡面番\",\"value\":\"泡面番\"},{\"text\":\"欢乐向\",\"value\":\"欢乐向\"}]},{\"name\":\"resource\",\"label\":\"资源\",\"data\":[{\"text\":\"全部\",\"value\":\"all\"},{\"text\":\"BDRIP\",\"value\":\"BDRIP\"},{\"text\":\"AGERIP\",\"value\":\"AGERIP\"}]},{\"name\":\"order\",\"label\":\"排序\",\"data\":[{\"text\":\"更新时间\",\"value\":\"time\"},{\"text\":\"名称\",\"value\":\"name\"},{\"text\":\"点击量\",\"value\":\"hits\"}]}]"
    }
}