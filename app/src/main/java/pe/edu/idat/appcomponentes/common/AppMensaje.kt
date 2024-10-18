package pe.edu.idat.appcomponentes.common

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import pe.edu.idat.appcomponentes.R

object AppMensaje {

    fun mensaje(vista: View, mensaje: String,
                tipo: TipoMensaje){
        val snackbar = Snackbar.make(vista,
            mensaje, Snackbar.LENGTH_LONG)
        if(tipo == TipoMensaje.INFO){
            snackbar.setBackgroundTint(ContextCompat.getColor(
                MiApp.instancia,
                R.color.snackinfo))
        }else if(tipo == TipoMensaje.ADVERTENCIA){
            snackbar.setBackgroundTint(ContextCompat.getColor(
                MiApp.instancia,
                R.color.snackadvertencia))
        } else if(tipo == TipoMensaje.ERROR){
            snackbar.setBackgroundTint(ContextCompat.getColor(
                MiApp.instancia,
                R.color.snackerror))
        } else if(tipo == TipoMensaje.CORRECTO) {
            snackbar.setBackgroundTint(ContextCompat.getColor(
                MiApp.instancia,
                R.color.snackcorrecto))
        }
        snackbar.show()
    }
}