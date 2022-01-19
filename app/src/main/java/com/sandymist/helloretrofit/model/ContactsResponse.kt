package com.sandymist.helloretrofit.model

data class ContactsResponse(
    val data: List<Contact?>?,
    val status: String?
) {
    data class Contact(
        val address: Address?,
        val firstName: String?,
        val lastName: String?
    ) {
        data class Address(
            val city: String?,
            val location: Location?,
            val state: String?,
            val streetAddress: String?,
            val zip: String?
        ) {
            data class Location(
                val latitude: Double?,
                val longitude: Double?
            )
        }
    }
}