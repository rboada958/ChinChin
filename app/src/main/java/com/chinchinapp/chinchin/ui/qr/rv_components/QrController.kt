package com.chinchinapp.chinchin.ui.qr.rv_components

import com.airbnb.epoxy.EpoxyController
import com.chinchinapp.chinchin.app.models.DataItem
import com.chinchinapp.chinchin.cryptoAll

class QrController(
    private var listCrypto: List<DataItem?>?,
    private val listener: CallbacksListener
) : EpoxyController() {

    override fun buildModels() {
        if (listCrypto == null) return

        listCrypto!!.forEach {
            cryptoAll {
                id(it!!.an)
                item(it)
                clickListener { _, _, _, _ ->
                    listener.onClickItem(it)
                }
            }
        }
    }

    interface CallbacksListener {
        fun onClickItem(item: DataItem)
    }
}