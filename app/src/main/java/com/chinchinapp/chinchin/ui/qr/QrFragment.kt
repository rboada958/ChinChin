package com.chinchinapp.chinchin.ui.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.app.models.DataItem
import com.chinchinapp.chinchin.databinding.FragmentHomeBinding
import com.chinchinapp.chinchin.databinding.FragmentQrBinding
import com.chinchinapp.chinchin.ui.home.mvvm.HomeViewModel
import com.chinchinapp.chinchin.ui.home.rv_components.HomeController
import com.chinchinapp.chinchin.ui.qr.mvvm.QrViewModel
import com.chinchinapp.chinchin.ui.qr.rv_components.QrController
import com.chinchinapp.chinchin.utils.Commons
import com.chinchinapp.chinchin.utils.base.BaseFragment
import com.chinchinapp.chinchin.utils.manager.PreferenceManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_qr.*
import net.glxn.qrgen.android.QRCode

class QrFragment : BaseFragment(), QrController.CallbacksListener {

    lateinit var binding: FragmentQrBinding
    private val viewModel by activityViewModels<QrViewModel>()
    private var controller: QrController? = null
    private var coin = ""
    private var amount = 0.0F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            binding = FragmentQrBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@QrFragment
            }
            fragmentView = binding.root
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        if (PreferenceManager.getInstance().getCryptos()) {
            coin = "api"
            amount = 0.0F
        } else {
            coin = requireArguments().getString("coin")!!
            amount = requireArguments().getFloat("amount")
        }

        viewModel.exchageRate(amount, coin)

        viewModel.mutableErrorType.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { errorType ->
                showErrorMessage(errorType)
            }
        })

        viewModel.qrData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                qr_data.setImageBitmap(
                    QRCode
                        .from(it)
                        .withColor(0xFFFFFFFF.toInt(), 0x0D1D2C)
                        .withSize(500, 500)
                        .bitmap()
                )
            }
        })

        viewModel.cryptoAll.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                controller = QrController(it, this)
                rv_crypto_all.setControllerAndBuildModels(controller!!)
            }
        })
    }

    override fun onClickItem(item: DataItem) {
        Commons.showToast(item.qn!!)
    }
}