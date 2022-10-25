package com.folliedimomi.activity


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.folliedimomi.R
import com.folliedimomi._app.BadgeDrawable
import com.folliedimomi._app.getRandomString
import com.folliedimomi._app.loadFragmentWithoutBackStack
import com.folliedimomi._observer.MyObserver
import com.folliedimomi.fragment.DashboardFragment
import com.folliedimomi.fragment.HomeFragment
import com.folliedimomi.fragment.ShoppingCartFragment
import com.folliedimomi.interfaces.IOnBackPressed
import com.folliedimomi.interfaces.ShoppingCartUpdateListener
import com.folliedimomi.model.DrawerMenuModel
import com.folliedimomi.model.Product
import com.folliedimomi.model.ShoppingCartResponse
import com.folliedimomi.model.ShoppingCartResult
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.folliedimomi.utils.Globals.drawerCatId
import com.google.android.material.navigation.NavigationView
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.IOException


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    FragmentManager.OnBackStackChangedListener,
    DashboardFragment.OnFragmentInteractionListener,
    ShoppingCartUpdateListener,
    KodeinAware {

    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()

    private var toggle: ActionBarDrawerToggle? = null
    private lateinit var bold: Typeface
    private lateinit var regular: Typeface
    private lateinit var llUserHeader: RelativeLayout
    private lateinit var tvDrawerUser: TextView
    private lateinit var tvDrawerEmail: TextView

    private lateinit var imgUserProfile: ImageView
    private var drawerListData = arrayListOf<DrawerMenuModel.Result>()

    var drawerCatText = "Home"


    override fun onCreate(savedInstanceState: Bundle?) {
        //forceRtlIfSupported() //Right to Left transaction
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mActivity = this
        lifecycle.addObserver(MyObserver()) //LifeCycle Observer from JetPack


        callAdvanceFilterApiCall()

        supportFragmentManager.addOnBackStackChangedListener(this)
        setSupportActionBar(toolbar)
        setTitle(R.string.app_name)

        bold = Typeface.createFromAsset(assets, "fonts/Montserrat-SemiBold.ttf")
        regular = Typeface.createFromAsset(assets, "fonts/Montserrat-Regular.ttf")

        if (session.getAppSession()!!.isEmpty()) session.setAppSession(String().getRandomString(14))

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle!!.setToolbarNavigationClickListener { onBackPressed() }
        drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        navView.setNavigationItemSelectedListener(this)

        imgLogo.setOnClickListener {
            this.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }


        //imgUserProfile = navView.getHeaderView(0).findViewById(R.id.img_drawer_user)
        llUserHeader = navView.getHeaderView(0).findViewById(R.id.llUserHeader)
        tvDrawerUser = navView.getHeaderView(0).findViewById(R.id.tvDrawerUsername)
        tvDrawerEmail = navView.getHeaderView(0).findViewById(R.id.tvDrawerEmail)
        tvDrawerUser.typeface = bold
        tvDrawerEmail.typeface = regular


        /*   navView.menu.findItem(R.id.nav_faq).setActionView(R.layout.menu_right_arrow)
           navView.menu.findItem(R.id.nav_condition_subscription)
               .setActionView(R.layout.menu_right_arrow)
           navView.menu.findItem(R.id.nav_news_latter).setActionView(R.layout.menu_right_arrow)
           navView.menu.findItem(R.id.nav_about_us).setActionView(R.layout.menu_right_arrow)
           navView.menu.findItem(R.id.nav_term_condition).setActionView(R.layout.menu_right_arrow)
   */

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        this.let {
            if (Session(it).isServerLoggedIn()) {
                llUserHeader.visibility = View.VISIBLE
                tvDrawerUser.text = Session(it).getUserName()
                tvDrawerEmail.text = Session(it).getUserEmail()
            } else llUserHeader.visibility = View.GONE
        }

        /*Get shopping cart data*/
        getShoppingCart(session.getUserId().toString(), session.getAppSession().toString())

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, HomeFragment()).commit()

        //imgClockGif
        Glide.with(applicationContext)
            .asGif()
            .load(R.drawable.ic_launcher_background)
            .into(imgClockGif)

//        app:menu="@menu/activity_main_drawer"
    }


    private fun callAdvanceFilterApiCall() {
        //show
        this.progress_bars_layout.show()
        Coroutines.main {
            try {
                var drawerData = repository.getDrawerMenu()

                drawerData.result.let {
                    if (drawerData.status == 1) {
                        drawerListData = drawerData.result as ArrayList<DrawerMenuModel.Result>
                        val menu = navView.menu
                        for (item in drawerData.result) {
                            if (item.submenu?.isNotEmpty() == true) {
                                menu.add(item.title)
                                val submenu: Menu = menu
                                for (itemD in item.submenu!!) {
                                    submenu.add("     " + itemD.title)
                                }
                            } else {
                                menu.add(item.title)
                            }

                        }
                        navView.invalidate()
                    }
                    //hide
                    this.progress_bars_layout.hide()
                    return@main
                }

            } catch (e: ApiException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()

                toast(e.message!!)
            } catch (e: NoInternetException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()
                toast(e.message!!)
            } catch (e: JsonSyntaxException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()

                toast(e.message!!)
            } catch (e: IOException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()

                toast(e.message!!)
            }
        }
    }


    private fun openBrowser(webUrl: String) {
        var url = webUrl
        if (!url.startsWith("https://") && !url.startsWith("http://")) url = "http://$url"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1) //call this before super.onCreate
    private fun forceRtlIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }

    fun setTitle(title: String?) {
        toolbar.setTitleTextAppearance(this, R.style.font)
        toolbar.title = title
    }

    fun onShowProgress() {
        progress_bars_layout.show()
    }

    fun onHideProgress() {
        progress_bars_layout.hide()
    }

    fun showError(msg: String) {
        appBarError.visibility = View.VISIBLE
        appBarError.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        tvError.text = msg
        tvError.setTextColor(ContextCompat.getColor(this, R.color.white))
        tvError.visibility = View.VISIBLE
        Handler().postDelayed({
            if (tvError != null) {
                tvError.visibility = View.GONE
                appBarError.visibility = View.GONE
            }
        }, 2000)
    }

    fun showSuccess(msg: String) {
        appBarError.visibility = View.VISIBLE
        appBarError.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        tvError.text = msg
        tvError.setTextColor(ContextCompat.getColor(this, R.color.black))
        tvError.visibility = View.VISIBLE
        Handler().postDelayed({
            if (tvError != null) {
                tvError.visibility = View.GONE
                appBarError.visibility = View.GONE
            }
        }, 2000)
    }

    fun loadFragmentMain(fragment: Fragment, title: String = "") {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
            .addToBackStack(fragment::class.java.simpleName).commit()
        if (title.isNotEmpty()) setTitle(title)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {

            val fragment = this.supportFragmentManager.findFragmentById(R.id.container)
            (fragment as? IOnBackPressed)?.onBackPressed().let {
                //super.onBackPressed()
            }

            if (supportFragmentManager.backStackEntryCount > 0) run {
                supportFragmentManager.popBackStackImmediate()
                if (supportFragmentManager.backStackEntryCount == 0) {
                    setTitle(resources.getString(R.string.app_name))
                }
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Chiudi l'app")//Close App
                    .setMessage("Sei sicuro di voler chiudere l'app?")//Are you sure you want to close the app?
                    .setPositiveButton("Si") { _, _ ->
                        //Constant.isRunning = false
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    override fun onBackStackChanged() {
        toggle!!.isDrawerIndicatorEnabled = supportFragmentManager.backStackEntryCount == 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
        toggle!!.syncState()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle!!.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val item: MenuItem = menu.findItem(R.id.action_cart)
        icon = item.icon as LayerDrawable
        setBadgeCount(applicationContext, icon!!, cartCount)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                loadFragmentMain(ShoppingCartFragment(), getString(R.string.shopping_cart))
                true
            }
            R.id.action_search -> {
                showSuccess("Coming soon")
                //loadFragment(FilterFragment())
                //setTitle("Search")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        Log.e("TAG", "navigation selected item --> $item")
        Log.e("TAG", "navigation selected item --> $drawerListData")

        drawerCatText = ""
        for (i in drawerListData) {
            if (i.submenu != null && i.submenu!!.isNotEmpty()) {
                for (j in i.submenu!!) {
                    if (j.title == item.toString().trim()) {
                        drawerCatText = i.title + " > "
                        drawerCatText += j.title
                        drawerCatId = j.id
                    }
                }

            } else {
                if (i.title == item.toString().trim()) {
                    drawerCatText = i.title
                    drawerCatId = i.id
                }
            }
        }

        if(drawerCatId == 1){
            drawerCatId = 12
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        loadFragmentWithoutBackStack(DashboardFragment(drawerCatText))
        return true
    }

    private fun loadLoginScreen() {
        val intent: Intent? = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun updateCount(context: Context, count: Int) {
        setBadgeCount(context, icon!!, count)
    }

    fun setBadgeCount(context: Context, icon: LayerDrawable, count: Int) {
        val badge: BadgeDrawable
        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
        if (reuse != null && reuse is BadgeDrawable) {
            badge = reuse
        } else {
            badge = BadgeDrawable(context)
        }

        if (count < 0) {
            icon.setVisible(false, false)
        } else {
            icon.setVisible(true, true)
            badge.setCount(count)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_badge, badge)
        }
        // this.invalidateOptionsMenu()
    }

    private fun getShoppingCart(userId: String, key: String) {
        Coroutines.main {
            try {
                val shoppingCartResponse: ShoppingCartResponse =
                    repository.getShoppingCart(userId, key) as ShoppingCartResponse
                shoppingCartResponse.let {
                    if (shoppingCartResponse.status == 1) {
                        val shoppingCart: ShoppingCartResult = shoppingCartResponse.result
                        shoppingCart.let {
                            val products: List<Product> = it.products
                            cartCount = if (products.isNullOrEmpty()) 0 else products.size
                            updateCount(this, cartCount)
                            invalidateOptionsMenu()
                        }
                    } else {
                        cartCount = 0
                    }
                    return@main
                }
            } catch (e: ApiException) {
            } catch (e: JsonSyntaxException) {
            } catch (e: NoInternetException) {
            } catch (e: IOException) {
            }
        }
    }

    companion object {
        var cartCount: Int = 0
        var icon: LayerDrawable? = null
       var mActivity: MainActivity?= null

    }

    override fun onShoppingCartUpdate() {
        getShoppingCart(session.getUserId().toString(), session.getAppSession().toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
