package com.khudyakovvladimir.vhowl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_score")
data class HighScore(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name: String,

    @ColumnInfo(name = "score", typeAffinity = ColumnInfo.INTEGER)
    var score: Int
)
