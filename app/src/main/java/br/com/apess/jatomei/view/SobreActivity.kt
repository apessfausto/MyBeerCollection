package br.com.apess.jatomei.view

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.apess.jatomei.R

class SobreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.social_menu, menu)
        return true
    }

    //criação do botão voltar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //botão home
        return if(item?.itemId == android.R.id.home) {
            finish()
            true
        }else if(item?.itemId == R.id.mnface){
            val uris = Uri.parse("http://www.facebook.com/cozinhabrewer")
            val intent = Intent(Intent.ACTION_VIEW, uris)
            startActivity(intent)

            return true
        }else if(item?.itemId == R.id.mnInsta)
        {
            val uris = Uri.parse("http://www.instagram.com/cozinhabrewer")
            val intent = Intent(Intent.ACTION_VIEW, uris)
            startActivity(intent)
            return true
        }
        else{
            super.onOptionsItemSelected(item)
        }
    }


}
