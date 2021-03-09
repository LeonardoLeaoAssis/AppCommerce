package br.com.arquitetoandroid.appcommerce.model.enums

enum class Status(val message: String) {

    PENDENT("Pendente"),
    PAID("Pago"),
    PROCESSED("Enviado"),
    CART("Carrinho Abandonado")

}