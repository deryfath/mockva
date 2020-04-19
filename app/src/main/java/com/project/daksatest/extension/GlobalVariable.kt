package com.project.daksatest.extension

/**
 * Created by Pazto
 * on 02/04/2019.
 */

enum class GlobalVariable(
    private val description: String
) : IEnum {
    Token("token"),
    SessionId("_sessionId"),
    AccountId("id"),
    VariableStatic("var_static"),
    PageHome("page_home"),
    PageBooking("page_booking"),
    PageMesssage("page_message"),
    PageAccount("page_account"),
    RequestCode("request_code"),
    LocalHelper("local_helper"),
    InLang("in"),
    DefaultLang("def");

    override fun toDescription(): String {
        return description
    }

    val REQUEST_CAMERA = 200
    val REQUEST_CROP_IMAGE = 222

    val REQUEST_EXIT_INTRO_LOGIN = 1000
    val REQUEST_EXIT_ITINERARY = 1002
    val REQUEST_EXIT_BOOKING_DETAIL = 1003
    val REQUEST_EXIT_TRANSFER_BALANCE = 1004

    val BOTTOM_SHEET_MYBOOKING = 1
    val BOTTOM_SHEET_TRAVELER_BOOKING = 2

    val DETAIL_MYBOOKING = 1
    val DETAIL_TRAVELER_BOOKING = 2

    val PENDING_STATE = 1
    val ACCEPT_STATE = 2
    val REJECT_STATE = 3
    val PAID_STATE = 4
    val FINISH_STATE = 5
    val CANCEL_STATE = 6

}
