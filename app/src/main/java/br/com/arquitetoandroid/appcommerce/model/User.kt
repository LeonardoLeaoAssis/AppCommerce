package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "user_usr")
data class User(

    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var email: String,
    var name: String,
    var surname: String,
    var password: String,
    var image: String): Serializable