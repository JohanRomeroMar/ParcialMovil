package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore


class Read : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var txtGameName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        setup()
    }

    private fun setup(){

        val buttonReturn: Button = findViewById(R.id.btn_Return)
        val buttonConsult: Button = findViewById(R.id.btn_Consult)

        val game: MutableMap<String, Any> = HashMap()


        txtGameName = findViewById(R.id.txt_GameName)


        buttonConsult.setOnClickListener {
            if (txtGameName.text.isNotEmpty()){

                game["Name"] = ""
                game["Value"] = ""
                game["Description"] = ""

                db.collection("games").document(txtGameName.text.toString().trim().toLowerCase())
                    .get()
                    .addOnSuccessListener {document ->
                        alertSucces()
                        game["Name"] = document.data?.get("Name").toString()
                        game["Value"] = document.data?.get("Value").toString()
                        game["Description"] = document.data?.get("Description").toString()
                        print( game["Name"])
                        val infoIntent = Intent(this, GameInfo::class.java).apply {
                            putExtra("name", game["Name"].toString())
                            putExtra("value", game["Value"].toString())
                            putExtra("description", game["Description"].toString())
                        }
                        startActivity(infoIntent)
                    }
                    .addOnFailureListener { alertFailure() }
            }
        }

        buttonReturn.setOnClickListener {
            val returnIntent = Intent(this, MainActivity::class.java).apply {  }
            startActivity(returnIntent)
        }
    }

    private fun alertSucces(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Succesful")
        builder.setMessage("Se ha consultado el juego con exito")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertFailure(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Failure")
        builder.setMessage("Se ha producido un error al consultar el juego el jeugo")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
