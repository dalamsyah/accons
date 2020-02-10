package com.dalamsyah.accons.model

import android.media.AudioTimestamp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Dimas Alamsyah on 1/18/2020
 */
@IgnoreExtraProperties
data class Transaction(
    var email: String? = "",
    var nominal: Double? = 0.0,
    var flow: String? = "",
    var note: String? = "",
    var timestamp: String? = ""
)