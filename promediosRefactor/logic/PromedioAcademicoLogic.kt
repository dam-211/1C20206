package ar.edu.gasesideales.logic

data class ResultadoAcademico(
    val nota1: Double,
    val nota2: Double,
    val nota3: Double,
    val asistencia: Double,
    val promedio: Double,
    val condicion: String

)

//objetc en kotlin declara un objeto único, osea una instancia que existe una sola vez

//Esto significa que la puedo usar PromedioAcademicoLogic.calcularResultado(...)

//La idea simple sería que : Class define un molde para crear muchas instancias, mientras que
//object define directamente una única instancia

//Usamos object dado que PromedioAcademicoLogic no tiene estado propio

//Companion object cuando usamos algo compartido dentro de una clase, como constantes
object PromedioAcademicoLogic {

    fun calcularResultado(
        nota1Texto: String,
        nota2Texto: String,
        nota3Texto: String,
        asistenciaTexto: String

    ): ResultadoCalculo {

        val nota1 = nota1Texto.normalizarNumero().toDoubleOrNull()
        val nota2 = nota2Texto.normalizarNumero().toDoubleOrNull()
        val nota3 = nota3Texto.normalizarNumero().toDoubleOrNull()
        val asistencia = asistenciaTexto.normalizarNumero().toDoubleOrNull()

        return when {
            nota1 == null || nota2 == null || nota3 == null || asistencia == null -> {
                ResultadoCalculo.Error("Ingresá valores numéricos válidos.")
            }

            nota1 !in 0.0..10.0 || nota2 !in 0.0..10.0 || nota3 !in 0.0..10.0 -> {
                ResultadoCalculo.Error("Las notas deben estar entre 0 y 10.")
            }

            asistencia !in 0.0..100.0 -> {
                ResultadoCalculo.Error("La asistencia debe estar entre 0 y 100.")
            }

            else -> {
                val promedio = (nota1 + nota2 + nota3) / 3.0
                val condicion = when {
                    promedio >= 8.0 && asistencia >= 75.0 -> "Promociona"
                    promedio >= 6.0 && asistencia >= 75.0 -> "Regulariza"
                    else -> "Recursa"
                }

                ResultadoCalculo.Exito(
                    ResultadoAcademico(
                        nota1 = nota1,
                        nota2 = nota2,
                        nota3 = nota3,
                        asistencia = asistencia,
                        promedio = promedio,
                        condicion = condicion
                    )
                )
            }
        }
    }

    private fun String.normalizarNumero(): String {
        return trim().replace(',', '.')
    }
}

sealed class ResultadoCalculo {
    data class Exito(val resultado: ResultadoAcademico) : ResultadoCalculo()
    data class Error(val mensaje: String) : ResultadoCalculo()
}

/*
TODO: Agregar un botón para limpiar el historial
Guardar fecha y hora del cálculo

Todo Clase  19/05 --> Agregar ViewModel sencillo, con estado fuera de @Composable.
 */