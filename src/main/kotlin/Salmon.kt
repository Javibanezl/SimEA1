class Salmon(
    precioPorKilo: Double,
    stockKilos: Double,
    val tipoCultivo: String
) : Pescado("Salmón", precioPorKilo, stockKilos){

    override fun descripcion(): String {
        return "El tipo de pescado $nombre cultivado en modalidad: $tipoCultivo, tiene un precio por kilo de: $$precioPorKilo y un stock en kilos de : $stockKilos kg."
    }

    override fun valorTotalStock(): Double {
        return precioPorKilo * stockKilos
    }
}