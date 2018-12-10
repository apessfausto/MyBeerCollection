package br.com.apess.jatomei.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import br.com.apess.jatomei.db.Beer
import br.com.apess.jatomei.db.BeerDAO

class BeerRepository(private val beerDAO: BeerDAO) {

    //buscar todos os objetos no banco
    val allBeers: LiveData<List<Beer>> = beerDAO.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    //adicionar novo objeto ao banco
    suspend fun insert(beer: Beer) {
        beerDAO.insert(beer)
    }

    fun update(beer: Beer) {
        beerDAO.update(beer)
    }

    fun delete(beer: Beer) {
        beerDAO.delete(beer)
    }

}