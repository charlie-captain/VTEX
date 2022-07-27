package com.charlie.vtex.base

import androidx.lifecycle.ViewModel
import com.charlie.vtex.repository.BaseRepository

open class BaseViewModel() : ViewModel() {

    open val repository: BaseRepository = BaseRepository()


}