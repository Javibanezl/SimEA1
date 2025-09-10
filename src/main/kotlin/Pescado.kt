abstract class Pescado(
    val nombre : String,
    val precioPorKilo : Double,
    val stockKilos : Double
){
    abstract fun descripcion(): String
    abstract fun valorTotalStock(): Double

    fun validar(): Result<Unit> {
        return try{
            when{
                nombre.isBlank() -> Result.failure(IllegalArgumentException("El nombre no puede estar vacío."))
                precioPorKilo <= 0 -> Result.failure(IllegalArgumentException("El precio debe ser mayor a 0."))
                stockKilos < 0 -> Result.failure(IllegalArgumentException("El stock debe ser mayor a 0."))
                else -> Result.success(Unit)
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}