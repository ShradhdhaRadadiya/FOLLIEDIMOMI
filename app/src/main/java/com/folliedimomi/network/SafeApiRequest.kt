package com.folliedimomi.network

import android.util.Log
import com.folliedimomi.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

@Suppress("CAST_NEVER_SUCCEEDS")
abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        Log.e("RESPONSE","RESPONSE 1 ---> ${ response}")

        if(response.isSuccessful){
            Log.e("RESPONSE","RESPONSE IS ---> ${ response.body()!!}")
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()

            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch(e: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

/*    suspend fun<T: Any> apiRequests(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        call.let { call }
        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()

            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch(e: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }*/
    /*suspend fun<T: String> cancel(call: suspend () -> Response<String>) :String {

        if (call != null){
            call.cancel();
        }
    }*/
}