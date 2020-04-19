package com.project.daksatest.module

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.daksatest.App
import com.project.daksatest.R
import com.project.daksatest.activity.main.MainPresenter
import com.project.daksatest.activity.main.MainView
import com.project.daksatest.extension.BottomNavigationViewHelper
import com.project.daksatest.extension.GlobalVariable
import com.project.daksatest.extension.Tools.addFragment
import com.project.daksatest.extension.Tools.callFragment
import com.project.daksatest.module.account.AccountFragment
import com.project.daksatest.module.base.BaseActivity
import com.project.daksatest.module.transfer.TransferFragment
import com.project.daksatest.module.main.HomeFragment
import com.project.daksatest.module.history.HistoryFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_card_view_search_bar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: MainPresenter

    private var isNavigationHide = false
    private var isSearchBarHide = false

    var fragmentManager = supportFragmentManager
    lateinit var fragment: Fragment
    lateinit var badgeMessage : View
    lateinit var badgeBooking : View
    lateinit var badgeNotification : View

    private lateinit var bottomSheetBehavior: BottomSheetDialog
    public lateinit var viewBottom : View


    var STATE_PAGE = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        App.component.inject(this)

        if (savedInstanceState == null) {
            fragment = HomeFragment()
            addFragment(fragment,fragmentManager,R.id.frame_container_main, GlobalVariable.PageHome.toDescription())
            STATE_PAGE = GlobalVariable.PageHome.toDescription()
        }

        initBottomSheet()

        initBottomNav()
        initNotification()
        initScrollContentEvent()

        onAttach()


    }

    private fun initBottomSheet(){
        viewBottom = this.getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);

        bottomSheetBehavior = BottomSheetDialog(this)
        bottomSheetBehavior.setContentView(viewBottom)

    }

    fun onShowingBottomSheet(bottomState:Int=GlobalVariable.VariableStatic.BOTTOM_SHEET_MYBOOKING){

        when(bottomState){
            GlobalVariable.VariableStatic.BOTTOM_SHEET_MYBOOKING ->{
                viewBottom.findViewById<LinearLayout>(R.id.layout_mybooking).visibility = View.VISIBLE
                viewBottom.findViewById<LinearLayout>(R.id.layout_traveler_booking).visibility = View.GONE
                viewBottom.findViewById<TextView>(R.id.tv_pending_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_accepted_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_rejected_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_paid_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_finished_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_canceled_booking).visibility = View.VISIBLE
            }
            GlobalVariable.VariableStatic.BOTTOM_SHEET_TRAVELER_BOOKING ->{
                viewBottom.findViewById<LinearLayout>(R.id.layout_mybooking).visibility = View.GONE
                viewBottom.findViewById<LinearLayout>(R.id.layout_traveler_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_pending_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_accepted_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_rejected_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_paid_booking).visibility = View.GONE
                viewBottom.findViewById<TextView>(R.id.tv_finished_booking).visibility = View.VISIBLE
                viewBottom.findViewById<TextView>(R.id.tv_canceled_booking).visibility = View.GONE
            }
        }

        showBottomSheet()
    }

    fun showBottomSheet() {
        bottomSheetBehavior.show()
    }

    fun hideBottomSheet() {
        bottomSheetBehavior.hide()
    }

    private fun initScrollContentEvent(){


        nested_scroll_main.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

            if(scrollY <= 0){ //when in top
                layout_appbar_home.elevation = 0F
            }else{ //down
                layout_appbar_home.elevation = 10F
            }


        }

    }

    fun initNotification(){
        badgeNotification = findViewById<ImageView>(R.id.iv_badge_notification)
//        badgeNotification.visibility = View.INVISIBLE
    }



    private fun initBottomNav(){

        navigation.selectedItemId = R.id.navigation_home
        tv_title_bar_main.visibility = View.VISIBLE
        tv_title_bar_main.text = "MOCKVA MOBILE"
        search_bar.visibility = View.GONE
        search_bar_main.visibility = View.VISIBLE

        val bottomNavigationMenuView = navigation.getChildAt(0) as BottomNavigationMenuView
        val vMessage = bottomNavigationMenuView.getChildAt(2)
        val itemViewMessage = vMessage as BottomNavigationItemView

        badgeMessage = LayoutInflater.from(this)
            .inflate(R.layout.badge_item_action, bottomNavigationMenuView, false)

        itemViewMessage.addView(badgeMessage)

        val vBooking = bottomNavigationMenuView.getChildAt(1)
        val itemViewBooking = vBooking as BottomNavigationItemView

        badgeBooking = LayoutInflater.from(this)
            .inflate(R.layout.badge_item_action, bottomNavigationMenuView, false)

        itemViewBooking.addView(badgeBooking)

        BottomNavigationViewHelper.removeShiftMode(navigation);

//        badgeBooking.visibility = View.INVISIBLE

        navigation.setOnNavigationItemSelectedListener(this);



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.navigation_home -> {
                callFragment(HomeFragment.newInstance(),fragmentManager,R.id.frame_container_main,GlobalVariable.PageHome.toDescription())
                tv_title_bar_main.visibility = View.VISIBLE
                tv_title_bar_main.text = "MOCKVA MOBILE"
                search_bar.visibility = View.GONE
                search_bar_main.visibility = View.VISIBLE
                STATE_PAGE = GlobalVariable.PageHome.toDescription()

            }
            R.id.navigation_booking -> {
                callFragment(TransferFragment.newInstance(),fragmentManager,R.id.frame_container_main,GlobalVariable.PageBooking.toDescription())
                tv_title_bar_main.visibility = View.VISIBLE
                tv_title_bar_main.text = "TRANSFER"
                search_bar.visibility = View.GONE
                search_bar_main.visibility = View.VISIBLE
                STATE_PAGE = GlobalVariable.PageBooking.toDescription()

            }
            R.id.navigation_message -> {
                callFragment(HistoryFragment.newInstance(),fragmentManager,R.id.frame_container_main,GlobalVariable.PageMesssage.toDescription())
                tv_title_bar_main.visibility = View.VISIBLE
                tv_title_bar_main.text = "HISTORY"
                search_bar.visibility = View.GONE
                search_bar_main.visibility = View.VISIBLE
                STATE_PAGE = GlobalVariable.PageMesssage.toDescription()
            }
            R.id.navigation_account -> {
                callFragment(AccountFragment.newInstance(),fragmentManager,R.id.frame_container_main,GlobalVariable.PageAccount.toDescription())
                tv_title_bar_main.visibility = View.VISIBLE
                tv_title_bar_main.text = "ACCOUNT"
                search_bar.visibility = View.GONE
                search_bar_main.visibility = View.VISIBLE
                STATE_PAGE = GlobalVariable.PageAccount.toDescription()
            }
        }

        return true
    }



    override fun onAttach() {

        presenter.onAttach(this)

    }


    public fun animateNavigation(hide: Boolean) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return
        isNavigationHide = hide
        val moveY = if (hide) 2 * navigation.height else 0
        navigation.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()


    }


    public fun animateAppbarMain(hide: Boolean) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return
        isSearchBarHide = hide
        val moveY = if (hide) -(2 * search_bar_main.height) else 0
        search_bar_main.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        bottomSheetBehavior.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetBehavior.dismiss()
    }

    override fun onDetach() {
    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
