package com.metel20.data

import com.metel20.data.dashboard.CurrentTimeInMillis
import java.util.concurrent.TimeUnit

class FakeCurrentTimeInMillis(
    private val value: Long = TimeUnit.HOURS.toMillis(24)
) :
    CurrentTimeInMillis {
    override fun time() = value
}


