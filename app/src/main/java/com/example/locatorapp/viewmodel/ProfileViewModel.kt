package com.example.locatorapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.locatorapp.ProfileApplication
import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.model.ResponseBean
import com.example.locatorapp.repository.ProfileRepository
import com.example.locatorapp.util.Resource
import retrofit2.Response

class ProfileViewModel(val repository: ProfileRepository, application: Application) :
    AndroidViewModel(application) {

    val profileRepository: MutableLiveData<Resource<ResponseBean>> = MutableLiveData()
    var responseBean: ResponseBean? = null

    suspend fun saveProfile(requestBean: RequestBean) {
        profileRepository.postValue(Resource.Loading())
        if (hasInternetConnection()) {
            val response = repository.saveContent(requestBean)
            profileRepository.postValue(handleSaveProfile(response))
        }
    }

    fun handleSaveProfile(response: Response<ResponseBean>): Resource<ResponseBean> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (responseBean == null) {
                    responseBean = result
                } else {
                    var oldData = responseBean
                    val newData = result
                    oldData = newData
                }
                return Resource.Success(responseBean ?: result)
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