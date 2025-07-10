package com.paykidscompose.data.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

// 토큰이 만료되면 갱신 해주기 위함.

class NetworkAuthenticator: Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        route.run {
            response.request.newBuilder()
            if (response.code == 401) {

            }
            return null
        }
    }

}