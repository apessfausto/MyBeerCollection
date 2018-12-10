package br.com.apess.jatomei.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import br.com.apess.jatomei.db.Beer
import br.com.apess.jatomei.repository.BeerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import kotlin.coroutines.CoroutineContext
import br.com.apess.jatomei.db.BeerDatabase

class BeersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BeerRepository
    val allBeers: LiveData<List<Beer>>

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val beerDao = BeerDatabase.getDatabase(application, scope).beerDAO()
        repository = BeerRepository(beerDao)
        allBeers = repository.allBeers
    }


   fun insert(beer: Beer) = scope.launch(Dispatchers.IO) {
        repository.insert(beer)
    }

    fun update(beer: Beer) = scope.launch( Dispatchers.IO){
        repository.update(beer)
    }

    fun delete(beer: Beer) = scope.launch(Dispatchers.IO){
        repository.delete(beer)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}



