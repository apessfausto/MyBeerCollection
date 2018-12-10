package br.com.apess.jatomei.view

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Toast
import br.com.apess.jatomei.R
import br.com.apess.jatomei.db.Beer

import kotlinx.android.synthetic.main.activity_add.*


class AddActivity : AppCompatActivity() {


    lateinit var beer: Beer
    private var image_uri: Uri? = null
    private var mCurrentPhotoPath: String =""
    private var notaAvaliada: Double = 0.0
    var menu: Menu? = null

    private var notificationManager: NotificationManager? = null

    companion object {
        const val EXTRA_REPLY = "vie.REPLY"
        private val REQUEST_IMAGE_CAPTURE = 2000
        private val REQUEST_IMAGE_GALLERY = 1000

        const val EXTRA_DELETE = "View.Delete"

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(
                "apess.com.mybeercollection.news",
            "NotifyDemo News",
            "Example News Channel")

        //Botão de voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //botão add imagem
        addImg?.setOnClickListener {
            addImg.setOnCreateContextMenuListener { menu, v, menuInfo ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Camera")
                menu.add(Menu.NONE, 2, Menu.NONE, "Galeria")
            }
        }

        val intent = intent
        try {
            beer = intent.getSerializableExtra(EXTRA_REPLY) as Beer
            beer.let {
                txtNome.setText(beer.txtNome)
                txtTipo.setText(beer.txtTipo)
                txtEstilo.setText(beer.txtEstilo)
                //  txtAbv.setText.toDouble.(beer.txtAbv)
                txtAnotacao.setText(beer.txtAnotacao)
                //     rtbNota.setText(beer.notaAvaliada)
                //    imgBeer.setImageDrawable(beer.imgBeer)


            }
            val menuItem = menu?.findItem(R.id.mnExcluir)
            menuItem?.isVisible = true

        } catch (exception: Exception) {
        val menuItem = menu?.findItem(R.id.mnExcluir)
            menuItem?.isVisible = false
        }


        //captura dados seekbar
        sbAbv.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                    txtAbv.text = ("Abv " + progress.toString().toDouble() / 10 + " %")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    txtAbv.text = ("Abv " + seekBar?.progress.toString().toDouble() / 10 + " %")
                }

            }
        )


        //captura dados da ratingbar
        rtbNota.onRatingBarChangeListener = object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {

                notaAvaliada = rtbNota.rating.toDouble()

            }

        }


    }

    //recursos chamar galeria/ camera
    private fun takePicture() {
        //organiza o conjunto de informações para ser trabalhadas posteriormente
        val values = ContentValues()
        //titulo na imagem
        values.put(MediaStore.Images.Media.TITLE, "Nova Foto")
        //descrição da imagem
        values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem da Camera")
        //tipo de imagem
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

        //define como sera o uri para pegar setar os dados da imagem
        image_uri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )

        //intent de ação da captura de imagem
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


        if (intent.resolveActivity(packageManager) != null) {

            //adiciona no caminho da imagem o caminho da imagem_uri
            mCurrentPhotoPath = image_uri.toString()

            //envia para a intent do tipo extra output a imagem_uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)


            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        }
        //após as verificações de permissão, será aberta a tela da camera para tirar a foto
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun getPermissionImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                // verifica permissão se não houver a permissão, será solicitada
                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, REQUEST_IMAGE_GALLERY)
            } else {
                //permissao cedida
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }

    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    private fun getPermissionTakePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //verificação de permissão
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                //caso não tenha permissão, será chamada a solicitação de permissão
                val permission =
                    arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, REQUEST_IMAGE_CAPTURE)


            } else {


                takePicture()

            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_IMAGE_GALLERY -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) pickImageFromGallery()
                else Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
            REQUEST_IMAGE_CAPTURE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) takePicture()
                else Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_GALLERY)
        //  imgBeer.visibility = View.VISIBLE
            imgBeer.setImageURI(data?.data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE)
        //  imgBeer.visibility = View.VISIBLE
            imgBeer.setImageURI(image_uri)


    }


    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            2 -> getPermissionImageFromGallery()
            1 -> getPermissionTakePicture()
        }
        return super.onContextItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        try {
            beer.let {
                val menuItem = menu?.findItem(R.id.mnExcluir)
                menuItem?.isVisible = true
            }
        } catch (e: Exception) {

        }

        return true
    }

    //criação do botão voltar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //botão home
        return if (item?.itemId == android.R.id.home) {
            finish()
            true


        } else if (item?.itemId == R.id.mnSalvar) {
            val replyIntent = Intent()

            if (txtNome.text.isNullOrEmpty()) {
                Toast.makeText(this, "Insira um nome",Toast.LENGTH_LONG).show()
                txtNome.requestFocus()

            } else if ((::beer.isInitialized) && (beer.id > 0)) {
                beer.txtNome = txtNome.text.toString()
                beer.txtTipo = txtTipo.text.toString()
                beer.txtEstilo = txtEstilo.text.toString()
                beer.txtAnotacao = txtAnotacao.text.toString()
                beer.txtAbv = sbAbv.progress.toDouble()
                beer.notaAvaliada = notaAvaliada
                beer.imgBeer = mCurrentPhotoPath

            } else {

                beer = Beer(
                    txtNome = txtNome.text.toString(),
                    txtTipo = txtTipo.text.toString(),
                    txtEstilo = txtEstilo.text.toString(),
                    txtAnotacao = txtAnotacao.text.toString(),
                    txtAbv = sbAbv.progress.toDouble(),
                    notaAvaliada = notaAvaliada,
                    imgBeer = mCurrentPhotoPath.toString()


                )
            }
            //val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_REPLY, beer)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
            true

        }else if(item?.itemId == R.id.mnExcluir){
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_DELETE, beer)
            setResult(Activity.RESULT_OK,replyIntent)

            finish()
            true

        }
        else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun createNotificationChannel(id: String, name: String,
                                          description: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(id, name, importance)

            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern =
                    longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}

