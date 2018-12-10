package br.com.apess.jatomei.view

import android.app.*
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.WindowId
import android.widget.Toast
import br.com.apess.jatomei.R
import br.com.apess.jatomei.adapter.ListaRecyclerAdapter
import br.com.apess.jatomei.db.Beer
import br.com.apess.jatomei.viewmodel.BeersViewModel
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_ja_tomei.*
import kotlinx.android.synthetic.main.lista_main.*
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.selects.whileSelect

class JaTomeiActivity : AppCompatActivity() {


    //criando a variavel da viewModel
    private lateinit var beersViewModel: BeersViewModel
    private val newActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ja_tomei)


        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        val recyclerView = rvMain
        val adapter = ListaRecyclerAdapter(this)
        //recyclerView.adapter = adapter

        adapter.onItemClick = { it ->
            val intent = Intent(this@JaTomeiActivity, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_REPLY, it)
            startActivityForResult(intent, newActivityRequestCode)
        }

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        beersViewModel = ViewModelProviders.of(this).get(BeersViewModel::class.java)

        beersViewModel.allBeers.observe(this, Observer { beers -> beers?.let { adapter.setBeerList(it) } })

        btnAdd.setOnClickListener {
            val intent = Intent(this@JaTomeiActivity, AddActivity::class.java)
            startActivityForResult(intent, newActivityRequestCode)
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

        if (requestCode == newActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let { data ->
                try {
                    val beer: Beer?
                    beer = data.getSerializableExtra(AddActivity.EXTRA_REPLY) as Beer
                    beer.let {
                        if (beer.id > 0) {
                            beersViewModel.update(beer)
                            notification()
                            Toast.makeText(applicationContext, "Atualizado com Sucesso!", Toast.LENGTH_SHORT).show()
                        } else beersViewModel.insert(beer)
                        Toast.makeText(applicationContext, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show()


                    }

                } catch (e: Exception) {
                    val beer: Beer? = data.getSerializableExtra(AddActivity.EXTRA_DELETE) as Beer
                    beer.let {
                        beersViewModel.delete(beer!!)
                        Toast.makeText(applicationContext, "Cadastro removido com Sucesso!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext, "O registro não foi salvo!", Toast.LENGTH_LONG).show()
        }
    }


    private fun notification() {
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mNotification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)

        } else {
            Notification.Builder(this)
        }.apply {
            setContentIntent(pendingIntent)

            //icone na notificação
            setSmallIcon(R.drawable.notification_icon_background)
            setContentTitle("My Beer Collection")
            setContentText("Atualizado com Sucesso!")
            setAutoCancel(true)


            //mensagem notificação

        }.build()
        val mNotificationId: Int = 1000
        val nManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nManager.notify(mNotificationId, mNotification)


    }

    private val channelId = "fgoncalves.apess.com"


}

