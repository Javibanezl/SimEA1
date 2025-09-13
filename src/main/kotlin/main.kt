import java.util.Scanner
import kotlinx.coroutines.*

// ------------------ SEALED --------------------
sealed class Resultado {
    data class Exitoso(val mensaje: String) : Resultado()
    data class Error(val mensaje: String) : Resultado()
}

// ------------------- SUSPEND -------------------------
suspend fun controlarCalidad(pescado: Pescado): Resultado {
    println("Iniciando control de calidad para: ${pescado.nombre}")
    delay(2000L)

    return if (pescado.stockKilos > 0){
        Resultado.Exitoso("El pescado ${pescado.nombre} pasó el control de calidad")
    } else {
        Resultado.Error("El pescado ${pescado.nombre} no pasó el control de calidad")
    }
}

// ----------------------- INICIO MAIN ----------------------------
fun main(){
    val pescados = mutableListOf<Pescado>()
    var opcion: Int

    println("=== Gestión Inventario Salmones del Sur SPA ===")

    do {
        println("\n--- Menú Principal ---")
        println("1. Agregar pescado")
        println("2. Mostrar todos los pescados")
        println("3. Mostrar pescados caros (mayor a $5.000 por kilo)")
        println("4. Calcular valor total del stock")
        println("5. Control de calidad")
        println("6. Salir")
        println("Seleccione una opción: ")

        opcion = readln().toIntOrNull() ?: -1

        when (opcion) {
            1 -> {
                println("Seleccione tipo de pescado: ")
                println("1. Salmón")
                println("2. Merluza")
                val tipo = readln().toIntOrNull()

                when (tipo) {
                    1 -> {
                        println("Ingrese precio por kilo: ")
                        val precio = readln().toDoubleOrNull()
                        println("Ingrese stock en kilos: ")
                        val stock = readln().toDoubleOrNull()
                        println("Ingrese tipo de cultivo: ")
                        val cultivo = readln()

                        if (precio != null && stock != null) {
                            val salmon = Salmon(precio, stock, cultivo)
                            val resultado = salmon.validar()
                            if(resultado.isSuccess) {
                                pescados.add(salmon)
                                println("Salmón agregado con éxito")
                            }else {
                                println("Error: ${resultado.exceptionOrNull()?.message}")
                            }
                        } else {
                            println("Ingreso inválido, intente nuevamente")
                        }
                    }

                    2 -> {
                        println("Ingrese precio por kilo: ")
                        val precio = readln().toDoubleOrNull()
                        println("Ingrese stock en kilos: ")
                        val stock = readln().toDoubleOrNull()
                        println("Ingrese zona de pesca: ")
                        val zona = readln()

                        if (precio != null && stock != null) {
                            val merluza = Merluza(precio, stock, zona)
                            val resultado = merluza.validar()
                            if(resultado.isSuccess) {
                                pescados.add(merluza)
                                println("Merluza agregado con éxito")
                            } else {
                                println("Error: ${resultado.exceptionOrNull()?.message}")
                            }
                        } else {
                            println("Ingreso inválido, intente nuevamente")
                        }
                    }

                    else -> println("Tipo de pescado no válido")
                }
            }
            2 -> {
                if (pescados.isEmpty()) {
                    println("No hay pescados registrados.")
                } else {
                    pescados.forEach {
                        println(it.descripcion() + " | Total stock: ${it.valorTotalStock()}")
                    }
                }
            }
            3 -> {
                val caros = pescados.filter { it.precioPorKilo > 5000 }
                if (caros.isEmpty()) {
                    println("No hay pescados con precio mayor a $5.000 por kilo")
                } else {
                    caros.forEach { println(it.descripcion()) }
                }
            }
            4 -> {
                val total = pescados.sumOf {it.valorTotalStock()}
                println("El valor total del stock es: $$total")
            }
            5 -> {
                if(pescados.isEmpty()){
                    println("No hay pescados registrados.")
                } else {
                    runBlocking {
                        pescados.forEach { pescado ->
                            val resultado = controlarCalidad(pescado)
                            when (resultado) {
                                is Resultado.Exitoso -> println(resultado.mensaje)
                                is Resultado.Error -> println(resultado.mensaje)
                            }
                        }
                    }
                }
            }
            6 -> println("Saliendo del programa")
            else -> println("Opción inválida, intente de nuevo")
        }
    } while (opcion != 5)
}