package br.com.apess.jatomei.view

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.apess.jatomei.R
import br.com.apess.jatomei.adapter.ListaRecyclerAdapter
import br.com.apess.jatomei.db.Beer
import br.com.apess.jatomei.viewmodel.BeersViewModel
import kotlinx.android.synthetic.main.activity_ja_tomei.*

class JaTomeiActivity : AppCompatActivity() {

    //criando a variavel da viewModel
    private lateinit var beersViewModel: BeersViewModel
    private val requestCodeAddBeer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ja_tomei)

        val recyclerView = rvMain
        val adapter = ListaRecyclerAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        beersViewModel = ViewModelProviders.of(this).get(BeersViewModel::class.java)

        beersViewModel.allBeers.observe(this, Observer { beers -> beers?.let { adapter.setBeerList(it) } })

        btnAdd.setOnClickListener {
            val intent = Intent(this@JaTomeiActivity, AddActivity::class.java)
            startActivityForResult(intent, requestCodeAddBeer)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.mnsobre -> {
                val intent = Intent(this, SobreActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

    //ação da activity add

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == requestCodeAddBeer && resultCode == Activity.RESULT_OK) {
            data.let {
                val beer: Beer = data?.getSerializableExtra(AddActivity.EXTRA_REPLY) as Beer
                beersViewModel.insert(beer)
            }
        } else {
            Toast.makeText(applicationContext, "Campo vazio, favor arrumar.", Toast.LENGTH_LONG).show()
        }
    }
}
