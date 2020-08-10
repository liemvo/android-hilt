package com.example.android.hilt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.scopes.ActivityScoped
import java.util.LinkedList
import javax.inject.Inject

@ActivityScoped
class LoggerInMemoryDataSource @Inject constructor() : LoggerDataSource {
    override val liveData: LiveData<List<Log>>
        get() = MutableLiveData<List<Log>>(emptyList())
    private val logs = LinkedList<Log>()
    
    override fun addLog(msg: String) {
        logs.addFirst(Log(msg, System.currentTimeMillis()))
    }
    
    override fun removeLogs() {
        logs.clear()
    }
}
