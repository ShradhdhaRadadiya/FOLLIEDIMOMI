package com.folliedimomi.fragment


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.loadFragment
import com.folliedimomi.model.CommonResponse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.fragment_delete_acc.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException


class DeleteAccFragment : Fragment(R.layout.fragment_coupon), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDelete.text = resources.getString(
            R.string.in_conformia
        )

        btnDelete.setOnClickListener {
            openDeleteConformationDialoge()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context


    }


    private fun openDeleteConformationDialoge() {
        AlertDialog.Builder(mContext)
            .setCancelable(true)
            .setMessage("Sei sicuro di voler cancellare i tuoi dati?") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Si ",
                DialogInterface.OnClickListener { dialog, which ->
                    // Continue with delete operation
                    doRequestForDeleteAcc()

                    dialog.cancel()
                })
            .setNegativeButton("No ", null)
            .show()
    }


    private fun doRequestForDeleteAcc() {
        Globals.showProgress(mContext)

        Coroutines.main {
            try {
                val addressResponse: CommonResponse =
                    repository.deleteAcc(customerId = session.getUserId().toString())

                addressResponse.let {

                    finishAffinity(requireActivity())
                    requireActivity().loadFragment(DashboardFragment("Home"))
                    Globals.hideProgress()
                    //hide
                    return@main
                }
            } catch (e: ApiException) {
                //hide
                Globals.hideProgress()

                requireActivity().toast(e.message!!)
            } catch (e: NoInternetException) {
                //hide
                Globals.hideProgress()

                requireActivity().toast(e.message!!)
            } catch (e: JsonSyntaxException) {
                //hide
                Globals.hideProgress()

                requireActivity().toast(e.message!!)
            } catch (e: IOException) {
                //hide
                Globals.hideProgress()

                requireActivity().toast(e.message!!)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete_acc, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val item: MenuItem = menu!!.findItem(R.id.action_cart)
        val search: MenuItem = menu!!.findItem(R.id.action_search)
        item.isVisible = false
        search.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}
