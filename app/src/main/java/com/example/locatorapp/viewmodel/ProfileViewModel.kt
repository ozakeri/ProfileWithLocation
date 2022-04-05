package com.example.locatorapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.locatorapp.ProfileApplication
import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.model.ResponseBean
import com.example.locatorapp.repository.ProfileRepository
import com.example.locatorapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ProfileViewModel(application: Application, val repository: ProfileRepository) :
    AndroidViewModel(application) {

    val request = RequestBean(
        1, "test", "09121234567", "02188888888", "omid", "Male", "zakeri", 35.7717503, 51.3365315
    )


    val saveAddressRepose: MutableLiveData<Resource<ResponseBean>> = MutableLiveData()
    val getAddressRepose: MutableLiveData<Resource<ResponseBean>> = MutableLiveData()

    init {
        getAddressListResponse()
    }

    fun getSaveAddressResponse(requestBean: RequestBean) = viewModelScope.launch {
        saveAddress(request)
    }

    fun getAddressListResponse() = viewModelScope.launch {
        getAddressList()
    }

    suspend fun saveAddress(requestBean: RequestBean) {

        saveAddressRepose.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.saveAddress(requestBean)
                print("requestBean==" + requestBean.address)
                print("requestBean==" + requestBean.first_name)
                print("requestBean==" + requestBean.last_name)
                saveAddressRepose.postValue(handleSaveAddress(response))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> saveAddressRepose.postValue(Resource.Error("Network Failure"))
                else -> saveAddressRepose.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    suspend fun getAddressList() {
        getAddressRepose.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repository.getAddress()
                getAddressRepose.postValue(handleGetAddress(response))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> getAddressRepose.postValue(Resource.Error("Network Failure"))
                else -> getAddressRepose.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    fun handleSaveAddress(response: Response<ResponseBean>): Resource<ResponseBean> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                print("========result=======" + result)
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun handleGetAddress(response: Response<ResponseBean>): Resource<ResponseBean> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                print("========result=======" + result)
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ProfileApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}