package com.metel20.data.core

interface LocalStorage {

    interface Save {
        fun save(key: String, value: String)
    }

    interface Read {
        fun read(key: String, default: String): String
    }

    interface Mutable : Save, Read

}