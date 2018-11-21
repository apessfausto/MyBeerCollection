package br.com.apess.jatomei.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.apess.jatomei.R
import br.com.apess.jatomei.db.Beer
import kotlinx.android.synthetic.main.lista_main.view.*

class ListaRecyclerAdapter  internal constructor(   context: Context):

    RecyclerView.Adapter<ListaRecyclerAdapter.ViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var beers = emptyList<Beer>()


    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.lista_main, holder, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = beers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current: Beer = beers[position]
        holder.nomeBeer.text = current.txtNome
    }

    inner class ViewHolder(itenView: View): RecyclerView.ViewHolder(itenView){
        val nomeBeer: TextView = itemView.txtNomeBeer
    }

  /*  private fun lista():List<String>{
        return listOf(
            "Weizenstófeles",
            "Red Ale 3.4",
            "Portestófeles",
            "MaracujAPA"

        )
  }
*/

    fun setBeerList(beerList: List<Beer>){
        this.beers = beerList
        notifyDataSetChanged()
    }
}

