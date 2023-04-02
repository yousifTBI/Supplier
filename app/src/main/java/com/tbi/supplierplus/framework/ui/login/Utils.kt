package com.tbi.supplierplus.framework.ui.login
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.tbi.supplierplus.business.utils.showHelperDialog
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.net.InetAddress

fun isInternetAvailable(): Boolean {
    return try {
        val ipAddr: InetAddress = InetAddress.getByName("google.com")
        //You can replace it with your name
        !ipAddr.equals("")
    } catch (e: Exception) {
        false
    }
}
inline fun < RequestType> wrapWithFlowApi(
    crossinline  fetch: suspend () -> Deferred<RequestType>, mContext: Context
  // crossinline saveFetchResult: suspend (RequestType) -> Unit,

    ) = flow {
    try {
                if (isInternetAvailable()) {
                  //  createSimpleOkDialog(mContext,"loddd","")
                    //showHelperDialog("",mContext,)
                    emit(State.Loading)
                    kotlin.runCatching {
                        fetch.invoke().await()
                    }.onFailure {
                        emit(State.Error(it.toString()))
                       // createSimpleOkDialog(mContext,"failre","")
                    }.onSuccess {
                         emit(State.Success(it))
                      //  createSimpleOkDialog(mContext,"failre","")!!.dismiss()

                    }

                } else {
                    emit(State.Error("لايوجد اتصال بالانترنت "))
                   // createSimpleOkDialog(mContext,"لايوجد اتصال بالانترنت ","")!!.dismiss()
                }
            } catch (e: Exception) {
       // createSimpleOkDialog(mContext,"لايوجد اتصال بالانترنت ","")!!.dismiss()
        emit(State.Error("لايوجد اتصال بالانترنت "))


            }

}.flowOn(Dispatchers.IO)

//
inline fun <RequestType> wrapWithFlow( crossinline fetch: suspend () -> Deferred<RequestType>): Flow<State<RequestType>> {
       return flow {

          if (isInternetAvailable()){

              emit(State.Loading)
              try {

              //    emit(State.Success( fetch.await()))
              } catch (ex: Exception) {
                  emit(State.Error( ex.message.toString()))
                 // Log.d("ssssssssssssss", ex.message + "aaaaaaaaaa")

              }
          }else{
              emit(State.Error( "الاتصال ب الانترنت ضعيف"))
          }
       }

   }
fun createSimpleOkDialog(context: Context?, title: String?, message: String?): Dialog? {
    val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context!!.applicationContext)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(android.R.string.ok, null)
    return alertDialog.create()
}