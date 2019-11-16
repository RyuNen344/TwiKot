package com.ryunen344.twikot.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "AccountDetail",
        foreignKeys = [ForeignKey(
                entity = Account::class,
                parentColumns = arrayOf("userId"),
                childColumns = arrayOf("userId"),
                onDelete = ForeignKey.CASCADE
        )])
data class AccountDetail(
        @PrimaryKey(autoGenerate = false)
        val userId : Long?,
        val userName : String?,
        val profileImage : String?,
        val localProfileImage : String?,
        val profileBannerImage : String?,
        val localProfileBannerImage : String?
) {
    constructor() : this(null, null, null, null, null, null)
}