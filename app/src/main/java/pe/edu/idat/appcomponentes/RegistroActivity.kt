package pe.edu.idat.appcomponentes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.edu.idat.appcomponentes.common.AppMensaje
import pe.edu.idat.appcomponentes.common.TipoMensaje
import pe.edu.idat.appcomponentes.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity(),
    View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityRegistroBinding
    private var estadoCivil = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnregistrar.setOnClickListener(this)
        binding.spestadocivil.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.estado_civil,
            android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item)
                binding.spestadocivil.adapter = adapter
            }
    }

    fun validarNombreApellido():Boolean {
        var respuesta = true
        if(binding.etnombres.text.toString().trim().isEmpty()){
            binding.etnombres.isFocusableInTouchMode = true
            binding.etnombres.requestFocus()
            respuesta = false
        }else if(binding.etapellido.text.toString().trim().isEmpty()){
            binding.etapellido.isFocusableInTouchMode = true
            binding.etapellido.requestFocus()
            respuesta = false
        }
        return respuesta
    }
    fun validarGenero():Boolean {
        var respuesta = true
        if(binding.radioGroup.checkedRadioButtonId == -1)
            respuesta = false
        return respuesta
    }
    fun validarHobbies():Boolean {
        var respuesta = false
        if(binding.cbfutbol.isChecked ||
            binding.cbarte.isChecked ||
            binding.cbotros.isChecked)
            respuesta = true
        return respuesta
    }
    fun validarEstadoCivil(): Boolean{
        return estadoCivil == ""
    }
    fun validarFormulario(): Boolean{
        var respuesta = false
        if(!validarNombreApellido()){
            AppMensaje.mensaje(binding.root,
                getString(R.string.valerrornomape), TipoMensaje.ERROR)
            respuesta = true
        }else if(!validarGenero()){
            AppMensaje.mensaje(binding.root,
                getString(R.string.valerrorgenero), TipoMensaje.ERROR)
            respuesta = true
        }else if(!validarHobbies()){
            AppMensaje.mensaje(binding.root,
                getString(R.string.valerrorhobbies), TipoMensaje.ERROR)
            respuesta = true
        }else if(validarEstadoCivil()){
            AppMensaje.mensaje(binding.root,
                getString(R.string.valerrorestcivil), TipoMensaje.ERROR)
            respuesta = true
        }
        return  respuesta
    }
    override fun onClick(v: View?) {
        if(!validarFormulario()){
            startActivity(Intent(this,
                ListaPersonaActivity::class.java))
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        estadoCivil = if(position > 0)
            parent!!.getItemAtPosition(position).toString()
        else ""
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}