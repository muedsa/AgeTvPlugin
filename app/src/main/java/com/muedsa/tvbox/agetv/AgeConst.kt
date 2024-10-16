package com.muedsa.tvbox.agetv

import com.muedsa.tvbox.tool.decodeBase64ToStr


const val AgeMobileUrlBase64 = "aHR0cHM6Ly9tLmFnZWRtLm9yZy8="
val AgeMobileUrl = AgeMobileUrlBase64.decodeBase64ToStr()
const val AgeMobileApiUrlBase64 = "aHR0cHM6Ly9hcGkuYWdlZG0ub3JnLw0K"
val AgeMobileApiUrl = AgeMobileApiUrlBase64.decodeBase64ToStr()

const val CardWidth = 128
const val CardHeight = 178
const val HorizontalCardWidth = 240
const val HorizontalCardHeight = 135