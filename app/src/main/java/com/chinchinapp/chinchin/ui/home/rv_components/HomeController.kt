package com.chinchinapp.chinchin.ui.home.rv_components

import com.airbnb.epoxy.EpoxyController
import com.chinchinapp.chinchin.app.models.CryptosItem
import com.chinchinapp.chinchin.crypto

class HomeController(
    private var listCrypto: List<CryptosItem?>?,
    private val listener: CallbacksListener
) : EpoxyController() {

    override fun buildModels() {
        if (listCrypto == null) return

        listCrypto!!.forEach {
            crypto {
                id(it!!.uid)
                item(it)
                clickListener { _, _, _, _ ->
                    listener.onClickItem(it)
                }
            }
        }
    }

    interface CallbacksListener {
        fun onClickItem(item: CryptosItem)
    }
}