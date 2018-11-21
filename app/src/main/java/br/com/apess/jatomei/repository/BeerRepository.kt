package br.com.apess.jatomei.repository

import android.arch.lifecycle.LiveData
import br.com.apess.jatomei.db.Beer
import br.com.apess.jatomei.db.BeerDAO

class BeerRepository (private val beerDAO: BeerDAO) {

    //buscar todos os objetos no banco
    val allBeers:LiveData<List<Beer>> = beerDAO.getAll()


    //adicionar novo objeto ao banco
    fun insert(beer: Beer)
    {
        beerDAO.insert(beer)
    }
}