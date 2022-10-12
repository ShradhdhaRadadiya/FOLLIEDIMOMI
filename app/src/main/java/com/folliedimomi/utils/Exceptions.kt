package com.folliedimomi.utils

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MediatorLiveData
//import androidx.lifecycle.Observer
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)

inline fun <T> dependantObservableField(
    vararg dependencies: Observable,
    crossinline mapper: () -> T?
) =
    object : ObservableField<T>(*dependencies) {
        override fun get(): T? {
            return mapper()
        }
    }

inline fun <T> dependantLiveData(vararg dependencies: LiveData<*>, crossinline mapper: () -> T?) =
    MediatorLiveData<T>().also { mediatorLiveData ->
        val observer = Observer<Any> { mediatorLiveData.value = mapper() }
        dependencies.forEach { dependencyLiveData ->
            mediatorLiveData.addSource(dependencyLiveData, observer)
        }
    }


inline fun <reified T : Observable> T.observe(crossinline observer: (T) -> Unit): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            observer(sender as T)
        }
    }.also { addOnPropertyChangedCallback(it) }


//Exception handlor
/*fun (() -> Unit).catch(vararg exceptions: KClass<out Throwable>, catchBlock: (Throwable) -> Unit) {
    try {
        this()
    } catch (e: Throwable) {
        if (e::class in exceptions) catchBlock(e) else throw e
    }
}*/

//Use
/* {
                val countryResponse: CountryRespnse = repository.getCountry()
                requireActivity().progress_bars_layout.hide()
                if (isAdded && isVisible) {
                    countryResponse.let {
                        if (countryResponse.status == 1) Constant.countryList = countryResponse.result
                        else Constant.countryList = emptyList()
                        //return@main
                    }
                }
            }.catch(ApiException::class, JsonSyntaxException::class,NoInternetException::class, IOException::class){
                onException(it.message.toString())
            }*/