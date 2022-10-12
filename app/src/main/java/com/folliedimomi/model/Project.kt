package com.folliedimomi.model

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("data") val `data`: List<ProjectList> = listOf(),
    @SerializedName("count") val count: Int = 0,
    @SerializedName("error") val error: Any = Any(),
    @SerializedName("returned") val returned: Int = 0
)

data class ProjectList(
    @SerializedName("column_name") val columnName: String = "",
    @SerializedName("table_name") val tableName: String = ""
)
