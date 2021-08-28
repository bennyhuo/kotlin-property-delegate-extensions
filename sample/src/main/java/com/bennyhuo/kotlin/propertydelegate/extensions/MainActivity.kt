package com.bennyhuo.kotlin.propertydelegate.extensions.sample

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("kotlin-property-delegate-extensions")

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
