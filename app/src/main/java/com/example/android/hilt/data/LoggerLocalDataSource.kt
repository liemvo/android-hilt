/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt.data

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

interface LoggerDataSource {
    val liveData: LiveData<List<Log>>
    fun addLog(msg: String)
    fun removeLogs()
}

/**
 * Data manager class that handles data manipulation between the database and the UI.
 */
@Singleton
class LoggerLocalDataSource @Inject constructor(private val logDao: LogDao) : LoggerDataSource {
    override val liveData: LiveData<List<Log>> = logDao.getAll()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    
    override fun addLog(msg: String) {
        executorService.execute {
            logDao.insertAll(
                Log(
                    msg,
                    System.currentTimeMillis()
                )
            )
        }
    }
    
    override fun removeLogs() {
        executorService.execute {
            logDao.nukeTable()
        }
    }
}
