package com.saharapps.cooklog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform