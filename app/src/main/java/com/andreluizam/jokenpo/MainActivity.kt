package com.andreluizam.jokenpo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.andreluizam.jokenpo.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var usuarioResult: Int = 0
    private var placarMaquina: Int = 0
    private var placarUsuario: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnPapel.setOnClickListener {
            usuarioResult = 1
            binding.escolha.text = "Papel"
        }

        binding.btnPedra.setOnClickListener {
            usuarioResult = 2
            binding.escolha.text = "Pedra"
        }

        binding.btnTesoura.setOnClickListener {
            usuarioResult = 3
            binding.escolha.text = "Tesoura"
        }
    }

    private enum class Escolha(val numero: Int) {
        Papel(1), Pedra(2), Tesoura(3)
    }

    private enum class Resultado(val numero: Int) {
        Usuario(1), Maquina(2), Empate(3)
    }

    public fun  jogarJogo(view: View){
        if(usuarioResult == 0){
            Toast.makeText(view.context, "Selecione uma opção", Toast.LENGTH_LONG).show()
            return
        }

        val maquinaResult = Random.nextInt(1,3)

        val resultadoJokenpo = verificarResultado(maquinaResult,usuarioResult)

        mostrarResultado(resultadoJokenpo, view, escolhaParaString(resultadoJokenpo))

        pontuar(resultadoJokenpo)
    }

    private fun verificarResultado(maquinaResult: Int, usuarioResult: Int) : Int{
        return when {
            maquinaResult == Escolha.Pedra.numero -> when (usuarioResult) {
                Escolha.Tesoura.numero -> Resultado.Maquina.numero
                Escolha.Papel.numero -> Resultado.Usuario.numero
                else -> Resultado.Empate.numero
            }

            maquinaResult == Escolha.Papel.numero -> when (usuarioResult) {
                Escolha.Pedra.numero -> Resultado.Maquina.numero
                Escolha.Tesoura.numero -> Resultado.Usuario.numero
                else -> Resultado.Empate.numero
            }

            maquinaResult == Escolha.Tesoura.numero -> when (usuarioResult) {
                Escolha.Papel.numero -> Resultado.Maquina.numero
                Escolha.Pedra.numero -> Resultado.Usuario.numero
                else -> Resultado.Empate.numero
            }

            else -> Resultado.Empate.numero
        }
    }

    private fun mostrarResultado(resultadoJokenpo: Int, view: View, escolha: String){
        if(resultadoJokenpo == Resultado.Empate.numero){
            Toast.makeText(view.context, "Ufa, eu também escolhi " + escolha + " Empatou !", Toast.LENGTH_LONG).show()
            return
        }

        if(resultadoJokenpo == Resultado.Maquina.numero)
            Toast.makeText(view.context, "Haha, eu escolhi " + escolha + " Você perdeu!", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(view.context, "Aaah, eu escolhi " + escolha + " Você ganhou!", Toast.LENGTH_LONG).show()
    }

    private fun pontuar(resultado: Int){

        if(resultado == Resultado.Maquina.numero)
            placarMaquina++
        else if (resultado == Resultado.Usuario.numero)
             placarUsuario++
        else
            return

        binding.txtPlacar.text = "Eu " + placarMaquina + "    X   Você " + placarUsuario
    }

    private fun escolhaParaString(escolha: Int): String {
        return when (escolha) {
            Escolha.Papel.numero -> "Papel"
            Escolha.Pedra.numero -> "Pedra"
            Escolha.Tesoura.numero -> "Tesoura"
            else -> "Outro"
        }
    }

}