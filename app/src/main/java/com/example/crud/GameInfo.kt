package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore

class GameInfo : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var txtGName: TextView
    private lateinit var txtGValue: TextView
    private lateinit var txtGDescription: TextView
    private lateinit var txtGValueEdit: EditText
    private lateinit var txtGDescriptionEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_info)

        var bundle:Bundle? = intent.extras
        val name:String? = bundle?.getString("name")
        val value:String? = bundle?.getString("value")
        val description:String? = bundle?.getString("description")

        setup(name ?: "", value ?: "", description ?: "")
    }

    private fun setup(Nam: String, Valu: String, Descrip: String){

        val buttonReturn: Button = findViewById(R.id.btn_Return)

        val buttonUpdate: Button = findViewById(R.id.btn_UPDATE)
        val buttonDelete: Button = findViewById(R.id.btn_DELETE)

        val game: MutableMap<String, Any> = HashMap()

        txtGName = findViewById(R.id.txt_GameName)
        txtGValue = findViewById(R.id.txt_GameValue)
        txtGDescription = findViewById(R.id.txt_GameDescription)
        txtGValueEdit = findViewById(R.id.txt_GameValue_edit)
        txtGDescriptionEdit = findViewById(R.id.txt_GameDescription_edit)

        txtGName.text = Nam
        txtGValue.text = Valu
        txtGDescription.text = Descrip

        game["Name"] = Nam

        game["Description"] = txtGDescriptionEdit.text.toString().toLowerCase()

        buttonUpdate.setOnClickListener {

            if (txtGValueEdit.text.toString().isNotEmpty()){
                game["Value"] = txtGValueEdit.text.toString().toLowerCase()
            }else{
                game["Value"] = Valu
            }

            if (txtGDescriptionEdit.text.toString().isNotEmpty()){
                game["Description"] = txtGDescriptionEdit.text.toString().toLowerCase()
            }else{
                game["Description"] = Descrip
            }

            db.collection("games").document(Nam)
                .set(game)//modificación de datos en bd
                .addOnSuccessListener {
                    db.collection("games").document(Nam)
                        .get()
                        .addOnSuccessListener {document ->
                            //modificación de los datos en pantalla con datos en bd
                            txtGValue.text = document.data?.get("Value").toString()
                            txtGDescription.text = document.data?.get("Description").toString()
                        }
                    alertSucces("Información del juego modificada")
                }
                .addOnFailureListener { alertFailure("Error modificando la información del juego") }

        }

        buttonDelete.setOnClickListener {
            db.collection("games").document(Nam)
                .delete()
        }

        buttonReturn.setOnClickListener {
            val readIntent = Intent(this, Read::class.java).apply {  }
            startActivity(readIntent)
        }

    }

    private fun alertSucces(Message: String){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Succesful")
        builder.setMessage(Message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertFailure(Message: String){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Failure")
        builder.setMessage(Message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}