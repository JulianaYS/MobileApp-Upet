package pe.edu.upc.upet.feature_smartCollar.data.mapper
import pe.edu.upc.upet.feature_smartCollar.data.remote.SmartCollarResponse
import pe.edu.upc.upet.feature_smartCollar.domain.SmartCollar

fun SmartCollarResponse.toDomainModel(): SmartCollar {
    return SmartCollar(
        id = this.id,
        serial_number = this.serial_number,
        temperature = this.temperature,
        lpm = this.lpm,
        battery = this.battery,
        location = this.location,
        pet_id = this.petId
    )
}