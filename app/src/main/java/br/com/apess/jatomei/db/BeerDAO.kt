package br.com.apess.jatomei.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface BeerDAO {

    @Insert
    fun insert(beer: Beer)

    @Query("DELETE FROM beer_table")
    fun deleteAll()

    @Query("SELECT * FROM beer_table")
    fun getAll(): LiveData<List<Beer>>
}