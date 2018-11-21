package br.com.apess.jatomei.view

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import br.com.apess.jatomei.R
import br.com.apess.jatomei.db.Beer
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY = "vie.REPLY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //captura dados seekbar
        sbAbv.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                    txtAbv.text = ("Abv " +progress.toString().toDouble()/10 + " %")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    txtAbv.text = ("Abv "+ seekBar?.progress.toString().toDouble()/10 + " %")
                }

            }
        )


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    //criação do botão voltar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //botão home
        return if (item?.itemId == android.R.id.home) {
            finish()
            true

        } else if (item?.itemId == R.id.mnSalvar) {
            if (txtNome.text.isNullOrEmpty()) Toast.makeText(this, "Insira um nome", Toast.LENGTH_LONG).show()
            else {
                val beer = Beer(
                    txtNome = txtNome.text.toString(),
                    txtTipo = txtTipo.text.toString(),
                    txtEstilo = txtEstilo.text.toString(),
                    txtAnotacao = txtAnotacao.text.toString(),
                    txtAbv = sbAbv.progress.toDouble(),
                    rtbNota = rtbNota.numStars.toDouble()
                )
                val replyIntent = Intent()
                replyIntent.putExtra(EXTRA_REPLY, beer)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
            true

        } else {
            super.onOptionsItemSelected(item)
        }
    }


}

