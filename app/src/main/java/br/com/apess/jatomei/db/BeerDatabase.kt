package br.com.apess.jatomei.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch


@Database(entities = [Beer::class], version = 6)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun beerDAO(): BeerDAO

    companion object {
        @Volatile
        private var INSTANCE: BeerDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BeerDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val instance: BeerDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    BeerDatabase::class.java,
                    "beer-database"
                )

                    .fallbackToDestructiveMigration()
                    .addCallback(BeerDatabaseCallBack(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class BeerDatabaseCallBack(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    popularTabela(database.beerDAO())
                }
            }
        }


        fun popularTabela(beerDAO: BeerDAO) {

        }
    }
}