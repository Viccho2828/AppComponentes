package pe.edu.idat.appcomponentes

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import pe.edu.idat.appcomponentes.databinding.ActivityListaPersonaBinding
import android.R

class ListaPersonaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaPersonaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPersonaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listaPersonas = intent.getSerializableExtra("lista")
                as ArrayList<*>
        val  adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            listaPersonas)
        binding.lvpersonas.adapter = adapter
    }
}