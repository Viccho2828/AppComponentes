package pe.edu.idat.appcomponentes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
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
    private val listaHobbies = ArrayList<String>()
    private val listaUsuarios = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnregistrar.setOnClickListener(this)
        binding.spestadocivil.onItemSelectedListener = this
        binding.btnlistado.setOnClickListener(this)
        binding.cbfutbol.setOnClickListener(this)
        binding.cbarte.setOnClickListener(this)
        binding.cbotros.setOnClickListener(this)
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
    override fun onClick(v: View) {
        if(v is CheckBox){
            agregarQuitarHobbie(v)
        } else {
            when(v.id){
                R.id.btnregistrar -> registrarPersona()
                R.id.btnlistado -> irListaPersonas()
            }
        }
    }
    private fun agregarQuitarHobbie(v: CheckBox) {
        if(v.isChecked)
            listaHobbies.add(v.text.toString())
        else
            listaHobbies.remove(v.text.toString())
    }
    fun obtenerGenero(): String{
        return when(binding.radioGroup.checkedRadioButtonId){
            R.id.rbmasculino ->  binding.rbmasculino.text.toString()
            R.id.rbfemenino ->  binding.rbfemenino.text.toString()
            R.id.rbotros ->  binding.rbotros.text.toString()
            else -> ""
        }
    }
    fun obtenerHobbies(): String{
        var hobbies = ""
        for(hobbie in listaHobbies){
            hobbies += "$hobbie -"
        }
        return hobbies
    }
    private fun irListaPersonas() {
        val intentListaPersona = Intent(
            this, ListaPersonaActivity::class.java)
            .apply {
                putExtra("lista", listaUsuarios)
            }
        startActivity(intentListaPersona)
    }
    private fun registrarPersona() {
        if(!validarFormulario()){
            var info = binding.etnombres.text.toString()+"-"+
                    binding.etapellido.text.toString()+"-"+
                    obtenerGenero()+"-"+
                    obtenerHobbies()+"-"+
                    estadoCivil+"-"+
                    binding.swemail.isChecked
            listaUsuarios.add(info)
            AppMensaje.mensaje(binding.root,
                getString(R.string.mensajeregpersona),
                TipoMensaje.CORRECTO)
            setearControles()
        }
    }
    fun setearControles(){
        binding.etnombres.setText("")
        binding.etapellido.setText("")
        binding.swemail.isChecked = false
        binding.cbfutbol.isChecked = false
        binding.cbarte.isChecked = false
        binding.cbotros.isChecked = false
        binding.radioGroup.clearCheck()
        binding.spestadocivil.setSelection(0)
        binding.etnombres.isFocusableInTouchMode = true
        binding.etnombres.requestFocus()
        listaHobbies.clear()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        estadoCivil = if(position > 0)
            parent!!.getItemAtPosition(position).toString()
        else ""
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}