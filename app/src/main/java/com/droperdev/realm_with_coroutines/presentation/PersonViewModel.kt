package com.droperdev.realm_with_coroutines.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droperdev.realm_with_coroutines.data.local.entity.Book
import com.droperdev.realm_with_coroutines.data.local.entity.Person
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonViewModel : ViewModel() {
    private var _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> = _persons

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _count = MutableLiveData<Long>()
    val count: LiveData<Long> = _count


    fun getCount() {
        viewModelScope.launch {
            val result: Long = withContext(Dispatchers.IO) {
                count()
            }
            _count.value = result
        }
    }


    fun get() {
        viewModelScope.launch {
            val result: List<Person> = withContext(Dispatchers.IO) {
                getPersons()
            }
            _persons.value = result
        }
    }

    fun insert() {
        _loading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                insertPerson()
            }
            _loading.value = false
        }

    }

    private suspend fun insertPerson() {
        delay(1)
        val realm1 = Realm.getDefaultInstance()
        realm1.executeTransaction { rm ->
            (1..1000000).forEach {
                val person = Person(
                    it,
                    "Pepito",
                    "Fierro",
                    transformModelsToRealm(listOf(Book(1, "Prueba", "Prueba")))
                )
                rm.insert(person)
            }
        }
        realm1.close()
    }

    private suspend fun count(): Long {
        delay(1)
        var count: Long = 0
        val realm2 = Realm.getDefaultInstance()
        realm2.executeTransaction {
            count = it.where(Person::class.java).count()
        }
        realm2.close()
        return count
    }

    private suspend fun getPersons(): List<Person> {
        delay(1)
        var persons: List<Person> = listOf()
        val realm2 = Realm.getDefaultInstance()
        realm2.executeTransaction {
            persons = it.copyFromRealm(it.where(Person::class.java).findAll())
        }
        realm2.close()
        return persons
    }

    private fun <T> transformModelsToRealm(models: List<T>): RealmList<T> {
        val beans = RealmList<T>()
        models.forEach { beans.add(it) }
        return beans
    }
}