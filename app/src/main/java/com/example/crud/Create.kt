package com.example.crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class Create : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var txtGameName: EditText
    private lateinit var txtGameValue: EditText
    private lateinit var txtGameDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setup()
    }

    private fun setup(){
        val buttonReturn: Button = findViewById(R.id.btn_Return)
        val buttonCreateGame: Button = findViewById(R.id.btn_CreateGame)

        val game: MutableMap<String, Any> = HashMap()


        txtGameName = findViewById(R.id.txt_GameName)
        txtGameValue = findViewById(R.id.txt_GameValue)
        txtGameDescription = findViewById(R.id.txt_GameDescription)

        buttonCreateGame.setOnClickListener {
            if (txtGameName.text.isNotEmpty() && txtGameValue.text.isNotEmpty() && txtGameDescription.text.isNotEmpty()){
                game["Name"] = txtGameName.text.toString()
                game["Value"] = txtGameValue.text.toString()
                game["Description"] = txtGameDescription.text.toString()
                db.collection("games").document(txtGameName.text.toString().trim().toLowerCase())
                    .set(game)
                .addOnSuccessListener { alertSucces() }
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
        builder.setMessage("Se ha registrado el juego con exito")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertFailure(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Failure")
        builder.setMessage("Se ha producido un error al registrar el jeugo")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}



