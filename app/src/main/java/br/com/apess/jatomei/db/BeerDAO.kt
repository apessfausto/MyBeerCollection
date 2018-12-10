package br.com.apess.jatomei.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface BeerDAO {

    @Insert
    fun insert(beer: Beer)

    @Update
    fun update(beer: Beer)

    @Delete
    fun delete(beer: Beer)

  //  @Query("DELETE FROM beer_table")
   // fun deleteAll()

    @Query("SELECT * FROM beer_table ORDER BY txtNome ASC")
    fun getAll(): LiveData<List<Beer>>

    @Query("SELECT * from beer_table WHERE id = :id")
    fun getBeer(id: Long): LiveData<Beer>
}