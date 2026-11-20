package com.university.skyway

data class Ticket(
    val id: Int,
    val from: String,
    val to: String,
    val date: String,
    val price: Int,
    val airline: String
)

val sampleTickets = listOf(
    Ticket(1, "Москва", "Санкт-Петербург", "12.06.2026", 4500, "Аэрофлот"),
    Ticket(2, "Москва", "Казань", "15.06.2026", 3800, "S7"),
    Ticket(3, "Санкт-Петербург", "Сочи", "18.06.2026", 6200, "Победа"),
    Ticket(4, "Екатеринбург", "Москва", "20.06.2026", 5100, "Уральские авиалинии"),
    Ticket(5, "Новосибирск", "Москва", "22.06.2026", 7300, "S7"),
    Ticket(6, "Казань", "Сочи", "25.06.2026", 4900, "Аэрофлот"),
    Ticket(7, "Москва", "Калининград", "28.06.2026", 5600, "Россия")
)