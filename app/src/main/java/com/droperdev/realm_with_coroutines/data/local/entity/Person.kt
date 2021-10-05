package com.droperdev.realm_with_coroutines.data.local.entity

import io.realm.RealmList
import io.realm.RealmObject

open class Person(
    var id: Int = 0,
    var name: String = "",
    var lastName: String = "",
    var books: RealmList<Book> = RealmList()
) : RealmObject() {
    override fun toString(): String {
        return "id-> $id, name -> $name, lastName -> $lastName, books -> $books"
    }
}

open class Book(
    var id: Int = 0,
    var name: String = "",
    var description: String = ""
) : RealmObject() {
    override fun toString(): String {
        return "id -> $id, name -> $name, description -> $description"
    }
}


open class Empleado()