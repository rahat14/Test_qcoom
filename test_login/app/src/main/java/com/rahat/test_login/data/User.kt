package com.rahat.test_login.data

import java.io.Serializable

data class User(
    var id: String,
    var username: String,
    var email: String,
    var password: String
) : Serializable {

    constructor() : this("", "", "", "")
}
