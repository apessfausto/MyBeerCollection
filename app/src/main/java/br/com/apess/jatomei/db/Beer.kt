package br.com.apess.jatomei.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.ImageFormat
import android.media.Image
import android.widget.ImageView
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "beer_table")
data class Beer(


    @ColumnInfo(name = "txtNome")
    @NotNull
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
    var notaAvaliada: Double,

    @ColumnInfo(name = "imgBeer")
    var imgBeer: String




) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}