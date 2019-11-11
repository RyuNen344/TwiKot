package com.ryunen344.twikot.accountlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ryunen344.twikot.IOState
import com.ryunen344.twikot.R
import com.ryunen344.twikot.databinding.FragmentAccountListBinding
import com.ryunen344.twikot.errorMessage
import com.ryunen344.twikot.home.HomeActivity
import com.ryunen344.twikot.util.LogUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.android.viewmodel.ext.android.viewModel


class AccountListFragment : Fragment() {

    private lateinit var binding : FragmentAccountListBinding
    private val accountListViewModel : AccountListViewModel by viewModel()

    override fun onCreateView(
            inflater : LayoutInflater,
            container : ViewGroup?,
            savedInstanceState : Bundle?
    ) : View? {
        LogUtil.d()

        binding = DataBindingUtil.inflate<FragmentAccountListBinding>(
                inflater,
                R.layout.fragment_account_list,
                container,
                false
        ).also {
            it.lifecycleOwner = this@AccountListFragment.viewLifecycleOwner
            (binding.accountList.adapter as AccountListAdapter).lifecycleOwner = this@AccountListFragment.viewLifecycleOwner
            it.viewModel = accountListViewModel
        }

        binding.fabAddAccount.setOnClickListener {
            LogUtil.d()
            accountListViewModel.generateOAuthRequestUri(getString(R.string.consumer_key), getString(R.string.consumer_secret_key))
        }

        (binding.accountList.adapter as AccountListAdapter).clickEvent.subscribe(
                {
                    LogUtil.d(it)
                    //showAccountHome(it)
                },
                {
                    LogUtil.d(it)
                }
        ).addTo(CompositeDisposable())

        (binding.accountList.adapter as AccountListAdapter).clickedUserId.observe(this@AccountListFragment.viewLifecycleOwner, Observer {
            if (it != null) {
                LogUtil.d()
                //showAccountHome(it)
            }
        })

        accountListViewModel.ioState.observe(this@AccountListFragment.viewLifecycleOwner, Observer { ioState ->
            when (ioState) {
                is IOState.NOPE -> return@Observer
                is IOState.LOADING -> LogUtil.d("loading")
                is IOState.LOADED -> LogUtil.d("load finish, hide seek bar")
                is IOState.ERROR -> {
                    LogUtil.d(ioState.error.createMessage { errorMessage(it) })
                    Toast.makeText(
                            context,
                            ioState.error.createMessage { errorMessage(it) },
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        accountListViewModel.oAuthRequestUri.observe(this@AccountListFragment.viewLifecycleOwner, Observer { uri ->
            if (uri != null) {
                showCallback(uri)
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        accountListViewModel.loadAccountList()
    }

    override fun onDestroy() {
        super.onDestroy()
        accountListViewModel.onDestroy()
    }

    private fun showCallback(uri : Uri?) {
        LogUtil.d()
        startActivityForResult(Intent(Intent.ACTION_VIEW, uri), 0)
    }

    private fun showAccountHome(userId : Long) {
        LogUtil.d()
        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra(AccountListActivity.INTENT_KEY_USER_ID, userId)
        startActivity(intent)
    }
}