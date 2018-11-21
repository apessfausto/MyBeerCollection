package br.com.apess.jatomei.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "beer_table")
data class Beer(


    @ColumnInfo(name = "txtNome")
    var txtNome: String,

    @ColumnInfo(name = "txtTipo")
    var txtTipo: String,

    @ColumnInfo(name = "txtEstilo")
    var txtEstilo: String,

    @ColumnInfo(name = "abv")
    var txtAbv: Double,

    @ColumnInfo(name = "txtAnotacao")
    var txtAnotacao: String,

    @ColumnInfo(name = "rtbNota")
    var rtbNota: Double


) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}