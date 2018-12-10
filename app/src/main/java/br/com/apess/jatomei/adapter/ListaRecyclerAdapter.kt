package br.com.apess.jatomei.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.apess.jatomei.R
import br.com.apess.jatomei.R.id.*
import br.com.apess.jatomei.db.Beer
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.view.*

import kotlinx.android.synthetic.main.lista_main.view.*
import java.net.URI

class ListaRecyclerAdapter  internal constructor(   context: Context):

    RecyclerView.Adapter<ListaRecyclerAdapter.ViewHolder>(){

    var onItemClick:((Beer) -> Unit)? = null

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var beers = emptyList<Beer>()



    inner class ViewHolder(itenView: View): RecyclerView.ViewHolder(itenView){
        val imgBeer: CircleImageView = itemView.imgBeerList
        val nomeBeer: TextView = itemView.txtNomeBeer
        val nota: TextView = itemView.txtValorNota
        val cardView: CardView = itemView.cardBeer


        init {
           itemView.setOnClickListener{
               onItemClick?.invoke(beers[adapterPosition])
           }
            //itemView.setOnClickListener{onItemClick?.invoke(beers[adapterPosition])}
        }

    }



    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder {
        val view = inflater.inflate(R.layout.lista_main, holder, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = beers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current = beers[position]
        holder.nomeBeer.text = current.txtNome
        holder.nota.text = current.notaAvaliada.toString()


    }



    fun setBeerList(beerList: List<Beer>){
        this.beers = beerList
        notifyDataSetChanged()
    }
}

