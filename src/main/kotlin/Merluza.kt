class Merluza(
    precioPorKilo: Double,
    stockKilos: Double,
    val zonaPesca : String
) : Pescado("Merluza", precioPorKilo, stockKilos){

    override fun descripcion(): String {
        return "El tipo de pescado $nombre capturado en $zonaPesca, tiene un precio por kilo de: $$precioPorKilo y un stock en kilos de : $stockKilos kg."
    }

    override fun valorTotalStock(): Double {
        return precioPorKilo * stockKilos
    }
}