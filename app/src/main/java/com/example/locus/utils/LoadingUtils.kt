package com.example.mentalhealthpatient.ui.view.commonviews.classes

import ProgressLoaderClass
import android.content.Context


open class LoadingUtils {

    companion object {
        private var loader: ProgressLoaderClass? = null
        fun showDialog(
            context: Context?,
            isCancelable: Boolean
        ) {
            hideDialog()
            if (context != null) {
                try {
                    loader = ProgressLoaderClass(context)
                    loader?.let { jarvisLoader->
                        jarvisLoader.setCanceledOnTouchOutside(true)
                        jarvisLoader.setCancelable(isCancelable)
                        jarvisLoader.show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun hideDialog() {
            if (loader!=null && loader?.isShowing!!) {
                loader = try {
                    loader?.dismiss()
                    null
                } catch (e: Exception) {
                    null
                }
            }
        }

    }


}
