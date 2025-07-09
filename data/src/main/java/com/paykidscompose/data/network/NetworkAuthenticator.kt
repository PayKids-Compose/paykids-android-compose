package com.paykidscompose.data.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class NetworkAuthenticator: Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        with(route) {
            response.request.newBuilder()
            if (response.code == 401) {

            }
            return null
        }
    }

}