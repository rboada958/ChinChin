package com.chinchinapp.chinchin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.app.models.CryptosItem
import com.chinchinapp.chinchin.databinding.FragmentHomeBinding
import com.chinchinapp.chinchin.ui.home.mvvm.HomeViewModel
import com.chinchinapp.chinchin.ui.home.rv_components.HomeController
import com.chinchinapp.chinchin.utils.Commons
import com.chinchinapp.chinchin.utils.base.BaseFragment
import com.chinchinapp.chinchin.utils.manager.PreferenceManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), HomeController.CallbacksListener {

    lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<HomeViewModel>()
    private var controller: HomeController? = null

    private val listCyrptos = listOf(
        CryptosItem("BS", "Bolivares", Commons.getDrawable(R.drawable.ic_bs)),
        CryptosItem("BTC", "Bitcoin", Commons.getDrawable(R.drawable.ic_btc)),
        CryptosItem("USD", "Dolar", Commons.getDrawable(R.drawable.ic_dolar)),
        CryptosItem("EUR", "Euro", Commons.getDrawable(R.drawable.ic_euro)),
        CryptosItem("ETH", "Etherum", Commons.getDrawable(R.drawable.ic_eth)),
        CryptosItem("PTR", "Petro", Commons.getDrawable(R.drawable.ic_petro))
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@HomeFragment
            }
            fragmentView = binding.root
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        controller = HomeController(listCyrptos, this)
        rv_crypto.layoutManager = GridLayoutManager(context, 2)
        rv_crypto.setControllerAndBuildModels(controller!!)
    }

    override fun onClickItem(item: CryptosItem) {
        PreferenceManager.getInstance().setCryptos(false)
        viewModel.amount.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val args = Bundle()
                args.putFloat("amount", it)
                args.putString("coin", item.uid)

                findNavController().navigate(
                    R.id.action_nav_home_to_nav_qr,
                    args,
                    getDefaultNavOptions()
                )
            } else {
                showSnackError("Enter amount")
            }
        })
    }
}